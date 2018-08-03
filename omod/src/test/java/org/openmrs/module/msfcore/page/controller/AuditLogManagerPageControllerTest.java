package org.openmrs.module.msfcore.page.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.api.util.DateUtils;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.ui.framework.page.PageModel;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Context.class})
public class AuditLogManagerPageControllerTest {
  AuditLogManagerPageController controller;

  @Mock
  private AuditService auditService;

  @Mock
  private HttpServletRequest request;

  PageModel model;

  @Mock
  private UserService userService;

  AuditLog viewPatient;

  AuditLog registerPatient;

  AuditLog login;

  User user;

  Patient patient;

  @Before
  public void before() throws ParseException {
    controller = new AuditLogManagerPageController();
    model = new PageModel();
    Assert.assertNotNull(auditService);
    Assert.assertNotNull(request);
    Assert.assertNotNull(userService);
    UserContext userContext = Mockito.mock(UserContext.class);
    Context.setUserContext(userContext);
    Mockito.when(userContext.isAuthenticated()).thenReturn(true);
    PowerMockito.mockStatic(Context.class);
    Mockito.when(Context.getUserService()).thenReturn(userService);
    Mockito.when(Context.getUserService().getAllUsers()).thenReturn(new ArrayList<User>());
    user = new User(1);
    user.setUsername("hacker");
    patient = Mockito.mock(Patient.class);
    viewPatient = AuditLog.builder().event(Event.VIEW_PATIENT).detail("viewed patient dashboard")
        .patient(patient).date(DateUtils.parse("2018-07-01", "yyyy-MM-dd")).build();
    registerPatient =
        AuditLog.builder().event(Event.REGISTER_PATIENT).detail("registered patient")
            .patient(new Patient(2)).date(DateUtils.parse("2018-07-25", "yyyy-MM-dd")).build();
    login = AuditLog.builder().event(Event.LOGIN).detail("user logged in").user(user)
        .date(DateUtils.parse("2018-08-01", "yyyy-MM-dd")).build();
  }

