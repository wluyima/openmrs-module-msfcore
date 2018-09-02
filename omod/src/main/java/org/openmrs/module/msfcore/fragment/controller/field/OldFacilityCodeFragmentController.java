package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.module.msfcore.RegistrationAppUiUtils;

public class OldFacilityCodeFragmentController {

  public void controller(FragmentModel model) {
    model.addAttribute("oldFacilityCodeUuid", MSFCoreConfig.PERSON_ATTRIBUTE_OLD_FACILITY_CODE_UUID);
    model.put("uiUtils", new RegistrationAppUiUtils());
  }
}
