<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("msfcore.patientSummary") ])
%>

<script src="${ui.resourceLink('msfcore', 'scripts/msf.js')}"></script>
<link href="${ui.resourceLink('msfcore', 'styles/patientSummary.css')}" rel="stylesheet" type="text/css" media="all">

<script type="text/javascript">
    jQuery(function() {
    	// add demographics
    	tabulateItemsIntoAnElement("#demograpics", [
    		"${ui.message("msfcore.name")}${patientSummary.demographics.name}",
    		"${ui.message("msfcore.age")}${patientSummary.demographics.age.age}",
    		"${ui.message("msfcore.dob")}${patientSummary.demographics.age.formattedBirthDate}"
    	]);
    	// add vitals
    	tabulateItemsIntoAnElement("#vitals", [
    		"${ui.message("msfcore.height")}${patientSummary.vitals.get(0).height.value} ${patientSummary.vitals.get(0).height.unit}",
    		"${ui.message("msfcore.weight")}${patientSummary.vitals.get(0).weight.value} ${patientSummary.vitals.get(0).weight.unit}",
    		"${ui.message("msfcore.bmi")}${patientSummary.vitals.get(0).bmi.value}",
    		"${ui.message("msfcore.temperature")}${patientSummary.vitals.get(0).temperature.value} ${patientSummary.vitals.get(0).temperature.unit}",
    		"${ui.message("msfcore.pulse")}${patientSummary.vitals.get(0).pulse.value} ${patientSummary.vitals.get(0).pulse.unit}",
    		"${ui.message("msfcore.respiratoryRate")}${patientSummary.vitals.get(0).respiratoryRate.value} ${patientSummary.vitals.get(0).respiratoryRate.unit}",
    		"${ui.message("msfcore.bloodPressure")}${patientSummary.vitals.get(0).bloodPressure.value} ${patientSummary.vitals.get(0).respiratoryRate.unit}",
    		"${ui.message("msfcore.bloodOxygenSaturation")}${patientSummary.vitals.get(0).bloodOxygenSaturation.value} ${patientSummary.vitals.get(0).bloodOxygenSaturation.unit}"
    	], 3);
    	
    	// add diagnoses
    	tabulateItemsIntoAnElement("#diagnoses", 
    		"<% patientSummary.diagnoses.each { d -> %>|s|${d.name}<% } %>".split("|s|").filter(v=>v!=''),
    	1);
    	
    	// add allergies
    	tabulateItemsIntoAnElement("#allergies", 
    		"<% patientSummary.allergies.each { a -> %>|s|${ui.message('msfcore.allergy')}${a.name}|s|${ui.message('msfcore.reactions')}<% a.reactions.each { r -> %>${r},<% } %>|s|${ui.message('msfcore.severity')}${a.severity}<% } %>"
    			.split("|s|").filter(v=>v!=''),
    	3);
    	
    	// add lab test results
    	tabulateItemsIntoAnElement("#lab-tests", 
    		"<% patientSummary.recentLabResults.each { m -> %>|s|${m.name}:${m.value}<% } %>".split("|s|").filter(v=>v!=''),
    	2);
    	
    	// add medications
    	tabulateItemsIntoAnElement("#medications", 
    		"<% patientSummary.currentMedications.each { m -> %>|s|${m.value}<% } %>".split("|s|").filter(v=>v!=''),
    	1);
    	
    	// add clinicalNotes
    	tabulateItemsIntoAnElement("#clinical-notes", 
    		"<% patientSummary.clinicalNotes.each { n -> %>|s|${n.value}<% } %>".split("|s|").filter(v=>v!=''),
    	1);
    	
    	jQuery("#print-patient-summary").click(function(e) {
    		printPageWithIgnore(".ignore-on-print");
    	});
    });
</script>
<div id="patient-summary">
	<div id="patient-summary-header">   
		<div class="logo" class="left">
			<img src="${ui.resourceLink("msfcore", "images/msf_logo.png")}"  height="50" width="100"/>
		</div>
		<div class="right">
			${patientSummary.facility}
		</div>
	</div>
	
	<h2>
		<% if (patientSummary.representation == "FULL") { %>
			${ui.message("msfcore.full")}
		<% } %>
		${ui.message("msfcore.patientSummary")}
	</h2>
	
	
	<h3 >${ui.message("msfcore.patientSummary.demograpicDetails")}</h3>
	<div id="demograpics"></div>
	
	<h3>${ui.message("msfcore.patientSummary.recentVitalsAndObservations")}</h3>
	<div id="vitals"></div>
	
	<h3>${ui.message("msfcore.patientSummary.workingDiagnosis")}</h3>
	<div id="diagnoses"></div>
	
	<h3>${ui.message("msfcore.patientSummary.knownAllergies")}</h3>
	<div id="allergies"></div>
	
	<h3>${ui.message("msfcore.patientSummary.clinicalHistory")}</h3>
	<div id="clinical-history"></div>
	
	<h3>${ui.message("msfcore.patientSummary.recentLabTest")}</h3>
	<div id="lab-tests"></div>
	
	<h3>${ui.message("msfcore.patientSummary.currentMedication")}</h3>
	<div id="medications"></div>
	
	<h3>${ui.message("msfcore.patientSummary.clinicalNotes")}</h3>
	<div id="clinical-notes"></div>
	
	<div id="patient-summary-signature">
		<div class="left">
			<b>${ui.message("msfcore.patientSummary.provider")}</b>${patientSummary.provider}
		</div>
		<div class="right">
			<b>${ui.message("msfcore.patientSummary.signature")}</b>
		</div>
	</div>
</div>

<div class="ignore-on-print">
	<input id="print-patient-summary" type="button" value="${ ui.message('msfcore.print')}"/>
	<input type="button" onclick="history.back();" value="${ ui.message('msfcore.close')}"/>
</div> 
