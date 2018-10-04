package org.openmrs.module.msfcore;

import java.util.Date;
import java.util.Set;

import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentRequest;
import org.openmrs.module.appointmentscheduling.AppointmentRequest.AppointmentRequestStatus;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.TimeFrameUnits;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.htmlformentry.CustomFormSubmissionAction;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.api.util.DateUtils;

public class HtmlFormsPostSubmissionAction implements CustomFormSubmissionAction {

    @Override
    public void applyAction(FormEntrySession session) {
        String formUuid = session.getForm().getUuid();
        Patient patient = session.getEncounter().getPatient();
        Set<Obs> obss = session.getEncounter().getObsAtTopLevel(false);

        if (formUuid.equals(MSFCoreConfig.HTMLFORM_REQUEST_APPOINTMENT_UUID)) {
            requestAppointment(patient, obss);
        }
    }

    private void requestAppointment(Patient patient, Set<Obs> obss) {
        String notes = "";
        Date requestedDate = null;

        for (Obs obs : obss) {
            if (obs.getConcept().getUuid().equals(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_DATE_UUID)) {
                requestedDate = obs.getValueDate();
            }
            if (obs.getConcept().getUuid().equals(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_COMMENT_UUID)) {
                notes = obs.getValueText();
            }
        }

        Date now = new Date();
        if (requestedDate == null || requestedDate.before(now)) {
            return;
        }

        AppointmentRequest appointmentRequest = new AppointmentRequest();
        // TODO: Appointment type is mandatory, decide which appointment type should be set by
        // default
        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setId(1);
        appointmentRequest.setAppointmentType(appointmentType);
        appointmentRequest.setNotes(notes);
        appointmentRequest.setPatient(patient);
        appointmentRequest.setMinTimeFrameUnits(TimeFrameUnits.DAYS);
        appointmentRequest.setMinTimeFrameValue(DateUtils.getDaysBetweenDate1AndDate2(now, requestedDate));
        appointmentRequest.setStatus(AppointmentRequestStatus.PENDING);
        appointmentRequest.setRequestedOn(new Date());
        Context.getService(AppointmentService.class).saveAppointmentRequest(appointmentRequest);
    }
}
