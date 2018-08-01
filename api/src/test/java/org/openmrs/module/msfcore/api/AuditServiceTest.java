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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * This is a unit test, which verifies logic in MSFCoreService.
 */
public class AuditServiceTest extends BaseModuleContextSensitiveTest {

  @Test
  public void getAuditLogs_shouldRetrieveAllWhenNoFiltersAreSpecified() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(null, null, null, null, null, null, null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(7));
    Assert.assertThat(logs.get(0).getDetail(), CoreMatchers.is("basic login log"));
    Assert.assertThat(logs.get(1).getEvent().name(), CoreMatchers.is("VIEW_PATIENT"));
    Assert.assertThat(logs.get(2).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00003"));
    Assert.assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(logs.get(3).getDate()),
        CoreMatchers.is("2018-07-23 20:30:00.0"));
    Assert.assertThat(logs.get(4).getId(), CoreMatchers.is(5));
    Assert.assertThat(logs.get(5).getCreator(), CoreMatchers.is(Context.getUserService().getUser(501)));
    Assert.assertThat(logs.get(6).getDetail(), CoreMatchers.is("basic login log2"));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingDateRange() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-23"),
        new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2018-07-23 20:59"),
        null, null, null, null, null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(5));
    Assert.assertThat(logs.get(0).getEvent().name(), CoreMatchers.is("VIEW_PATIENT"));
    Assert.assertThat(logs.get(1).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00003"));
    Assert.assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(logs.get(2).getDate()),
        CoreMatchers.is("2018-07-23 20:30:00.0"));
    Assert.assertThat(logs.get(3).getId(), CoreMatchers.is(5));
    Assert.assertThat(logs.get(4).getCreator(), CoreMatchers.is(Context.getUserService().getUser(501)));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingEvents() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        Arrays.asList(Event.LOGIN, Event.VIEW_PATIENT), null, null, null, null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(3));
    Assert.assertThat(logs.get(0).getEvent().name(), CoreMatchers.is("LOGIN"));
    Assert.assertThat(logs.get(1).getDetail(), CoreMatchers.is("basic view patient log"));
    Assert.assertThat(logs.get(2).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00007"));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingCreator() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        null, Context.getUserService().getUser(501), null, null, null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(7));
    Assert.assertThat(logs.get(0).getDetail(), CoreMatchers.is("basic login log"));
    Assert.assertThat(logs.get(1).getEvent().name(), CoreMatchers.is("VIEW_PATIENT"));
    Assert.assertThat(logs.get(2).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00003"));
    Assert.assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(logs.get(3).getDate()),
        CoreMatchers.is("2018-07-23 20:30:00.0"));
    Assert.assertThat(logs.get(4).getId(), CoreMatchers.is(5));
    Assert.assertThat(logs.get(5).getCreator(), CoreMatchers.is(Context.getUserService().getUser(501)));
    Assert.assertThat(logs.get(6).getDetail(), CoreMatchers.is("basic login log2"));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingPatients() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        new ArrayList<Event>(EnumSet.allOf(Event.class)), Context.getUserService().getUser(501),
        Arrays.asList(Context.getPatientService().getPatient(6)), null, null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(4));
    Assert.assertThat(logs.get(0).getEvent().name(), CoreMatchers.is("REGISTER_PATIENT"));
    Assert.assertThat(logs.get(1).getId(), CoreMatchers.is(4));
    Assert.assertThat(logs.get(2).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00005"));
    Assert.assertThat(logs.get(3).getDetail(), CoreMatchers.is("basic register patient log4"));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingUsers() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        new ArrayList<Event>(EnumSet.allOf(Event.class)), null, null, Arrays.asList(Context.getUserService().getUser(501)), null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(1));
    Assert.assertThat(logs.get(0).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00001"));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingProviders() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        new ArrayList<Event>(EnumSet.allOf(Event.class)), null, null, null, Arrays.asList(Context.getProviderService().getProvider(1)),
        null);

    Assert.assertThat(logs.size(), CoreMatchers.is(2));
    Assert.assertThat(logs.get(0).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00004"));
    Assert.assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(logs.get(1).getDate()),
        CoreMatchers.is("2018-07-23 20:50:00.0"));
  }

  @Test
  public void getAuditLogs_shouldRetrieveLogsMatchingLocation() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        new ArrayList<Event>(EnumSet.allOf(Event.class)), null, null, null, Arrays.asList(Context.getProviderService().getProvider(1)),
        Arrays.asList(Context.getLocationService().getLocation(1)));

    Assert.assertThat(logs.size(), CoreMatchers.is(1));
    Assert.assertThat(logs.get(0).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00006"));
  }

  @Test
  public void getAuditLogByUuid_shouldReturnRightLog() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    AuditLog auditLog = Context.getService(AuditService.class).getAuditLogByUuid("9e663d66-6b78-11e0-93c3-18a905e00001");
    Assert.assertThat(auditLog.getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00001"));
    Assert.assertThat(auditLog.getEvent(), CoreMatchers.is(Event.LOGIN));
  }

  @Test
  public void deleteAuditLog_shouldCompletelyDeleteLog() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    Assert.assertThat(Context.getService(AuditService.class).getAuditLogs(null, null, null, null, null, null, null, null).size(),
        CoreMatchers.is(7));
    AuditLog auditLog = Context.getService(AuditService.class).getAuditLogByUuid("9e663d66-6b78-11e0-93c3-18a905e00001");
    Assert.assertThat(auditLog.getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00001"));
    Assert.assertThat(auditLog.getEvent(), CoreMatchers.is(Event.LOGIN));
    Context.getService(AuditService.class).deleteAuditLog(auditLog);
    Assert.assertThat(Context.getService(AuditService.class).getAuditLogs(null, null, null, null, null, null, null, null).size(),
        CoreMatchers.is(6));
  }

  @Test
  public void deleteAuditLogsInLastNMonths_shouldCompletelyDeleteRangedLogs() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    Assert.assertThat(Context.getService(AuditService.class).getAuditLogs(null, null, null, null, null, null, null, null).size(),
        CoreMatchers.is(7));
    Context.getService(AuditService.class)
        .deleteAuditLogsToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-23 20:39:59"));
    List<AuditLog> logs = Context.getService(AuditService.class).getAuditLogs(null, null, null, null, null, null, null, null);
    Assert.assertThat(logs.size(), CoreMatchers.is(3));
    Assert.assertThat(logs.get(0).getDetail(), CoreMatchers.is("basic register patient log3"));
    Assert.assertThat(logs.get(1).getEvent().name(), CoreMatchers.is("REGISTER_PATIENT"));
    Assert.assertThat(logs.get(2).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00007"));
  }

  @Test
  public void getAuditLog_shouldRetrieveRightLog() throws Exception {
    executeDataSet("MSFCoreAuditLogs.xml");
    AuditLog log = Context.getService(AuditService.class).getAuditLog(1);
    Assert.assertEquals("9e663d66-6b78-11e0-93c3-18a905e00001", log.getUuid());
    Assert.assertEquals("basic login log", log.getDetail());
    Assert.assertEquals(Context.getUserService().getUser(501), log.getCreator());
    Assert.assertEquals(Context.getUserService().getUser(501), log.getUser());
  }

  @Test
  public void saveAuditLog_shouldPersistLogWithMandetoryFields() {
    AuditLog savedLog = Context.getService(AuditService.class).getAuditLog(Context.getService(AuditService.class).saveAuditLog(
        new AuditLog(Event.LOGIN, "user logging in", Context.getUserService().getUser(501))));
    Assert.assertEquals("user logging in", savedLog.getDetail());
    Assert.assertNotNull(savedLog.getId());
    Assert.assertNotNull(savedLog.getUuid());
    Assert.assertNull(savedLog.getPatient());
    Assert.assertNull(savedLog.getUser());
    Assert.assertNull(savedLog.getLocation());
    Assert.assertNull(savedLog.getProvider());
  }

  @Test
  public void saveAuditLog_shouldPersistLogWithNonMandetoryFields() {
    AuditLog log = new AuditLog(Event.REGISTER_PATIENT, "Registering a patient", Context.getUserService().getUser(501));
    log.setPatient(Context.getPatientService().getPatient(6));
    AuditLog savedLog = Context.getService(AuditService.class).getAuditLog(
        Context.getService(AuditService.class).saveAuditLog(log));
    Assert.assertEquals("Registering a patient", savedLog.getDetail());
    Assert.assertNotNull(savedLog.getId());
    Assert.assertNotNull(savedLog.getUuid());
    Assert.assertEquals(log.getId(), savedLog.getId());
    Assert.assertEquals(log.getUuid(), savedLog.getUuid());
    Assert.assertEquals(Context.getPatientService().getPatient(6), savedLog.getPatient());
  }
}
