package org.openmrs.module.msfcore.result;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.module.msfcore.result.ResultColumn.Type;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class ResultsDataTest extends BaseModuleContextSensitiveTest {

    @Test
    public void addRetriedResults_shouldAddLabTestOrders() {
        executeDataSet("ResultsData.xml");

        // should retrieve and add lab test orders with matching results
        ResultsData resultsData = ResultsData.builder().resultCategory(ResultCategory.LAB_RESULTS).patient(
                        Context.getPatientService().getPatient(7)).build();
        resultsData.addRetrievedResults();

        assertEquals(Arrays.asList("msfcore.labTest", "msfcore.result", "msfcore.uom", "msfcore.range", "msfcore.orderDate",
                        "msfcore.sampleDate", "msfcore.resultDate"), resultsData.getKeys());

        // should only test orders and match their respective results
        // obs 51 for concept#500029 should have been left out since it has no
        // test order
        assertEquals(3, resultsData.getResults().size());

        // should retrieve with a completed result
        assertEquals(ResultColumn.builder().value(50).build(), resultsData.getResults().get(0).get("id"));
        assertEquals(ResultColumn.builder().value(ResultStatus.COMPLETED).build(), resultsData.getResults().get(0).get("status"));
        assertEquals(ResultColumn.builder().value(Arrays.asList(ResultAction.EDIT)).build(), resultsData.getResults().get(0).get("actions"));
        assertEquals(ResultColumn.builder().value("Creatinine").build(), resultsData.getResults().get(0).get("msfcore.labTest"));
        assertEquals(ResultColumn.builder().value("0.97").editable(true).build(), resultsData.getResults().get(0).get("msfcore.result"));
        assertEquals(ResultColumn.builder().value("mg/dL").build(), resultsData.getResults().get(0).get("msfcore.uom"));
        assertEquals(ResultColumn.builder().value("0.5 - 1.1").build(), resultsData.getResults().get(0).get("msfcore.range"));
        assertEquals(ResultColumn.builder().value("31/10/2018").editable(true).type(Type.DATE).build(), resultsData.getResults().get(0)
                        .get("msfcore.orderDate"));
        assertEquals(ResultColumn.builder().value("01/11/2018").editable(true).type(Type.DATE).build(), resultsData.getResults().get(0)
                        .get("msfcore.resultDate"));

        // should retrieve a pending result
        assertEquals(ResultColumn.builder().value(51).build(), resultsData.getResults().get(1).get("id"));
        assertEquals(ResultColumn.builder().value(ResultStatus.PENDING).build(), resultsData.getResults().get(1).get("status"));
        assertEquals(ResultColumn.builder().value(Arrays.asList(ResultAction.EDIT, ResultAction.DELETE)).build(), resultsData.getResults()
                        .get(1).get("actions"));

        // should retrieve a cancelled result
        assertEquals(ResultColumn.builder().value(52).build(), resultsData.getResults().get(2).get("id"));
        assertEquals(ResultColumn.builder().value(ResultStatus.CANCELLED).build(), resultsData.getResults().get(2).get("status"));
        assertEquals(ResultColumn.builder().value(Arrays.asList()).build(), resultsData.getResults().get(2).get("actions"));

        // should retreive filters
        assertEquals(ResultFilters.builder().statuses(Arrays.asList(ResultStatus.values())).dates(
                        Arrays.asList("msfcore.orderDate", "msfcore.sampleDate", "msfcore.resultDate")).build(), resultsData.getFilters());

        // should retrive default pagination with number of found results set
        assertEquals(Pagination.builder().totalResultNumber(3).build(), resultsData.getPagination());
    }

    @Test
    public void parseCategory() {
        assertEquals(ResultCategory.DRUG_LIST, ResultsData.parseCategory("drugList"));
        assertEquals(ResultCategory.LAB_RESULTS, ResultsData.parseCategory("labResults"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseCategory_failWithNonMatchable() {
        ResultsData.parseCategory("anyThingElse");
    }
}
