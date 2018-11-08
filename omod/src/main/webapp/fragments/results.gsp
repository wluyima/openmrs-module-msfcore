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

<div id="results-header" class="print-ignore">
	<h3 class="pull-left">${pageLabel}</h3>
	<input type="button" class="pull-right" id="print-results" value="${ui.message('msfcore.printResults')}"/>
</div>
<br />
<div ng-app="resultsApp" ng-controller="ResultsController" ng-init="retrieveResults()">
	<div class="print-ignore" ng-if="results.filters">
		<table id="filters-table">
			<tbody>
				<tr>
					<td ng-if="results.filters.name">{{results.filters.name}} ${ui.message('general.search')}</td>
					<td ng-if="results.filters.statuses">${ui.message('msfcore.filter')}</td>
					<td>${ui.message('msfcore.dateFilter')}</td>
				</tr>
				<tr>
					<td ng-if="results.filters.name" class="no-wrap">
						{{results.filters.name}} <input id="filter-name"/><input type="button" ng-click="nameFilter()" value="${ui.message('general.search')}"/>
					</td>
					<td ng-if="results.filters.statuses" class="no-wrap">
						<select ng-model="filterStatusValue" id="filter-status" ng-change="statusFilter()">
							<option value="all">${ui.message('msfcore.statusAll')}</option>
							<option ng-repeat="status in results.filters.statuses" value="{{status}}">{{status.charAt(0).toUpperCase() + status.substr(1).toLowerCase();}}</option>
						</select>
					</td>
					<td ng-if="results.filters.dates" class="no-wrap">
						<div>
							<input onfocus="this.type='date'" id="filter-start-date" placeholder="${ui.message('msfcore.startDate')}" ng-model="filterStartDate" ng-change="datesFilter()" />
							<input onfocus="this.type='date'" id="filter-end-date" placeholder="${ui.message('msfcore.endDate')}" ng-model="filterEndDate" ng-change="datesFilter()" />
							<select id="filter-dates" ng-model="filterDateValue" ng-change="datesFilter()"">
								<option ng-repeat="date in results.filters.dates" value="{{date}}">{{date}}</option>
							</select>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</br>
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
		    			<label ng-if="editableAndPending" ng-class="{'column-status':editableAndPending, 'editable':result[key].editable}" id="{{result.uuid.value}}_{{results.keys.indexOf(key)}}_{{result[key].type}}_{{result.concept.value}}">{{renderResultValue(result, key, editableAndPending)}}</label>
		    			<label ng-if="!editableAndPending" id="{{result.uuid.value}}_{{results.keys.indexOf(key)}}_{{result[key].type}}_{{result.concept.value}}"  ng-class="{'editable':result[key].editable}" time="{{result[key].type == 'DATE' ? result[key].value : ''}}">{{renderResultValue(result, key, editableAndPending)}}</label>
		    		</td>
			    	<td ng-if="result.actions.value.length > 0" class="print-ignore">
			    		<span ng-if="result.actions.value.includes('EDIT') > 0"><i class="icon-edit" ng-click="edit(\$event, result);"></i></span>
			    		<span ng-if="result.actions.value.includes('DELETE') > 0"><i class="icon-trash" ng-click="purge(result);"></i></span>
			    	</td>
			    	<td ng-if="result.actions.value.length == 0" class="print-ignore"></td>
		    </tbody>
		</table>
	</div>
	<div class="print-ignore wrap-center pagination">
		<div class="left">
			<span><input type="button" onclick="history.back();" value="${ ui.message('general.back')}"/></span>
			<span>${ui.message('msfcore.show')}</span>
			<span>
				<select ng-model="resultsPerPage" ng-change="pagination()">
					<option value="25">25</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="all" ng-show="results.pagination.totalResultNumber > 100">${ui.message('msfcore.all')}</option>
				</select>
			</span>
			<span>${ui.message('msfcore.entries')}</span>
		</div>
		<div class="right">
			<span class='page' ng-repeat="page in pages" ng-class="{'current-page':page.page==currentPage}" ng-click="paginate(page)"> {{page.page}} </span>
			<span ng-class="{'page':nextPage, 'disabled': !nextPage}" ng-click="paginate(nextPage)">${ui.message('general.next')}</span>
			<span ng-class="{'page':previousPage, 'disabled': !previousPage}" ng-click="paginate(previousPage)">${ui.message('general.previous')}</span>
		</div>
		<div class="center">
			<span class="disabled">
				${ui.message('msfcore.showing')} {{results.pagination.fromResultNumber}} ${ui.message('general.to')} {{results.pagination.toResultNumber}} ${ui.message('general.of')} {{results.pagination.totalResultNumber}} ${ui.message('msfcore.entries').toLowerCase()}
			</span>	
		</div>
	</div>
</div>