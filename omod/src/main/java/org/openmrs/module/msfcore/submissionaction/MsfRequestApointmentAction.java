package org.openmrs.module.msfcore.submissionaction;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentRequest;
import org.openmrs.module.appointmentscheduling.AppointmentRequest.AppointmentRequestStatus;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.TimeFrameUnits;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.util.DateUtils;
import org.openmrs.module.msfcore.submissionaction.handler.MsfSubmissionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsfRequestApointmentAction implements MsfSubmissionAction {

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public void apply(String operation, FormEntrySession session) {
        String formUuid = session.getForm().getUuid();
        if (formUuid.equals(MSFCoreConfig.HTMLFORM_REQUEST_APPOINTMENT_UUID)) {
            Patient patient = session.getEncounter().getPatient();
            Set<Obs> observations = session.getEncounter().getObsAtTopLevel(false);
            requestAppointment(patient, observations);
        }
    }

    public AppointmentRequest requestAppointment(Patient patient, Set<Obs> observations) {
        String notes = "";
        String appointmentTypeUuid = "";
        Date requestedDate = null;

        for (Obs obs : observations) {
            if (obs.getConcept().getUuid().equals(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_DATE_UUID)) {
                requestedDate = obs.getValueDate();
            }
            if (obs.getConcept().getUuid().equals(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_COMMENT_UUID)) {
                notes = obs.getValueText();
            }
            if (obs.getConcept().getUuid().equals(MSFCoreConfig.CONCEPT_REQUEST_APPOINTMENT_TYPE_UUID)) {
                appointmentTypeUuid = obs.getValueText();
            }
        }

        Date now = new Date();
        if (requestedDate == null || requestedDate.before(now)) {
            return null;
        }

        if (appointmentService == null) {
            appointmentService = Context.getService(AppointmentService.class);
        }

        AppointmentType appointmentType = appointmentService.getAppointmentTypeByUuid(appointmentTypeUuid);

        List<AppointmentRequest> appointmentRequests = appointmentService.getAllAppointmentRequests(false);
        for (AppointmentRequest request : appointmentRequests) {
            if (request.getAppointmentType() == appointmentType && request.getPatient().getId() == patient.getId()
                            && DateUtils.isSameDate(now, request.getDateCreated())
                            && request.getStatus() == AppointmentRequestStatus.PENDING) {
                return null;
            }
        }

        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setAppointmentType(appointmentType);
        appointmentRequest.setNotes(notes);
        appointmentRequest.setPatient(patient);
        appointmentRequest.setMinTimeFrameUnits(TimeFrameUnits.DAYS);
        appointmentRequest.setMinTimeFrameValue(DateUtils.getDaysBetweenDates(now, requestedDate));
        appointmentRequest.setStatus(AppointmentRequestStatus.PENDING);
        appointmentRequest.setRequestedOn(new Date());
        appointmentService.saveAppointmentRequest(appointmentRequest);

        return appointmentRequest;
    }

}
