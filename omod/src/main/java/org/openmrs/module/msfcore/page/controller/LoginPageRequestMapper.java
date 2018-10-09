package org.openmrs.module.msfcore.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.ui.framework.page.PageRequestMapper;
import org.springframework.stereotype.Controller;

@Controller
public class LoginPageRequestMapper implements PageRequestMapper {

    @Override
    public boolean mapRequest(PageRequest request) {
        if ("true".equals(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_ENABLE_MSF_UI))
                        && request.getProviderName().equals("referenceapplication")) {
            if (request.getPageName().equals("login")) {
                request.setProviderNameOverride("msfcore");
                return true;
            }
        }
        return false;
    }

}
