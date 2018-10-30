package org.openmrs.module.msfcore.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
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
import org.openmrs.module.msfcore.result.Filters.StatusFilter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Used to populate and handle data from results.gsp widget
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
    private Pagination pagination;
    private ResultCategory resultCategory;
    // result row: key (localised message property), value
    @Builder.Default
    private List<Map<String, ResultColumn>> results = new ArrayList<Map<String, ResultColumn>>();
    @Builder.Default
    private Filters filters = Filters.builder().build();
    @Builder.Default
    private List<Action> actions = new ArrayList<Action>();

    public void addRetriedResults() {
        if (resultCategory.equals(ResultCategory.DRUG_LIST) || resultCategory.equals(ResultCategory.LAB_RESULTS)) {
            addOrders();
        }
        // TODO from config, messageName:[conceptIDs] for obs listing
    }

    private void addOrders() {
        OrderType orderType = null;
        if (resultCategory.equals(ResultCategory.DRUG_LIST)) {
            orderType = Context.getOrderService().getOrderTypeByUuid(OrderType.DRUG_ORDER_TYPE_UUID);
        } else if (resultCategory.equals(ResultCategory.LAB_RESULTS)) {
            actions.addAll(Arrays.asList(Action.values()));
            orderType = Context.getOrderService().getOrderTypeByUuid(OrderType.TEST_ORDER_TYPE_UUID);
            filters.setStatuses(Arrays.asList(StatusFilter.values()));
        }
        List<Order> orders = Context.getService(MSFCoreService.class).getOrders(patient, orderType, null, pagination);
        for (Order o : orders) {
            Map<String, ResultColumn> resultRow = new HashMap<String, ResultColumn>();
            if (resultCategory.equals(ResultCategory.DRUG_LIST) && o instanceof DrugOrder && !o.getVoided()) {
                addDrugOrders(o, resultRow);
            }
            if (resultCategory.equals(ResultCategory.LAB_RESULTS) && o instanceof TestOrder && !o.getVoided()) {
                addTestOrders(o, resultRow);
            }
        }
    }

    private void addTestOrders(Order o, Map<String, ResultColumn> resultRow) {
        TestOrder testOrder = (TestOrder) o;
        // order concept must be a labSet with expected result concepts members
        for (Concept concept : testOrder.getConcept().getSetMembers()) {
            Obs obs = getLabTestResultObs(testOrder.getPatient(), concept, testOrder.getEncounter());
            resultRow.put("id", ResultColumn.builder().value(testOrder.getOrderId()).build());
            resultRow.put("msfcore.labTest", ResultColumn.builder().value(concept.getName().getName()).build());
            resultRow.put("msfcore.result", obs != null ? ResultColumn.builder().editable(true).value(
                            obs.getValueAsString(Context.getLocale())).build() : null); // TODO
            resultRow.put("msfcore.uom", ResultColumn.builder().value(getUnit(concept)).build());
            resultRow.put("msfcore.range", obs != null ? ResultColumn.builder().value(getRange(obs.getConcept())).build() : null);
            resultRow.put("msfcore.orderDate", ResultColumn.builder().value(testOrder.getDateCreated()).build()); // TODO
            resultRow.put("msfcore.sampleDate", null); // TODO
            resultRow.put("msfcore.resultDate", obs != null ? ResultColumn.builder().value(
                            Context.getDateFormat().format(obs.getObsDatetime())).build() : null);
            addResultRow(resultRow);
        }

    }

    // TODO add unit test when working on drug order
    private void addDrugOrders(Order o, Map<String, ResultColumn> resultRow) {
        DrugOrder drugOrder = (DrugOrder) o;
        resultRow.put("id", ResultColumn.builder().value(drugOrder.getOrderId()).build());
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
            if (Collections.disjoint(keys, resultRow.keySet())) {
                keys.addAll(resultRow.keySet());
            }
        }
    }

    private String getUnit(Concept concept) {
        // from concept or messages
        return "";// TODO
    }

    private String getRange(Concept concept) {
        return "";// TODO
    }

    /**
     * TODO when saving results, ensure to use the same concept and encounter as
     * their respective test orders
     */
    private Obs getLabTestResultObs(Patient patient, Concept concept, Encounter encounter) {
        List<Obs> obs = Context.getObsService().getObservations(Arrays.asList(patient.getPerson()), Arrays.asList(encounter),
                        Arrays.asList(concept), null, null, null, null, null, null, null, null, false);

        // TODO one order is currently expected to contain one result
        return !obs.isEmpty() ? obs.get(0) : null;
    }

    public static ResultCategory parseCategory(String category) {
        return ResultCategory.valueOf(category.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toUpperCase());
    }
}
