var app = angular.module("resultsApp", []);
var url;
var patientId;
var category;
var initialPageLoad = false;

app.controller("ResultsController", ResultsController);
ResultsController.$inject = ['$scope'];

function ResultsController($scope) {
    $scope.resultsPerPage = "25";
    this.retrieveResults = this.retrieveResults || function(initialisePages, callback) {
        // TODO probably wrap in some loading...
        if (isEmpty(url)) {
            var urlParams = new URLSearchParams(window.location.search);
            patientId = urlParams.get('patientId');
            category = urlParams.get('category');
            url = '/' + OPENMRS_CONTEXT_PATH +
                '/ws/rest/v1/msfcore/resultData?patientId=' + patientId +
                "&category=" + category;
            initialPageLoad = true;
        } else {
            initialPageLoad = false;
        }
        jQuery.get(url, function(data) {
            //initialise results list
            var results = data.results[0];
            if (initialPageLoad) {
                logViewResultsEvent(results);
            }
            if (callback) {
                if (callback.name.indexOf("filterBy") >= 0) {
                    callback(results, $scope);
                }
            } else {
                clearFilterFields($scope, results);
            }
            var pagination = results.pagination;
            if (isEmpty(pagination.toItemNumber) || pagination.totalItemsNumber <= pagination.toItemNumber) {
                pagination.toItemNumber = pagination.totalItemsNumber;
            }
            if (pagination.totalItemsNumber == 0) {
                pagination.fromItemNumber = 0;
            }
            if (initialisePages) {
                // render pagination on first page load or resultsPerPage change
                $scope.pages = [];
                // one page
                if (pagination.toItemNumber == pagination.totalItemsNumber) {
                    $scope.pages[1] = getPageObject(1, url);
                } else { // more than one pages
                    $scope.pages = getPossiblePages(url, parseInteger($scope.resultsPerPage), pagination.totalItemsNumber);
                    setNextAndPreviousPages($scope, $scope.pages[0]);
                }
                $scope.currentPage = 1;
            }
            //display results
            $scope.results = results;
            $scope.$apply();
        });
    }

    // page is page number/next/previous
    this.paginate = this.paginate || function(page) {
        if (page) {
            url = page.url;
            $scope.currentPage = page.page;
            $scope.retrieveResults(false);
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

    this.resultPendingWhenEditable = this.resultPendingWhenEditable || function(result, key) {
        return result[key].editable && result.status.value != 'COMPLETED';
    }

    this.edit = this.edit || function($event, result) {
        // locate result and replace labels with text fields on editable columns
        if (jQuery($event.currentTarget).hasClass("icon-edit")) {
            triggerEditing($scope, jQuery("input.editable"), result, true);
            triggerEditing($scope, jQuery("label.editable"), result);
            toggleEditingIcon($event);
        } else {
            var payload = generateResultRowData($scope);
            if (!isEmpty(payload)) {
                var data = {
                    "payload": payload,
                    "category": category
                };
                jQuery.ajax({
                    url: "/" + OPENMRS_CONTEXT_PATH + "/ws/rest/v1/msfcore/resultRow",
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function(obs) {
                        console.log("Updated/Added Result at Obs: " + obs.uuid);
                        $scope.retrieveResults(false);
                    }
                });
            } else {
                // TODO use ${ui.message('')}
                alert("Enter correct details before saving!");
            }
        }
    }

    this.purge = this.purge || function(result) {
        // TODO use ${ui.message('msfcore.resultList.confirmCancel')}
        if (confirm("Are you sure you want to Cancel this Record?")) {
            jQuery.ajax({
                url: '/' + OPENMRS_CONTEXT_PATH + '/ws/rest/v1/order/' + result.uuid.value + '?!purge',
                type: 'DELETE',
                success: function(result) {
                    $scope.retrieveResults(false);
                }
            });
        }
    }

    this.renderResultValue = this.renderResultValue || function(result, key, editableAndPending) {
        if (editableAndPending) {
            return result.status.value;
        } else {
            var value = result[key].value;
            if (result[key].type == 'DATE' && !isEmpty(value)) {
                value = convertToOpenMRSDateFormat($scope, new Date(value));
            }
            return value;
        }
    }

    this.nameFilter = this.nameFilter || function() {
        if (!isEmpty(jQuery("#filter-name").val())) {
            replacePaginationInURLToRetrieveAll($scope);
            $scope.retrieveResults(true, filterByName);
        }
    }

    this.statusFilter = this.statusFilter || function() {
        replacePaginationInURLToRetrieveAll($scope);
        $scope.retrieveResults(true, filterByStatus);
    }

    this.datesFilter = this.datesFilter || function() {
        if (isValidDate(jQuery("#filter-start-date").val()) && isValidDate(jQuery("#filter-end-date").val())) {
            replacePaginationInURLToRetrieveAll($scope);
            $scope.retrieveResults(true, filterByDates);
        }
    }

    $scope.retrieveResults = this.retrieveResults;
    $scope.resultPendingWhenEditable = this.resultPendingWhenEditable;
    $scope.edit = this.edit;
    $scope.purge = this.purge;
    $scope.paginate = this.paginate;
    $scope.pagination = this.pagination;
    $scope.renderResultValue = this.renderResultValue;
    $scope.nameFilter = this.nameFilter;
    $scope.statusFilter = this.statusFilter;
    $scope.datesFilter = this.datesFilter;
}

function removePaginationFromURL(urlString) {
    if (urlString.indexOf("&fromItemNumber=") > 0) {
        urlString = urlString.substring(0, urlString.indexOf("&fromItemNumber="));
    }
    return urlString;
}

/**
 * Replace pagination params from in if they exist
 */
function replacePaginationInURL(urlString, from, to) {
    urlString = removePaginationFromURL(urlString);
    return urlString + "&fromItemNumber=" + from + "&toItemNumber=" + to;
}

function replacePaginationInURLToRetrieveAll($scope) {
    //TODO searching is matching against all results
    $scope.resultsPerPage = "all";
    url = replacePaginationInURL(url, "1", $scope.resultsPerPage);
}

/**
 * Check if an object is not existing or empty/blank
 */
function isEmpty(object) {
    return !object || object == null || object == undefined || object == "" || object.length == 0;
}

function parseInteger(string) {
    if (string == 'all') {
        return Number.MAX_SAFE_INTEGER;
    } else {
        return parseInt(string);
    }
}

function getPossiblePages(urlString, resultsPerPage, totalItemsNumber) {
    var pages = [];
    var paginationAttempts = Math.ceil(totalItemsNumber / resultsPerPage);
    // i is the pagecount to display
    var from = 1;
    for (i = 1; i <= paginationAttempts; i++) {
        var to;
        if (i == 1) {
            to = resultsPerPage;
        } else {
            if (from >= totalItemsNumber) {
                to = totalItemsNumber;
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

function replaceElementWithTextInput($scope, element) {
    element.replaceWith(function() {
        var id = jQuery(this).attr('id');
        var time = jQuery(this).attr('time');
        var type = id.split("_")[2];
        var originalValue = jQuery(this).text();
        var originalClass = jQuery(this).attr('class');
        var value = originalValue.replace("PENDING", "").replace("CANCELLED", "");
        if (type == "DATE" && !isEmpty(time)) {
            value = convertToDatePickerDateFormat(new Date(parseInteger(time)));
        }
        var inputElement = "<input class='editable' original_class='" + originalClass + "' original_value='" + originalValue + "' id='" + id + "' value='" + value + "' type='" + type + "' time='" + time + "' />";
        if (type == "BOOLEAN") {
        	inputElement = "<select class='editable' original_class='" + originalClass + "' original_value='" + originalValue + "' id='" + id + "' value='" + value + "' time='" + time + "'>" +
            	"<option value='true'>True</option><option value='false'>False</option>" +
            "</select>";
        }
        return jQuery(inputElement);
    });
}

function convertToOpenMRSDateFormat($scope, date) {
    return jQuery.datepicker.formatDate($scope.results.dateFormatPattern.toLowerCase().replace("yyyy", "yy"), date);
}

function convertToDatePickerDateFormat(date) {
    return jQuery.datepicker.formatDate("yy-mm-dd", date);
}

function replaceElementWithLabel(element) {
    element.replaceWith(function() {
        var id = jQuery(this).attr('id');
        var time = jQuery(this).attr('time');
        var value = jQuery(this).attr('original_value');
        var claz = jQuery(this).attr('original_class');
        return jQuery("<label class='" + claz + "' id='" + id + "' time='" + time + "'>" + value + "<label/>");
    });
}

function triggerEditing($scope, element, result, edit) {
    element.each(function(key, value) {
        var elementId = value.getAttribute('id');
        if (edit) {
            // re-render all edits back to labels
            replaceElementWithLabel(jQuery("#" + elementId));
            jQuery(".icon-ok").addClass("icon-edit").removeClass("icon-ok");
        } else {
            if (elementId.startsWith(result.uuid.value)) {
                replaceElementWithTextInput($scope, jQuery("#" + elementId));
            }
        }
    });
}

function toggleEditingIcon($event) {
    jQuery($event.currentTarget).toggleClass("icon-edit");
    jQuery($event.currentTarget).toggleClass("icon-ok");
}

function generateResultRowData($scope) {
    var data = [];
    jQuery("input.editable,select.editable").each(function(key, value) {
        var id = value.getAttribute('id');
        var value = jQuery("#" + id).val();
        var type = id.split("_")[2];
        if (type == "DATE") {
            if (isValidDate(value)) {
                value = convertToOpenMRSDateFormat($scope, new Date(value));
            } else {
                value = "";
            }
        }
        // TODO support validation and on fail return empty array
        if (!isEmpty(value)) {
            data.push({
                "uuid_key_type_concept": id,
                "value": value
            });
        }
    });
    return data;
}

function applyFilterChanges(results) {
    //remove any pending empty results because of excluding some
    results.results = results.results.filter(function() {
        return true;
    });
    // update totalNumber of results
    results.pagination.totalItemsNumber = results.results.length;
}

function filterByName(results, $scope) {
    const name = jQuery("#filter-name").val();
    jQuery.each(results.results, function(i, resultRow) {
        if (resultRow[results.filters.name].value.toLowerCase().indexOf(name.toLowerCase()) == -1) {
            results.results = removeItemAtIndex(results.results, i);
        }
    });
    applyFilterChanges(results);
    clearFilterFields($scope, results);
    jQuery("#filter-name").val(name);
}

function filterByStatus(results, $scope) {
    const status = jQuery("#filter-status").val();
    if (status != "all") {
        jQuery.each(results.results, function(i, resultRow) {
            if (resultRow.status.value != status) {
                results.results = removeItemAtIndex(results.results, i);
            }
        });
    }
    applyFilterChanges(results);
    clearFilterFields($scope, results);
    jQuery("#filter-status").val(status);
}

function removeItemAtIndex(items, index) {
    delete items[index];
    return items;
}

function clearFilterFields($scope, results) {
    jQuery("#filter-name").val("");
    jQuery("#filter-status").val("all");
    jQuery("#filter-dates").val(results.filters.dates[0]);
    jQuery("#filter-start-date").val("");
    jQuery("#filter-end-date").val("");
    $scope.filterStartDate = "";
    $scope.filterEndDate = "";
    $scope.filterStatusValue = "all";
    $scope.filterDateValue = results.filters.dates[0];
}

function filterByDates(results, $scope) {
    const startDate = jQuery("#filter-start-date").val();
    const endDate = jQuery("#filter-end-date").val();
    const dateField = jQuery("#filter-dates").val();
    jQuery.each(results.results, function(i, resultRow) {
        //the conversion removes time
        var dateString = convertToDatePickerDateFormat(new Date(parseInteger(resultRow[dateField].value)));
        var date = new Date(dateString);
        if (!isValidDate(dateString) || date.getTime() < new Date(startDate).getTime() || date.getTime() > new Date(endDate).getTime()) {
            results.results = removeItemAtIndex(results.results, i);
        }
    });
    applyFilterChanges(results);
    clearFilterFields($scope, results);
    jQuery("#filter-dates").val(dateField);
    jQuery("#filter-start-date").val(startDate)
    jQuery("#filter-end-date").val(endDate)
}

function isValidDate(dateString) {
    if (!isEmpty(dateString)) {
        return !isNaN(new Date(dateString).getTime());
    }
}

function logViewResultsEvent(results) {
    var event;
    if (results.resultCategory == "LAB_RESULTS") {
        event = "VIEW_LAB_RESULTS";
    }
    var data = {
        "event": event,
        "patient": results.patient
    };
    jQuery.ajax({
        url: "/" + OPENMRS_CONTEXT_PATH + "/ws/rest/v1/msfcore/auditlog",
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(event) {}
    });
}