<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("msfcore.auditlogs.manager.label") ])
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.message("coreapps.app.systemAdministration.label")}", link: '/' + OPENMRS_CONTEXT_PATH + '/coreapps/systemadministration/systemAdministration.page'},
        { label: "${ ui.message("msfcore.configurations")}"}
    ];
    jQuery(function() {
    	jQuery('#location-codes').slideToggle("slow");
    });
</script>

<div class="note-container">
	<div class="note warning">
		${ui.message("msfcore.configurations.info")}
	</div>
</div>
<form method="post">
	<h2 style="background-color:#f3f3f3;">${ui.message("msfcore.mandatory")}</h2>
	${ui.message("msfcore.instanceId")}<br />
	<input type="text" name="instanceId" value="${instanceId}"/>
	<br />${ui.message("msfcore.defaultLocation")}<br />
	<select name="defaultLocationUuid">
		<% allLocations.each { loc -> %>
	        	<option value="${loc.uuid}" <% if(defaultLocation.uuid.equals(loc.uuid)){ %>selected<% } %>>${loc.name}</option>
	    <% } %>
	</select>
	<h2 style="background-color:#f3f3f3;cursor:pointer;" onclick="jQuery('#location-codes').toggle();">${ui.message("msfcore.locationCodes")}</h2>
	<div id="location-codes">
		<table>
		    <thead>
			    <tr>
			        <th>${ ui.message("Location.title")}</th>
			        <th>${ ui.message("msfcore.code")}</th>
			        <th>${ ui.message("msfcore.uid")}</th>
			    </tr>
		    </thead>
		    <% msfLocations.each { mLoc -> %>
			    <tbody>
			    	<tr>
			    		<td>${mLoc.display}</td>
			    		<td><input type="text" name="${mLoc.uuid}" value="${mLoc.code}" /></td>
			    		<td><input type="text" name="${mLoc.uuid}_uid" value="${mLoc.uid}" /></td>
			    	</tr>
			    </tbody>
		    <% } %>
		</table>
	</div>
	<input type="submit" value="${ ui.message('general.submit')}"/>
</form>