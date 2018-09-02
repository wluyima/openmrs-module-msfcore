package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.module.msfcore.RegistrationAppUiUtils;

public class PhoneNumberFragmentController {

  public void controller(FragmentModel model) {
    model.put("phoneNumberUuid", "14d4f066-15f5-102d-96e4-000c29c2a5d7");
    model.put("uiUtils", new RegistrationAppUiUtils());
  }
}
