/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore.api.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.msfcore.audit.MSFCoreLog;
import org.openmrs.module.msfcore.audit.MSFCoreLog.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("msfcore.MSFCoreDao")
public class MSFCoreDao {

  @Autowired
  DbSessionFactory sessionFactory;

  private DbSession getSession() {
    return sessionFactory.getCurrentSession();
  }

  @SuppressWarnings("unchecked")
  public List<MSFCoreLog> getMSFCoreLogs(Date startDate, Date endDate, List<Event> events, User creator, List<Patient> patients,
          List<User> users, List<Provider> providers, List<Location> locations) {
    Criteria criteria = getSession().createCriteria(MSFCoreLog.class);

    if (startDate != null) {
      criteria.add(Restrictions.ge("date", startDate));
    }
    if (endDate != null) {
      criteria.add(Restrictions.le("date", endDate));
    }
    if (events != null) {
      criteria.add(Restrictions.in("event", events));
    }
    if (creator != null) {
      criteria.add(Restrictions.eq("creator", creator));
    }
    if (users != null) {
      criteria.add(Restrictions.in("user", users));
    }
    if (patients != null) {
      criteria.add(Restrictions.in("patient", patients));
    }
    if (providers != null) {
      criteria.add(Restrictions.in("provider", providers));
    }
    if (locations != null) {
      criteria.add(Restrictions.in("location", locations));
    }
    return criteria.list();
  }

  public MSFCoreLog getMSFCoreLogByUuid(String uuid) {
    return (MSFCoreLog) getSession().createQuery("from MSFCoreLog where uuid = :uuid").setString("uuid", uuid)
            .uniqueResult();
  }

  public void deleteMSFCoreLog(MSFCoreLog msfCoreLog) {
    getSession().delete(msfCoreLog);
  }

  @SuppressWarnings("unchecked")
  public List<Concept> getAllConceptAnswers(Concept question) {
    List<Concept> answers = null;
    if (question != null && question.getDatatype().isCoded()) {
      answers = new ArrayList<Concept>();
      for (ConceptAnswer answer : (List<ConceptAnswer>) getSession().createCriteria(ConceptAnswer.class)
          .add(Restrictions.eq("concept", question)).list()) {
        answers.add(answer.getAnswerConcept());
      }
    }
    return answers;
  }

  @SuppressWarnings("unchecked")
  public List<LocationAttribute> getLocationAttributeByTypeAndLocation(LocationAttributeType type,
      Location location) {
    return getSession().createCriteria(LocationAttribute.class).add(Restrictions.eq("location", location))
        .add(Restrictions.eq("attributeType", type)).list();
  }

  @Transactional
  public IdentifierSource updateIdentifierSource(SequentialIdentifierGenerator identifierSource) throws APIException {
    DbSession currentSession = sessionFactory.getCurrentSession();
    SequentialIdentifierGenerator source = (SequentialIdentifierGenerator) currentSession
        .load(SequentialIdentifierGenerator.class, identifierSource.getId());
    source.setPrefix(identifierSource.getPrefix());
    source.setDateChanged(new Date());
    currentSession.update(identifierSource);
    return identifierSource;
  }

  public Integer saveMSFCoreLog(MSFCoreLog msfCoreLog) {
    return (Integer) getSession().save(msfCoreLog);
  }

  public MSFCoreLog getMSFCoreLog(Integer msfCoreLogId) {
    return (MSFCoreLog) getSession().get(MSFCoreLog.class, msfCoreLogId);
  }
}
