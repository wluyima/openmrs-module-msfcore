package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.module.msfcore.ControllerService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class NationalityFragmentController {
	
	public void controller(FragmentModel model) {
		model.addAttribute("nationalities",
		    ControllerService.getAllConceptAnswerNames(MSFCoreConfig.NATIONALITY_CONCEPT_UUID));
	}
}
