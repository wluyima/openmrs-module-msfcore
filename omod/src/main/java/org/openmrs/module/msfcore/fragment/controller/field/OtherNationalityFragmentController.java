package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.module.msfcore.ControllerService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class OtherNationalityFragmentController {

  public void controller(FragmentModel model) {
    model.addAttribute("otherNationalities",
        ControllerService.getAllConceptAnswerNames(MSFCoreConfig.OTHER_NATIONALITY_CONCEPT_UUID));
  }
}
