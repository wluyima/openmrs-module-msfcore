<script type="text/javascript">
if (jQuery) {
    jq(document).ready(function () {
        // Set active class on the menu currently selected
        var currentFormName = jq("htmlform").attr("formName");
        if (currentFormName == "Visit Details Followup") {
            jq("#visitdetails").attr("class","active");
        }
		if (currentFormName == "Diagnosis Followup") {
			jq("#diagnosis").attr("class","active");
		}
        if (currentFormName == "Complications Since Last Visit Followup") {
            jq("#complicationsincelastvisit").attr("class","active");
        }
		if (currentFormName == "Prescribe Medication Followup") {
			jq("#prescribemedication").attr("class","active");
		}
		if (currentFormName == "Clinical note Followup") {
			jq("#clinicalnote").attr("class","active");
		}
		if (currentFormName == "Refer Patient Followup") {
			jq("#referpatient").attr("class","active");
		}
        if (currentFormName == "Request Investigation Followup") {
            jq("#investigationrequest").attr("class","active");
        }
        if (currentFormName == "Schedule Appointment Followup") {
            jq("#scheduleappointmentfollowup").attr("class","active");
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
        <h3>NCD Followup Note</h3>
        <ol>
            <li>
                <a id="visitdetails" href="<% print visitDetailsLink %>">Visit details</a>
            </li>
            <li>
                <a id="diagnosis" href="<% print diagnosisLink %>">${ui.message("msfcore.ncdbaseline.diagnosis.title")}</a>
            </li>
            <li>
                <a id="complicationsincelastvisit" href="<% print complicationsSinceLastVisitLink %>">${ui.message("msfcore.ncdfollowup.complicationssincelastvisit.title")}</a>
            </li>
			<li>
				<a id="prescribemedication" href="<% print prescribeMedicationLink %>">${ui.message("msfcore.ncdbaseline.prescribemedication.title")}</a>
			</li>
            <li>
                <a id="clinicalnote" href="<% print clinicalNoteLink %>">${ui.message("msfcore.ncdbaseline.clinicalnote.title")}</a>
            </li>
			<li>
				<a id="referpatient" href="<% print referPatientLink %>">${ui.message("msfcore.ncdbaseline.referpatient.title")}</a>
			</li>
	        <li>
                <a id="investigationrequest" href="<% print requestInvestigationLink %>">${ui.message("msfcore.ncdbaseline.investigationrequest.title")}</a>
            </li>
            <li>
                <a id="scheduleappointmentfollowup" href="<% print scheduleAppointmentLink %>">${ui.message("msfcore.ncdfollowup.scheduleappointment.title")}</a>
            </li>
         
        </ol>

        <button class="nav-button msf-operation-button final" value="save.and.exit.action">${ui.message('msfcore.save.and.exit')}</button>
        <button class="nav-button msf-operation-button save" value="complete.action">${ui.message('msfcore.save.final')}</button>
	    <input id="msf-operation" name="msf.operation" type="hidden" />

    </div>
</div>