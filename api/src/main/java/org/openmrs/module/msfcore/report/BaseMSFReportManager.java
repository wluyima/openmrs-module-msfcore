package org.openmrs.module.msfcore.report;

import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.library.BuiltInCohortDefinitionLibrary;
import org.openmrs.module.reporting.report.manager.BaseReportManager;
import org.openmrs.module.reporting.report.manager.ReportManagerUtil;

public abstract class BaseMSFReportManager extends BaseReportManager {
    BuiltInCohortDefinitionLibrary getBuiltInCohortDefinitions() {
        return Context.getRegisteredComponents(BuiltInCohortDefinitionLibrary.class).get(0);
    }

    public void initialise() {
        ReportManagerUtil.setupReport(this);
    }
}
