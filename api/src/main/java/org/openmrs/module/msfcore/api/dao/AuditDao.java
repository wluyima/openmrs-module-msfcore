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

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("msfcore.AuditDao")
public class AuditDao {

  @Autowired
  DbSessionFactory sessionFactory;

  private DbSession getSession() {
    return sessionFactory.getCurrentSession();
  }

  @SuppressWarnings("unchecked")
  public List<AuditLog> getAuditLogs(Date startDate, Date endDate, List<Event> events, List<Patient> patients,
          List<User> users, List<Provider> providers, List<Location> locations) {
    Criteria criteria = getSession().createCriteria(AuditLog.class);

    if (startDate != null) {
      criteria.add(Restrictions.ge("date", startDate));
    }
    if (endDate != null) {
      criteria.add(Restrictions.le("date", endDate));
    }
    if (events != null) {
      criteria.add(Restrictions.in("event", events));
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

    criteria.addOrder(Order.desc("id"));
    return criteria.list();
  }

  public AuditLog getAuditLogByUuid(String uuid) {
    return (AuditLog) getSession().createQuery("from AuditLog where uuid = :uuid").setString("uuid", uuid)
            .uniqueResult();
  }

  public void deleteAuditLog(AuditLog auditLog) {
    getSession().delete(auditLog);
  }

  public Integer saveAuditLog(AuditLog auditLog) {
    return (Integer) getSession().save(auditLog);
  }

  public AuditLog getAuditLog(Integer auditLogId) {
    return (AuditLog) getSession().get(AuditLog.class, auditLogId);
  }
}
