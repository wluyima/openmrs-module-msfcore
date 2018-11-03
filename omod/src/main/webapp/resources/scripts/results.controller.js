var app = angular.module("resultsApp", []);
var url;

app.controller("ResultsController", ResultsController);
ResultsController.$inject = ['$scope'];

function ResultsController($scope) {
  this.retrieveResults = this.retrieveResults || function() {
    if(!url) {
      var urlParams = new URLSearchParams(window.location.search);
      patientId = urlParams.get('patientId');
      category = urlParams.get('category');	
      url = '/' + OPENMRS_CONTEXT_PATH +
        '/ws/rest/v1/msfcore/resultData?patientId=' + patientId +
        "&category=" + category;
  	}
    jQuery.get(url, function(data) {
      $scope.results = data.results[0];
      $scope.$apply();
    });
  }
  
  // page is page number/next/previous
  this.paginate = this.paginate || function(page) {
	  // TODO add fromResultNumber & toResultNumber to url
	  //$scope.retrieveResults();
  }

  this.resultPendingWhenEditable = this.resultPendingWhenEditable ||
    function(result, key) {
      return result[key].editable &&
        result.status.value != 'COMPLETED';
    }

  this.edit = this.edit || function(result, key) {
    // locate result and replace labels with text fields on editable columns
    console.log("");
  }

  this.purge = this.purge || function(result) {
    // TODO use ${ui.message('msfcore.resultList.confirmCancel')}
    if (confirm("Are you sure you want to Cancel this Record?")) {
    	jQuery.ajax({
    	  url: '/' + OPENMRS_CONTEXT_PATH + '/ws/rest/v1/order/' + result.uuid.value + '?!purge',
    	  type: 'DELETE',
    	  success: function(result) {
    		  $scope.retrieveResults();
    	  }
    	});
    }
  }

  $scope.retrieveResults = this.retrieveResults;
  $scope.resultPendingWhenEditable = this.resultPendingWhenEditable;
  $scope.edit = this.edit;
  $scope.purge = this.purge;
}