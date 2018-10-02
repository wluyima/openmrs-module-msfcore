if (jQuery) {
    jq(document).ready(function () {
        jq("#medicalhistory").attr("href", jq("#medical-history-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#lifestyle").attr("href", jq("#lifestyle-form-url").html() + '&patientId='+ jq('input[name=personId]').val());
        jq("#allergies").attr("href", jq("#allergies-form-url").html() + '&patientId='+ jq('input[name=personId]').val());
        jq("#diagnosis").attr("href", jq("#diagnosis-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#prescribemedication").attr("href", jq("#prescribe-medication-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
        jq("#clinicalnote").attr("href", jq("#clinical-note-form-url").html() + '&patientId=' + jq('input[name=personId]').val());
    });
}