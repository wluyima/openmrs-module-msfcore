import angular from 'angular';

import RecentTestResults from './recenttestresults';

export default angular.module("msfcore-openmrs-contrib-dashboardwidgets", [ RecentTestResults]).name;

angular.element(document).ready(function() {
    for (var dashboardwidget of document.getElementsByClassName('msfcore-openmrs-contrib-dashboardwidgets')) {
        angular.bootstrap(dashboardwidget, [ 'msfcore-openmrs-contrib-dashboardwidgets' ]);
    }
});