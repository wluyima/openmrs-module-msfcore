package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.module.msfcore.RegistrationAppUiUtils;

public class ProvenanceFragmentController {

  public void controller(FragmentModel model) {
    model.addAttribute("provenances",
        Context.getService(MSFCoreService.class).getAllConceptAnswerNames(MSFCoreConfig.CONCEPT_PROVENANCE_UUID));
    model.addAttribute("provenanceUuid", MSFCoreConfig.PERSON_ATTRIBUTE_PROVENANCE_UUID);
    model.put("uiUtils", new RegistrationAppUiUtils());
  }
}
