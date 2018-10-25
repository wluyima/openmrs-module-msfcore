<%
    ui.decorateWith("appui", "standardEmrPage", [ title: ui.message("msfcore.labResultsHistory") ])
%>

<script type="text/javascript">
    var breadcrumbs = [
        { icon: "icon-home", link: '/' + OPENMRS_CONTEXT_PATH + '/index.htm' },
        { label: "${ ui.encodeHtmlContent(ui.format(patient.familyName)) } ${ ui.encodeHtmlContent(ui.format(patient.givenName)) }",
            link: "${ ui.pageLink("coreapps", "clinicianfacing/patient", [patientId: patient.uuid]) }" },
        { label: "${ ui.message("msfcore.labResultsHistory") }" }
    ];
</script>


<style>
	#results-print-close-wrapper {
	    text-align: center;
	}
	#results-print-close {
		display: inline-block;
	}
</style>

${ ui.includeFragment("coreapps", "patientHeader", [ patient: patient ]) }

<br />

<div id="results-data">
	<table>
	    <thead>
		    <tr>
				<th>${ui.message("msfcore.testorderandresult.testname")}</th>
				<th>${ui.message("msfcore.testorderandresult.result")}</th>
				<th>${ui.message("msfcore.testorderandresult.unitofmeasure")}</th>
				<th>${ui.message("msfcore.testorderandresult.range")}</th>
				<th>${ui.message("msfcore.testorderandresult.orderdate")}</th>
				<th>${ui.message("msfcore.testorderandresult.sampledate")}</th>
				<th>${ui.message("msfcore.testorderandresult.resultdate")}</th>
				<th>${ui.message("msfcore.testorderandresult.actions")}</th>
		    </tr>
	    </thead>
	    <tbody>
	    	<% results.each { result -> %>
		    	<tr>
					<td>${result.getTestName()}</td>
					<td>
						<% if(result.getTestResult() == "_PENDING") { %>
							<span class="pending">${ui.message("msfcore.testorderandresult.pending")}</span>
						<% } else { %>
							<span>${result.getTestResult()}</span>
						<% } %>
					</td>
					<td>${result.getUnitOfMeasure()}</td>
					<td>${result.getRange()}</td>
					<td>${result.getOrderDate()}</td>
					<td>
						<% if(result.getSampleDate() == "_PENDING") { %>
							<span class="pending">${ui.message("msfcore.testorderandresult.pending")}</span>
						<% } else { %>
							<span>${result.getSampleDate()}</span>
						<% } %>
					</td>
					<td>
						<% if(result.getResultDate() == "_PENDING") { %>
							<span class="pending">${ui.message("msfcore.testorderandresult.pending")}</span>
						<% } else { %>
							<span>${result.getResultDate()}</span>
						<% } %>
					</td>
					<td></td>
		    	</tr>
	    	<% } %>
	    </tbody>
	</table>
</div>