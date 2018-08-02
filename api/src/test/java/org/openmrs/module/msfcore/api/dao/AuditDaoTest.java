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

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.msfcore.api.util.DateUtils.parse;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hibernate.PropertyValueException;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This is a unit test, which verifies logic in MSFCoreService.
 */
public class AuditDaoTest extends BaseModuleContextSensitiveTest {

  @Autowired
  AuditDao auditDao;

  @Test
  public void getAuditLogs_shouldRetrieveAllLogsOrderByIdDescWhenNoFiltersAreSet()
      throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = auditDao.getAuditLogs(null, null, null, null, null, null, null);

    assertThat(logs.size(), CoreMatchers.is(7));
    assertThat(logs.get(0).getId(), is(7));
    assertThat(logs.get(1).getId(), is(6));
    assertThat(logs.get(2).getId(), is(5));
    assertThat(logs.get(3).getId(), is(4));
    assertThat(logs.get(4).getId(), is(3));
    assertThat(logs.get(5).getId(), is(2));
    assertThat(logs.get(6).getId(), is(1));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsOrderByIdInDateRangeWhenStartAndEndDateIsSet()
      throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = auditDao.getAuditLogs(parse("2018-04-20", "yyyy-MM-dd"),
        parse("2018-05-25", "yyyy-MM-dd"), null, null, null, null, null);

    assertThat(logs.size(), is(4));
    assertThat(logs.get(0).getId(), is(6));
    assertThat(logs.get(1).getId(), is(5));
    assertThat(logs.get(2).getId(), is(4));
    assertThat(logs.get(3).getId(), is(3));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingEventsWhenEventsAreSet()
      throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs =
        auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-05-25", "yyyy-MM-dd"),
            Arrays.asList(Event.LOGIN, Event.VIEW_PATIENT), null, null, null, null);

    assertThat(logs.size(), is(2));
    assertThat(logs.get(0).getId(), is(2));
    assertThat(logs.get(1).getId(), is(1));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingPatientsWhenPatientsAreSet() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs =
        auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-08-25", "yyyy-MM-dd"),
            Arrays.asList(Event.REGISTER_PATIENT),
            Arrays.asList(Context.getPatientService().getPatient(2)), null, null, null);

    assertThat(logs.size(), is(1));
    assertThat(logs.get(0).getId(), is(5));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingUsersWhenUsersAreSet() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs =
        auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-08-25", "yyyy-MM-dd"),
            Arrays.asList(Event.REGISTER_PATIENT),
            Arrays.asList(Context.getPatientService().getPatient(2)),
            Arrays.asList(Context.getUserService().getUser(501)), null, null);

    assertThat(logs.size(), is(1));
    assertThat(logs.get(0).getId(), is(5));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingProvidersWhenProvidersAreSet()
      throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs =
        auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-08-25", "yyyy-MM-dd"),
            Arrays.asList(Event.REGISTER_PATIENT),
            Arrays.asList(Context.getPatientService().getPatient(2)),
            Arrays.asList(Context.getUserService().getUser(501)),
            Arrays.asList(Context.getProviderService().getProvider(1)), null);

    assertThat(logs.size(), is(1));
    assertThat(logs.get(0).getId(), is(5));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingLocation() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs =
        auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-08-25", "yyyy-MM-dd"),
            Arrays.asList(Event.REGISTER_PATIENT),
            Arrays.asList(Context.getPatientService().getPatient(2)),
            Arrays.asList(Context.getUserService().getUser(501)),
            Arrays.asList(Context.getProviderService().getProvider(1)),
            Arrays.asList(Context.getLocationService().getLocation(1)));

    assertThat(logs.size(), is(1));
    assertThat(logs.get(0).getId(), is(5));
  }

  @Test
  public void getAuditLogByUuid_shouldRetrieveRightLog() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    AuditLog auditLog = auditDao.getAuditLogByUuid("9e663d66-6b78-11e0-93c3-18a905e00001");
    assertThat(auditLog.getUuid(), is("9e663d66-6b78-11e0-93c3-18a905e00001"));
  }

  @Test
  public void deleteAuditLog_shouldDeleteLog() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    AuditLog auditLog = auditDao.getAuditLog(1);
    assertThat(auditLog.getId(), is(1));

    auditDao.deleteAuditLog(auditLog);
    auditLog = auditDao.getAuditLog(1);

    assertThat(auditLog, is(nullValue()));
  }

  @Test
  public void getAuditLog_shouldRetrieveRightLog() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    AuditLog log = auditDao.getAuditLog(1);
    assertThat(log.getId(), is(1));
  }

  @Test
  public void saveAuditLog_shouldPersistLog() throws Exception {
    AuditLog log = AuditLog.builder()
        .event(Event.REGISTER_PATIENT)
        .detail("registered patient#")
        .patient(Context.getPatientService().getPatient(6))
        .user(Context.getAuthenticatedUser())
        .location(Context.getLocationService().getDefaultLocation())
        .provider(Context.getProviderService().getProvider(1))
        .build();

    Integer id = auditDao.saveAuditLog(log);
    AuditLog savedLog = auditDao.getAuditLog(id);

    AuditLog expected = AuditLog.builder()
        .id(id)
        .uuid(log.getUuid())
        .date(log.getDate())
        .event(Event.REGISTER_PATIENT)
        .detail("registered patient#")
        .patient(Context.getPatientService().getPatient(6))
        .user(Context.getAuthenticatedUser())
        .location(Context.getLocationService().getDefaultLocation())
        .provider(Context.getProviderService().getProvider(1))
        .build();

    assertThat(savedLog, is(expected));
  }

  @Test
  public void saveAuditLog_shouldPersistLogWithNonMandatoryFields() throws Exception {
    AuditLog log = AuditLog.builder()
        .event(Event.REGISTER_PATIENT)
        .detail("registered patient#")
        .build();

    Integer id = auditDao.saveAuditLog(log);
    AuditLog savedLog = auditDao.getAuditLog(id);

    AuditLog expected = AuditLog.builder()
        .id(id)
        .uuid(log.getUuid())
        .date(log.getDate())
        .event(Event.REGISTER_PATIENT)
        .detail("registered patient#")
        .build();

    assertThat(savedLog, is(expected));
  }

  @Test(expected = PropertyValueException.class)
  public void saveAuditLog_shouldThrowWhenNoEventIsSet() {
    AuditLog log = AuditLog.builder()
        .detail("registered patient#")
        .build();
    auditDao.saveAuditLog(log);
  }

  @Test(expected = PropertyValueException.class)
  public void saveAuditLog_shouldThrowWhenNoDetailIsSet() {
    AuditLog log = AuditLog.builder()
        .event(Event.REGISTER_PATIENT)
        .build();
    auditDao.saveAuditLog(log);
  }
}
