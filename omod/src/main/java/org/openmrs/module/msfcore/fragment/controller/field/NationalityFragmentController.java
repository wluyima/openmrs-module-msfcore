package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.module.msfcore.RegistrationAppUiUtils;

public class NationalityFragmentController {

  public void controller(FragmentModel model) {
    model.addAttribute("nationalities",
        Context.getService(MSFCoreService.class).getAllConceptAnswerNames(MSFCoreConfig.CONCEPT_NATIONALITY_UUID));
    model.addAttribute("nationalityUuid", MSFCoreConfig.PERSON_ATTRIBUTE_NATIONALITY_UUID);
    model.put("uiUtils", new RegistrationAppUiUtils());
  }
}
