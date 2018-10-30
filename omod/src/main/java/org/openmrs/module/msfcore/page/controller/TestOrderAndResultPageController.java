package org.openmrs.module.msfcore.page.controller;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.module.msfcore.result.TestOrderAndResultView;
import org.openmrs.module.msfcore.web.service.TestOrderAndResultService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang3.StringUtils;

public class TestOrderAndResultPageController {

    private TestOrderAndResultService testOrderAndResultService = new TestOrderAndResultService(); // FIXME use dependency injection here

    public void controller(PageModel pageModel, @RequestParam("patientId") Patient patient, @RequestParam("operation") String operation,
                    @RequestParam(value = "testId", required = false) Integer testId,
                    @RequestParam(value = "result", required = false) String result,
                    @RequestParam(value = "sampleDate", required = false) String sampleDate,
                    @RequestParam(value = "resultDate", required = false) String resultDate,
                    @RequestParam(value = "testNameSearch", required = false) String testNameSearch,
                    @RequestParam(value = "statusSearch", required = false) String statusSearch,
                    @RequestParam(value = "startDateSearch", required = false) String startDateSearch,
                    @RequestParam(value = "endDateSearch", required = false) String endDateSearch,
                    @RequestParam(value = "dateTypeSearch", required = false) String dateTypeSearch) throws Exception {
        if (operation.equals("ADD_TEST_RESULT")) {
            testOrderAndResultService.saveTestResult(patient, testId, result, sampleDate, resultDate);
        }

        if (operation.equals("REMOVE_TEST_RESULT")) {
            testOrderAndResultService.removeTestOrder(patient, testId);
        }

        List<TestOrderAndResultView> results = testOrderAndResultService.getTestsAndResults(patient, testNameSearch, null, null, null,
                        null, 1, 10);
        pageModel.addAttribute("results", results);

        if (!StringUtils.isEmpty(testNameSearch)) {
            pageModel.addAttribute("testNameSearch", testNameSearch);
        } else {
            pageModel.addAttribute("testNameSearch", "");
        }

        if (!StringUtils.isEmpty(statusSearch)) {
            pageModel.addAttribute("statusSearch", statusSearch);
        } else {
            pageModel.addAttribute("statusSearch", "");
        }

        if (!StringUtils.isEmpty(startDateSearch)) {
            pageModel.addAttribute("startDateSearch", startDateSearch);
        } else {
            pageModel.addAttribute("startDateSearch", "");
        }

        if (!StringUtils.isEmpty(endDateSearch)) {
            pageModel.addAttribute("endDateSearch", endDateSearch);
        } else {
            pageModel.addAttribute("endDateSearch", "");
        }

        if (!StringUtils.isEmpty(dateTypeSearch)) {
            pageModel.addAttribute("dateTypeSearch", dateTypeSearch);
        } else {
            pageModel.addAttribute("dateTypeSearch", "");
        }
    }
}
