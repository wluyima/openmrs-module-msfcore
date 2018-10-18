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
import org.openmrs.parameter.EncounterSearchCriteria;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;

public class LeftMenuFragmentController {
	
	public void controller(@SpringBean AppFrameworkService appFrameworkService, FragmentModel fragmentModel,
	        HttpServletRequest request, HttpServletResponse response) throws IOException {
		String patientUuid = request.getParameter("patientId");
		List<Encounter> ncdEncounters = getAllNCDEncountersByPatientUuid(patientUuid);
		initializeLinks(patientUuid, ncdEncounters, fragmentModel);
	}
	
	private void initializeLinks(String patientUuid, List<Encounter> ncdEncounters, FragmentModel fragmentModel) {
		String medicalHistoryEncounterUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_MEDICAL_HISTORY_UUID);
		String medicalHistoryLink = buildLink(patientUuid, MSFCoreConfig.FORM_MEDICAL_HISTORY_UUID,
		    medicalHistoryEncounterUuid);
		fragmentModel.addAttribute("medicalHistoryLink", medicalHistoryLink);
		
		String lifestyleEncounterUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_LIFESTYLE_UUID);
		String lifestyleLink = buildLink(patientUuid, MSFCoreConfig.FORM_LIFESTYLE_UUID, lifestyleEncounterUuid);
		fragmentModel.addAttribute("lifestyleLink", lifestyleLink);
		
		String allergiesUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_ALLERGIES_UUID);
		String allergiesLink = buildLink(patientUuid, MSFCoreConfig.FORM_ALLERGIES_UUID, allergiesUuid);
		fragmentModel.addAttribute("allergiesLink", allergiesLink);
		
		String diagnosisUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_DIAGNOSIS_UUID);
		String diagnosisLink = buildLink(patientUuid, MSFCoreConfig.FORM_DIAGNOSIS_UUID, diagnosisUuid);
		fragmentModel.addAttribute("diagnosisLink", diagnosisLink);
		
		String complicationsUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_COMPLICATIONS_UUID);
		String complicationsLink = buildLink(patientUuid, MSFCoreConfig.FORM_COMPLICATIONS_UUID, complicationsUuid);
		fragmentModel.addAttribute("complicationsLink", complicationsLink);
		
		String requestInvestigationUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_REQUEST_INVESTIGATION_UUID);
		String requestInvestigationLink = buildLink(patientUuid, MSFCoreConfig.FORM_REQUEST_INVESTIGATION_UUID,
		    requestInvestigationUuid);
		fragmentModel.addAttribute("requestInvestigationLink", requestInvestigationLink);
		
		String prescribeMedicationUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_PRESCRIBE_MEDICATION_UUID);
		String prescribeMedicationLink = buildLink(patientUuid, MSFCoreConfig.FORM_PRESCRIBE_MEDICATION_UUID,
		    prescribeMedicationUuid);
		fragmentModel.addAttribute("prescribeMedicationLink", prescribeMedicationLink);
		
		String patientTargetUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_PATIENT_TARGET_UUID);
		String patientTargetLink = buildLink(patientUuid, MSFCoreConfig.FORM_PATIENT_TARGET_UUID, patientTargetUuid);
		fragmentModel.addAttribute("patientTargetLink", patientTargetLink);
		
		String regularPatientReviewUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_REGULAR_PATIENT_REVIEW_UUID);
		String regularPatientReviewLink = buildLink(patientUuid, MSFCoreConfig.FORM_REGULAR_PATIENT_REVIEW_UUID,
		    regularPatientReviewUuid);
		fragmentModel.addAttribute("regularPatientReviewLink", regularPatientReviewLink);
		
		String clinicalNoteUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_CLINICAL_NOTE_UUID);
		String clinicalNoteLink = buildLink(patientUuid, MSFCoreConfig.FORM_CLINICAL_NOTE_UUID, clinicalNoteUuid);
		fragmentModel.addAttribute("clinicalNoteLink", clinicalNoteLink);
		
		String requestAppointmentUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_REQUEST_APPOINTMENT_UUID);
		String requestAppointmentLink = buildLink(patientUuid, MSFCoreConfig.FORM_REQUEST_APPOINTMENT_UUID,
		    requestAppointmentUuid);
		fragmentModel.addAttribute("requestAppointmentLink", requestAppointmentLink);
		
		String referPatientUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_REFER_PATIENT_UUID);
		String referPatientLink = buildLink(patientUuid, MSFCoreConfig.FORM_REFER_PATIENT_UUID, referPatientUuid);
		fragmentModel.addAttribute("referPatientLink", referPatientLink);
	}
	
	private String buildLink(String patientUuid, String formUuid, String encounterUuid) {
		if (encounterUuid == null) { // case of a new entry
			return "enterHtmlFormWithStandardUi.page?formUuid=" + formUuid + "&patientId=" + patientUuid;
		} else { // case of edit
			return "editHtmlFormWithStandardUi.page?formUuid=" + formUuid + "&patientId=" + patientUuid + "&encounterId="
			        + encounterUuid;
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
	
	private List<Encounter> getAllNCDEncountersByPatientUuid(String patientUuid) {
		Context.getEncounterService().getEncountersByPatient(patientUuid);
		Patient patient = Context.getPatientService().getPatientByUuid(patientUuid);
		EncounterType ncdEncounterType = Context.getEncounterService()
		        .getEncounterTypeByUuid(MSFCoreConfig.MSF_NCD_BASELINE_ENCOUNTER_TYPE_UUID);
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
}
