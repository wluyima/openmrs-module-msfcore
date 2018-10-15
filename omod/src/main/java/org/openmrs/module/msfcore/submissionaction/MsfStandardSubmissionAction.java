package org.openmrs.module.msfcore.submissionaction;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.module.htmlformentry.CustomFormSubmissionAction;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This class is resposible for defining the next url when a submit button is clicked on a form
 * 
 * @author Edrisse
 */
public class MsfStandardSubmissionAction implements CustomFormSubmissionAction {

    private static final String DASHBOARD_URL = "/coreapps/clinicianfacing/patient.page?patientId=%s&app=msfcore.app.clinicianDashboard";

    private static final String MEDICAL_HISTORY_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=06807e2b-ce97-4d65-8796-e955fcbe057d&patientId=%s";

    private static final String LIFESTYLE_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=3209cd5f-656e-42f4-984e-ab466a5b77ef&patientId=%s";

    private static final String ALLERGIES_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=30d1fda4-4161-4666-ad0c-e2ba20eb73a6&patientId=%s";

    private static final String DIAGNOSIS_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=7ba65c3e-3e16-4637-824f-ce23ccb30746&patientId=%s";

    private static final String COMPLICATIONS_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=f09a3a3a-810e-4cf6-b432-3d43da303933&patientId=%s";

    private static final String PRESCRIBE_MEDICATION_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=aab2cab6-c280-438b-9afd-3c54e799ef2a&patientId=%s";

    private static final String PATIENT_TARGETS_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=f88f341a-2a37-47e9-ac81-b5dae813ab26&patientId=%s";

    private static final String REGULAR_PATIENT_REVIEW_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=b450ec93-f4b5-4a4b-8143-4564d84028bc&patientId=%s";

    private static final String CLINICAL_NOTE_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=f09a3a3a-810e-4cf6-b432-3d43da303948&patientId=%s";

    private static final String INVESTIGATION_REQUEST_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=fc14cfa5-6cbc-47bf-9674-efdcc7628350&patientId=%s";

    private static final String REQUEST_APPOINTMENT_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=f09a3a3a-810e-4cf6-b432-3d43da303911&patientId=%s";

    private static final String REFER_PATIENT_URL = "/htmlformentryui/htmlform/enterHtmlFormWithStandardUi.page?formUuid=a9f3411c-03d8-4652-8143-886d572cbf4d&patientId=%s";

    private static final Map<String, String> OPERATION_TO_URL = new HashMap<String, String>() {

        private static final long serialVersionUID = -4322535511417688724L;

        {
            put("save.and.exit.action", DASHBOARD_URL);
            put("complete.action", DASHBOARD_URL);
            put("ncd.baseline.medicalhistory.next", LIFESTYLE_URL);
            put("ncd.baseline.lifestyle.previous", MEDICAL_HISTORY_URL);
            put("ncd.baseline.lifestyle.next", ALLERGIES_URL);
            put("ncd.baseline.alergies.previous", LIFESTYLE_URL);
            put("ncd.baseline.alergies.next", DIAGNOSIS_URL);
            put("ncd.baseline.diagnosis.previous", ALLERGIES_URL);
            put("ncd.baseline.diagnosis.next", COMPLICATIONS_URL);
            put("ncd.baseline.complications.previous", DIAGNOSIS_URL);
            put("ncd.baseline.complications.next", PRESCRIBE_MEDICATION_URL);
            put("ncd.baseline.prescribemedication.previous", COMPLICATIONS_URL);
            put("ncd.baseline.prescribemedication.next", PATIENT_TARGETS_URL);
            put("ncd.baseline.patienttargets.previous", PRESCRIBE_MEDICATION_URL);
            put("ncd.baseline.patienttargets.next", REGULAR_PATIENT_REVIEW_URL);
            put("ncd.baseline.regularpatientreview.previous", PATIENT_TARGETS_URL);
            put("ncd.baseline.regularpatientreview.next", CLINICAL_NOTE_URL);
            put("ncd.baseline.clinicalnote.previous", REGULAR_PATIENT_REVIEW_URL);
            put("ncd.baseline.clinicalnote.next", INVESTIGATION_REQUEST_URL);
            put("ncd.baseline.requestinvestigation.previous", CLINICAL_NOTE_URL);
            put("ncd.baseline.requestinvestigation.next", REQUEST_APPOINTMENT_URL);
            put("ncd.baseline.requestappointment.previous", INVESTIGATION_REQUEST_URL);
            put("ncd.baseline.requestappointment.next", REFER_PATIENT_URL);
            put("ncd.baseline.referpatient.previous", REQUEST_APPOINTMENT_URL);
        }
    };

    @Override
    public void applyAction(FormEntrySession formEntrySession) {
        String operation = getRequest().getParameter("msf.operation");
        if (operation != null) {
            String nextUrl = OPERATION_TO_URL.get(operation);
            if (nextUrl != null) {
                formEntrySession.setAfterSaveUrlTemplate(String.format(nextUrl, formEntrySession.getPatient().getId()));
            } else {
                throw new IllegalArgumentException(String.format("No URL found for operation %s", operation));
            }
        } else {
            throw new IllegalArgumentException("msf.operation parameter not set");
        }
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        } else {
            throw new IllegalStateException("This really should not happen");
        }
    }

}
