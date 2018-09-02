package org.openmrs.module.msfcore.fragment.controller.field;

import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.module.msfcore.RegistrationAppUiUtils;

public class DriverLicenseFragmentController {

  public void controller(FragmentModel model) {
    model.put("uiUtils", new RegistrationAppUiUtils());
  }
}
