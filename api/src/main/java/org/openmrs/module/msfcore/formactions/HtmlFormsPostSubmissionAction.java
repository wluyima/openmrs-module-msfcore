package org.openmrs.module.msfcore.formactions;

import java.util.Set;

import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.module.htmlformentry.CustomFormSubmissionAction;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class HtmlFormsPostSubmissionAction implements CustomFormSubmissionAction {

    RequestAppointmentAction requestAppointmentAction = new RequestAppointmentAction();

    @Override
    public void applyAction(FormEntrySession session) {
        String formUuid = session.getForm().getUuid();
        Patient patient = session.getEncounter().getPatient();
        Set<Obs> observations = session.getEncounter().getObsAtTopLevel(false);

        if (formUuid.equals(MSFCoreConfig.HTMLFORM_REQUEST_APPOINTMENT_UUID)) {
            requestAppointmentAction.requestAppointment(patient, observations);
        }
    }
}
