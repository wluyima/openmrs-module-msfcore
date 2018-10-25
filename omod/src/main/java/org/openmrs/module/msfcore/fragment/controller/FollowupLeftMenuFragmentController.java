package org.openmrs.module.msfcore.fragment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Encounter;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.NCDFollowUpLinks;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class FollowupLeftMenuFragmentController {

    private BaselineLeftMenuFragmentController baselineController = new BaselineLeftMenuFragmentController();

    public void controller(@SpringBean AppFrameworkService appFrameworkService, FragmentModel fragmentModel, HttpServletRequest request,
                    HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");

        getNCDFollowUpLinks(patientId, fragmentModel);
    }

    public NCDFollowUpLinks getNCDFollowUpLinks(String patientId) {
        return getNCDFollowUpLinks(patientId, null);
    }

    private NCDFollowUpLinks getNCDFollowUpLinks(String patientId, FragmentModel fragmentModel) {

        NCDFollowUpLinks links = new NCDFollowUpLinks();

        List<Encounter> ncdEncounters = baselineController.getAllEncountersByPatientIdAndEncounterTypeUuid(patientId,
                        MSFCoreConfig.MSF_NCD_FOLLOWUP_ENCOUNTER_TYPE_UUID);

        String visitDetailsEncounter = baselineController.getEncounterUuidByFormUuid(ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_VISIT_DETAILS_UUID);
        links.setVisitDetailsLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_VISIT_DETAILS_UUID,
                        visitDetailsEncounter));

        String diagnosisEncounter = baselineController.getEncounterUuidByFormUuid(ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_DIAGNOSIS_UUID);
        links.setDiagnosisLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_DIAGNOSIS_UUID, diagnosisEncounter));

        String complicationSinceLastVisitEncounter = baselineController.getEncounterUuidByFormUuid(ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_COMPLICATIONS_SINCE_LAST_VISIT_UUID);
        links.setComplicationsSinceLastVisitLink(baselineController.buildLink(patientId,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_COMPLICATIONS_SINCE_LAST_VISIT_UUID, complicationSinceLastVisitEncounter));

        String prescribeMedicationEncounter = baselineController.getEncounterUuidByFormUuid(ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_PRESCRIBE_MEDICATION_UUID);
        links.setPrescribeMedicationLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_PRESCRIBE_MEDICATION_UUID,
                        prescribeMedicationEncounter));

        String clinicalNoteEncounter = baselineController.getEncounterUuidByFormUuid(ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_CLINICAL_NOTE_UUID);
        links.setClinicalNoteLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_CLINICAL_NOTE_UUID,
                        clinicalNoteEncounter));

        String referPatientEncounter = baselineController.getEncounterUuidByFormUuid(ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_REFER_PATIENT_UUID);
        links.setReferPatientLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_REFER_PATIENT_UUID,
                        referPatientEncounter));

        String requestInvestigationEncounter = baselineController.getEncounterUuidByFormUuid(ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_REQUEST_INVESTIGATION_UUID);
        links.setRequestInvestigationLink(baselineController.buildLink(patientId,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_REQUEST_INVESTIGATION_UUID, requestInvestigationEncounter));

        String scheduleAppointment = baselineController.getEncounterUuidByFormUuid(ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_SCHEDULE_APPOINTMENT_UUID);
        links.setScheduleAppointmentLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_SCHEDULE_APPOINTMENT_UUID,
                        scheduleAppointment));

        if (fragmentModel != null) {
            fragmentModel.addAttribute("visitDetailsLink", links.getVisitDetailsLink());
            fragmentModel.addAttribute("diagnosisLink", links.getDiagnosisLink());
            fragmentModel.addAttribute("complicationsSinceLastVisitLink", links.getComplicationsSinceLastVisitLink());
            fragmentModel.addAttribute("prescribeMedicationLink", links.getPrescribeMedicationLink());
            fragmentModel.addAttribute("clinicalNoteLink", links.getClinicalNoteLink());
            fragmentModel.addAttribute("referPatientLink", links.getReferPatientLink());
            fragmentModel.addAttribute("requestInvestigationLink", links.getRequestInvestigationLink());
            fragmentModel.addAttribute("scheduleAppointmentLink", links.getScheduleAppointmentLink());
        }

        return links;
    }
}
