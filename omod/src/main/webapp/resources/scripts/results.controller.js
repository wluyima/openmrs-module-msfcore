var app = angular.module("resultsApp",
    ['app.restfulServices', 'app.models', 'app.commonFunctionsFactory']);

app.controller("ResultsController", ResultsController);
ResultsController.$inject = ['$scope', 'RestfulService', 'CommonFunctions'];

function ResultsController($scope, RestfulService, CommonFunctions) {
    var self = this;
    RestfulService.setBaseUrl('/' + OPENMRS_CONTEXT_PATH + '/ws/rest/v1/msfcore');
    
    self.retrieveResults = self.retrieveResults || function (patientUuid, category) {
        if (patientUuid == null || patientUuid == undefined) {
	   		patientUuid = CommonFunctions.extractUrlArgs(window.location.search)['patientId'];
	    }
        if (category == null || category == undefined) {
        	category = CommonFunctions.extractUrlArgs(window.location.search)['category'];
	    }
	    if (patientUuid !== null && patientUuid !== undefined) {
	    	RestfulService.get('resultData', {"patientUuid": patientUuid, "category" : category},
	    		function (data) {
	    			$scope.results = data;
	            }, function (error) {}
	        );
	    }
    }
    
    self.resultPending(result, key) {
    	return result[key]['editable'] && result['status'] != 'COMPLETED';
    }
    
    $scope.retrieveResults = self.retrieveResults;
    $scope.resultPending = self.resultPending;
}