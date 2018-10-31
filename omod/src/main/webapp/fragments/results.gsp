<script src="${ui.resourceLink('msfcore', 'scripts/msf.js')}"></script>
<% ui.includeJavascript('msfcore', 'scripts/results.controller.js') %>
<% ui.includeCss('msfcore', 'scripts/results.css') %>

<script type="text/javascript">
    jQuery(function() {
    	jQuery("#print-results").click(function(e) {
    		printPageWithIgnore("#results-print-close,#results-filter");
    	});
	});
</script>

<div ng-app="resultsApp" ng-controller="ResultsController" ng-init="results = retrieveResults()">
	<div id="results-filter" class="right">
		<select ng-if="results.filters.length > 0" class="right">
			<option>${ui.message("msfcore.filter")}</option>
		</select>
	</div>
	<div id="results-data">
		<table>
		    <thead>
			    <tr>
			    	<th ng-repeat="key in results.keys">${ui.message({{key}})}</th>
			        <th>${ui.message("msfcore.actions")}</th>
			    </tr>
		    </thead>
		    <tbody>
		    	<tr ng-repeat="result in results.results" id="{{result["id"]['value']}}">
		    		<td ng-repeat="key in results.keys">
		    			<label ng-if="resultPending(result, key);else value_label" ng-class="{'column-status': resultPending(result, key);'value-editable': result[key]['value']['editable']}">{{result['status']}}</label>
		    			<ng-template #value_label>{{result[key]['value']}}</ng-template>
		    		</td>
			    	<td ng-if="result['actions'].length > 0">
			    		<span ng-if="result['actions'].indexOf('EDIT') > 0"><i class="icon-edit"></i></span>
			    		<span ng-if="result['actions'].indexOf('DELETE') > 0"><i class="icon-trash"></i></span>
			    	</td>
		    </tbody>
		</table>
	</div>
	<div id="results-print-close-wrapper">
		<div id="results-print-close">
			<input type="button" id="print-results" value="${ ui.message('msfcore.print')}"/>
			<input type="button" onclick="history.back();" value="${ ui.message('msfcore.close')}"/>
		</div>
	</div>
</div>