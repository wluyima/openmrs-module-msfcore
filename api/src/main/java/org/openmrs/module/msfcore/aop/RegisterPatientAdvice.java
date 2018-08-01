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
      AuditLog viewPatientLog = new AuditLog(Event.REGISTER_PATIENT,
          "registered patient#" + patient.getPatientIdentifier().getIdentifier(), Context.getAuthenticatedUser());
      viewPatientLog.setPatient(patient);
      viewPatientLog.setUser(Context.getAuthenticatedUser());
      Context.getService(AuditService.class).saveAuditLog(viewPatientLog);
    }
  }

}
