<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("msfcore.auditlogs.manager.label") ])
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("coreapps.app.systemAdministration.label")}", link: '/' + OPENMRS_CONTEXT_PATH + '/coreapps/systemadministration/systemAdministration.page'},
        { label: "${ ui.message("msfcore.auditlogs.manager.label")}"}
    ];
</script>

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