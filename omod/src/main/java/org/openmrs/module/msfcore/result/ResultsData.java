package org.openmrs.module.msfcore.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.DrugOrder;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.TestOrder;

import lombok.NoArgsConstructor;

/**
 * Used to populate and handle data from results.gsp widget
 */
@lombok.Data
@NoArgsConstructor
public class ResultsData {
    private Patient patient;
    private List<String> keys = new ArrayList<String>();
    // key (localised message property), value (with readable toString)
    private List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
    private List<Filter> filters;

    public void addOrders(List<Order> orders, ResultCategory category) {
        for (Order o : orders) {
            Map<String, Object> resultRow = new HashMap<String, Object>();
            if (category.equals(ResultCategory.DRUG_LIST) && o instanceof DrugOrder && !o.getVoided()) {
                DrugOrder drugOrder = (DrugOrder) o;
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
            if (category.equals(ResultCategory.LAB_RESULTS) && o instanceof TestOrder && !o.getVoided()) {
                TestOrder testOrder = (TestOrder) o;
                resultRow.put("msfcore.labTest", testOrder.getConcept().getName().getName());
                resultRow.put("msfcore.result", null); // TODO
                resultRow.put("msfcore.uom", null); // TODO unit
                resultRow.put("msfcore.range", null); // TODO
                resultRow.put("msfcore.sampleDate", null); // TODO
                resultRow.put("msfcore.encounterDate", null); // TODO
            }

            if (!resultRow.isEmpty()) {
                results.add(resultRow);
                if (Collections.disjoint(keys, resultRow.keySet())) {
                    keys.addAll(resultRow.keySet());
                }
            }
        }
    }

    public static ResultCategory parseCategory(String category) {
        return ResultCategory.valueOf(category.replaceAll("([^_A-Z])([A-Z])", "$1_$2").toUpperCase());
    }
}
