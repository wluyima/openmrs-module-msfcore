package org.openmrs.module.msfcore.tasks;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.scheduler.tasks.AbstractTask;

/**
 * This task runs at-least everyday so as to re-build the MSF id prefix
 */
public class MSFIdentifierMantenanceScheduler extends AbstractTask {

    @Override
    public void execute() {
        Context.getService(MSFCoreService.class).msfIdentifierGeneratorInstallation();
    }
}