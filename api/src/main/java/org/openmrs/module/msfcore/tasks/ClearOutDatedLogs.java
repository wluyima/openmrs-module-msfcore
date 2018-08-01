package org.openmrs.module.msfcore.tasks;

import java.util.Calendar;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.api.util.DateUtils;
import org.openmrs.scheduler.tasks.AbstractTask;

public class ClearOutDatedLogs extends AbstractTask {

  @Override
  public void execute() {
    Context.getService(AuditService.class).deleteAuditLogsFromDate(
        DateUtils.getDateAtNDaysFromDate(Calendar.getInstance().getTime(), Integer.parseInt(Context
            .getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_DAYS_TO_KEEP_LOGS))));
  }

}
