package org.openmrs.module.msfcore.page.controller;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.module.msfcore.result.TestOrderAndResultView;
import org.openmrs.module.msfcore.web.service.TestOrderAndResultService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class TestOrderAndResultPageController {

    private TestOrderAndResultService testOrderAndResultService = new TestOrderAndResultService(); // FIXME use dependency injection here

    public void controller(PageModel pageModel, @RequestParam("patientId") Patient patient, @RequestParam("operation") String operation,
                    @RequestParam(value = "testId", required = false) Integer testId,
                    @RequestParam(value = "result", required = false) String result,
                    @RequestParam(value = "sampleDate", required = false) String sampleDate,
                    @RequestParam(value = "resultDate", required = false) String resultDate) throws Exception {
        if (operation.equals("ADD_TEST_RESULT")) {
            testOrderAndResultService.saveTestResult(patient, testId, result, sampleDate, resultDate);
        }

        if (operation.equals("REMOVE_TEST_RESULT")) {
            testOrderAndResultService.removeTestOrder(patient, testId);
        }

        List<TestOrderAndResultView> results = testOrderAndResultService.getTestsAndResults(patient.getUuid(), null, null, null, null,
                        null, 1, 10);
        pageModel.addAttribute("results", results);
    }
}
