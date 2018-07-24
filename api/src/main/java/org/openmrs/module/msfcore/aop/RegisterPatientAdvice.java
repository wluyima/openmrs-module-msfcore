package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.audit.MSFCoreLog;
import org.openmrs.module.msfcore.audit.MSFCoreLog.Event;
import org.springframework.aop.AfterReturningAdvice;

public class RegisterPatientAdvice implements AfterReturningAdvice {

  @Override
  public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
    if (method.getName().equals("registerPatient") && args[0] != null && returnValue != null) {
      MSFCoreLog viewPatientLog = new MSFCoreLog(Event.REGISTER_PATIENT, "registered patient", Context.getAuthenticatedUser());
      viewPatientLog.setPatient((Patient) returnValue);
      viewPatientLog.setUser(Context.getAuthenticatedUser());
      Context.getService(MSFCoreService.class).saveMSFCoreLog(viewPatientLog);
    }
  }

}
