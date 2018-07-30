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
    		jQuery("#filters-icon").toggleClass("icon-angle-down")
    		jQuery("#filters-icon").toggleClass("icon-angle-up")
    	});
    }
    
    function resetFiltersForm() {
    	jQuery("#start-time-display").val("");
    	jQuery("#end-time-display").val("");
    	jQuery("#events option:selected").prop("selected", false);
    	jQuery("#creator").val("");
    }
    
    jQuery(function() {
    	jQuery("#start-time-display").val("${startTime}");
    	jQuery("#end-time-display").val("${endTime}");
    	toggleFiltersDisplay();
    	jQuery.each("${selectedEvents}".replace('[', '').replace(']', '').split(","), function(i,e) {
    		jQuery("#events option[value=" + e + "]").prop("selected", true);
		});
		jQuery("#creator").autocomplete({
	      source: "${userSuggestions}".replace('[', '').replace(']', '').split(",")
	    });
    });
</script>

<h2 style="background-color:#f3f3f3;" onclick="toggleFiltersDisplay()">${ui.message("msfcore.filters")}<i id="filters-icon" class="icon-angle-down right"></i></h2>

<form id="filters" method="post">
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
	
	<p><input class="left" type="submit" value="${ ui.message('msfcore.filter')}"/> <input type="button" onclick="resetFiltersForm()" class="right" value="${ ui.message('msfcore.reset')}"/></p>
</form>

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