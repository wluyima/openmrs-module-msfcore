package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.Report;
import org.openmrs.module.reporting.report.ReportRequest;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.aop.AfterReturningAdvice;

public class ReportAuditLogAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        try {
            if (method.getName().toLowerCase().matches("runreport") && returnValue != null) {
                ReportRequest reportRequest = ((Report) returnValue).getRequest();
                Event event = Event.RUN_REPORT;
                String initialMessage = Context.getMessageSourceService().getMessage("msfcore.runReport");

                StringBuilder sb = new StringBuilder();

                Mapped<ReportDefinition> reportDefinition = reportRequest.getReportDefinition();
                if (reportDefinition != null) {
                    sb.append(initialMessage + reportDefinition.getUuidOfMappedOpenmrsObject());
                    sb.append(" with Report request: " + reportRequest.getUuid());

                    if (reportDefinition.getParameterizable() != null) {
                        sb.append(" for " + reportDefinition.getParameterizable().getName());
                        sb.append(" with " + reportDefinition.getParameterizable());
                    }

                    if (reportRequest.getRenderingMode() != null && reportRequest.getRenderingMode().getRenderer() != null) {
                        sb.append(" output to " + reportRequest.getRenderingMode().getRenderer().getClass().getSimpleName());
                    }

                    sb.append(" with status: " + reportRequest.getStatus());

                    AuditLog reportLog = AuditLog.builder().event(event).detail(sb.toString()).user(reportRequest.getRequestedBy()).build();

                    Context.getService(AuditService.class).saveAuditLog(reportLog);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
