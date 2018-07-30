package org.openmrs.module.msfcore.page.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.audit.MSFCoreLog;
import org.openmrs.module.msfcore.audit.MSFCoreLog.Event;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class AuditLogManagerPageController {

  public void controller(PageModel model, @SpringBean("msfCoreService") MSFCoreService msfCoreService,
      @RequestParam(value = "startTime", required = false) String startTime,
      @RequestParam(value = "endTime", required = false) String endTime, HttpServletRequest request) {
    Date startDate = null;
    Date endDate = null;
    String[] selectedEvents = request.getParameterValues("events");
    List<Event> logEvents = new ArrayList<Event>();
    if (selectedEvents != null) {
      for (String event : selectedEvents) {
        logEvents.add(Event.valueOf(event));
      }
    } else {
      logEvents = Arrays.asList(Event.values());
    }
    try {
      SimpleDateFormat dateISOFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      if (StringUtils.isNotBlank(startTime)) {
        startDate = dateISOFormatted.parse(startTime.replaceAll(",", ""));
      }
      if (StringUtils.isNotBlank(endTime)) {
        endDate = dateISOFormatted.parse(endTime.replaceAll(",", ""));
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    List<MSFCoreLog> msfCoreLogs = msfCoreService.getMSFCoreLogs(startDate, endDate, logEvents, null, null, null, null, null);
    model.addAttribute("msfCoreLogs", msfCoreLogs);
    model.addAttribute("startTime", startTime.replaceAll(",", ""));
    model.addAttribute("endTime", startTime.replaceAll(",", ""));
    model.addAttribute("events", Arrays.asList(Event.values()));
    model.addAttribute("selectedEvents", selectedEvents);
  }
}
