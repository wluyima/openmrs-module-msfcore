package org.openmrs.module.msfcore.listener;

import java.util.Date;

import org.openmrs.User;
import org.openmrs.UserSessionListener;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.springframework.stereotype.Component;

@Component
public class LogInOrOutEventListener implements UserSessionListener {

  @Override
  public void loggedInOrOut(User user, Event event, Status status) {
    if (event != null && user != null) {
      AuditLog logInOrOut = AuditLog.builder()
          .detail(Context.getMessageSourceService().getMessage("msfcore.logInOrOut", new Object[]{user.getUsername(), status}, null))
          .date(new Date()).build();
      if (UserSessionListener.Event.LOGIN.equals(event)) {
        logInOrOut.setEvent(AuditLog.Event.LOGIN);
        if (status.equals(Status.SUCCESS)) {
          logInOrOut.setUser(user);
        }
      } else if (UserSessionListener.Event.LOGOUT.equals(event)) {
        logInOrOut.setEvent(AuditLog.Event.LOGOUT);
        logInOrOut.setUser(user);
      }
      Context.getService(AuditService.class).saveAuditLog(logInOrOut);
    }
  }

}
