package org.openmrs.module.msfcore.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.TestOrder;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.module.msfcore.api.MSFCoreService;

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
    // key (localised message property), value (with readable toString)
    @Builder.Default
    private List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
    private Filters filters;
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
        }
        List<Order> orders = Context.getService(MSFCoreService.class).getOrders(patient, orderType, null, pagination);
        for (Order o : orders) {
            Map<String, Object> resultRow = new HashMap<String, Object>();
            if (resultCategory.equals(ResultCategory.DRUG_LIST) && o instanceof DrugOrder && !o.getVoided()) {
                DrugOrder drugOrder = (DrugOrder) o;
                resultRow.put("id", drugOrder.getOrderId());
                resultRow.put("msfcore.drugName", drugOrder.getDrug().getName());
                resultRow.put("msfcore.frequency", drugOrder.getFrequency() != null
                                && drugOrder.getFrequency().getFrequencyPerDay() != null
                                ? drugOrder.getFrequency().getFrequencyPerDay()
                                : "");
                resultRow.put("msfcore.duration", drugOrder.getDuration());
                resultRow.put("msfcore.notes", "");// TODO support order notes
                resultRow.put("msfcore.datePrescribed", drugOrder.getEffectiveStartDate());// TODO
                resultRow.put("msfcore.prescriptionStatus", "");// TODO support
                resultRow.put("msfcore.dispenseStatus", "");// TODO support
            }
            if (resultCategory.equals(ResultCategory.LAB_RESULTS) && o instanceof TestOrder && !o.getVoided()) {
                TestOrder testOrder = (TestOrder) o;
                Obs obs = getLabTestResultObs(testOrder);
                resultRow.put("id", testOrder.getOrderId());
                resultRow.put("msfcore.labTest", testOrder.getConcept().getName().getName());
                resultRow.put("msfcore.result", obs != null ? obs.getValueAsString(Context.getLocale()) : ""); // TODO
                resultRow.put("msfcore.uom", obs != null ? getUnit(obs.getConcept()) : "");
                resultRow.put("msfcore.range", obs != null ? getRange(obs.getConcept()) : "");
                resultRow.put("msfcore.sampleDate", null); // TODO
                resultRow.put("msfcore.encounterDate", Context.getDateFormat().format(obs.getEncounter().getEncounterDatetime()));
            }
            if (!resultRow.isEmpty()) {
                results.add(resultRow);
                if (Collections.disjoint(keys, resultRow.keySet())) {
                    keys.addAll(resultRow.keySet());
                }
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

    private Obs getLabTestResultObs(TestOrder testOrder) {
        List<Obs> obs = Context.getObsService().getObservations(Arrays.asList(testOrder.getPatient().getPerson()),
                        Arrays.asList(testOrder.getEncounter()), Arrays.asList(testOrder.getConcept()), null, null, null, null, null, null,
                        null, null, false);

        return !obs.isEmpty() ? obs.get(0) : null;
    }

    public static ResultCategory parseCategory(String category) {
        return ResultCategory.valueOf(category.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toUpperCase());
    }
}
