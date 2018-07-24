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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.api.dao.MSFCoreDao;
import org.openmrs.module.msfcore.audit.MSFCoreLog;
import org.openmrs.module.msfcore.audit.MSFCoreLog.Event;

public class MSFCoreServiceImpl extends BaseOpenmrsService implements MSFCoreService {

  MSFCoreDao dao;

  /**
   * Injected in moduleApplicationContext.xml
   */
  public void setDao(MSFCoreDao dao) {
    this.dao = dao;
  }

  public List<MSFCoreLog> getMSFCoreLogs(Date startDate, List<Event> events, User creator, List<Patient> patients,
          List<User> users, List<Provider> providers, List<Location> locations) {
    return dao.getMSFCoreLogs(startDate, events, creator, patients, users, providers, locations);
  }

  public MSFCoreLog getMSFCoreLogByUuid(String uuid) {
    return dao.getMSFCoreLogByUuid(uuid);
  }

  public void deleteMSFCoreLog(MSFCoreLog msfCoreLog) {
    dao.deleteMSFCoreLog(msfCoreLog);
  }

  public void deleteMSFCoreFromDate(Date startDate) {
    for (MSFCoreLog log : getMSFCoreLogs(startDate, null, null, null, null, null, null)) {
      deleteMSFCoreLog(log);
    }
  }

  public List<Concept> getAllConceptAnswers(Concept question) {
    return dao.getAllConceptAnswers(question);
  }

  public List<LocationAttribute> getLocationAttributeByTypeAndLocation(LocationAttributeType type, Location location) {
    return dao.getLocationAttributeByTypeAndLocation(type, location);
  }

  public IdentifierSource updateIdentifierSource(SequentialIdentifierGenerator identifierSource) throws APIException {
    return dao.updateIdentifierSource(identifierSource);
  }

  public List<DropDownFieldOption> getAllConceptAnswerNames(String uuid) {
    List<DropDownFieldOption> answerNames = new ArrayList<DropDownFieldOption>();
    for (Concept answer : getAllConceptAnswers(Context.getConceptService().getConceptByUuid(uuid))) {
      answerNames.add(new DropDownFieldOption(String.valueOf(answer.getId()), answer.getName().getName()));
    }
    return answerNames;
  }

  public Date getDateAtNDaysFromData(Date date, Integer nDays) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_YEAR, -nDays);
    return calendar.getTime();
  }
}
