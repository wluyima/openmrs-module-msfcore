package org.openmrs.module.msfcore.submissionaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Encounter;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.fragment.controller.LeftMenuFragmentController;
import org.openmrs.module.msfcore.submissionaction.handler.MsfSubmissionAction;
import org.springframework.stereotype.Component;

@Component
public class MsfNavigationSubmissionAction implements MsfSubmissionAction {
	
	LeftMenuFragmentController controller = new LeftMenuFragmentController();
	
	private static final String DASHBOARD_URL = "/coreapps/clinicianfacing/patient.page?patientId=%s&app=msfcore.app.clinicianDashboard";
	
	private static Map<String, String> OPERATION_TO_URL;
	
	@Override
	public void apply(String operation, FormEntrySession session) {
		List<Encounter> ncdEncounters = controller.getAllNCDEncountersByPatientId(session.getPatient().getUuid());
		controller.initializeLinks(session.getPatient().getUuid(), ncdEncounters, null);
		
		OPERATION_TO_URL = new HashMap<String, String>() {
			
			private static final long serialVersionUID = -4322535511417688724L;
			
			{
				put("save.and.exit.action", DASHBOARD_URL);
				put("complete.action", DASHBOARD_URL);
				put("ncd.baseline.medicalhistory.next", "/htmlformentryui/htmlform/" + controller.lifestyleLink);
				put("ncd.baseline.lifestyle.previous", "/htmlformentryui/htmlform/" + controller.medicalHistoryLink);
				put("ncd.baseline.lifestyle.next", "/htmlformentryui/htmlform/" + controller.allergiesLink);
				put("ncd.baseline.alergies.previous", "/htmlformentryui/htmlform/" + controller.lifestyleLink);
				put("ncd.baseline.alergies.next", "/htmlformentryui/htmlform/" + controller.diagnosisLink);
				put("ncd.baseline.diagnosis.previous", "/htmlformentryui/htmlform/" + controller.allergiesLink);
				put("ncd.baseline.diagnosis.next", "/htmlformentryui/htmlform/" + controller.complicationsLink);
				put("ncd.baseline.complications.previous", "/htmlformentryui/htmlform/" + controller.diagnosisLink);
				put("ncd.baseline.complications.next", "/htmlformentryui/htmlform/" + controller.requestInvestigationLink);
				put("ncd.baseline.prescribemedication.previous",
				    "/htmlformentryui/htmlform/" + controller.requestInvestigationLink);
				put("ncd.baseline.prescribemedication.next", "/htmlformentryui/htmlform/" + controller.patientTargetLink);
				put("ncd.baseline.patienttargets.previous",
				    "/htmlformentryui/htmlform/" + controller.prescribeMedicationLink);
				put("ncd.baseline.patienttargets.next", "/htmlformentryui/htmlform/" + controller.regularPatientReviewLink);
				put("ncd.baseline.regularpatientreview.previous",
				    "/htmlformentryui/htmlform/" + controller.patientTargetLink);
				put("ncd.baseline.regularpatientreview.next", "/htmlformentryui/htmlform/" + controller.clinicalNoteLink);
				put("ncd.baseline.clinicalnote.previous",
				    "/htmlformentryui/htmlform/" + controller.regularPatientReviewLink);
				put("ncd.baseline.clinicalnote.next", "/htmlformentryui/htmlform/" + controller.requestAppointmentLink);
				put("ncd.baseline.requestinvestigation.previous",
				    "/htmlformentryui/htmlform/" + controller.complicationsLink);
				put("ncd.baseline.requestinvestigation.next",
				    "/htmlformentryui/htmlform/" + controller.prescribeMedicationLink);
				put("ncd.baseline.requestappointment.previous", "/htmlformentryui/htmlform/" + controller.clinicalNoteLink);
				put("ncd.baseline.requestappointment.next", "/htmlformentryui/htmlform/" + controller.referPatientLink);
				put("ncd.baseline.referpatient.previous", "/htmlformentryui/htmlform/" + controller.requestAppointmentLink);
			}
		};
		
		if (operation != null) {
			String nextUrl = OPERATION_TO_URL.get(operation);
			if (nextUrl != null) {
				session.setAfterSaveUrlTemplate(String.format(nextUrl, session.getPatient().getId()));
			} else {
				throw new IllegalArgumentException(String.format("No URL found for operation %s", operation));
			}
		} else {
			throw new IllegalArgumentException("msf.operation parameter not set");
		}
	}
	
}
