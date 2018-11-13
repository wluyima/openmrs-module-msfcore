package org.openmrs.module.msfcore.result;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptNumeric;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.TestOrder;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.AuditLogBuilder;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.module.msfcore.result.ResultColumn.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Used to populate and handle data to from results.gsp widget for drug orders,
 * test orders and any others results widget
 */
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultsData {
    private Patient patient;
    @Builder.Default
    private List<String> keys = new ArrayList<String>();
    // helps on pagination
    @Builder.Default
    private Pagination pagination = Pagination.builder().build();
    private ResultCategory resultCategory;
    @Builder.Default
    private List<ResultRow> results = new ArrayList<ResultRow>();
    @Builder.Default
    private ResultFilters filters = ResultFilters.builder().build();
    private String dateFormatPattern;

    public void addRetrievedResults() {
        dateFormatPattern = Context.getDateFormat().toPattern();
        if (resultCategory.equals(ResultCategory.DRUG_LIST) || resultCategory.equals(ResultCategory.LAB_RESULTS)) {
            addOrders();
        }
        // TODO from config, messageName:[conceptIDs] for obs listing
    }

    private static List<String> getLocalizedKeys(List<String> keys) {
        List<String> localizedKeys = new ArrayList<String>();
        for (String key : keys) {
            localizedKeys.add(Context.getMessageSourceService().getMessage(key));
        }
        return localizedKeys;
    }

    private void addOrders() {
        OrderType orderType = null;
        if (resultCategory.equals(ResultCategory.DRUG_LIST)) {
            orderType = Context.getOrderService().getOrderTypeByUuid(OrderType.DRUG_ORDER_TYPE_UUID);
            keys.addAll(getDrugOrdersKeys());
            filters.setDates(getLocalizedKeys(Arrays.asList("msfcore.datePrescribed", "msfcore.dispenseDate")));
            filters.setName(Context.getMessageSourceService().getMessage("msfcore.drugName"));
        } else if (resultCategory.equals(ResultCategory.LAB_RESULTS)) {
            orderType = Context.getOrderService().getOrderTypeByUuid(OrderType.TEST_ORDER_TYPE_UUID);
            filters.setStatuses(Arrays.asList(ResultStatus.values()));
            filters.setDates(getLocalizedKeys(Arrays.asList("msfcore.orderDate", "msfcore.resultDate")));
            filters.setName(Context.getMessageSourceService().getMessage("msfcore.testName"));
            keys.addAll(getLabResultKeys());
        }
        List<Order> orders = Context.getService(MSFCoreService.class).getOrders(patient, orderType, null, pagination);
        for (Order o : orders) {
            if (resultCategory.equals(ResultCategory.DRUG_LIST) && o instanceof DrugOrder) {
                addDrugOrders(o);
            }
            if (resultCategory.equals(ResultCategory.LAB_RESULTS) && o instanceof TestOrder) {
                addTestOrders(o);
            }
        }
    }

    private List<String> getDrugOrdersKeys() {
        return getLocalizedKeys(Arrays.asList("msfcore.drugName", "msfcore.dose", "msfcore.frequency", "msfcore.duration",
                        "msfcore.instructions", "msfcore.datePrescribed", "msfcore.stop", "msfcore.dispensed", "msfcore.dispenseDate",
                        "msfcore.details"));
    }

    private static List<String> getLabResultKeys() {
        return getLocalizedKeys(Arrays.asList("msfcore.testName", "msfcore.result", "msfcore.uom", "msfcore.range", "msfcore.orderDate",
                        "msfcore.resultDate"));
    }

    private void addTestOrders(Order o) {
        ResultRow resultRow = new ResultRow();
        TestOrder testOrder = (TestOrder) o;
        // order concept must be a labSet with expected result concepts members
        for (Concept concept : testOrder.getConcept().getSetMembers()) {
            // TODO fix for other investigation order
            Obs resultObs = getLabTestResultObs(testOrder.getPatient(), testOrder, concept);
            resultRow.put("uuid", ResultColumn.builder().value(testOrder.getUuid()).build());
            Object status = null;
            List<ResultAction> actions = new ArrayList<ResultAction>();
            if (testOrder.getVoided()) {
                status = ResultStatus.CANCELLED;
            } else if (resultObs == null) {
                status = ResultStatus.PENDING;
                actions.add(ResultAction.EDIT);
                actions.add(ResultAction.DELETE);
            } else {
                status = ResultStatus.COMPLETED;
                actions.add(ResultAction.EDIT);
            }
            Type resultType = Type.STRING;
            if (concept.isNumeric()) {
                resultType = Type.NUMBER;
            } else if (concept.getDatatype().getUuid().equals(ConceptDatatype.BOOLEAN_UUID)) {
                resultType = Type.BOOLEAN;
            }
            resultRow.put("status", ResultColumn.builder().value(status).build());
            resultRow.put("actions", ResultColumn.builder().value(actions).build());
            resultRow.put("concept", ResultColumn.builder().value(concept.getUuid()).build());
            resultRow.put(Context.getMessageSourceService().getMessage("msfcore.testName"),
                            ResultColumn.builder().value(concept.getName().getName()).build());
            resultRow.put(Context.getMessageSourceService().getMessage("msfcore.result"),
                            resultObs != null
                                            ? ResultColumn.builder().editable(true).type(resultType)
                                                            .value(resultObs.getValueAsString(Context.getLocale())).build()
                                            : ResultColumn.builder().editable(true).type(resultType).value("").build());
            resultRow.put(Context.getMessageSourceService().getMessage("msfcore.uom"),
                            ResultColumn.builder().value(getUnit(concept)).build());
            resultRow.put(Context.getMessageSourceService().getMessage("msfcore.range"),
                            ResultColumn.builder().value(getRange(concept)).build());
            resultRow.put(Context.getMessageSourceService().getMessage("msfcore.orderDate"),
                            ResultColumn.builder().type(Type.DATE).value(testOrder.getDateActivated()).build());
            resultRow.put(Context.getMessageSourceService().getMessage("msfcore.resultDate"),
                            resultObs != null
                                            ? ResultColumn.builder().editable(true).type(Type.DATE).value(resultObs.getObsDatetime())
                                                            .build()
                                            : ResultColumn.builder().editable(true).type(Type.DATE).value("").build());
            addResultRow(resultRow);
        }

    }

    // TODO add unit test when working on drug order
    private void addDrugOrders(Order o) {
        ResultRow resultRow = new ResultRow();
        DrugOrder drugOrder = (DrugOrder) o;
        resultRow.put("uuid", ResultColumn.builder().value(drugOrder.getUuid()).build());
        resultRow.put("status", ResultColumn.builder().build());
        resultRow.put("actions", ResultColumn.builder().value(Arrays.asList()).build());
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.drugName"),
                        ResultColumn.builder().value(drugOrder.getDrug().getName()).build());
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.dose"),
                        ResultColumn.builder().value(getDrugOrderDose(drugOrder)).build());
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.frequency"), ResultColumn.builder()
                        .value(drugOrder.getFrequency() != null && drugOrder.getFrequency().getFrequencyPerDay() != null
                                        ? drugOrder.getFrequency().getFrequencyPerDay()
                                                        + Context.getMessageSourceService().getMessage("msfcore.perDayShort")
                                        : "")
                        .build());
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.duration"),
                        ResultColumn.builder().value(getDrugOrderDuration(drugOrder)).build());
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.instructions"),
                        ResultColumn.builder().value(drugOrder.getInstructions()).build());
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.datePrescribed"),
                        ResultColumn.builder().type(Type.DATE).value(drugOrder.getDateCreated()).build());
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.stop"), ResultColumn.builder().value("").build());// TODO
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.dispensed"), ResultColumn.builder().value("").build());// TODO
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.dispenseDate"),
                        ResultColumn.builder().type(Type.DATE).value(drugOrder.getDateActivated()).build());// TODO
        resultRow.put(Context.getMessageSourceService().getMessage("msfcore.details"), ResultColumn.builder().value("").build());// TODO

        addResultRow(resultRow);
    }

    private Object getDrugOrderDuration(DrugOrder drugOrder) {
        // TODO Auto-generated method stub
        String duration = "";
        if (drugOrder.getDuration() != null) {
            duration += drugOrder.getDuration();
        }
        if (drugOrder.getDurationUnits() != null) {
            duration += " " + drugOrder.getDurationUnits().getName().getName();
        }
        return duration;
    }

    private String getDrugOrderDose(DrugOrder drugOrder) {
        String dose = "";
        if (drugOrder.getDose() != null) {
            dose += drugOrder.getDose();
        }
        if (drugOrder.getDoseUnits() != null) {
            dose += " " + drugOrder.getDoseUnits().getName().getName();
        }
        return dose;
    }

    private void addResultRow(ResultRow resultRow) {
        if (!resultRow.isEmpty()) {
            results.add(resultRow);
        }
    }

    private String getUnit(Concept concept) {
        return concept.isNumeric() ? Context.getConceptService().getConceptNumeric(concept.getConceptId()).getUnits() : "";
    }

    private String getRange(Concept concept) {
        String range = "";
        if (concept.isNumeric()) {
            ConceptNumeric cn = Context.getConceptService().getConceptNumeric(concept.getConceptId());
            if (cn.getHiAbsolute() != null && cn.getLowAbsolute() != null) {
                range = cn.getLowAbsolute() + " - " + cn.getHiAbsolute();
            }
        }
        return range;
    }

    private static Obs getLabTestResultObs(Patient patient, Order order, Concept concept) {
        List<Obs> obs = Context.getService(MSFCoreService.class).getObservationsByPersonAndOrderAndConcept(patient, order, concept);
        // the latest result obs
        return !obs.isEmpty() ? obs.get(0) : null;
    }

    public static ResultCategory parseCategory(String category) {
        return ResultCategory.valueOf(category.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toUpperCase());
    }

    private static Obs createBasicObsWithOrder(Order order, ResultCategory category, Concept concept) {
        Obs obs = new Obs();
        obs.setConcept(concept);
        obs.setPerson(order.getPatient());
        obs.setEncounter(buildEncounter(category, order.getPatient()));
        obs.setLocation(Context.getLocationService().getDefaultLocation());
        obs.setOrder(order);
        return obs;
    }

    private static Encounter buildEncounter(ResultCategory category, Patient patient) {
        Encounter enc = new Encounter();
        enc.setEncounterType(category.equals(ResultCategory.LAB_RESULTS)
                        ? Context.getEncounterService().getEncounterTypeByUuid(MSFCoreConfig.ENCOUNTER_TYPE_LAB_RESULTS_UUID)
                        : null);// suport other enounterTypes if needed
        enc.setEncounterDatetime(new Date());
        enc.setPatient(patient);
        return enc;
    }

    public static List<Obs> updateResultRow(LinkedHashMap<String, Object> propertiesToCreate) {
        try {
            if (parseCategory((String) propertiesToCreate.get("category")).equals(ResultCategory.LAB_RESULTS)) {
                return updateLabResultRow(propertiesToCreate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List<Obs> updateLabResultRow(LinkedHashMap<String, Object> propertiesToCreate) throws ParseException {
        Obs obs = null;
        String changeReason = "Updating obs from results widget";
        TestOrder testOrder = null;
        Concept concept = null;
        for (Map resultColumnEntry : (ArrayList<Map>) propertiesToCreate.get("payload")) {
            String[] idContent = ((String) resultColumnEntry.get("uuid_key_type_concept")).split("_");
            String value = (String) resultColumnEntry.get("value");
            String key = getLabResultKeys().get(Integer.parseInt(idContent[1]));
            if (testOrder == null) {
                testOrder = (TestOrder) Context.getOrderService().getOrderByUuid(idContent[0]);
            }
            if (concept == null) {
                concept = Context.getConceptService().getConceptByUuid(idContent[3]);
            }
            if (obs == null) {
                obs = getLabTestResultObs(testOrder.getPatient(), testOrder, concept);
            }
            if (obs == null) {
                obs = createBasicObsWithOrder(testOrder, ResultCategory.LAB_RESULTS, concept);
            }
            if (idContent[2].equals("DATE")) {
                if (key.equals(Context.getMessageSourceService().getMessage("msfcore.resultDate"))) {
                    obs.setObsDatetime(Context.getDateFormat().parse(value));
                }
            } else if (key.equals(Context.getMessageSourceService().getMessage("msfcore.result"))) {
                obs.setValueAsString(value);
            }
        }
        Patient patient = Context.getPatientService().getPatient(obs.getPerson().getPersonId());
        AuditLogBuilder labResultEventBuilder = AuditLog.builder().event(Event.EDIT_LAB_RESULT).user(Context.getAuthenticatedUser())
                        .patient(patient)
                        .detail(Context.getMessageSourceService().getMessage("msfcore.labResultEvent",
                                        new Object[]{"Edited", obs.getConcept().getName().getName(),
                                                        obs.getPerson().getPersonName().getFullName(),
                                                        patient.getPatientIdentifier().getIdentifier()},
                                        null));
        if (obs.getEncounter().getId() == null) {// this is a new obs/result
            obs.setEncounter(Context.getEncounterService().saveEncounter(obs.getEncounter()));
            labResultEventBuilder.event(Event.ADD_LAB_RESULT).detail(Context.getMessageSourceService().getMessage(
                            "msfcore.labResultEvent", new Object[]{"Added", obs.getConcept().getName().getName(),
                                            obs.getPerson().getPersonName().getFullName(), patient.getPatientIdentifier().getIdentifier()},
                            null));
        }
        obs = Context.getObsService().saveObs(obs, changeReason);
        Context.getService(AuditService.class).saveAuditLog(labResultEventBuilder.build());
        return Arrays.asList(obs);
    }
}
