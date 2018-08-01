/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore.api.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.api.dao.AuditDao;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;

public class AuditServiceImpl extends BaseOpenmrsService implements AuditService {

  AuditDao dao;

  /**
   * Injected in moduleApplicationContext.xml
   */
  public void setDao(AuditDao dao) {
    this.dao = dao;
  }

  public List<AuditLog> getMSFCoreLogs(Date startDate, Date endDate, List<Event> events, User creator, List<Patient> patients,
          List<User> users, List<Provider> providers, List<Location> locations) {
    return dao.getMSFCoreLogs(startDate, endDate, events, creator, patients, users, providers, locations);
  }

  public AuditLog getMSFCoreLogByUuid(String uuid) {
    return dao.getMSFCoreLogByUuid(uuid);
  }

  public void deleteMSFCoreLog(AuditLog msfCoreLog) {
    dao.deleteMSFCoreLog(msfCoreLog);
  }

  public void deleteMSFCoreFromDate(Date startDate) {
    for (AuditLog log : getMSFCoreLogs(startDate, null, null, null, null, null, null, null)) {
      deleteMSFCoreLog(log);
    }
  }

  public Date getDateAtNDaysFromData(Date date, Integer nDays) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_YEAR, -nDays);
    return calendar.getTime();
  }

  public Integer saveMSFCoreLog(AuditLog msfCoreLog) {
    return dao.saveMSFCoreLog(msfCoreLog);
  }

  public AuditLog getMSFCoreLog(Integer msfCoreLogId) {
    return dao.getMSFCoreLog(msfCoreLogId);
  }
}
