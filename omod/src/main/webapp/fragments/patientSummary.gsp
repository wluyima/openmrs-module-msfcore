
<script type="text/javascript">
    jQuery(function() {
    	// add demographics
    	overWriteWithTable("#demograpics", [
    		"${ui.message("msfcore.name")}${patientSummary.demographics.name}",
    		"${ui.message("msfcore.age")}${patientSummary.demographics.age.age}",
    		"${ui.message("msfcore.dob")}${patientSummary.demographics.age.formattedBirthDate}"
    	]);
    	// add vitals
    	overWriteWithTable("#vitals", [
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
    	overWriteWithTable("#diagnoses", 
    		"<% patientSummary.diagnoses.each { d -> %>|s|${d.name}<% } %>".split("|s|").filter(v=>v!=''),
    	1);
    	
    	// add medications
    	overWriteWithTable("#medications", 
    		"<% patientSummary.currentMedications.each { m -> %>|s|${m.value}<% } %>".split("|s|").filter(v=>v!=''),
    	1);
    	
    	// add clinicalNotes
    	overWriteWithTable("#clinical-notes", 
    		"<% patientSummary.clinicalNotes.each { n -> %>|s|${n.value}<% } %>".split("|s|").filter(v=>v!=''),
    	1);
    });
    
    // TODO to support full representation, we may move this to patientSummary.js and share it across or use different page than same fragment
    function overWriteWithTable(element, items, limitHorizontally) {
    	if(element && items) {
	    	var tableContent = "<table>";
	    	var lastBreakIndex;
	    	var lastIndex;
	    	var breakPoint = limitHorizontally && limitHorizontally != 1 ? Math.ceil(items.length/limitHorizontally) : (limitHorizontally ? 1 : undefined);
	    	//remove empty items
	    	items = items.filter(function (el) {
			  return el != null;
			});
	    	for(i = 0; i < items.length; i++) {
	    		//build horizontally at first item or after break point
	    		if(i == 0 || breakPoint == lastIndex - lastBreakIndex) {
	    			tableContent += "<tr>";
	    		}
	    		// build table data/records
	    		tableContent += "<td>" + items[i] + "</td>";
	    		// break table vertically at next break point
	    		if(!tableContent.endsWith("</tr>") 
	    			&& (breakPoint - 1 == i || (breakPoint == 0 && i == items.length - 1))) {
	    			tableContent += "</tr>";
	    			lastBreakIndex = i;
	    		}
	    		lastIndex = i;
	    	}
	    	
	    	tableContent += "</table>"
	    	jQuery(element).html(tableContent);
    	}
    }
</script>

<div id="patient-summary-header">   
	<div class="logo" class="left">
		<img src="${ui.resourceLink("msfcore", "images/msf_logo.png")}"  height="50" width="100"/>
	</div>
	<div class="right">
		${patientSummary.facility}
	</div>
</div>

<h1>
	<% if (patientSummary.representation == "FULL") { %>
		${ui.message("msfcore.full")}
	<% } %>
	${ui.message("msfcore.patientSummary")}
</h1>


<h2 >${ui.message("msfcore.patientSummary.demograpicDetails")}</h2>
<div id="demograpics"></div>

<h2>${ui.message("msfcore.recentVitalsAndObservations")}</h2>
<div id="vitals"></div>

<h2>${ui.message("msfcore.workingDiagnosis")}</h2>
<div id="diagnoses"></div>

<h2>${ui.message("msfcore.knownAllergies")}</h2>
<div id="allergies"></div>

<h2>${ui.message("msfcore.clinicalHistory")}</h2>
<div id="clinical-history"></div>

<h2>${ui.message("msfcore.recentLabTest")}</h2>
<div id="lab-tests"></div>

<h2>${ui.message("msfcore.currentMedication")}</h2>
<div id="medications"></div>

<h2>${ui.message("msfcore.clinicalNotes")}</h2>
<div id="clinical-notes"></div>

<div id="patient-summary-signature">
	<div class="left">
		<b>${ui.message("msfcore.patientSummary.provider")}</b>${patientSummary.provider}
	</div>
	<div class="right">
		<b>${ui.message("msfcore.patientSummary.signature")}</b>
	</div>
</div>

<br />

<div class="logo" class="center">
	<input type="button" onclick="window.print();" class="noprint" value="${ ui.message('msfcore.print')}"/>
	<input type="button" onclick="history.back();" class="noprint" value="${ ui.message('msfcore.close')}"/>
</div>
