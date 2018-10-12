package org.openmrs.module.msfcore.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.module.msfcore.api.PatientSummaryService;
import org.openmrs.module.msfcore.patientSummary.PatientSummary.Representation;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class PatientSummaryFragmentController {
    public void controller(FragmentModel fragmentModel, @RequestParam("patientId") Patient patient,
                    @RequestParam(value = "representation", required = false) Representation representation,
                    @SpringBean("patientSummaryService") PatientSummaryService patientSummaryService) {
        patientSummaryService.setRepresentation(representation != null ? representation : Representation.SUMMARY);
        fragmentModel.addAttribute("patientSummary", patientSummaryService.generatePatientSummary(patient));
    }
}
