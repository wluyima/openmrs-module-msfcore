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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.openmrs.module.msfcore.api.util.DateUtils.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hibernate.PropertyValueException;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.ContextAuthenticationException;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.terracotta.modules.ehcache.wan.IllegalConfigurationException;

/**
 * This is a unit test, which verifies logic in MSFCoreService.
 */
public class AuditDaoTest extends BaseModuleContextSensitiveTest {

  @Autowired
  AuditDao auditDao;

  Patient patient;
  Patient anotherPatient;
  User user;
  User anotherUser;
  Provider provider;
  Location location;

  @Before
  public void setup() {
    try {
      executeDataSet("MSFCoreAuditLogs.xml");
    } catch (Exception e) {
      throw new IllegalConfigurationException("MSFCoreAuditLogs.xml not found");
    }
    patient = Context.getPatientService().getPatient(2);
    anotherPatient = Context.getPatientService().getPatient(6);
    user = Context.getUserService().getUser(501);
    anotherUser = Context.getUserService().getUser(502);
    provider = Context.getProviderService().getProvider(1);
    location = Context.getLocationService().getLocation(1);
  }

  @Test
  public void getAuditLogs_shouldRetrieveAllLogsOrderByIdDescWhenNoFiltersAreSet() {
    List<AuditLog> logs = auditDao.getAuditLogs(null, null, null, null, null, null, null);
    assertThat(logs.size(), CoreMatchers.is(8));
    assertThat(logs.get(0).getEvent(), CoreMatchers.is(Event.LOGIN));
    assertThat(logs.get(0).getDetail(), CoreMatchers.is("User: admin: SUCCESS"));
    logs.remove(0);
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
  public void getAuditLogs_shouldRetrieveLogsOrderByIdInDateRangeWhenStartAndEndDateIsSet() {
    List<AuditLog> logs = auditDao.getAuditLogs(parse("2018-04-20", "yyyy-MM-dd"), parse("2018-05-25", "yyyy-MM-dd"), null, null, null,
        null, null);

    assertThat(logs.size(), is(4));
    assertThat(logs.get(0).getId(), is(6));
    assertThat(logs.get(1).getId(), is(5));
    assertThat(logs.get(2).getId(), is(4));
    assertThat(logs.get(3).getId(), is(3));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingEventsWhenEventsAreSet() {
    List<AuditLog> logs = auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-05-25", "yyyy-MM-dd"),
        Arrays.asList(Event.LOGIN, Event.VIEW_PATIENT), null, null, null, null);

    assertThat(logs.size(), is(2));
    assertThat(logs.get(0).getId(), is(2));
    assertThat(logs.get(1).getId(), is(1));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingPatientsWhenPatientsAreSet() {
    List<AuditLog> logs = auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-08-25", "yyyy-MM-dd"),
        Arrays.asList(Event.REGISTER_PATIENT), Arrays.asList(patient), null, null, null);

    assertThat(logs.size(), is(1));
    assertThat(logs.get(0).getId(), is(5));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingUsersWhenUsersAreSet() {
    List<AuditLog> logs = auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-08-25", "yyyy-MM-dd"),
        Arrays.asList(Event.REGISTER_PATIENT), Arrays.asList(patient), Arrays.asList(anotherUser), null, null);

    assertThat(logs.size(), is(1));
    assertThat(logs.get(0).getId(), is(5));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingProvidersWhenProvidersAreSet() {
    List<AuditLog> logs = auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-08-25", "yyyy-MM-dd"),
        Arrays.asList(Event.REGISTER_PATIENT), Arrays.asList(patient), Arrays.asList(anotherUser), Arrays.asList(provider), null);

    assertThat(logs.size(), is(1));
    assertThat(logs.get(0).getId(), is(5));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingLocation() {
    List<AuditLog> logs = auditDao.getAuditLogs(parse("2018-02-20", "yyyy-MM-dd"), parse("2018-08-25", "yyyy-MM-dd"),
        Arrays.asList(Event.REGISTER_PATIENT), Arrays.asList(patient), Arrays.asList(anotherUser), Arrays.asList(provider),
        Arrays.asList(location));

    assertThat(logs.size(), is(1));
    assertThat(logs.get(0).getId(), is(5));
  }

  @Test
  public void getAuditLogByUuid_shouldRetrieveRightLog() {
    AuditLog auditLog = auditDao.getAuditLogByUuid("9e663d66-6b78-11e0-93c3-18a905e00001");
    assertThat(auditLog.getUuid(), is("9e663d66-6b78-11e0-93c3-18a905e00001"));
  }

  @Test
  public void getAuditLog_shouldRetrieveRightLog() throws Exception {
    AuditLog log = auditDao.getAuditLog(1);
    assertThat(log.getId(), is(1));
  }

  @Test
  public void saveAuditLog_shouldPersistLog() {
    AuditLog log = AuditLog.builder().event(Event.REGISTER_PATIENT).detail("registered patient#").patient(anotherPatient).user(user)
        .location(Context.getLocationService().getDefaultLocation()).provider(provider).build();

    Integer id = auditDao.saveAuditLog(log);
    AuditLog savedLog = auditDao.getAuditLog(id);

    AuditLog expected = AuditLog.builder().id(id).uuid(log.getUuid()).date(log.getDate()).event(Event.REGISTER_PATIENT)
        .detail("registered patient#").patient(anotherPatient).user(user).location(Context.getLocationService().getDefaultLocation())
        .provider(provider).build();

    assertThat(savedLog, is(expected));
  }

  @Test
  public void saveAuditLog_shouldPersistLogWithNonMandatoryFields() {
    AuditLog log = AuditLog.builder().event(Event.REGISTER_PATIENT).detail("registered patient#").build();

    Integer id = auditDao.saveAuditLog(log);
    AuditLog savedLog = auditDao.getAuditLog(id);

    AuditLog expected = AuditLog.builder().id(id).uuid(log.getUuid()).date(log.getDate()).event(Event.REGISTER_PATIENT)
        .detail("registered patient#").build();

    assertThat(savedLog, is(expected));
  }

  @Test(expected = PropertyValueException.class)
  public void saveAuditLog_shouldThrowWhenNoEventIsSet() {
    AuditLog log = AuditLog.builder().detail("registered patient#").build();
    auditDao.saveAuditLog(log);
  }

  @Test(expected = PropertyValueException.class)
  public void saveAuditLog_shouldThrowWhenNoDetailIsSet() {
    AuditLog log = AuditLog.builder().event(Event.REGISTER_PATIENT).build();
    auditDao.saveAuditLog(log);
  }

  @Test
  public void deleteAuditLogsToDate_shouldCompletelyDeleteRangedLogs() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    auditDao.deleteAuditLogsToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-23 11:00:00"));

    assertNull(auditDao.getAuditLog(1));
    assertNull(auditDao.getAuditLog(2));
  }

  @Test
  public void getAuditLogs_shouldRetrieveAllLoginAndLogoutLogs() throws ParseException, InterruptedException {
    Context.logout();
    try {
      Context.authenticate("admin", "wrongPass");
    } catch (ContextAuthenticationException e) {
    }
    try {
      Context.authenticate("wrongUser", "test");
    } catch (ContextAuthenticationException e) {
    }
    Context.authenticate("admin", "test");//success
    List<AuditLog> logs = auditDao.getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"), null, null, null, null, null, null);

    assertThat(logs.size(), CoreMatchers.is(5));
    assertThat(logs.get(0).getEvent(), CoreMatchers.is(Event.LOGIN));
    assertThat(logs.get(0).getUser(), CoreMatchers.is(Context.getAuthenticatedUser()));
    assertThat(logs.get(0).getDetail(), CoreMatchers.is("User: admin: SUCCESS"));
    assertThat(logs.get(1).getEvent(), CoreMatchers.is(Event.LOGIN));
    assertNull(logs.get(1).getUser());
    assertThat(logs.get(1).getDetail(), CoreMatchers.is("User: wrongUser: FAIL"));
    assertThat(logs.get(2).getEvent(), CoreMatchers.is(Event.LOGIN));
    assertThat(logs.get(2).getDetail(), CoreMatchers.is("User: admin: FAIL"));
    assertNull(logs.get(2).getUser());
    assertThat(logs.get(3).getEvent(), CoreMatchers.is(Event.LOGOUT));
    assertThat(logs.get(3).getDetail(), CoreMatchers.is("User: admin: SUCCESS"));
    assertThat(logs.get(3).getUser(), CoreMatchers.is(Context.getAuthenticatedUser()));
    assertThat(logs.get(4).getEvent(), CoreMatchers.is(Event.LOGIN));
    assertThat(logs.get(4).getUser(), CoreMatchers.is(Context.getAuthenticatedUser()));
    assertThat(logs.get(4).getDetail(), CoreMatchers.is("User: admin: SUCCESS"));

  }
}
