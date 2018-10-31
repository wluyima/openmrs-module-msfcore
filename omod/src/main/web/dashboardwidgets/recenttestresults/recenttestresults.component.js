import RecentTestResultsController from './recenttestresults.controller';
import template from './recenttestresults.html';

export let RecentTestResultsComponent = {
    template,
    controller: RecentTestResultsController,
    selector: 'recenttestresults',
    bindings: {
        config: '<'
    }
};