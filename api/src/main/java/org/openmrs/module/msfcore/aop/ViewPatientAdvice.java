package org.openmrs.module.msfcore.aop;

import java.lang.reflect.Method;

import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.springframework.aop.AfterReturningAdvice;

public class ViewPatientAdvice implements AfterReturningAdvice {

  @Override
  public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
    /*
     * depends on
     * org.openmrs.module.emrapi.event.ApplicationEventServiceImpl#patientViewed
     * retaining its structure
     */
    if (method.getName().equals("patientViewed") && args.length == 2 && args[0].getClass().equals(Patient.class)
        && args[1].getClass().equals(User.class)) {
      Patient patient = (Patient) args[0];
      AuditLog viewPatientLog = new AuditLog(Event.VIEW_PATIENT,
          "loaded/viewed patient#" + patient.getPatientIdentifier().getIdentifier(), Context.getAuthenticatedUser());
      viewPatientLog.setPatient(patient);
      viewPatientLog.setUser((User) args[1]);
      Context.getService(AuditService.class).saveAuditLog(viewPatientLog);
    }
  }

}
