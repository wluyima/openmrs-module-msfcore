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
	
	public String medicalHistoryLink;
	
	public String lifestyleLink;
	
	public String allergiesLink;
	
	public String diagnosisLink;
	
	public String complicationsLink;
	
	public String requestInvestigationLink;
	
	public String prescribeMedicationLink;
	
	public String patientTargetLink;
	
	public String regularPatientReviewLink;
	
	public String clinicalNoteLink;
	
	public String requestAppointmentLink;
	
	public String referPatientLink;
	
	public void controller(@SpringBean AppFrameworkService appFrameworkService, FragmentModel fragmentModel,
	        HttpServletRequest request, HttpServletResponse response) throws IOException {
		String patientUuid = request.getParameter("patientId");
		List<Encounter> ncdEncounters = getAllNCDEncountersByPatientUuid(patientUuid);
		initializeLinks(patientUuid, ncdEncounters, fragmentModel);
	}
	
	public void initializeLinks(String patientUuid, List<Encounter> ncdEncounters, FragmentModel fragmentModel) {
		String medicalHistoryEncounterUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_MEDICAL_HISTORY_UUID);
		medicalHistoryLink = buildLink(patientUuid, MSFCoreConfig.FORM_MEDICAL_HISTORY_UUID, medicalHistoryEncounterUuid);
		
		String lifestyleEncounterUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_LIFESTYLE_UUID);
		lifestyleLink = buildLink(patientUuid, MSFCoreConfig.FORM_LIFESTYLE_UUID, lifestyleEncounterUuid);
		
		String allergiesUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_ALLERGIES_UUID);
		allergiesLink = buildLink(patientUuid, MSFCoreConfig.FORM_ALLERGIES_UUID, allergiesUuid);
		
		String diagnosisUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_DIAGNOSIS_UUID);
		diagnosisLink = buildLink(patientUuid, MSFCoreConfig.FORM_DIAGNOSIS_UUID, diagnosisUuid);
		
		String complicationsUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_COMPLICATIONS_UUID);
		complicationsLink = buildLink(patientUuid, MSFCoreConfig.FORM_COMPLICATIONS_UUID, complicationsUuid);
		
		String requestInvestigationUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_REQUEST_INVESTIGATION_UUID);
		requestInvestigationLink = buildLink(patientUuid, MSFCoreConfig.FORM_REQUEST_INVESTIGATION_UUID,
		    requestInvestigationUuid);
		
		String prescribeMedicationUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_PRESCRIBE_MEDICATION_UUID);
		prescribeMedicationLink = buildLink(patientUuid, MSFCoreConfig.FORM_PRESCRIBE_MEDICATION_UUID,
		    prescribeMedicationUuid);
		
		String patientTargetUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_PATIENT_TARGET_UUID);
		patientTargetLink = buildLink(patientUuid, MSFCoreConfig.FORM_PATIENT_TARGET_UUID, patientTargetUuid);
		
		String regularPatientReviewUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_REGULAR_PATIENT_REVIEW_UUID);
		regularPatientReviewLink = buildLink(patientUuid, MSFCoreConfig.FORM_REGULAR_PATIENT_REVIEW_UUID,
		    regularPatientReviewUuid);
		
		String clinicalNoteUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_CLINICAL_NOTE_UUID);
		clinicalNoteLink = buildLink(patientUuid, MSFCoreConfig.FORM_CLINICAL_NOTE_UUID, clinicalNoteUuid);
		
		String requestAppointmentUuid = getEncounterUuidByFormUuid(ncdEncounters,
		    MSFCoreConfig.FORM_REQUEST_APPOINTMENT_UUID);
		requestAppointmentLink = buildLink(patientUuid, MSFCoreConfig.FORM_REQUEST_APPOINTMENT_UUID, requestAppointmentUuid);
		
		String referPatientUuid = getEncounterUuidByFormUuid(ncdEncounters, MSFCoreConfig.FORM_REFER_PATIENT_UUID);
		referPatientLink = buildLink(patientUuid, MSFCoreConfig.FORM_REFER_PATIENT_UUID, referPatientUuid);
		
		if (fragmentModel != null) {
			fragmentModel.addAttribute("medicalHistoryLink", medicalHistoryLink);
			fragmentModel.addAttribute("lifestyleLink", lifestyleLink);
			fragmentModel.addAttribute("allergiesLink", allergiesLink);
			fragmentModel.addAttribute("diagnosisLink", diagnosisLink);
			fragmentModel.addAttribute("complicationsLink", complicationsLink);
			fragmentModel.addAttribute("requestInvestigationLink", requestInvestigationLink);
			fragmentModel.addAttribute("prescribeMedicationLink", prescribeMedicationLink);
			fragmentModel.addAttribute("patientTargetLink", patientTargetLink);
			fragmentModel.addAttribute("regularPatientReviewLink", regularPatientReviewLink);
			fragmentModel.addAttribute("clinicalNoteLink", clinicalNoteLink);
			fragmentModel.addAttribute("requestAppointmentLink", requestAppointmentLink);
			fragmentModel.addAttribute("referPatientLink", referPatientLink);
		}
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
	
	public List<Encounter> getAllNCDEncountersByPatientUuid(String patientUuid) {
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
