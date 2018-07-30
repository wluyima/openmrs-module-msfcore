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

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.audit.MSFCoreLog;
import org.openmrs.module.msfcore.audit.MSFCoreLog.Event;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface MSFCoreService extends OpenmrsService {

  public List<MSFCoreLog> getMSFCoreLogs(Date startDate, Date endDate, List<Event> events, User creator, List<Patient> patients,
          List<User> users, List<Provider> providers, List<Location> locations);

  public MSFCoreLog getMSFCoreLogByUuid(String uuid);

  public void deleteMSFCoreLog(MSFCoreLog msfCoreLog);

  public void deleteMSFCoreFromDate(Date startDate);

  public List<Concept> getAllConceptAnswers(Concept question);

  public List<LocationAttribute> getLocationAttributeByTypeAndLocation(LocationAttributeType type, Location location);

  public IdentifierSource updateIdentifierSource(SequentialIdentifierGenerator identifierSource) throws APIException;

  public List<DropDownFieldOption> getAllConceptAnswerNames(String uuid);

  public Date getDateAtNDaysFromData(Date date, Integer nDays);

  public Integer saveMSFCoreLog(MSFCoreLog msfCoreLog);

  public MSFCoreLog getMSFCoreLog(Integer msfCoreLogId);
}
