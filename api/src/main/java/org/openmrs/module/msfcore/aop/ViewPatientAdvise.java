package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;

import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.audit.MSFCoreLog;
import org.openmrs.module.msfcore.audit.MSFCoreLog.Event;
import org.springframework.aop.AfterReturningAdvice;

public class ViewPatientAdvise implements AfterReturningAdvice {

  @Override
  public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
    /*
     * depends on
     * org.openmrs.module.emrapi.event.ApplicationEventServiceImpl#patientViewed
     * retaining its structure
     */
    if (method.getName().equals("patientViewed") && args.length == 2 && args[0].getClass().equals(Patient.class)
        && args[1].getClass().equals(User.class)) {
      MSFCoreLog viewPatientLog = new MSFCoreLog(Event.VIEW_PATIENT, "loaded/viewed patient dashboard", (User) args[1]);
      viewPatientLog.setPatient((Patient) args[0]);
      Context.getService(MSFCoreService.class).saveMSFCoreLog(viewPatientLog);
    }
  }

}
