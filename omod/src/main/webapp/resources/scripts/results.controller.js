var app = angular.module("resultsApp", []);
var url;
var patientId;
var category;

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
                    setNextAndPreviousPages($scope, $scope.pages[0]);
                }
                $scope.currentPage = 1;
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
                        $scope.retrieveResults();
                        toggleEditingIcon($event);
                    }
                });
            } else {
                // TODO use ${ui.message('')}
                alert("Enter details before saving!");
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
                    $scope.retrieveResults();
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

    $scope.retrieveResults = this.retrieveResults;
    $scope.resultPendingWhenEditable = this.resultPendingWhenEditable;
    $scope.edit = this.edit;
    $scope.purge = this.purge;
    $scope.paginate = this.paginate;
    $scope.pagination = this.pagination;
    $scope.renderResultValue = this.renderResultValue;
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
    return !object || object == null || object == undefined || object == "" || object.length == 0;
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
        return jQuery("<input class='editable' original_class='" + originalClass + "' original_value='" + originalValue + "' id='" + id + "' value='" + value + "' type='" + type + "' time='" + time + "' />");
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
    jQuery("input.editable").each(function(key, value) {
        var id = value.getAttribute('id');
        var value = jQuery("#" + id).val();
        var type = id.split("_")[2];
        if (type == "DATE" && !isEmpty(value)) {
            value = convertToOpenMRSDateFormat($scope, new Date(value));
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