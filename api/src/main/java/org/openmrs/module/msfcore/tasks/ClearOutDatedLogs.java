package org.openmrs.module.msfcore.tasks;

import java.util.Calendar;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.scheduler.tasks.AbstractTask;

public class ClearOutDatedLogs extends AbstractTask {

  @Override
  public void execute() {
    Context.getService(MSFCoreService.class)
        .deleteMSFCoreFromDate(Context.getService(MSFCoreService.class).getDateAtNDaysFromData(Calendar.getInstance().getTime(),
            Integer.parseInt(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_DAYS_TO_KEEP_LOGS))));
  }

}
