package org.openmrs.module.msfcore.fragment.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.NCDFollowUpLinks;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class FollowupLeftMenuFragmentController {

    private BaselineLeftMenuFragmentController baselineController = new BaselineLeftMenuFragmentController();
    private static final Comparator<Visit> MOST_RECENT_VISIT_FIRST_ORDER = (v1, v2) -> v2.getStartDatetime()
                    .compareTo(v1.getStartDatetime());

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

        Patient patient = baselineController.getPatient(patientId);

        List<Encounter> ncdEncounters = baselineController.getAllEncountersByPatientAndEncounterTypeUuid(patient,
                        MSFCoreConfig.MSF_NCD_FOLLOWUP_ENCOUNTER_TYPE_UUID);

        String visitDetailsEncounter = getEncounterUuidByFormUuid(patient, ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_VISIT_DETAILS_UUID);
        links.setVisitDetailsLink(
                        baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_VISIT_DETAILS_UUID, visitDetailsEncounter));

        String diagnosisEncounter = getEncounterUuidByFormUuid(patient, ncdEncounters, MSFCoreConfig.FORM_NCD_FOLLOWUP_DIAGNOSIS_UUID);
        links.setDiagnosisLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_DIAGNOSIS_UUID, diagnosisEncounter));

        String complicationSinceLastVisitEncounter = getEncounterUuidByFormUuid(patient, ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_COMPLICATIONS_SINCE_LAST_VISIT_UUID);
        links.setComplicationsSinceLastVisitLink(baselineController.buildLink(patientId,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_COMPLICATIONS_SINCE_LAST_VISIT_UUID, complicationSinceLastVisitEncounter));

        String prescribeMedicationEncounter = getEncounterUuidByFormUuid(patient, ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_PRESCRIBE_MEDICATION_UUID);
        links.setPrescribeMedicationLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_PRESCRIBE_MEDICATION_UUID,
                        prescribeMedicationEncounter));

        String clinicalNoteEncounter = getEncounterUuidByFormUuid(patient, ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_CLINICAL_NOTE_UUID);
        links.setClinicalNoteLink(
                        baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_CLINICAL_NOTE_UUID, clinicalNoteEncounter));

        String referPatientEncounter = getEncounterUuidByFormUuid(patient, ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_REFER_PATIENT_UUID);
        links.setReferPatientLink(
                        baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_REFER_PATIENT_UUID, referPatientEncounter));

        String requestInvestigationEncounter = getEncounterUuidByFormUuid(patient, ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_REQUEST_INVESTIGATION_UUID);
        links.setRequestInvestigationLink(baselineController.buildLink(patientId,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_REQUEST_INVESTIGATION_UUID, requestInvestigationEncounter));

        String scheduleAppointment = getEncounterUuidByFormUuid(patient, ncdEncounters,
                        MSFCoreConfig.FORM_NCD_FOLLOWUP_REQUEST_APPOINTMENT_UUID);
        links.setRequestAppointmentLink(baselineController.buildLink(patientId, MSFCoreConfig.FORM_NCD_FOLLOWUP_REQUEST_APPOINTMENT_UUID,
                        scheduleAppointment));

        if (fragmentModel != null) {
            fragmentModel.addAttribute("visitDetailsLink", links.getVisitDetailsLink());
            fragmentModel.addAttribute("diagnosisLink", links.getDiagnosisLink());
            fragmentModel.addAttribute("complicationsSinceLastVisitLink", links.getComplicationsSinceLastVisitLink());
            fragmentModel.addAttribute("prescribeMedicationLink", links.getPrescribeMedicationLink());
            fragmentModel.addAttribute("clinicalNoteLink", links.getClinicalNoteLink());
            fragmentModel.addAttribute("referPatientLink", links.getReferPatientLink());
            fragmentModel.addAttribute("requestInvestigationLink", links.getRequestInvestigationLink());
            fragmentModel.addAttribute("requestAppointmentLink", links.getRequestAppointmentLink());
        }

        return links;
    }

    /**
     * Finds the encounter from active visit or from most recent visit if no
     * active visit exists.
     */
    private String getEncounterUuidByFormUuid(Patient patient, List<Encounter> encounters, String formUuid) {
        if (!encounters.isEmpty()) {
            Visit visit = getAppropriateVisit(patient);
            Stream<Encounter> stream = encounters.stream().filter(e -> e.getForm().getUuid().equals(formUuid))
                            .sorted((e1, e2) -> MOST_RECENT_VISIT_FIRST_ORDER.compare(e1.getVisit(), e2.getVisit()));
            if (visit != null) {
                stream = stream.filter(e -> e.getVisit().equals(visit));
            }
            Optional<Encounter> encounter = stream.findFirst();
            if (encounter.isPresent()) {
                return encounter.get().getUuid();
            }
        }
        return null;
    }

    /**
     * Find the active visit or the most recent visit if no active visit exists
     */
    private Visit getAppropriateVisit(Patient patient) {
        List<Visit> visits = Context.getVisitService().getVisitsByPatient(patient, true, false).stream()
                        .sorted(MOST_RECENT_VISIT_FIRST_ORDER).collect(Collectors.toList());
        Optional<Visit> activeVisit = visits.stream().filter(v -> v.getStopDatetime() == null).findFirst();
        Optional<Visit> mostRecentVisit = visits.stream().findFirst();
        if (activeVisit.isPresent()) {
            return activeVisit.get();
        } else if (mostRecentVisit.isPresent()) {
            return mostRecentVisit.get();
        }
        return null;
    }
}
