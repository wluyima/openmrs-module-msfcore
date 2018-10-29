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
    
    function editTestOrder(testId) {
    	jQuery("#result-view-"+testId).addClass("hidden");
    	jQuery("#sample-date-view-"+testId).addClass("hidden");
    	jQuery("#result-date-view-"+testId).addClass("hidden");
    	jQuery("#result-edit-"+testId).removeClass("hidden");
    	jQuery("#sample-date-edit-"+testId).removeClass("hidden");
    	jQuery("#result-date-edit-"+testId).removeClass("hidden");
    	
    	jQuery("#edit-button-"+testId).addClass("hidden");
    	jQuery("#delete-button-"+testId).addClass("hidden");
    	jQuery("#save-button-"+testId).removeClass("hidden");
    	jQuery("#cancel-button-"+testId).removeClass("hidden");
    }
    
    function cancel(testId) {
    	jQuery("#result-view-"+testId).removeClass("hidden");
    	jQuery("#sample-date-view-"+testId).removeClass("hidden");
    	jQuery("#result-date-view-"+testId).removeClass("hidden");
    	jQuery("#result-edit-"+testId).addClass("hidden");
    	jQuery("#sample-date-edit-"+testId).addClass("hidden");
    	jQuery("#result-date-edit-"+testId).addClass("hidden");
    	
    	jQuery("#edit-button-"+testId).removeClass("hidden");
    	jQuery("#save-button-"+testId).addClass("hidden");
    	jQuery("#cancel-button-"+testId).addClass("hidden");
    }
    
    function saveTestOrder(testId) {
    	var result = jQuery("#result-edit-"+testId).val();
    	var sampleDate = jQuery("#sample-date-edit-"+testId).val();
    	var resultDate = jQuery("#result-date-edit-"+testId).val();
    	window.location.href = '/' + OPENMRS_CONTEXT_PATH + "/msfcore/testOrderAndResult.page?patientId=${patient.getId()}&testId="+testId+"&operation=ADD_TEST_RESULT&result="+result+"&sampleDate="+sampleDate+"&resultDate="+resultDate;
    }

	function deleteTestOrder(testId) {
		window.location.href = '/' + OPENMRS_CONTEXT_PATH + "/msfcore/testOrderAndResult.page?patientId=${patient.getId()}&testId="+testId+"&operation=REMOVE_TEST_RESULT";
	}
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
							<span id="result-view-${ result.getTestId() }" class="pending">${ui.message("msfcore.testorderandresult.pending")}</span>
						<% } else if(result.getTestResult() == "_CANCELLED") { %>
							<span id="result-view-${ result.getTestId() }" class="pending">${ui.message("msfcore.testorderandresult.cancelled")}</span>
						<% } else { %>
							<span id="result-view-${ result.getTestId() }">${result.getTestResult()}</span>
						<% } %>
						<input id="result-edit-${ result.getTestId() }" class="hidden"/>
					</td>
					<td>${result.getUnitOfMeasure()}</td>
					<td>${result.getRange()}</td>
					<td>${result.getOrderDate()}</td>
					<td>
						<% if(result.getSampleDate() == "_PENDING") { %>
							<span id="sample-date-view-${ result.getTestId() }" class="pending">${ui.message("msfcore.testorderandresult.pending")}</span>
						<% } else if(result.getSampleDate() == "_CANCELLED") { %>
							<span id="sample-date-view-${ result.getTestId() }" class="pending">${ui.message("msfcore.testorderandresult.cancelled")}</span>
						<% } else { %>
							<span id="sample-date-view-${ result.getTestId() }">${result.getSampleDate()}</span>
						<% } %>
						<input id="sample-date-edit-${ result.getTestId() }" type="date" class="hidden"/>
					</td>
					<td>
						<% if(result.getResultDate() == "_PENDING") { %>
							<span id="result-date-view-${ result.getTestId() }" class="pending">${ui.message("msfcore.testorderandresult.pending")}</span>
						<% } else if(result.getResultDate() == "_CANCELLED") { %>
							<span id="result-date-view-${ result.getTestId() }" class="pending">${ui.message("msfcore.testorderandresult.cancelled")}</span>
						<% } else { %>
							<span id="result-date-view-${ result.getTestId() }">${result.getResultDate()}</span>
						<% } %>
						<input id="result-date-edit-${ result.getTestId() }" type="date" class="hidden"/>
					</td>
					<td>
						<i id="edit-button-${ result.getTestId() }" onclick="editTestOrder(${ result.getTestId() })" style="cursor:pointer" class="fas fa-edit"></i>
						<i id="save-button-${ result.getTestId() }" onclick="saveTestOrder(${ result.getTestId() })" style="cursor:pointer" class="fas fa-check hidden"></i>
						<i id="cancel-button-${ result.getTestId() }" onclick="cancel(${ result.getTestId() })" style="cursor:pointer" class="fas fa-times hidden"></i>
						<% if(result.getTestResult() == "_PENDING") { %>
							<i id="delete-button-${ result.getTestId() }" onclick="deleteTestOrder(${ result.getTestId() })" style="cursor:pointer" class="fas fa-trash-alt"></i>
						<% } %>
					</td>
		    	</tr>
	    	<% } %>
	    </tbody>
	</table>
</div>