var app = angular.module("auditLogsApp", []);
var url;
var initialPageLoad = false;
var retrieveAuditLogs;

app.controller("AuditLogsController", AuditLogsController);
AuditLogsController.$inject = ['$scope', '$sce'];

function AuditLogsController($scope, $sce) {
    $scope.resultsPerPage = "25";
    this.retrieveAuditLogs = this.retrieveAuditLogs || function(initialisePages) {
        // TODO probably wrap in some loading...
        if (isEmpty(url)) {
            url = '/' + OPENMRS_CONTEXT_PATH + '/ws/rest/v1/msfcore/auditlog';
            initialPageLoad = true;
        } else {
            initialPageLoad = false;
        }
        jQuery.get(url, function(data) {
            // initialise results list
            var audits = data.results[0];
            if (initialPageLoad) {
                //TODO fire view auditLogManager event
            }
            //TODO fix dates
            jQuery("[id^=start-time-]").val(audits.startTime);
      	    jQuery("[id^=end-time-]").val(audits.endTime);
      	    jQuery.each(audits.events, function(i, e) {
      	    	jQuery("#events option[value=" + e + "]").prop("selected", true);
      	    });
      	    jQuery("#viewer").autocomplete({
      	    	source: audits.userSuggestions
      	    });
            
            renderPagination($scope, audits.pagination, initialisePages);

            // display audits
            $scope.audits = audits;
            $scope.$apply();
        });
    }
    // caching retrieveAuditLogs to make it accessible outside AuditLogsController
    retrieveAuditLogs = this.retrieveAuditLogs;

    this.retrieveAuditLogsInitialisePages = this.retrieveAuditLogsInitialisePages || function() {
        retrieveAuditLogs(true);
    }

    $scope.retrieveAuditLogs = retrieveAuditLogs;
    $scope.retrieveAuditLogsInitialisePages = this.retrieveAuditLogsInitialisePages;
    $scope.paginate = paginate;
    $scope.pagination = pagination;
    $scope.convertToDateFormat = convertToDateFormat;
}