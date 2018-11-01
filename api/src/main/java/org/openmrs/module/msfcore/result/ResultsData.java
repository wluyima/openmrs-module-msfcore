package org.openmrs.module.msfcore.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.ConceptNumeric;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.TestOrder;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.module.msfcore.api.MSFCoreService;
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
    // result row: key (localised message property), value
    @Builder.Default
    private List<Map<String, ResultColumn>> results = new ArrayList<Map<String, ResultColumn>>();
    @Builder.Default
    private ResultFilters filters = ResultFilters.builder().build();

    public void addRetrievedResults() {
        if (resultCategory.equals(ResultCategory.DRUG_LIST) || resultCategory.equals(ResultCategory.LAB_RESULTS)) {
            addOrders();
        }
        // TODO from config, messageName:[conceptIDs] for obs listing
    }

    private void addOrders() {
        OrderType orderType = null;
        if (resultCategory.equals(ResultCategory.DRUG_LIST)) {
            orderType = Context.getOrderService().getOrderTypeByUuid(OrderType.DRUG_ORDER_TYPE_UUID);
            keys.addAll(Arrays.asList("msfcore.drugName", "msfcore.frequency", "msfcore.duration", "msfcore.notes",
                            "msfcore.datePrescribed", "msfcore.prescriptionStatus", "msfcore.dispenseStatus"));
        } else if (resultCategory.equals(ResultCategory.LAB_RESULTS)) {
            orderType = Context.getOrderService().getOrderTypeByUuid(OrderType.TEST_ORDER_TYPE_UUID);
            filters.setStatuses(Arrays.asList(ResultStatus.values()));
            filters.setDates(Arrays.asList("msfcore.orderDate", "msfcore.sampleDate", "msfcore.resultDate"));
            keys.addAll(Arrays.asList("msfcore.labTest", "msfcore.result", "msfcore.uom", "msfcore.range", "msfcore.orderDate",
                            "msfcore.sampleDate", "msfcore.resultDate"));
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

    private void addTestOrders(Order o) {
        Map<String, ResultColumn> resultRow = new HashMap<String, ResultColumn>();
        TestOrder testOrder = (TestOrder) o;
        // order concept must be a labSet with expected result concepts members
        for (Concept concept : testOrder.getConcept().getSetMembers()) {
            // TODO fix for other investigation order
            Obs resultObs = getLabTestResultObs(testOrder.getPatient(), testOrder);
            resultRow.put("id", ResultColumn.builder().value(testOrder.getOrderId()).build());
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
            resultRow.put("status", ResultColumn.builder().value(status).build());
            resultRow.put("actions", ResultColumn.builder().value(actions).build());
            resultRow.put("msfcore.labTest", ResultColumn.builder().value(concept.getName().getName()).build());
            resultRow.put("msfcore.result", resultObs != null ? ResultColumn.builder().editable(true).value(
                            resultObs.getValueAsString(Context.getLocale())).build() : null);
            resultRow.put("msfcore.uom", ResultColumn.builder().value(getUnit(concept)).build());
            resultRow.put("msfcore.range", resultObs != null
                            ? ResultColumn.builder().value(getRange(resultObs.getConcept())).build()
                            : null);
            resultRow.put("msfcore.orderDate", ResultColumn.builder().editable(true).type(Type.DATE).value(
                            Context.getDateFormat().format(testOrder.getDateCreated())).build());
            resultRow.put("msfcore.sampleDate", null); // TODO
            resultRow.put("msfcore.resultDate", resultObs != null ? ResultColumn.builder().editable(true).type(Type.DATE).value(
                            Context.getDateFormat().format(resultObs.getObsDatetime())).build() : null);
            addResultRow(resultRow);
        }

    }

    // TODO add unit test when working on drug order
    private void addDrugOrders(Order o) {
        Map<String, ResultColumn> resultRow = new HashMap<String, ResultColumn>();
        DrugOrder drugOrder = (DrugOrder) o;
        resultRow.put("id", ResultColumn.builder().value(drugOrder.getOrderId()).build());
        resultRow.put("status", ResultColumn.builder().build());
        resultRow.put("actions", ResultColumn.builder().build());
        resultRow.put("msfcore.drugName", ResultColumn.builder().value(drugOrder.getDrug().getName()).build());
        resultRow.put("msfcore.frequency", ResultColumn.builder().value(
                        drugOrder.getFrequency() != null && drugOrder.getFrequency().getFrequencyPerDay() != null ? drugOrder
                                        .getFrequency().getFrequencyPerDay() : "").build());
        resultRow.put("msfcore.duration", ResultColumn.builder().value(drugOrder.getDuration()).build());
        resultRow.put("msfcore.notes", ResultColumn.builder().value("").build());// TODO
        resultRow.put("msfcore.datePrescribed", ResultColumn.builder().value(drugOrder.getEffectiveStartDate()).build());// TODO
        resultRow.put("msfcore.prescriptionStatus", ResultColumn.builder().value("").build());// TODO
        resultRow.put("msfcore.dispenseStatus", ResultColumn.builder().value("").build());// TODO
        addResultRow(resultRow);
    }

    private void addResultRow(Map<String, ResultColumn> resultRow) {
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

    private Obs getLabTestResultObs(Patient patient, Order order) {
        List<Obs> obs = Context.getService(MSFCoreService.class).getObservationsByPersonAndOrder(patient, order);

        // TODO one order is currently expected to contain one result
        return !obs.isEmpty() ? obs.get(0) : null;
    }

    public static ResultCategory parseCategory(String category) {
        return ResultCategory.valueOf(category.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toUpperCase());
    }
}
