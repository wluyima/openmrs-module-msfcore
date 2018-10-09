package org.openmrs.module.msfcore.page.controller;

import org.openmrs.Patient;
import org.openmrs.module.msfcore.result.ResultCategory;
import org.openmrs.module.msfcore.result.ResultsData;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class OrderResultHistoryPageController {
    public void controller(PageModel pageModel, @RequestParam("patientId") Patient patient, @RequestParam("category") String category) {
        pageModel.addAttribute("patient", patient);
        ResultCategory resCat = ResultsData.parseCategory(category);
        if (resCat.equals(ResultCategory.DRUG_LIST)) {
            pageModel.addAttribute("pageLabel", "msfcore.drugListHistory");
        } else if (resCat.equals(ResultCategory.LAB_RESULTS)) {
            pageModel.addAttribute("pageLabel", "msfcore.labResultsHistory");
        }
    }
}
