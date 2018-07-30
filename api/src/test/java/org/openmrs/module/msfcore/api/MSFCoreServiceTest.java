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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.audit.MSFCoreLog;
import org.openmrs.module.msfcore.audit.MSFCoreLog.Event;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * This is a unit test, which verifies logic in MSFCoreService.
 */
public class MSFCoreServiceTest extends BaseModuleContextSensitiveTest {

  @Test
  public void getMSFCoreLogs_shouldRetrieveAllWhenNoFiltersAreSpecified() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(null, null, null, null, null, null, null, null);

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
  public void getMSFCoreLogs_shouldRetrieveLogsMatchingDateRange() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        null, null, null, null, null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(6));
    Assert.assertThat(logs.get(0).getEvent().name(), CoreMatchers.is("VIEW_PATIENT"));
    Assert.assertThat(logs.get(1).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00003"));
    Assert.assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(logs.get(2).getDate()),
        CoreMatchers.is("2018-07-23 20:30:00.0"));
    Assert.assertThat(logs.get(3).getId(), CoreMatchers.is(5));
    Assert.assertThat(logs.get(4).getCreator(), CoreMatchers.is(Context.getUserService().getUser(501)));
    Assert.assertThat(logs.get(5).getDetail(), CoreMatchers.is("basic login log2"));
  }

  @Test
  public void getMSFCoreLogs_shouldRetrieveLogsMatchingEvents() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        Arrays.asList(Event.LOGIN, Event.VIEW_PATIENT), null, null, null, null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(3));
    Assert.assertThat(logs.get(0).getEvent().name(), CoreMatchers.is("LOGIN"));
    Assert.assertThat(logs.get(1).getDetail(), CoreMatchers.is("basic view patient log"));
    Assert.assertThat(logs.get(2).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00007"));
  }

  @Test
  public void getMSFCoreLogs_shouldRetrieveLogsMatchingCreator() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
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
  public void getMSFCoreLogs_shouldRetrieveLogsMatchingPatients() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
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
  public void getMSFCoreLogs_shouldRetrieveLogsMatchingUsers() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        new ArrayList<Event>(EnumSet.allOf(Event.class)), null, null, Arrays.asList(Context.getUserService().getUser(501)), null, null);

    Assert.assertThat(logs.size(), CoreMatchers.is(1));
    Assert.assertThat(logs.get(0).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00001"));
  }

  @Test
  public void getMSFCoreLogs_shouldRetrieveLogsMatchingProviders() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        new ArrayList<Event>(EnumSet.allOf(Event.class)), null, null, null, Arrays.asList(Context.getProviderService().getProvider(1)),
        null);

    Assert.assertThat(logs.size(), CoreMatchers.is(2));
    Assert.assertThat(logs.get(0).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00004"));
    Assert.assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(logs.get(1).getDate()),
        CoreMatchers.is("2018-07-23 20:50:00.0"));
  }

  @Test
  public void getMSFCoreLogs_shouldRetrieveLogsMatchingLocation() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-23"),
        new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-24"),
        new ArrayList<Event>(EnumSet.allOf(Event.class)), null, null, null, Arrays.asList(Context.getProviderService().getProvider(1)),
        Arrays.asList(Context.getLocationService().getLocation(1)));

    Assert.assertThat(logs.size(), CoreMatchers.is(1));
    Assert.assertThat(logs.get(0).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00006"));
  }

  @Test
  public void getMSFCoreLogByUuid_shouldReturnRightLog() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    MSFCoreLog msfCoreLog = Context.getService(MSFCoreService.class).getMSFCoreLogByUuid("9e663d66-6b78-11e0-93c3-18a905e00001");
    Assert.assertThat(msfCoreLog.getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00001"));
    Assert.assertThat(msfCoreLog.getEvent(), CoreMatchers.is(Event.LOGIN));
  }

  @Test
  public void deleteMSFCoreLog_shouldCompletelyDeleteLog() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    Assert.assertThat(Context.getService(MSFCoreService.class).getMSFCoreLogs(null, null, null, null, null, null, null, null).size(),
        CoreMatchers.is(7));
    MSFCoreLog msfCoreLog = Context.getService(MSFCoreService.class).getMSFCoreLogByUuid("9e663d66-6b78-11e0-93c3-18a905e00001");
    Assert.assertThat(msfCoreLog.getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00001"));
    Assert.assertThat(msfCoreLog.getEvent(), CoreMatchers.is(Event.LOGIN));
    Context.getService(MSFCoreService.class).deleteMSFCoreLog(msfCoreLog);
    Assert.assertThat(Context.getService(MSFCoreService.class).getMSFCoreLogs(null, null, null, null, null, null, null, null).size(),
        CoreMatchers.is(6));
  }

  @Test
  public void deleteMSFCoreLogsInLastNMonths_shouldCompletelyDeleteRangedLogs() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    Assert.assertThat(Context.getService(MSFCoreService.class).getMSFCoreLogs(null, null, null, null, null, null, null, null).size(),
        CoreMatchers.is(7));
    Context.getService(MSFCoreService.class)
        .deleteMSFCoreFromDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-23 20:29:59"));
    List<MSFCoreLog> logs = Context.getService(MSFCoreService.class).getMSFCoreLogs(null, null, null, null, null, null, null, null);
    Assert.assertThat(logs.size(), CoreMatchers.is(3));
    Assert.assertThat(logs.get(0).getDetail(), CoreMatchers.is("basic login log"));
    Assert.assertThat(logs.get(1).getEvent().name(), CoreMatchers.is("VIEW_PATIENT"));
    Assert.assertThat(logs.get(2).getUuid(), CoreMatchers.is("9e663d66-6b78-11e0-93c3-18a905e00003"));
  }

  @Test
  public void getAllConceptAnswers_shouldReturnConceptsFromAnswers() {
    Assert.assertNotNull(Context.getService(MSFCoreService.class));
    List<Concept> answers = Context.getService(MSFCoreService.class).getAllConceptAnswers(Context.getConceptService().getConcept(4));
    Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(5)));
    Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(6)));
  }

  @Test
  public void getAllConceptAnswerNames_shouldReturnRightDropDownOptions() {
    Assert.assertNotNull(Context.getService(MSFCoreService.class));
    Concept concept = Context.getConceptService().getConcept(4);
    List<DropDownFieldOption> options = Context.getService(MSFCoreService.class).getAllConceptAnswerNames(concept.getUuid());
    Assert.assertEquals("CIVIL STATUS", concept.getName().getName());
    Assert.assertEquals("5", options.get(0).getValue());
    Assert.assertEquals("SINGLE", options.get(0).getLabel());
    Assert.assertEquals("6", options.get(1).getValue());
    Assert.assertEquals("MARRIED", options.get(1).getLabel());
  }

  @Test
  public void getDateAtNDaysFromData_shouldEvalueteCorrectly() throws ParseException {
    Date date = Context.getService(MSFCoreService.class)
        .getDateAtNDaysFromData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-30 20:29:59"), 30);
    Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-06-30 20:29:59"), date);
  }

  @Test
  public void getMSFCoreLog_shouldRetrieveRightLog() throws Exception {
    executeDataSet("MSFCoreLogs.xml");
    MSFCoreLog log = Context.getService(MSFCoreService.class).getMSFCoreLog(1);
    Assert.assertEquals("9e663d66-6b78-11e0-93c3-18a905e00001", log.getUuid());
    Assert.assertEquals("basic login log", log.getDetail());
    Assert.assertEquals(Context.getUserService().getUser(501), log.getCreator());
    Assert.assertEquals(Context.getUserService().getUser(501), log.getUser());
  }

  @Test
  public void saveMSFCoreLog_shouldPersistLogWithMandetoryFields() {
    MSFCoreLog savedLog = Context.getService(MSFCoreService.class).getMSFCoreLog(Context.getService(MSFCoreService.class).saveMSFCoreLog(
        new MSFCoreLog(Event.LOGIN, "user logging in", Context.getUserService().getUser(501))));
    Assert.assertEquals("user logging in", savedLog.getDetail());
    Assert.assertNotNull(savedLog.getId());
    Assert.assertNotNull(savedLog.getUuid());
    Assert.assertNull(savedLog.getPatient());
    Assert.assertNull(savedLog.getUser());
    Assert.assertNull(savedLog.getLocation());
    Assert.assertNull(savedLog.getProvider());
  }

  @Test
  public void saveMSFCoreLog_shouldPersistLogWithNonMandetoryFields() {
    MSFCoreLog log = new MSFCoreLog(Event.REGISTER_PATIENT, "Registering a patient", Context.getUserService().getUser(501));
    log.setPatient(Context.getPatientService().getPatient(6));
    MSFCoreLog savedLog = Context.getService(MSFCoreService.class).getMSFCoreLog(
        Context.getService(MSFCoreService.class).saveMSFCoreLog(log));
    Assert.assertEquals("Registering a patient", savedLog.getDetail());
    Assert.assertNotNull(savedLog.getId());
    Assert.assertNotNull(savedLog.getUuid());
    Assert.assertEquals(log.getId(), savedLog.getId());
    Assert.assertEquals(log.getUuid(), savedLog.getUuid());
    Assert.assertEquals(Context.getPatientService().getPatient(6), savedLog.getPatient());
  }
}
