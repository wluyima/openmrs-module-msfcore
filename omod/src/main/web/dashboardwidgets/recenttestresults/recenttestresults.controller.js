export default class RecentTestResultsController {
    constructor($filter, openmrsRest, widgetsCommons) {
        'ngInject';

        Object.assign(this, { $filter, openmrsRest, widgetsCommons });
    }

    $onInit() {
        this.maxConceptCount = 10;
        this.maxAgeInDays = undefined;
        this.obs = [];

        this.openmrsRest.setBaseAppPath("/coreapps");

        this.maxAgeInDays = this.widgetsCommons.maxAgeToDays(this.config.maxAge);

        // Remove whitespaces
        this.config.concepts = this.config.concepts.replace(/\s/g,'');

        //let concepts = this.config.concepts.split(",");
        this.openmrsRest.list('latestobs', {
            patient: this.config.patientUuid,
            v: 'full',
            concept: this.config.concepts
        }).then((resp) => {
        	for (var i = 0; i < resp.results.length; i++) {
        		// Don't add more items if max concept count is reached
        		if (this.obs.length < this.maxConceptCount && resp.results.length > 0) {
        			let obs = resp.results[i];
        			// Don't add obs older than maxAge
        			if (angular.isUndefined(this.maxAgeInDays) || this.widgetsCommons.dateToDaysAgo(obs.obsDatetime) <= this.maxAgeInDays) {
        				// Add last obs for concept to list
        				
        				if (['8d4a505e-c2cc-11de-8d13-0010c6dffd0f',
        					'8d4a591e-c2cc-11de-8d13-0010c6dffd0f',
        					'8d4a5af4-c2cc-11de-8d13-0010c6dffd0f'].indexOf(obs.concept.datatype.uuid) > -1) {
        					//If value is date, time or datetime
        					var date = this.$filter('date')(new Date(obs.value), this.config.dateFormat);
        					obs.value = date;
        				} else if (angular.isDefined(obs.value.display)) {
        					//If value is a concept
        					obs.value = obs.value.display;
        				}
        				
        				this.obs.push(obs);
        			}
        		}
			}
        });
    }
}
