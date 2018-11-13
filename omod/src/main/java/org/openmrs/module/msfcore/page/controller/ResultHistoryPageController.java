package org.openmrs.module.msfcore.page.controller;

import org.openmrs.Patient;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class ResultHistoryPageController {
    public void controller(PageModel pageModel, @RequestParam("patientId") Patient patient) {
        pageModel.addAttribute("patient", patient);
    }
}
