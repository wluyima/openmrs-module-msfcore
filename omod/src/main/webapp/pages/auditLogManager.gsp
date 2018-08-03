<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("msfcore.auditlogs.manager.label") ])
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("coreapps.app.systemAdministration.label")}", link: '/' + OPENMRS_CONTEXT_PATH + '/coreapps/systemadministration/systemAdministration.page'},
        { label: "${ ui.message("msfcore.auditlogs.manager.label")}"}
    ];
    
    jQuery(function() {
	  jQuery("[id^=start-time-]").val("${startTime}");
	  jQuery("[id^=end-time-]").val("${endTime}");
	  jQuery.each("${selectedEvents}".replace('[', '').replace(']', '').split(","), function(i, e) {
	    jQuery("#events option[value=" + e + "]").prop("selected", true);
	  });
	  jQuery("#viewer").autocomplete({
	    source: "${userSuggestions}".replace('[', '').replace(']', '').split(",")
	  });
	});
</script>

<% ui.includeJavascript("msfcore", "auditLogsManager.js") %>

<h2 style="background-color:#f3f3f3;" onclick="toggleFiltersDisplay()">${ui.message("msfcore.filters")}<i id="filters-icon" class="icon-angle-down right"></i></h2>

<div id="filters">
	<form method="post" id="filter-form" onsubmit="return true">
		${ ui.includeFragment("uicommons", "field/datetimepicker", [id: 'start-time', label: 'msfcore.starttime', formFieldName: 'startTime', useTime: true ]) }
		
		${ ui.includeFragment("uicommons", "field/datetimepicker", [id: 'end-time', label: 'msfcore.endtime', formFieldName: 'endTime', useTime: true ]) }
		
		<br /><br />${ui.message("msfcore.events")}<br />
		<select id="events" name="events" multiple="true">
			<% events.each { event -> %>
	        	<option value="${event}">${event}</option>
	        <% } %>
	    </select>
	    
	    <br />${ui.message("msfcore.user")}<br />
	    <input type="text" id="viewer" name="viewer" class="field-display ui-autocomplete-input" value="${selectedViewer}">
	    
	    <br />${ui.message("msfcore.patient")} <b id="patient-display">${patientDisplay}</b><br />
	    <input type="hidden" name="patientId" id="patient-id" value="${patientId}"/>
	    ${ ui.includeFragment("coreapps", "patientsearch/patientSearchWidget", [ showLastViewedPatients: false ]) }
		<br/>
		<input type="button" id="submit-form" value="${ ui.message('msfcore.filter')}"/>
		<input type="button" onclick="window.location.href='auditLogManager.page'" value="${ ui.message('msfcore.reset')}"/>
	</form>
</div>

<h2 style="background-color:#f3f3f3;">${ui.message("msfcore.logsDisplay")}</h2>

<table>
    <thead>
	    <tr>
	        <th>${ ui.message("msfcore.event")}</th>
	        <th>${ ui.message("msfcore.time")}</th>
	        <th>${ ui.message("msfcore.user")}</th>
	        <th>${ ui.message("msfcore.detail")}</th>
	    </tr>
    </thead>
    <% auditLogs.each { log -> %>
	    <tbody>
	    	<tr>
	    		<td>${log.event}</td>
	    		<td>${ui.formatDatetimePretty(log.date)}</td>
	    		<td>${log.user}</td>
	    		<td>${log.detail}</td>
	    	</tr>
	    </tbody>
    <% } %>
</table>