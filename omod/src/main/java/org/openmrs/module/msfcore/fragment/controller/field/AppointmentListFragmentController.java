package org.openmrs.module.msfcore.fragment.controller.field;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.appointmentscheduling.AppointmentType;
import org.openmrs.module.appointmentscheduling.api.AppointmentService;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.ui.framework.page.PageModel;

public class AppointmentListFragmentController {
	
	public void controller(PageModel model) {
		List<AppointmentType> appointments = Context.getService(AppointmentService.class).getAllAppointmentTypes(false);
		List<DropDownFieldOption> options = new ArrayList<>();
		for (AppointmentType appointment : appointments) {
			DropDownFieldOption option = new DropDownFieldOption(appointment.getUuid(), appointment.getName());
			options.add(option);
		}
		
		model.addAttribute("appointments", options);
	}
}
