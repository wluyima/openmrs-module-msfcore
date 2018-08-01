package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.MSFCoreLog;
import org.openmrs.module.msfcore.audit.MSFCoreLog.Event;
import org.springframework.aop.AfterReturningAdvice;

public class RegisterPatientAdvice implements AfterReturningAdvice {

  @Override
  public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
    if (method.getName().equals("registerPatient") && args[0] != null && returnValue != null) {
      Patient patient = (Patient) returnValue;
      MSFCoreLog viewPatientLog = new MSFCoreLog(Event.REGISTER_PATIENT,
          "registered patient#" + patient.getPatientIdentifier().getIdentifier(), Context.getAuthenticatedUser());
      viewPatientLog.setPatient(patient);
      viewPatientLog.setUser(Context.getAuthenticatedUser());
      Context.getService(AuditService.class).saveMSFCoreLog(viewPatientLog);
    }
  }

}
