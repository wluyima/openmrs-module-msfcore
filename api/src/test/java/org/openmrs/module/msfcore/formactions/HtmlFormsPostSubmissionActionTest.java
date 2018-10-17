package org.openmrs.module.msfcore.formactions;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;

@RunWith(MockitoJUnitRunner.class)
public class HtmlFormsPostSubmissionActionTest {

    @InjectMocks
    HtmlFormsPostSubmissionAction action;

    @Mock
    FormEntrySession session;

    @Mock
    RequestAppointmentAction requestAppointmentAction;

    @Test
    public void applyAction_shouldRedirectToRequestAppointmentsForCorrectUuid() {
        Form form = new Form();
        form.setUuid(MSFCoreConfig.HTMLFORM_REQUEST_APPOINTMENT_UUID);
        Patient patient = new Patient();
        Obs obs = new Obs();
        Encounter encounter = new Encounter();
        encounter.setPatient(patient);
        encounter.addObs(obs);

        when(session.getForm()).thenReturn(form);
        when(session.getEncounter()).thenReturn(encounter);

        action.applyAction(session);

        verify(requestAppointmentAction).requestAppointment(patient, encounter.getAllObs());
    }

    @Test
    public void applyAction_shouldNotRedirectToRequestAppointmentsForOtherUuid() {
        Form form = new Form();
        form.setUuid("other uuid");
        Patient patient = new Patient();
        Obs obs = new Obs();
        Encounter encounter = new Encounter();
        encounter.setPatient(patient);
        encounter.addObs(obs);

        when(session.getForm()).thenReturn(form);
        when(session.getEncounter()).thenReturn(encounter);

        action.applyAction(session);

        verify(requestAppointmentAction, never()).requestAppointment(patient, encounter.getAllObs());
    }

}
