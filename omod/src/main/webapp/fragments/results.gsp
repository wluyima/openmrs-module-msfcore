<script src="${ui.resourceLink('msfcore', 'scripts/msf.js')}"></script>
<script src="${ui.resourceLink('msfcore', 'scripts/results.controller.js')}"></script>
<link href="${ui.resourceLink('msfcore', 'styles/results.css')}" rel="stylesheet" type="text/css" media="all">

<script type="text/javascript">
    jQuery(function() {
    	jQuery("#print-results").click(function(e) {
    		printPageWithIgnore(".print-ignore");
    	});
	});
</script>

<h3>${pageLabel}</h3>
<div ng-app="resultsApp" ng-controller="ResultsController" ng-init="retrieveResults()">
	<div class="right print-ignore">
		<select ng-if="results.filters.length > 0" class="right">
			<option>${ui.message("msfcore.filter")}</option>
		</select>
	</div>
	<div id="results-data">
		<table>
		    <thead>
			    <tr>
			    	<th ng-repeat="key in results.keys">{{key}}</th>
			        <th class="print-ignore">${ui.message('msfcore.actions')}</th>
			    </tr>
		    </thead>
		    <tbody>
		    	<tr ng-repeat="result in results.results" id="{{result.uuid.value}}">
		    		<td ng-repeat="key in results.keys" ng-init="editableAndPending = resultPendingWhenEditable(result, key);">
		    			<label ng-if="editableAndPending" ng-class="{'column-status':editableAndPending, 'editable':result[key].editable}" id="{{result.uuid.value}}_{{results.keys.indexOf(key)}}">{{result.status.value}}</label>
		    			<label ng-if="!editableAndPending" id="{{result.uuid.value}}_{{results.keys.indexOf(key)}}"  ng-class="{'editable':result[key].editable}">{{result[key].value}}</label>
		    		</td>
			    	<td ng-if="result.actions.value.length > 0" class="print-ignore">
			    		<span ng-if="result.actions.value.includes('EDIT') > 0"><i class="icon-edit" ng-click="edit(result);"></i></span>
			    		<span ng-if="result.actions.value.includes('DELETE') > 0"><i class="icon-trash" ng-click="purge(result);"></i></span>
			    	</td>
			    	<td ng-if="result.actions.value.length == 0" class="print-ignore"></td>
		    </tbody>
		</table>
	</div>
	<div id="results-print-close-wrapper">
		<div class="print-ignore">
			<input type="button" id="print-results" value="${ ui.message('msfcore.print')}"/>
			<input type="button" onclick="history.back();" value="${ ui.message('msfcore.close')}"/>
		</div>
	</div>
</div>