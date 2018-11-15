package org.openmrs.module.msfcore.result;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.module.msfcore.result.ResultColumn.Type;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class ResultsDataTest extends BaseModuleContextSensitiveTest {

    @Test
    public void addRetriedResults_shouldAddLabTestOrders() throws ParseException {
        executeDataSet("ResultsData.xml");

        // should retrieve and add lab test orders with matching results
        ResultsData resultsData = ResultsData.builder().resultCategory(ResultCategory.LAB_RESULTS).patient(
                        Context.getPatientService().getPatient(7)).build();
        resultsData.addRetrievedResults();

        assertEquals(Arrays.asList(Context.getMessageSourceService().getMessage("msfcore.testName"), Context.getMessageSourceService()
                        .getMessage("msfcore.result"), Context.getMessageSourceService().getMessage("msfcore.uom"), Context
                        .getMessageSourceService().getMessage("msfcore.range"), Context.getMessageSourceService().getMessage(
                        "msfcore.orderDate"), Context.getMessageSourceService().getMessage("msfcore.resultDate")), resultsData.getKeys());

        // should only test orders and match their respective results
        // obs 51 for concept#500029 should have been left out since it has no
        // test order
        assertEquals(3, resultsData.getResults().size());

        // should retrieve with a completed result
        assertEquals(ResultColumn.builder().value("00ddc453-fc20-4f1b-a351-7eff54b4daf1").build(), resultsData.getResults().get(0).get(
                        "uuid"));
        assertEquals(ResultColumn.builder().value(ResultStatus.COMPLETED).build(), resultsData.getResults().get(0).get("status"));
        assertEquals(ResultColumn.builder().value(Arrays.asList(ResultAction.EDIT)).build(), resultsData.getResults().get(0).get("actions"));
        assertEquals(ResultColumn.builder().value("Creatinine").build(), resultsData.getResults().get(0).get(
                        Context.getMessageSourceService().getMessage("msfcore.testName")));
        assertEquals(ResultColumn.builder().value("500022AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build(), resultsData.getResults().get(0).get(
                        "concept"));
        assertEquals(ResultColumn.builder().value("0.97").editable(true).type(Type.NUMBER).build(), resultsData.getResults().get(0).get(
                        Context.getMessageSourceService().getMessage("msfcore.result")));
        assertEquals(ResultColumn.builder().value("mg/dL").build(), resultsData.getResults().get(0).get(
                        Context.getMessageSourceService().getMessage("msfcore.uom")));
        assertEquals(ResultColumn.builder().value("0.5 - 1.1").build(), resultsData.getResults().get(0).get(
                        Context.getMessageSourceService().getMessage("msfcore.range")));
        assertEquals("2018-10-31 02:14:38.0", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(resultsData.getResults().get(0).get(
                        Context.getMessageSourceService().getMessage("msfcore.orderDate")).getValue()));
        assertEquals("2018-11-01 02:00:00.0", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(resultsData.getResults().get(0).get(
                        Context.getMessageSourceService().getMessage("msfcore.resultDate")).getValue()));

        // should retrieve a pending result
        assertEquals(ResultColumn.builder().value("00ddc453-fc20-4f1b-a351-7eff54b4daf2").build(), resultsData.getResults().get(1).get(
                        "uuid"));
        assertEquals(ResultColumn.builder().value(ResultStatus.PENDING).build(), resultsData.getResults().get(1).get("status"));
        assertEquals(ResultColumn.builder().value(Arrays.asList(ResultAction.EDIT, ResultAction.DELETE)).build(), resultsData.getResults()
                        .get(1).get("actions"));

        // should retrieve a cancelled result
        assertEquals(ResultColumn.builder().value("00ddc453-fc20-4f1b-a351-7eff54b4daf3").build(), resultsData.getResults().get(2).get(
                        "uuid"));
        assertEquals(ResultColumn.builder().value(ResultStatus.CANCELLED).build(), resultsData.getResults().get(2).get("status"));
        assertEquals(ResultColumn.builder().value(Arrays.asList()).build(), resultsData.getResults().get(2).get("actions"));

        // should retreive filters
        assertEquals(ResultFilters.builder().name(Context.getMessageSourceService().getMessage("msfcore.testName")).statuses(
                        Arrays.asList(ResultStatus.values())).dates(
                        Arrays.asList(Context.getMessageSourceService().getMessage("msfcore.orderDate"), Context.getMessageSourceService()
                                        .getMessage("msfcore.resultDate"))).build(), resultsData.getFilters());

        // should retrive default pagination with number of found results set
        assertEquals(Pagination.builder().totalItemsNumber(3).build(), resultsData.getPagination());

        // should retrieve right dateFormatPattern
        assertEquals(Context.getDateFormat().toPattern(), resultsData.getDateFormatPattern());
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

    @Test
    public void updateResultRow_shouldUpdateExistingLabResult() {
        executeDataSet("ResultsData.xml");

        LinkedHashMap<String, Object> mapWithPayload = new LinkedHashMap<String, Object>();
        mapWithPayload.put("category", "labResults");
        List<LinkedHashMap<String, Object>> payload = new ArrayList<LinkedHashMap<String, Object>>();
        LinkedHashMap<String, Object> payloadCreatinineResultDate = new LinkedHashMap<String, Object>();
        payloadCreatinineResultDate.put("uuid_key_type_concept",
                        "00ddc453-fc20-4f1b-a351-7eff54b4daf1_5_DATE_500022AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        payloadCreatinineResultDate.put("value", "02/11/2018");
        LinkedHashMap<String, Object> payloadCreatinineResult = new LinkedHashMap<String, Object>();
        payloadCreatinineResult.put("uuid_key_type_concept",
                        "00ddc453-fc20-4f1b-a351-7eff54b4daf1_1_STRING_500022AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        payloadCreatinineResult.put("value", "0.98");

        payload.add(payloadCreatinineResultDate);
        payload.add(payloadCreatinineResult);
        mapWithPayload.put("payload", payload);
        List<Obs> updated = (List<Obs>) ResultsData.updateResultRow(mapWithPayload);
        Assert.assertEquals(1, updated.size());
        Assert.assertEquals("2018-11-02", new SimpleDateFormat("yyyy-MM-dd").format(updated.get(0).getObsDatetime()));
        Assert.assertEquals(Double.valueOf("0.98"), updated.get(0).getValueNumeric());

        // should add a respective auditLog
        AuditLog audit = Context.getService(AuditService.class).getAuditLogs(null, null, Arrays.asList(Event.EDIT_LAB_RESULT),
                        Arrays.asList(Context.getPatientService().getPatient(7)), null, null, null, null).get(0);
        Assert.assertEquals(Event.EDIT_LAB_RESULT, audit.getEvent());
        Assert.assertEquals("Edited: Creatinine Lab result for Patient: Collet Test Chebaskwony - 6TS-4", audit.getDetail());
    }

    @Test
    public void updateResultRow_shouldCreateNewLabResultEntry() {
        executeDataSet("ResultsData.xml");

        LinkedHashMap<String, Object> mapWithPayload = new LinkedHashMap<String, Object>();
        mapWithPayload.put("category", "labResults");
        List<LinkedHashMap<String, Object>> payload = new ArrayList<LinkedHashMap<String, Object>>();
        LinkedHashMap<String, Object> payloadCreatinineResultDate = new LinkedHashMap<String, Object>();
        payloadCreatinineResultDate.put("uuid_key_type_concept",
                        "00ddc453-fc20-4f1b-a351-7eff54b4daf3_5_DATE_500025AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        payloadCreatinineResultDate.put("value", "03/11/2018");
        LinkedHashMap<String, Object> payloadCreatinineResult = new LinkedHashMap<String, Object>();
        payloadCreatinineResult.put("uuid_key_type_concept",
                        "00ddc453-fc20-4f1b-a351-7eff54b4daf3_1_STRING_500025AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        payloadCreatinineResult.put("value", "1.8");

        payload.add(payloadCreatinineResultDate);
        payload.add(payloadCreatinineResult);
        mapWithPayload.put("payload", payload);
        List<Obs> created = (List<Obs>) ResultsData.updateResultRow(mapWithPayload);
        Assert.assertEquals(1, created.size());
        Assert.assertEquals("2018-11-03", new SimpleDateFormat("yyyy-MM-dd").format(created.get(0).getObsDatetime()));
        Assert.assertEquals(Integer.valueOf(500025), created.get(0).getConcept().getId());
        Assert.assertEquals("00ddc453-fc20-4f1b-a351-7eff54b4daf3", created.get(0).getOrder().getUuid());
        Assert.assertEquals(Double.valueOf("1.8"), created.get(0).getValueNumeric());
        Assert.assertEquals(MSFCoreConfig.ENCOUNTER_TYPE_LAB_RESULTS_UUID, created.get(0).getEncounter().getEncounterType().getUuid());

        // should add a respective auditLog
        AuditLog audit = Context.getService(AuditService.class).getAuditLogs(null, null, Arrays.asList(Event.ADD_LAB_RESULT),
                        Arrays.asList(Context.getPatientService().getPatient(7)), null, null, null, null).get(0);
        Assert.assertEquals(Event.ADD_LAB_RESULT, audit.getEvent());
        Assert.assertEquals("Added: ECG Lab result for Patient: Collet Test Chebaskwony - 6TS-4", audit.getDetail());
    }
}
