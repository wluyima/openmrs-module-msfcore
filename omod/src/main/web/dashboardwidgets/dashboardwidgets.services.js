import angular from 'angular';
import WidgetsCommons from './dashboardwidgetscommons.service';

let services = angular.module('msfcore.dashboardwidgets.services', []);

services.service('widgetsCommons', WidgetsCommons);
 
export default services.name;