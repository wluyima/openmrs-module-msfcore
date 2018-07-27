package org.openmrs.module.msfcore.page.controller;

import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;

public class AuditLogManagerPageController {

  public void controller(PageModel model, @SpringBean("msfCoreService") MSFCoreService msfCoreService) {
    model.addAttribute("msfCoreLogs", msfCoreService.getMSFCoreLogs(null, null, null, null, null, null, null));
  }
}
