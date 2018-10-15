package org.openmrs.module.msfcore.page.controller;

import org.openmrs.Patient;
import org.openmrs.module.msfcore.api.PatientSummaryService;
import org.openmrs.module.msfcore.patientSummary.PatientSummary.Representation;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class PatientSummaryPageController {
    public void controller(PageModel pageModel, @RequestParam("patientId") Patient patient,
                    @RequestParam(value = "representation", required = false) Representation representation,
                    @SpringBean("patientSummaryService") PatientSummaryService patientSummaryService) {
        patientSummaryService.setRepresentation(representation != null ? representation : Representation.SUMMARY);
        pageModel.addAttribute("patientSummary", patientSummaryService.generatePatientSummary(patient));
    }
}
