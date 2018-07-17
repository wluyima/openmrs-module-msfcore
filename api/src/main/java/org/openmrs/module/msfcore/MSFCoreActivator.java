/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.msfcore.id.MSFIdentifierGenerator;

/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class MSFCoreActivator extends BaseModuleActivator {

  private Log log = LogFactory.getLog(getClass());

  /**
   * @see #started()
   */
  public void started() {
    log.info("Started MSF Core");

    //disabling default reference application registration app
    Context.getService(AppFrameworkService.class).disableApp(MSFCoreConfig.REGISTRATION_APP_EXTENSION_ID);

    //installation and configuration of default MSF Identifier
    MSFIdentifierGenerator.installation();
  }

  /**
   * @see #shutdown()
   */
  public void shutdown() {
    log.info("Shutdown MSF Core");
  }

}
