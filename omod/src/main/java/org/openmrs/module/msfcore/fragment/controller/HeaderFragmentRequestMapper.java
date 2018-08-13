package org.openmrs.module.msfcore.fragment.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.ui.framework.fragment.FragmentRequest;
import org.openmrs.ui.framework.fragment.FragmentRequestMapper;
import org.springframework.stereotype.Component;

@Component
public class HeaderFragmentRequestMapper implements FragmentRequestMapper {

  @Override
  public boolean mapRequest(FragmentRequest request) {
    if ("true".equals(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_ENABLE_MSF_UI))
        && request.getProviderName().equals("appui")) {
      if (request.getFragmentId().equals("header")) {
        request.setProviderNameOverride("msfcore");
        return true;
      }
    }
    return false;
  }

}
