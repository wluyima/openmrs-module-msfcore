var app = angular.module("resultsApp", []);
var url;

app.controller("ResultsController", ResultsController);
ResultsController.$inject = ['$scope'];

function ResultsController($scope) {
    $scope.resultsPerPage = "25";
    this.retrieveResults = this.retrieveResults || function(renderPagination) {
        // TODO probably wrap in some loading...
        if (isEmpty(url)) {
            var urlParams = new URLSearchParams(window.location.search);
            patientId = urlParams.get('patientId');
            category = urlParams.get('category');
            url = '/' + OPENMRS_CONTEXT_PATH +
                '/ws/rest/v1/msfcore/resultData?patientId=' + patientId +
                "&category=" + category;
        }
        jQuery.get(url, function(data) {
            // render pagination on first page load or resultsPerPage change
            var pagination = data.results[0].pagination;
            if (isEmpty(pagination.toResultNumber) || pagination.totalResultNumber <= pagination.toResultNumber) {
                pagination.toResultNumber = pagination.totalResultNumber;
            }
            if (renderPagination) {
                $scope.pages = [];
                // one page
                if (pagination.toResultNumber == pagination.totalResultNumber) {
                    $scope.pages[1] = getPageObject(1, url);
                } else { // more than one pages
                    $scope.pages = getPossiblePages(url, parseInteger($scope.resultsPerPage), pagination.totalResultNumber);
                    $scope.currentPage = 1;
                    setNextAndPreviousPages($scope, $scope.pages[0]);
                }
            }
            //initialise results list
            $scope.results = data.results[0];
            $scope.$apply();
        });
    }

    // page is page number/next/previous
    this.paginate = this.paginate || function(page) {
        if (page) {
            url = page.url;
            $scope.currentPage = page.page;
            $scope.retrieveResults();
            setNextAndPreviousPages($scope, page);
        }
    }

    //change results per page
    this.pagination = this.pagination || function() {
        //remove and re-add pagination to url
        url = replacePaginationInURL(url, "1", $scope.resultsPerPage);
        //retrive new results by new url
        $scope.retrieveResults(true);
    }

    this.resultPendingWhenEditable = this.resultPendingWhenEditable ||
        function(result, key) {
            return result[key].editable &&
                result.status.value != 'COMPLETED';
        }

    this.edit = this.edit || function(result) {
        // locate result and replace labels with text fields on editable columns
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
    $scope.paginate = this.paginate;
    $scope.pagination = this.pagination;
}

/**
 * Replace pagination params from in if they exist
 */
function replacePaginationInURL(urlString, from, to) {
    if (urlString.indexOf("&fromResultNumber=") > 0) {
        urlString = urlString.substring(0, urlString.indexOf("&fromResultNumber="));
    }
    return urlString + "&fromResultNumber=" + from + "&toResultNumber=" + to;
}

/**
 * Check if an object is not existing or empty/blank
 */
function isEmpty(object) {
    return !object || object == null || object == undefined;
}

function parseInteger(string) {
    if (string == 'all') {
        return Number.MAX_SAFE_INTEGER;
    } else {
        return parseInt(string);
    }
}

function getPossiblePages(urlString, resultsPerPage, totalResultNumber) {
    var pages = [];
    var paginationAttempts = Math.ceil(totalResultNumber / resultsPerPage);
    // i is the pagecount to display
    var from = 1;
    for (i = 1; i <= paginationAttempts; i++) {
        var to;
        if (i == 1) {
            to = resultsPerPage;
        } else {
            if (from >= totalResultNumber) {
                to = totalResultNumber;
            } else {
                to = (from - 1) + resultsPerPage;
            }
        }
        urlString = replacePaginationInURL(urlString, from, to);
        pages[i] = getPageObject(i, urlString);
        from = to + 1;
    }
    //return compact pages
    return pages.filter(function() {
        return true;
    });
}

function getPageObject(page, pageUrl) {
	//page is non 0 index whereas $scope.pages is
    return {
        "page": page,
        "url": pageUrl
    };
}

function setNextAndPreviousPages($scope, currentPage) {
    $scope.nextPage = $scope.pages[currentPage.page];
    $scope.previousPage = $scope.pages[currentPage.page - 2];
}