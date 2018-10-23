<script src="${ui.resourceLink('msfcore', 'scripts/msf.js')}"></script>

<script type="text/javascript">
    jQuery(function() {
    	jQuery("#print-results").click(function(e) {
    		printPageWithIgnore("#results-print-close,#results-filter");
    	});
	});
</script>

<div id="results-filter" class="right">
	<select class="right">
		<option>${ui.message("msfcore.filter")}</option>
	</select>
</div>
<div id="results-data">
	<table>
	    <thead>
		    <tr>
		    	<% resultsData.keys.each { key -> %>
		    		<% if(key != 'id') { %>
		        		<th>${ui.message(key)}</th>
		        	<% } %>
		        <% } %>
		        <% if(!resultsData.actions.isEmpty()) { %>
		        	<th>${ui.message("msfcore.actions")}</th>
		        <% } %>
		    </tr>
	    </thead>
	    <tbody>
	    	<% resultsData.results.each { result -> %>
		    	<tr id="${result.get("id")}">
		    		<% resultsData.keys.each { key -> %>
		    			<% if(key != 'id') { %>
			        		<td>${result.get(key)}</td>
			        	<% } %>
			        <% } %>
			        <% if(!resultsData.actions.isEmpty()) { %>
			        	<td>
			        		<% if(!resultsData.actions.contains('EDIT')) { %>
			        			<!-- display edit icon and add logic -->
			        		<% } %>
			        		<% if(!resultsData.actions.contains('DELETE')) { %>
			        			<!-- display delete icon and add logic -->
			        		<% } %>
			        	</td>
			        <% } %>
		    	</tr>
	    	<% } %>
	    </tbody>
	</table>
</div>
<div id="results-print-close-wrapper">
	<div id="results-print-close">
		<input type="button" id="print-results" value="${ ui.message('msfcore.print')}"/>
		<input type="button" onclick="history.back();" value="${ ui.message('msfcore.close')}"/>
	</div>
</div>
