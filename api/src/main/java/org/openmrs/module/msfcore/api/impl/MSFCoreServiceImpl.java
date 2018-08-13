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
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.api.dao.MSFCoreDao;

public class MSFCoreServiceImpl extends BaseOpenmrsService implements MSFCoreService {

  MSFCoreDao dao;

  /**
   * Injected in moduleApplicationContext.xml
   */
  public void setDao(MSFCoreDao dao) {
    this.dao = dao;
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
}
