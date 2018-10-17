<script type="text/javascript">
if (jQuery) {
    jq(document).ready(function () {
        // Set active class on the menu currently selected
        var currentFormName = jq("htmlform").attr("formName");
        if (currentFormName == "Medical History") {
            jq("#medicalhistory").attr("class","active");
        }
        if (currentFormName == "Lifestyle") {
            jq("#lifestyle").attr("class","active");
        }
        if (currentFormName == "Allergies") {
            jq("#allergies").attr("class","active");
        }
        if (currentFormName == "Diagnosis") {
            jq("#diagnosis").attr("class","active");
        }
        if (currentFormName == "Complications") {
            jq("#complications").attr("class","active");
        }
        if (currentFormName == "Prescribe Medication") {
            jq("#prescribemedication").attr("class","active");
        }
        if (currentFormName == "Regular Patient Review") {
            jq("#regular-patient-review").attr("class","active");
        }
        if (currentFormName == "Clinical Note") {
            jq("#clinicalnote").attr("class","active");
        }
        if (currentFormName == "Request Investigation") {
            jq("#investigationrequest").attr("class","active");
        }
        if (currentFormName == "Refer Patient") {
            jq("#referpatient").attr("class","active");
        }
        if (currentFormName == "Patient Targets") {
            jq("#patienttargets").attr("class","active");
        }
        if (currentFormName == "Request Appointment") {
            jq("#requestappointment").attr("class","active");
        }

        if (window.location.href.indexOf("htmlform/viewEncounterWithHtmlForm.page") != -1) {
            jq('button.no-print').addClass('hidden');
        }
        jq("#medicalhistory").attr("href", jq("#medical-history-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#lifestyle").attr("href", jq("#lifestyle-form-url").html() + '&patientId='+ jq('input[name=personId]').val());
        jq("#allergies").attr("href", jq("#allergies-form-url").html() + '&patientId='+ jq('input[name=personId]').val());
        jq("#diagnosis").attr("href", jq("#diagnosis-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#complications").attr("href", jq("#complications-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#prescribemedication").attr("href", jq("#prescribe-medication-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#regular-patient-review").attr("href", jq("#regular-patient-review-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#clinicalnote").attr("href", jq("#clinical-note-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#investigationrequest").attr("href", jq("#investigation-request-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#referpatient").attr("href", jq("#referpatient-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#patienttargets").attr("href", jq("#patient-targets-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#requestappointment").attr("href", jq("#request-appointment-form-url").html() + '&patientId=' + jq('input[name=personId]').val());

        //handle nav bar button clicks
        jq(".msf-operation-button").on("click", function() {
            jq("#msf-operation").attr("value", this.value);
			return true;
        });

    });

}

</script>
<div class="left-column">
    <div class="left-navigation">
        <h3>NCD Baseline Consultation Note</h3>
        <ol>
            <li>
                <a id="medicalhistory" href="#medicalhistory">Medical history</a>
                <span class="hidden section-link" id="medical-history-form-url">enterHtmlFormWithStandardUi.page?formUuid=06807e2b-ce97-4d65-8796-e955fcbe057d</span>
            </li>
            <li>
                <a id="lifestyle" href="#lifestyle">${ui.message("msfcore.ncdbaseline.lifestyle.title")}</a>
                <span class="hidden section-link" id="lifestyle-form-url">enterHtmlFormWithStandardUi.page?formUuid=3209cd5f-656e-42f4-984e-ab466a5b77ef</span>
            </li>
            <li>
                <a id="allergies" href="#allergies">${ui.message("msfcore.ncdbaseline.allergies.title")}</a>
                <span class="hidden section-link" id="allergies-form-url">enterHtmlFormWithStandardUi.page?formUuid=30d1fda4-4161-4666-ad0c-e2ba20eb73a6</span>
            </li>
            <li>
                <a id="diagnosis" href="#diagnosis">${ui.message("msfcore.ncdbaseline.diagnosis.title")}</a>
                <span class="hidden section-link" id="diagnosis-form-url">enterHtmlFormWithStandardUi.page?formUuid=860d4952-7490-4a70-9e75-8cf4ebf10df8</span>
            </li>
            <li>
                <a id="complications" href="#complications">${ui.message("msfcore.ncdbaseline.complications.title")}</a>
                <span class="hidden section-link" id="complications-form-url">enterHtmlFormWithStandardUi.page?formUuid=f09a3a3a-810e-4cf6-b432-3d43da303933</span>
            </li>
            <li>
                <a id="investigationrequest" href="#investigationrequest">${ui.message("msfcore.ncdbaseline.investigationrequest.title")}</a>
                <span class="hidden section-link" id="investigation-request-form-url">enterHtmlFormWithStandardUi.page?formUuid=fc14cfa5-6cbc-47bf-9674-efdcc7628350</span>
            </li>
            <li>
                <a id="prescribemedication" href="#prescribemedication">${ui.message("msfcore.ncdbaseline.prescribemedication.title")}</a>
                <span class="hidden section-link" id="prescribe-medication-form-url">enterHtmlFormWithStandardUi.page?formUuid=aab2cab6-c280-438b-9afd-3c54e799ef2a</span>
            </li>
            <li>
                <a id="patienttargets" href="#patienttargets">${ui.message("msfcore.ncdbaseline.patienttargets.title")}</a>
                <span class="hidden section-link" id="patient-targets-form-url">enterHtmlFormWithStandardUi.page?formUuid=f88f341a-2a37-47e9-ac81-b5dae813ab26</span>
            </li>
            <li>
                <a id="regular-patient-review" href="#regular-patient-review">${ui.message("msfcore.ncdbaseline.regularpatientreview.title")}</a>
                <span class="hidden section-link" id="regular-patient-review-form-url">enterHtmlFormWithStandardUi.page?formUuid=b450ec93-f4b5-4a4b-8143-4564d84028bc</span>
            </li>
            <li>
                <a id="clinicalnote" href="#clinicalnote">${ui.message("msfcore.ncdbaseline.clinicalnote.title")}</a>
                <span class="hidden section-link" id="clinical-note-form-url">enterHtmlFormWithStandardUi.page?formUuid=f09a3a3a-810e-4cf6-b432-3d43da303948</span>
            </li>
            <li>
                <a id="requestappointment" href="#requestappointment">${ui.message("msfcore.ncdbaseline.requestappointment.title")}</a>
                <span class="hidden section-link" id="request-appointment-form-url">enterHtmlFormWithStandardUi.page?formUuid=f09a3a3a-810e-4cf6-b432-3d43da303911</span>
            </li>
            <li>
                <a id="referpatient" href="#referpatient">${ui.message("msfcore.ncdbaseline.referpatient.title")}</a>
                <span class="hidden section-link" id="referpatient-form-url">enterHtmlFormWithStandardUi.page?formUuid=a9f3411c-03d8-4652-8143-886d572cbf4d</span>
            </li>
        </ol>

        <button class="nav-button msf-operation-button save" value="save.and.exit.action">${ui.message('msfcore.save.and.exit')}</button>
        <button class="nav-button msf-operation-button final" value="complete.action">${ui.message('msfcore.save.final')}</button>
	    <input id="msf-operation" name="msf.operation" type="hidden" />

    </div>
</div>