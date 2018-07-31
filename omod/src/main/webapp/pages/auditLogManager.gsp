<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("msfcore.auditlogs.manager.label") ])
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("coreapps.app.systemAdministration.label")}", link: '/' + OPENMRS_CONTEXT_PATH + '/coreapps/systemadministration/systemAdministration.page'},
        { label: "${ ui.message("msfcore.auditlogs.manager.label")}"}
    ];
    
    function toggleFiltersDisplay() {
    	jQuery("#filters").toggle(500, function() {
    		jQuery("#filters-icon").toggleClass("icon-angle-down");
    		jQuery("#filters-icon").toggleClass("icon-angle-up");
    	});
    }
    
    jQuery(function() {
	    jQuery.urlParam = function(param){
			var result = new RegExp(param + "=([^&]*)", "i").exec(window.location.search); 
			return result && unescape(result[1]) || ""; 
		};
		
    	jQuery("#start-time-display").val("${startTime}");
    	jQuery("#end-time-display").val("${endTime}");
    	toggleFiltersDisplay();
    	jQuery.each("${selectedEvents}".replace('[', '').replace(']', '').split(","), function(i,e) {
    		jQuery("#events option[value=" + e + "]").prop("selected", true);
		});
		jQuery("#creator").autocomplete({
	      source: "${userSuggestions}".replace('[', '').replace(']', '').split(",")
	    });
	    jQuery("#viewer").autocomplete({
		    source: "${userSuggestions}".replace('[', '').replace(']', '').split(","),
		    close: function(e, ui) {
		    	jQuery("#patient-viewed-by-user").submit();
		    }
		});
    });
</script>

<h2 style="background-color:#f3f3f3;" onclick="toggleFiltersDisplay()">${ui.message("msfcore.filters")}<i id="filters-icon" class="icon-angle-down right"></i></h2>

<div id="filters">
	<form method="post">
		${ ui.includeFragment("uicommons", "field/datetimepicker", [id: 'start-time', label: 'msfcore.starttime', formFieldName: 'startTime', useTime: true ]) }
		${ ui.includeFragment("uicommons", "field/datetimepicker", [id: 'end-time', label: 'msfcore.endtime', formFieldName: 'endTime', useTime: true ]) }
		
		<br />${ui.message("msfcore.events")}<br />
		<select id="events" name="events" multiple="true">
			<% events.each { event -> %>
	        	<option value="${event}">${event}</option>
	        <% } %>
	    </select>
	    ${ui.message("msfcore.creator")}<br />
		<input type="text" id="creator" name="creator" class="field-display ui-autocomplete-input" value="${selectedUser}">
		
		<input class="right" type="submit" value="${ ui.message('msfcore.filter')}"/>
	</form>
	<h3>${ui.message("msfcore.quickFilters")}</h3>
	<form id="patient-viewed-by-user" method="post">
		${ui.message("msfcore.quickFilters.patientViewedByUser")}<b>${selectedViewer}</b><br/>
		<input type="text" id="viewer" name="viewer" class="field-display ui-autocomplete-input">
	</form>
		
	<br />${ui.message("msfcore.quickFilters.usersPatientView")}<b>${patientDisplay}</b>
	<div class="dialog">
		<div class="dialog-content">
			${ ui.includeFragment("coreapps", "patientsearch/patientSearchWidget",
		       [ afterSelectedUrl: '/msfcore/auditLogManager.page?patientId={{patientId}}', showLastViewedPatients: false ]) }
	    </div>
	</div>
	<input type="button" onclick=window.location.href='auditLogManager.page' class="right" value="${ ui.message('msfcore.reset')}"/>
	<br /><br />
</div>

<h2 style="background-color:#f3f3f3;">${ui.message("msfcore.logsDisplay")}</h2>

<table>
    <thead>
	    <tr>
	        <th>${ ui.message("msfcore.event")}</th>
	        <th>${ ui.message("msfcore.time")}</th>
	        <th>${ ui.message("msfcore.creator")}</th>
	        <th>${ ui.message("msfcore.detail")}</th>
	    </tr>
    </thead>
    <% msfCoreLogs.each { log -> %>
	    <tbody>
	    	<tr>
	    		<td>${log.event}</td>
	    		<td>${ui.formatDatetimePretty(log.date)}</td>
	    		<td>${log.creator}</td>
	    		<td>${log.detail}</td>
	    	</tr>
	    </tbody>
    <% } %>
</table>