package org.openmrs.module.msfcore.page.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

public class AuditLogManagerPageController {

    public void controller(PageModel model, @SpringBean("auditService") AuditService auditService,
                    @RequestParam(value = "startTime", required = false) String startTime,
                    @RequestParam(value = "endTime", required = false) String endTime, HttpServletRequest request,
                    @RequestParam(value = "patientId", required = false) Patient patient,
                    @RequestParam(value = "viewer", required = false) String selectedViewer) {
        Date startDate = null;
        Date endDate = null;
        String[] selectedEvents = request.getParameterValues("events");
        List<Event> logEvents = new ArrayList<Event>();
        List<Patient> patients = null;
        List<User> users = null;

        if (patient != null) {
            patients = Arrays.asList(patient);
        }
        if (StringUtils.isNotBlank(selectedViewer)) {
            users = new ArrayList<User>();
            User viewer = Context.getUserService().getUserByUsername(selectedViewer.trim());
            if (viewer == null) {
                selectedViewer = "";
            }
            users.add(viewer);
        }
        if (selectedEvents != null) {
            for (String event : selectedEvents) {
                logEvents.add(Event.valueOf(event));
            }
        } else {
            logEvents = null;
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
        List<AuditLog> auditLogs = auditService.getAuditLogs(startDate, endDate, logEvents, patients, users, null, null, null);
        model.addAttribute("auditLogs", auditLogs);
        model.addAttribute("startTime", startTime.replaceAll(",", ""));
        model.addAttribute("endTime", endTime.replaceAll(",", ""));
        model.addAttribute("events", Arrays.asList(Event.values()));
        model.addAttribute("selectedEvents", selectedEvents);
        model.addAttribute("userSuggestions", userSuggestions());
        model.addAttribute("selectedViewer", selectedViewer.trim());
        String patientDisplay = "";
        String patientId = "";
        if (patient != null) {
            patientId = patient.getUuid();
            patientDisplay = patient.getPersonName().getFullName() + " #" + patient.getPatientIdentifier().getIdentifier();
        }
        model.addAttribute("patientId", patientId);
        model.addAttribute("patientDisplay", patientDisplay);
    }

    private List<String> userSuggestions() {
        List<String> suggestions = new ArrayList<String>();

        for (User u : Context.getUserService().getAllUsers()) {
            if (StringUtils.isNotBlank(u.getUsername())) {
                suggestions.add(u.getUsername());
            }
            if (StringUtils.isNotBlank(u.getSystemId())) {
                suggestions.add(u.getSystemId());
            }
        }
        return new ArrayList<String>(new HashSet<String>(suggestions));
    }
}
