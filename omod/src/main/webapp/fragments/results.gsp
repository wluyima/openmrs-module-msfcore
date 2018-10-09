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
		        	<th>${ui.message(key)}</th>
		        <% } %>
		    </tr>
	    </thead>
	    <tbody>
	    	<% resultsData.results.each { result -> %>
		    	<tr>
		    		<% resultsData.keys.each { key -> %>
			        	<td>${result.get(key)}</td>
			        <% } %>
		    	</tr>
	    	<% } %>
	    </tbody>
	</table>
</div>
<div id="results-print-close">
	<input type="button" onclick="window.print();" class="noprint" value="${ ui.message('msfcore.print')}"/>
	<input type="button" onclick="history.back();" class="noprint" value="${ ui.message('msfcore.close')}"/>
</div>
