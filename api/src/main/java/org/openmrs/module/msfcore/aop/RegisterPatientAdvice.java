package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.springframework.aop.AfterReturningAdvice;

public class RegisterPatientAdvice implements AfterReturningAdvice {

  @Override
  public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
    if (method.getName().equals("registerPatient") && args[0] != null && returnValue != null) {
      Patient patient = (Patient) returnValue;
      AuditLog viewPatientLog = AuditLog.builder().event(Event.REGISTER_PATIENT)
          .detail(Context.getMessageSourceService().getMessage("msfcore.registerPatient")
              + patient.getPersonName().getFullName() + " - "
              + patient.getPatientIdentifier().getIdentifier())
          .user(Context.getAuthenticatedUser()).patient(patient).user(Context.getAuthenticatedUser()).build();
      Context.getService(AuditService.class).saveAuditLog(viewPatientLog);
    }
  }

}
