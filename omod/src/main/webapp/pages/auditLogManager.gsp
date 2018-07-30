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
    
    jQuery(function() {
    	jQuery("#start-time-display").val("${startTime}");
    	jQuery("#end-time-display").val("${endTime}");
    	toggleFiltersDisplay();
    });
</script>

<h2 style="background-color:#f3f3f3;" onclick="toggleFiltersDisplay()">${ui.message("msfcore.filters")}<i id="filters-icon" class="icon-angle-down right"></i></h2>

<form id="filters" method="post">
	${ ui.includeFragment("uicommons", "field/datetimepicker", [id: 'start-time', label: 'msfcore.starttime', formFieldName: 'startTime', useTime: true ]) }
	${ ui.includeFragment("uicommons", "field/datetimepicker", [id: 'end-time', label: 'msfcore.endtime', formFieldName: 'endTime', useTime: true ]) }

	<p align="right"><input type="submit" value="${ ui.message('msfcore.filter')}"/></p>
</form>
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