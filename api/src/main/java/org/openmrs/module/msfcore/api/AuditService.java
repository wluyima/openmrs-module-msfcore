/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore.api;

import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface AuditService extends OpenmrsService {

  public List<AuditLog> getMSFCoreLogs(Date startDate, Date endDate, List<Event> events, User creator, List<Patient> patients,
          List<User> users, List<Provider> providers, List<Location> locations);

  public AuditLog getMSFCoreLogByUuid(String uuid);

  public void deleteMSFCoreLog(AuditLog msfCoreLog);

  public void deleteMSFCoreFromDate(Date startDate);

  public Date getDateAtNDaysFromData(Date date, Integer nDays);

  public Integer saveMSFCoreLog(AuditLog msfCoreLog);

  public AuditLog getMSFCoreLog(Integer msfCoreLogId);
}