  @SuppressWarnings("unchecked")
  @Test
  public void get_defaultWithoutLogs() {
    controller.controller(model, auditService, "", "", request, null, "");
    Assert.assertEquals(0, ((List<AuditLog>) model.getAttribute("auditLogs")).size());
    Assert.assertEquals("", (String) model.getAttribute("startTime"));
    Assert.assertEquals("", (String) model.getAttribute("endTime"));
    Assert.assertEquals(Arrays.asList(Event.values()), (List<Event>) model.getAttribute("events"));
    Assert.assertNull(model.getAttribute("selectedEvents"));
    Assert.assertEquals(0, ((List<User>) model.getAttribute("userSuggestions")).size());
    Assert.assertEquals("", (String) model.getAttribute("selectedViewer"));
    Assert.assertEquals("", (String) model.getAttribute("patientDisplay"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void get_defaultWithoutLogsButWithUserSuggestions() {
    // user suggestions should include both unique username and system id
    User user2 = new User(2);
    user2.setSystemId("3-4");
    user2.setUsername("hacker2");
    User user3 = new User(2);
    user3.setSystemId("3-4");
    user3.setUsername("hacker3");
    Mockito.when(Context.getUserService().getAllUsers()).thenReturn(Arrays.asList(user, user2, user3));
    controller.controller(model, auditService, "", "", request, null, "");
    Assert.assertEquals(0, ((List<AuditLog>) model.getAttribute("auditLogs")).size());
    Assert.assertEquals("", (String) model.getAttribute("startTime"));
    Assert.assertEquals("", (String) model.getAttribute("endTime"));
    Assert.assertEquals(Arrays.asList(Event.values()), (List<Event>) model.getAttribute("events"));
    Assert.assertNull(model.getAttribute("selectedEvents"));
    List<String> userSuggestions = (List<String>) model.getAttribute("userSuggestions");
    Assert.assertEquals(4, userSuggestions.size());
    Assert.assertThat(userSuggestions, CoreMatchers.hasItems("hacker", "hacker2", "3-4", "hacker3"));
    Assert.assertEquals("", (String) model.getAttribute("selectedViewer"));
    Assert.assertEquals("", (String) model.getAttribute("patientDisplay"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void get_defaultWithLogs() {
    Mockito.when(auditService.getAuditLogs(null, null, null, null, null, null, null))
        .thenReturn(Arrays.asList(viewPatient, registerPatient, login));
    controller.controller(model, auditService, "", "", request, null, "");
    List<AuditLog> logs = (List<AuditLog>) model.getAttribute("auditLogs");
    Assert.assertThat(logs.size(), CoreMatchers.is(3));
    Assert.assertThat(logs.get(0), CoreMatchers.is(viewPatient));
    Assert.assertThat(logs.get(1), CoreMatchers.is(registerPatient));
    Assert.assertThat(logs.get(2), CoreMatchers.is(login));
    Assert.assertEquals("", (String) model.getAttribute("startTime"));
    Assert.assertEquals("", (String) model.getAttribute("endTime"));
    Assert.assertEquals(Arrays.asList(Event.values()), (List<Event>) model.getAttribute("events"));
    Assert.assertNull(model.getAttribute("selectedEvents"));
    Assert.assertEquals(0, ((List<User>) model.getAttribute("userSuggestions")).size());
    Assert.assertEquals("", (String) model.getAttribute("selectedViewer"));
    Assert.assertEquals("", (String) model.getAttribute("patientDisplay"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void post_filter_inRangeWithLogs() throws ParseException {
    Mockito
        .when(auditService.getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-22"),
            new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-29"), null, null, null, null, null))
        .thenReturn(Arrays.asList(registerPatient));
    controller.controller(model, auditService, "2018-07-22 00:00:00", "2018-07-29 00:00:00", request, null, "");
    List<AuditLog> logs = (List<AuditLog>) model.getAttribute("auditLogs");
    Assert.assertThat(logs.size(), CoreMatchers.is(1));
    Assert.assertThat(logs.get(0), CoreMatchers.is(registerPatient));
    Assert.assertEquals("2018-07-22 00:00:00", (String) model.getAttribute("startTime"));
    Assert.assertEquals("2018-07-29 00:00:00", (String) model.getAttribute("endTime"));
    Assert.assertEquals(Arrays.asList(Event.values()), (List<Event>) model.getAttribute("events"));
    Assert.assertNull(model.getAttribute("selectedEvents"));
    Assert.assertEquals(0, ((List<User>) model.getAttribute("userSuggestions")).size());
    Assert.assertEquals("", (String) model.getAttribute("selectedViewer"));
    Assert.assertEquals("", (String) model.getAttribute("patientDisplay"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void post_filter_withSelectedEvents() throws ParseException {
    Mockito
        .when(
            auditService.getAuditLogs(null, null, Arrays.asList(Event.VIEW_PATIENT, Event.REGISTER_PATIENT), null, null, null, null))
        .thenReturn(Arrays.asList(viewPatient, registerPatient));
    Mockito.when(request.getParameterValues("events")).thenReturn(new String[]{Event.VIEW_PATIENT.name(), Event.REGISTER_PATIENT.name()});
    controller.controller(model, auditService, "", "", request, null, "");
    List<AuditLog> logs = (List<AuditLog>) model.getAttribute("auditLogs");
    Assert.assertThat(logs.size(), CoreMatchers.is(2));
    Assert.assertThat(logs.get(0), CoreMatchers.is(viewPatient));
    Assert.assertThat(logs.get(1), CoreMatchers.is(registerPatient));
    Assert.assertEquals("", (String) model.getAttribute("startTime"));
    Assert.assertEquals("", (String) model.getAttribute("endTime"));
    Assert.assertEquals(Arrays.asList(Event.values()), (List<Event>) model.getAttribute("events"));
    String[] events = (String[]) model.getAttribute("selectedEvents");
    Assert.assertThat(events[0], CoreMatchers.is(Event.VIEW_PATIENT.name()));
    Assert.assertThat(events[1], CoreMatchers.is(Event.REGISTER_PATIENT.name()));
    Assert.assertEquals(0, ((List<User>) model.getAttribute("userSuggestions")).size());
    Assert.assertEquals("", (String) model.getAttribute("selectedViewer"));
    Assert.assertEquals("", (String) model.getAttribute("patientDisplay"));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void post_filter_inRange_selectedEvents() throws ParseException {
    Mockito.when(Context.getUserService().getUserByUsername("hacker")).thenReturn(user);
    Mockito
        .when(auditService.getAuditLogs(new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-22"),
            new SimpleDateFormat("yyyy-MM-dd").parse("2018-07-29"), Arrays.asList(Event.REGISTER_PATIENT), null, null, null, null))
        .thenReturn(Arrays.asList(registerPatient));
    Mockito.when(request.getParameterValues("events")).thenReturn(new String[]{Event.REGISTER_PATIENT.name()});
    controller.controller(model, auditService, "2018-07-22 00:00:00", "2018-07-29 00:00:00", request, null, "");
    List<AuditLog> logs = (List<AuditLog>) model.getAttribute("auditLogs");
    Assert.assertThat(logs.size(), CoreMatchers.is(1));
    Assert.assertThat(logs.get(0), CoreMatchers.is(registerPatient));
    Assert.assertThat(logs.get(0).getEvent(), CoreMatchers.is(Event.REGISTER_PATIENT));
    Assert.assertEquals("2018-07-22 00:00:00", (String) model.getAttribute("startTime"));
    Assert.assertEquals("2018-07-29 00:00:00", (String) model.getAttribute("endTime"));
    Assert.assertEquals(Arrays.asList(Event.values()), (List<Event>) model.getAttribute("events"));
    String[] events = (String[]) model.getAttribute("selectedEvents");
    Assert.assertThat(events.length, CoreMatchers.is(1));
    Assert.assertThat(events[0], CoreMatchers.is(Event.REGISTER_PATIENT.name()));
    Assert.assertEquals(0, ((List<User>) model.getAttribute("userSuggestions")).size());
    Assert.assertEquals("", (String) model.getAttribute("selectedViewer"));
    Assert.assertEquals("", (String) model.getAttribute("patientDisplay"));
  }

}
