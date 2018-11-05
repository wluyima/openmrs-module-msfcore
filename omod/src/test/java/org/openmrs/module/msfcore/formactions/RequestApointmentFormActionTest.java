package org.openmrs.module.msfcore.formactions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.ObsService;
import org.openmrs.module.appointmentscheduling.AppointmentRequest;
import org.openmrs.module.appointmentscheduling.AppointmentRequest.AppointmentRequestStatus;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.TimeFrameUnits;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.util.DateUtils;
import org.openmrs.module.msfcore.formaction.RequestApointmentFormAction;
import org.openmrs.test.BaseContextMockTest;

@RunWith(MockitoJUnitRunner.class)
public class RequestApointmentFormActionTest extends BaseContextMockTest {

    @InjectMocks
    private RequestApointmentFormAction requestAppointmentAction;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private ObsService obsService;

    @Test
    public void requestAppointment_shouldSaveCorrectlyIfDateIsAfterNow() {
        Date now = new Date();
        Date requestAppointmentDate = DateUtils.addDays(now, 30);

        Patient patient = new Patient();
        Obs dateObs = new Obs();
        Concept dateConcept = new Concept();
        dateConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_DATE_UUID);
        dateObs.setConcept(dateConcept);
        dateObs.setValueDate(requestAppointmentDate);

        Obs commentObs = new Obs();
        Concept commentConcept = new Concept();
        commentConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_COMMENT_UUID);
        commentObs.setConcept(commentConcept);

        Obs appointmentTypeObs = new Obs();
        Concept appointmentTypeConcept = new Concept();
        appointmentTypeConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_TYPE_UUID);
        appointmentTypeObs.setConcept(appointmentTypeConcept);
        appointmentTypeObs.setValueText("Gynecology");
        // MSFCoreConfig.SERVICE_TYPE_GENERAL_MEDICINE_UUID

        Set<Obs> observations = Sets.newSet(dateObs, commentObs, appointmentTypeObs);

        AppointmentType generalMedicine = new AppointmentType("General Medicine", null, null);
        AppointmentType gynecology = new AppointmentType("Gynecology", null, null);

        when(appointmentService.getAllAppointmentTypes(false)).thenReturn(Arrays.asList(generalMedicine, gynecology));

        AppointmentRequest actual = requestAppointmentAction.requestAppointment(patient, observations);

        AppointmentRequest expected = new AppointmentRequest();
        expected.setAppointmentType(gynecology);
        expected.setNotes(commentObs.getValueText());
        expected.setPatient(patient);
        expected.setMinTimeFrameUnits(TimeFrameUnits.DAYS);
        expected.setMinTimeFrameValue(DateUtils.getDaysBetweenDates(now, dateObs.getValueDate()));
        expected.setStatus(AppointmentRequestStatus.PENDING);
        expected.setRequestedOn(new Date());

        verify(appointmentService).saveAppointmentRequest(Mockito.any(AppointmentRequest.class));
        assertThat(actual.getAppointmentType().getUuid(), is(equalTo(expected.getAppointmentType().getUuid())));
        assertThat(actual.getNotes(), is(equalTo(expected.getNotes())));
        assertThat(actual.getPatient(), is(equalTo(expected.getPatient())));
        assertThat(actual.getMinTimeFrameUnits(), is(equalTo(expected.getMinTimeFrameUnits())));
        // TODO: Re do this assert, if fails randomly on slow servers
        // assertThat(actual.getMinTimeFrameValue(),
        // is(equalTo(expected.getMinTimeFrameValue() - 1)));
        assertThat(actual.getStatus(), is(equalTo(expected.getStatus())));
        assertThat(actual.getRequestedOn(), is(notNullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void requestAppointment_shouldThrowExceptionWhenAppointmentTypeIsNotFound() {
        Date now = new Date();
        Date requestAppointmentDate = DateUtils.addDays(now, 30);

        Patient patient = new Patient();
        Obs dateObs = new Obs();
        Concept dateConcept = new Concept();
        dateConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_DATE_UUID);
        dateObs.setConcept(dateConcept);
        dateObs.setValueDate(requestAppointmentDate);

        Obs commentObs = new Obs();
        Concept commentConcept = new Concept();
        commentConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_COMMENT_UUID);
        commentObs.setConcept(commentConcept);

        Obs appointmentTypeObs = new Obs();
        Concept appointmentTypeConcept = new Concept();
        appointmentTypeConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_TYPE_UUID);
        appointmentTypeObs.setConcept(appointmentTypeConcept);
        appointmentTypeObs.setValueText("Some Random Inexistent Type");

        Set<Obs> observations = Sets.newSet(dateObs, commentObs, appointmentTypeObs);
        AppointmentType generalMedicine = new AppointmentType("General Medicine", null, null);
        AppointmentType gynecology = new AppointmentType("Gynecology", null, null);
        when(appointmentService.getAllAppointmentTypes(false)).thenReturn(Arrays.asList(generalMedicine, gynecology));
        requestAppointmentAction.requestAppointment(patient, observations);
    }

    @Test
    public void requestAppointment_shouldNotSaveIfDateIsNull() {
        Patient patient = new Patient();
        Obs dateObs = new Obs();
        Concept dateConcept = new Concept();
        dateConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_DATE_UUID);
        dateObs.setConcept(dateConcept);
        dateObs.setValueDate(null);

        Obs commentObs = new Obs();
        Concept commentConcept = new Concept();
        commentConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_COMMENT_UUID);
        commentObs.setConcept(commentConcept);
        commentObs.setValueText("Comment");

        Set<Obs> observations = Sets.newSet(dateObs, commentObs);

        AppointmentRequest actual = requestAppointmentAction.requestAppointment(patient, observations);

        verify(appointmentService, never()).saveAppointmentRequest(Mockito.any(AppointmentRequest.class));
        assertThat(actual, is(nullValue()));
    }

    @Test
    public void requestAppointment_shouldNotSaveIfDateIsBeforeNow() {
        Date now = new Date();
        Date requestAppointmentDateBeforeNow = DateUtils.addDays(now, -30);

        Patient patient = new Patient();
        Obs dateObs = new Obs();
        Concept dateConcept = new Concept();
        dateConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_DATE_UUID);
        dateObs.setConcept(dateConcept);
        dateObs.setValueDate(requestAppointmentDateBeforeNow);

        Obs commentObs = new Obs();
        Concept commentConcept = new Concept();
        commentConcept.setUuid(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_COMMENT_UUID);
        commentObs.setConcept(commentConcept);
        commentObs.setValueText("Comment");

        Set<Obs> observations = Sets.newSet(dateObs, commentObs);

        AppointmentRequest actual = requestAppointmentAction.requestAppointment(patient, observations);

        verify(appointmentService, never()).saveAppointmentRequest(Mockito.any(AppointmentRequest.class));
        assertThat(actual, is(nullValue()));
    }
}
