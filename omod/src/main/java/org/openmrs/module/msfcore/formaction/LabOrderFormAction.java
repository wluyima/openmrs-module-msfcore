package org.openmrs.module.msfcore.formaction;

import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.formaction.handler.FormAction;
import org.springframework.stereotype.Component;

@Component
public class LabOrderFormAction implements FormAction {

    /**
     * Generate test orders when request investication section of baseline form is submited
     */
    @Override
	public void apply(String operation, FormEntrySession session) {
		if (session.getForm().getUuid().equals(MSFCoreConfig.HTMLFORM_REQUEST_INVESTIGATION_UUID)) {
			Visit visit = session.getEncounter().getVisit();
            Context.getEncounterService().getEncountersByVisit(visit, false).stream()
			        .filter(e -> e.getForm().getUuid().equals(MSFCoreConfig.HTMLFORM_REQUEST_INVESTIGATION_UUID)).findFirst()
                    .ifPresent(e -> Context.getService(MSFCoreService.class).saveTestOrders(e));
		}
	}
}
