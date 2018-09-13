package org.openmrs.module.msfcore.tasks;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.MSFCoreService;

/**
 * Posting to DHIS2 should be wrapped into a thread since it has delays and
 * failures that maynot have to chock other functionality
 */
public class PostTrackerInstanceForPatientRunnable implements Runnable {
    private Patient patient;

    public PostTrackerInstanceForPatientRunnable(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void run() {
        Context.getService(MSFCoreService.class).postTrackerInstanceThroughOpenHimForAPatient(this.patient);
    }

}
