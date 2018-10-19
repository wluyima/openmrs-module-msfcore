package org.openmrs.module.msfcore.fragment.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.NCDBaselineLinks;
import org.openmrs.parameter.EncounterSearchCriteria;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class LeftMenuFragmentController {

    public void controller(@SpringBean AppFrameworkService appFrameworkService, FragmentModel fragmentModel, HttpServletRequest request,
                    HttpServletResponse response) throws IOException {
        String patientId = request.getParameter("patientId");

        getNCDBaselineLinks(patientId, fragmentModel);
    }

    public NCDBaselineLinks getNCDBaselineLinks(String patientId) {
        return getNCDBaselineLinks(patientId, null);
    }

    public NCDBaselineLinks getNCDBaselineLinks(String patientId, FragmentModel fragmentModel) {
        NCDBaselineLinks links = new NCDBaselineLinks();

        List<Encounter> ncdEncounters = getAllNCDEncountersByPatientId(patientId);
        String medicalHistoryEncounterUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_MEDICAL_HISTORY_UUID);
        links.setMedicalHistoryLink(buildLink(patientId, MSFCoreConfig.FORM_MEDICAL_HISTORY_UUID, medicalHistoryEncounterUuid));

        String lifestyleEncounterUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_LIFESTYLE_UUID);
        links.setLifestyleLink(buildLink(patientId, MSFCoreConfig.FORM_LIFESTYLE_UUID, lifestyleEncounterUuid));

        String allergiesUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_ALLERGIES_UUID);
        links.setAllergiesLink(buildLink(patientId, MSFCoreConfig.FORM_ALLERGIES_UUID, allergiesUuid));

        String diagnosisUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_DIAGNOSIS_UUID);
        links.setDiagnosisLink(buildLink(patientId, MSFCoreConfig.FORM_DIAGNOSIS_UUID, diagnosisUuid));

        String complicationsUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_COMPLICATIONS_UUID);
        links.setComplicationsLink(buildLink(patientId, MSFCoreConfig.FORM_COMPLICATIONS_UUID, complicationsUuid));

        String requestInvestigationUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_REQUEST_INVESTIGATION_UUID);
        links.setRequestInvestigationLink(buildLink(patientId, MSFCoreConfig.FORM_REQUEST_INVESTIGATION_UUID, requestInvestigationUuid));

        String prescribeMedicationUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_PRESCRIBE_MEDICATION_UUID);
        links.setPrescribeMedicationLink(buildLink(patientId, MSFCoreConfig.FORM_PRESCRIBE_MEDICATION_UUID, prescribeMedicationUuid));

        String patientTargetUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_PATIENT_TARGET_UUID);
        links.setPatientTargetLink(buildLink(patientId, MSFCoreConfig.FORM_PATIENT_TARGET_UUID, patientTargetUuid));

        String regularPatientReviewUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_REGULAR_PATIENT_REVIEW_UUID);
        links.setRegularPatientReviewLink(buildLink(patientId, MSFCoreConfig.FORM_REGULAR_PATIENT_REVIEW_UUID, regularPatientReviewUuid));

        String clinicalNoteUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_CLINICAL_NOTE_UUID);
        links.setClinicalNoteLink(buildLink(patientId, MSFCoreConfig.FORM_CLINICAL_NOTE_UUID, clinicalNoteUuid));

        String requestAppointmentUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_REQUEST_APPOINTMENT_UUID);
        links.setRequestAppointmentLink(buildLink(patientId, MSFCoreConfig.FORM_REQUEST_APPOINTMENT_UUID, requestAppointmentUuid));

        String referPatientUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_REFER_PATIENT_UUID);
        links.setReferPatientLink(buildLink(patientId, MSFCoreConfig.FORM_REFER_PATIENT_UUID, referPatientUuid));

        if (fragmentModel != null) {
            fragmentModel.addAttribute("medicalHistoryLink", links.getMedicalHistoryLink());
            fragmentModel.addAttribute("lifestyleLink", links.getLifestyleLink());
            fragmentModel.addAttribute("allergiesLink", links.getAllergiesLink());
            fragmentModel.addAttribute("diagnosisLink", links.getDiagnosisLink());
            fragmentModel.addAttribute("complicationsLink", links.getComplicationsLink());
            fragmentModel.addAttribute("requestInvestigationLink", links.getRequestInvestigationLink());
            fragmentModel.addAttribute("prescribeMedicationLink", links.getPrescribeMedicationLink());
            fragmentModel.addAttribute("patientTargetLink", links.getPatientTargetLink());
            fragmentModel.addAttribute("regularPatientReviewLink", links.getRegularPatientReviewLink());
            fragmentModel.addAttribute("clinicalNoteLink", links.getClinicalNoteLink());
            fragmentModel.addAttribute("requestAppointmentLink", links.getRequestAppointmentLink());
            fragmentModel.addAttribute("referPatientLink", links.getReferPatientLink());
        }

        return links;
    }

    private String buildLink(String patientUuid, String formUuid, String encounterUuid) {
        if (encounterUuid == null) { // case of a new entry
            return String.format("enterHtmlFormWithStandardUi.page?formUuid=%s&patientId=%s", formUuid, patientUuid);
        } else { // case of edit
            return String.format("editHtmlFormWithStandardUi.page?formUuid=%s&patientId=%s&encounterId=%s", formUuid, patientUuid,
                            encounterUuid);
        }
    }

    private String getEncounterUuidByFormUuid(List<Encounter> encounters, String formUuid) {
        for (Encounter encounter : encounters) {
            if (encounter.getForm().getUuid().equals(formUuid)) {
                return encounter.getUuid();
            }
        }
        return null;
    }

    private List<Encounter> getAllNCDEncountersByPatientId(String patientId) {
        Context.getEncounterService().getEncountersByPatient(patientId);
        Patient patient;
        if (isANumber(patientId)) {
            patient = Context.getPatientService().getPatient(Integer.parseInt(patientId));
        } else {
            patient = Context.getPatientService().getPatientByUuid(patientId);
        }

        EncounterType ncdEncounterType = Context.getEncounterService().getEncounterTypeByUuid(
                        MSFCoreConfig.MSF_NCD_BASELINE_ENCOUNTER_TYPE_UUID);
        EncounterSearchCriteria criteria = new EncounterSearchCriteria(patient, null, // location
                        null, // fromDate
                        null, // toDate
                        null, // dateChanged
                        null, // enteredViaForms
                        Arrays.asList(ncdEncounterType), null, // providers
                        null, // visitTypes
                        null, // visits
                        false // include voided
        );
        return Context.getEncounterService().getEncounters(criteria);
    }

    private boolean isANumber(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
