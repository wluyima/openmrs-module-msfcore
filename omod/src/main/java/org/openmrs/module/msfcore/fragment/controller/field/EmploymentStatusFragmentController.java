package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.module.msfcore.ControllerService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class EmploymentStatusFragmentController {
	
	public void controller(FragmentModel model) {
		model.addAttribute("employmentStatuses",
		    ControllerService.getAllConceptAnswerNames(MSFCoreConfig.EMPLOYMENT_STATUS_CONCEPT_UUID));
	}
}
