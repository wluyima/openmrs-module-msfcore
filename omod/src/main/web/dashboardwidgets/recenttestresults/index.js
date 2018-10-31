import angular from 'angular';
import openmrsApi from '@openmrs/angularjs-openmrs-api';
import commons from './../dashboardwidgets.services';

import { RecentTestResultsComponent } from './recenttestresults.component';

export default angular.module("openmrs-contrib-dashboardwidgets.recenttestresults", [ openmrsApi, commons ])
	.component(RecentTestResultsComponent.selector, RecentTestResultsComponent).name;