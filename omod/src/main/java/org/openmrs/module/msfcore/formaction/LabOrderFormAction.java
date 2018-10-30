package org.openmrs.module.msfcore.formaction;

import java.util.Arrays;

import org.openmrs.Visit;
import org.openmrs.api.EncounterService;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.formaction.handler.FormAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LabOrderFormAction implements FormAction {

    @Autowired
    private EncounterService encounterService;

    @Autowired
    private MSFCoreService msfCoreService;

    /**
     * Generate test orders when request investication section of baseline form
     * is submited
     */
    @Override
	public void apply(String operation, FormEntrySession session) {

		boolean isRequestInvestigationForm = Arrays
				.asList(MSFCoreConfig.HTMLFORM_REQUEST_INVESTIGATION_UUID,
						MSFCoreConfig.FORM_NCD_FOLLOWUP_REQUEST_INVESTIGATION_UUID)
				.contains(session.getForm().getUuid());

		if (isRequestInvestigationForm) {

			Visit visit = session.getEncounter().getVisit();
			encounterService.getEncountersByVisit(visit, false).stream()
					.filter(e -> e.getForm().getUuid().equals(session.getForm().getUuid())).findFirst()
					.ifPresent(e -> msfCoreService.saveTestOrders(e));
		}
	}
}
