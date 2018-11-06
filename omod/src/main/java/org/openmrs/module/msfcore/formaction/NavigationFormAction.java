package org.openmrs.module.msfcore.formaction;

import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.NCDBaselineLinks;
import org.openmrs.module.msfcore.NCDFollowUpLinks;
import org.openmrs.module.msfcore.formaction.handler.FormAction;
import org.openmrs.module.msfcore.fragment.controller.BaselineLeftMenuFragmentController;
import org.openmrs.module.msfcore.fragment.controller.FollowupLeftMenuFragmentController;
import org.springframework.stereotype.Component;

@Component
public class NavigationFormAction implements FormAction {

    private BaselineLeftMenuFragmentController baselineMenuController = new BaselineLeftMenuFragmentController();

    private FollowupLeftMenuFragmentController followupMenuController = new FollowupLeftMenuFragmentController();

    private static final String DASHBOARD_URL = "/coreapps/clinicianfacing/patient.page?patientId=%s&app=msfcore.app.clinicianDashboard";

    private static final String BASE_FORM_URL_TEMPLATE = "/htmlformentryui/htmlform/%s";

    @Override
    public void apply(String operation, FormEntrySession session) {
        final NCDBaselineLinks links = baselineMenuController.getNCDBaselineLinks(session.getPatient().getUuid());
        NCDFollowUpLinks followUpLinks = followupMenuController.getNCDFollowUpLinks(session.getPatient().getUuid());

        Map<String, String> operationToUrl = generateOperationToUrlMap(links, followUpLinks);
        if (operation != null) {
            String nextUrl = operationToUrl.get(operation);
            if (nextUrl != null) {
                session.setAfterSaveUrlTemplate(String.format(nextUrl, session.getPatient().getId()));
            } else {
                throw new IllegalArgumentException(String.format("No URL found for operation %s", operation));
            }
        } else {
            throw new IllegalArgumentException("msf.operation parameter not set");
        }
    }

    private Map<String, String> generateOperationToUrlMap(final NCDBaselineLinks links, NCDFollowUpLinks followUpLinks) {
        Map<String, String> operationToUrl = new HashMap<String, String>();

        // NCD BASELINE NAVIGATIONS
        operationToUrl.put("save.and.exit.action", DASHBOARD_URL);
        operationToUrl.put("complete.action", DASHBOARD_URL);
        operationToUrl.put("ncd.baseline.medicalhistory.next", String.format(BASE_FORM_URL_TEMPLATE, links.getLifestyleLink()));
        operationToUrl.put("ncd.baseline.lifestyle.previous", String.format(BASE_FORM_URL_TEMPLATE, links.getMedicalHistoryLink()));
        operationToUrl.put("ncd.baseline.lifestyle.next", String.format(BASE_FORM_URL_TEMPLATE, links.getAllergiesLink()));
        operationToUrl.put("ncd.baseline.alergies.previous", String.format(BASE_FORM_URL_TEMPLATE, links.getLifestyleLink()));
        operationToUrl.put("ncd.baseline.alergies.next", String.format(BASE_FORM_URL_TEMPLATE, links.getDiagnosisLink()));
        operationToUrl.put("ncd.baseline.diagnosis.previous", String.format(BASE_FORM_URL_TEMPLATE, links.getAllergiesLink()));
        operationToUrl.put("ncd.baseline.diagnosis.next", String.format(BASE_FORM_URL_TEMPLATE, links.getComplicationsLink()));
        operationToUrl.put("ncd.baseline.complications.previous", String.format(BASE_FORM_URL_TEMPLATE, links.getDiagnosisLink()));
        operationToUrl.put("ncd.baseline.complications.next", String.format(BASE_FORM_URL_TEMPLATE, links.getRequestInvestigationLink()));
        operationToUrl.put("ncd.baseline.prescribemedication.previous", String.format(BASE_FORM_URL_TEMPLATE, links
                        .getRequestInvestigationLink()));
        operationToUrl.put("ncd.baseline.prescribemedication.next", String.format(BASE_FORM_URL_TEMPLATE, links.getPatientTargetLink()));
        operationToUrl.put("ncd.baseline.patienttargets.previous", String
                        .format(BASE_FORM_URL_TEMPLATE, links.getPrescribeMedicationLink()));
        operationToUrl.put("ncd.baseline.patienttargets.next", String.format(BASE_FORM_URL_TEMPLATE, links.getRegularPatientReviewLink()));
        operationToUrl.put("ncd.baseline.regularpatientreview.previous", String
                        .format(BASE_FORM_URL_TEMPLATE, links.getPatientTargetLink()));
        operationToUrl.put("ncd.baseline.regularpatientreview.next", String.format(BASE_FORM_URL_TEMPLATE, links.getClinicalNoteLink()));
        operationToUrl
                        .put("ncd.baseline.clinicalnote.previous", String.format(BASE_FORM_URL_TEMPLATE, links
                                        .getRegularPatientReviewLink()));
        operationToUrl.put("ncd.baseline.clinicalnote.next", String.format(BASE_FORM_URL_TEMPLATE, links.getRequestAppointmentLink()));
        operationToUrl.put("ncd.baseline.requestinvestigation.previous", String
                        .format(BASE_FORM_URL_TEMPLATE, links.getComplicationsLink()));
        operationToUrl.put("ncd.baseline.requestinvestigation.next", String.format(BASE_FORM_URL_TEMPLATE, links
                        .getPrescribeMedicationLink()));
        operationToUrl.put("ncd.baseline.requestappointment.previous", String.format(BASE_FORM_URL_TEMPLATE, links.getClinicalNoteLink()));
        operationToUrl.put("ncd.baseline.requestappointment.next", String.format(BASE_FORM_URL_TEMPLATE, links.getReferPatientLink()));
        operationToUrl.put("ncd.baseline.referpatient.previous", String.format(BASE_FORM_URL_TEMPLATE, links.getRequestAppointmentLink()));

        // NCD FOLLOWUP NAVIGATIONS
        operationToUrl.put("ncd.followup.visitdetails.next", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks.getDiagnosisLink()));
        operationToUrl.put("ncd.followup.diagnosis.previous", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks.getVisitDetailsLink()));
        operationToUrl.put("ncd.followup.diagnosis.next", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getComplicationsSinceLastVisitLink()));
        operationToUrl.put("ncd.followup.complicationssincelastvisit.previous", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getDiagnosisLink()));
        operationToUrl.put("ncd.followup.complicationssincelastvisit.next", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getPrescribeMedicationLink()));
        operationToUrl.put("ncd.followup.prescribemedication.previous", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getComplicationsSinceLastVisitLink()));
        operationToUrl.put("ncd.followup.prescribemedication.next", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getClinicalNoteLink()));
        operationToUrl.put("ncd.followup.clinicalnote.previous", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getPrescribeMedicationLink()));
        operationToUrl.put("ncd.followup.clinicalnote.next", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks.getReferPatientLink()));
        operationToUrl
                        .put("ncd.followup.referpatient.previous", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                                        .getClinicalNoteLink()));
        operationToUrl.put("ncd.followup.referpatient.next", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getRequestInvestigationLink()));
        operationToUrl.put("ncd.followup.requestinvestigation.previous", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getReferPatientLink()));
        operationToUrl.put("ncd.followup.requestinvestigation.next", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getRequestAppointmentLink()));
        operationToUrl.put("ncd.followup.requestappointment.previous", String.format(BASE_FORM_URL_TEMPLATE, followUpLinks
                        .getRequestInvestigationLink()));

        return operationToUrl;
    }
}
