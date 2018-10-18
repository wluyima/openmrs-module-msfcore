package org.openmrs.module.msfcore.submissionaction;

import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.NCDBaselineLinks;
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
		final NCDBaselineLinks links = controller.getNCDBaselineLinks(session.getPatient().getUuid());
		
		OPERATION_TO_URL = new HashMap<String, String>() {
			
			private static final long serialVersionUID = -4322535511417688724L;
			
			{
				put("save.and.exit.action", DASHBOARD_URL);
				put("complete.action", DASHBOARD_URL);
				put("ncd.baseline.medicalhistory.next", "/htmlformentryui/htmlform/" + links.getLifestyleLink());
				put("ncd.baseline.lifestyle.previous", "/htmlformentryui/htmlform/" + links.getMedicalHistoryLink());
				put("ncd.baseline.lifestyle.next", "/htmlformentryui/htmlform/" + links.getAllergiesLink());
				put("ncd.baseline.alergies.previous", "/htmlformentryui/htmlform/" + links.getLifestyleLink());
				put("ncd.baseline.alergies.next", "/htmlformentryui/htmlform/" + links.getDiagnosisLink());
				put("ncd.baseline.diagnosis.previous", "/htmlformentryui/htmlform/" + links.getAllergiesLink());
				put("ncd.baseline.diagnosis.next", "/htmlformentryui/htmlform/" + links.getComplicationsLink());
				put("ncd.baseline.complications.previous", "/htmlformentryui/htmlform/" + links.getDiagnosisLink());
				put("ncd.baseline.complications.next", "/htmlformentryui/htmlform/" + links.getRequestInvestigationLink());
				put("ncd.baseline.prescribemedication.previous",
				    "/htmlformentryui/htmlform/" + links.getRequestInvestigationLink());
				put("ncd.baseline.prescribemedication.next", "/htmlformentryui/htmlform/" + links.getPatientTargetLink());
				put("ncd.baseline.patienttargets.previous",
				    "/htmlformentryui/htmlform/" + links.getPrescribeMedicationLink());
				put("ncd.baseline.patienttargets.next", "/htmlformentryui/htmlform/" + links.getRegularPatientReviewLink());
				put("ncd.baseline.regularpatientreview.previous",
				    "/htmlformentryui/htmlform/" + links.getPatientTargetLink());
				put("ncd.baseline.regularpatientreview.next", "/htmlformentryui/htmlform/" + links.getClinicalNoteLink());
				put("ncd.baseline.clinicalnote.previous",
				    "/htmlformentryui/htmlform/" + links.getRegularPatientReviewLink());
				put("ncd.baseline.clinicalnote.next", "/htmlformentryui/htmlform/" + links.getRequestAppointmentLink());
				put("ncd.baseline.requestinvestigation.previous",
				    "/htmlformentryui/htmlform/" + links.getComplicationsLink());
				put("ncd.baseline.requestinvestigation.next",
				    "/htmlformentryui/htmlform/" + links.getPrescribeMedicationLink());
				put("ncd.baseline.requestappointment.previous", "/htmlformentryui/htmlform/" + links.getClinicalNoteLink());
				put("ncd.baseline.requestappointment.next", "/htmlformentryui/htmlform/" + links.getReferPatientLink());
				put("ncd.baseline.referpatient.previous", "/htmlformentryui/htmlform/" + links.getRequestAppointmentLink());
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
