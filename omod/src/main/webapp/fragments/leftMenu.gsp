<script type="text/javascript">
if (jQuery) {
    jq(document).ready(function () {
        jq("#medicalhistory").attr("href", jq("#medical-history-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#lifestyle").attr("href", jq("#lifestyle-form-url").html() + '&patientId='+ jq('input[name=personId]').val());
        jq("#allergies").attr("href", jq("#allergies-form-url").html() + '&patientId='+ jq('input[name=personId]').val());
        jq("#diagnosis").attr("href", jq("#diagnosis-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#prescribemedication").attr("href", jq("#prescribe-medication-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#clinicalnote").attr("href", jq("#clinical-note-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#investigationrequest").attr("href", jq("#investigation-request-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
    });
}
</script>
<div class="left-column">
    <div class="left-navigation">
        <h3>NCD Baseline Consultation Note</h3>
        <span>Paper Form ID: </span>
        <ol>
            <li>
                <a id="medicalhistory" href="#medicalhistory">Medical history</a>
                <span class="hidden" id="medical-history-form-url">enterHtmlFormWithStandardUi.page?formUuid=06807e2b-ce97-4d65-8796-e955fcbe057d</span>
            </li>
            <li>
                <a id="lifestyle" href="#lifestyle">Lifestyle</a>
                <span class="hidden" id="lifestyle-form-url">enterHtmlFormWithStandardUi.page?formUuid=3209cd5f-656e-42f4-984e-ab466a5b77ef</span>
            </li>
            <li>
                <a id="allergies" href="#allergies">Allergies</a>
                <span class="hidden" id="allergies-form-url">enterHtmlFormWithStandardUi.page?formUuid=30d1fda4-4161-4666-ad0c-e2ba20eb73a6</span>
            </li>
            <li>
                <a id="diagnosis" href="#diagnosis">Diagnosis</a>
                <span class="hidden" id="diagnosis-form-url">enterHtmlFormWithStandardUi.page?formUuid=7ba65c3e-3e16-4637-824f-ce23ccb30746</span>
            </li>
            <li>
                <a id="prescribemedication" href="#prescribemedication">Prescribe medication</a>
                <span class="hidden" id="prescribe-medication-form-url">enterHtmlFormWithStandardUi.page?formUuid=aab2cab6-c280-438b-9afd-3c54e799ef2a</span>
            </li>
            <li>
                <a href="#patienttargets">Patient targets</a>
            </li>
            <li>
                <a href="#regularpatientreview">Regular patient review</a>
            </li>
            <li>
                <a id="clinicalnote" href="#clinicalnote">Clinical note</a>
                <span class="hidden" id="clinical-note-form-url">enterHtmlFormWithStandardUi.page?formUuid=f09a3a3a-810e-4cf6-b432-3d43da303948</span>
            </li>
            <li>
                <a id="investigationrequest" href="#investigationrequest">Request investigation</a>
                <span class="hidden" id="investigation-request-form-url">enterHtmlFormWithStandardUi.page?formUuid=fc14cfa5-6cbc-47bf-9674-efdcc7628350</span>
            </li>
            <li>
                <a href="#scheduleappointment">Schedule appointment</a>
            </li>
            <li>
                <a href="#referpatient">Refer patient</a>
            </li>

        </ol>

        <button class="nav-button save">Save</button>
        <button class="nav-button cancel">Cancel</button>
        <button class="nav-button final">
            <i class="fas fa-lock"></i>
            Save final
        </button>
    </div>
</div>