package org.openmrs.module.msfcore.formactions;

import org.openmrs.EncounterType;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.CustomFormSubmissionAction;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;

public class HtmlFormsPostSubmissionAction implements CustomFormSubmissionAction {
    RequestAppointmentAction requestAppointmentAction = new RequestAppointmentAction();

    @Override
    public void applyAction(FormEntrySession session) {
        String formUuid = session.getForm().getUuid();

        if (formUuid.equals(MSFCoreConfig.HTMLFORM_REQUEST_APPOINTMENT_UUID)) {
            requestAppointmentAction
                            .requestAppointment(session.getEncounter().getPatient(), session.getEncounter().getObsAtTopLevel(false));
        }

        EncounterType encounterType = session.getEncounter().getEncounterType();
        if (encounterType != null
                        && encounterType.getUuid().matches(
                                        MSFCoreConfig.ENCOUNTER_TYPE_UUID_BASELINE + "|" + MSFCoreConfig.ENCOUNTER_TYPE_UUID_FOLLOWUP + "|"
                                                        + MSFCoreConfig.ENCOUNTER_TYPE_UUID_EXIT)) {
            Context.getService(MSFCoreService.class).manageNCDProgram(session.getEncounter());
        }
    }
}
