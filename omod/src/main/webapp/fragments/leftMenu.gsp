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
                <a id="medicalhistory" href="<% print medicalHistoryLink %>">Medical history</a>
            </li>
            <li>
                <a id="lifestyle" href="<% print lifestyleLink %>">${ui.message("msfcore.ncdbaseline.lifestyle.title")}</a>
            </li>
            <li>
                <a id="allergies" href="<% print allergiesLink %>">${ui.message("msfcore.ncdbaseline.allergies.title")}</a>
            </li>
            <li>
                <a id="diagnosis" href="<% print diagnosisLink %>">${ui.message("msfcore.ncdbaseline.diagnosis.title")}</a>
            </li>
            <li>
                <a id="complications" href="<% print complicationsLink %>">${ui.message("msfcore.ncdbaseline.complications.title")}</a>
            </li>
            <li>
                <a id="investigationrequest" href="<% print requestInvestigationLink %>">${ui.message("msfcore.ncdbaseline.investigationrequest.title")}</a>
            </li>
            <li>
                <a id="prescribemedication" href="<% print prescribeMedicationLink %>">${ui.message("msfcore.ncdbaseline.prescribemedication.title")}</a>
            </li>
            <li>
                <a id="patienttargets" href="<% print patientTargetLink %>">${ui.message("msfcore.ncdbaseline.patienttargets.title")}</a>
            </li>
            <li>
                <a id="regular-patient-review" href="<% print regularPatientReviewLink %>">${ui.message("msfcore.ncdbaseline.regularpatientreview.title")}</a>
            </li>
            <li>
                <a id="clinicalnote" href="<% print clinicalNoteLink %>">${ui.message("msfcore.ncdbaseline.clinicalnote.title")}</a>
            </li>
            <li>
                <a id="requestappointment" href="<% print requestAppointmentLink %>">${ui.message("msfcore.ncdbaseline.requestappointment.title")}</a>
            </li>
            <li>
                <a id="referpatient" href="<% print referPatientLink %>">${ui.message("msfcore.ncdbaseline.referpatient.title")}</a>
            </li>
        </ol>

        <button class="nav-button msf-operation-button final" value="save.and.exit.action">${ui.message('msfcore.save.and.exit')}</button>
        <button class="nav-button msf-operation-button save" value="complete.action">${ui.message('msfcore.save.final')}</button>
	    <input id="msf-operation" name="msf.operation" type="hidden" />

    </div>
</div>