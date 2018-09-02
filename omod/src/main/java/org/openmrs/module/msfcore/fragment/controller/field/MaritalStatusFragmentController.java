package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.module.msfcore.RegistrationAppUiUtils;

public class MaritalStatusFragmentController {

  public void controller(FragmentModel model) {
    model.addAttribute("maritalStatuses",
        Context.getService(MSFCoreService.class).getAllConceptAnswerNames(MSFCoreConfig.CONCEPT_MARITAL_STATUS_UUID));
    model.addAttribute("maritalStatusUuid", MSFCoreConfig.PERSON_ATTRIBUTE_MARITAL_STATUS_UUID);
    model.put("uiUtils", new RegistrationAppUiUtils());
  }
}
