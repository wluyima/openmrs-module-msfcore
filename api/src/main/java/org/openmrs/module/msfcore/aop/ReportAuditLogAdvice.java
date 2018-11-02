package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.module.reporting.report.ReportRequest;
import org.springframework.aop.AfterReturningAdvice;

public class ReportAuditLogAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        try {
            if (method.getName().toLowerCase().contains("savereportrequest") && returnValue != null) {
                ReportRequest reportRequest = (ReportRequest) returnValue;

                StringBuilder sb = new StringBuilder(reportRequest.getUuid());
                if (reportRequest.getReportDefinition() != null && reportRequest.getReportDefinition().getParameterizable() != null) {
                    sb.append(" for " + reportRequest.getReportDefinition().getParameterizable().getName());
                    sb.append(" with " + reportRequest.getReportDefinition().getParameterizable());
                }
                if (reportRequest.getRenderingMode() != null && reportRequest.getRenderingMode().getRenderer() != null) {
                    sb.append(" to " + reportRequest.getRenderingMode().getRenderer().getClass().getSimpleName());
                }

                AuditLog reportLog = AuditLog.builder().event(Event.RUN_REPORT).detail(
                                Context.getMessageSourceService().getMessage("msfcore.runReport") + sb.toString()).user(
                                reportRequest.getRequestedBy()).build();

                Context.getService(AuditService.class).saveAuditLog(reportLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
