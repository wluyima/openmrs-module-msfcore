package org.openmrs.module.msfcore.submissionaction;

import org.openmrs.Visit;
import org.openmrs.api.EncounterService;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.submissionaction.handler.MsfSubmissionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsfLabOrderAction implements MsfSubmissionAction {

    @Autowired
    private EncounterService encounterService;

    @Autowired
    private MSFCoreService msfCoreService;

    /**
     * Generate test orders when request investication section of baseline form is submited
     */
    @Override
	public void apply(String operation, FormEntrySession session) {
		if (session.getForm().getUuid().equals(MSFCoreConfig.HTMLFORM_REQUEST_INVESTIGATION_UUID)) {
			Visit visit = session.getEncounter().getVisit();
			encounterService.getEncountersByVisit(visit, false).stream()
			        .filter(e -> e.getForm().getUuid().equals(MSFCoreConfig.HTMLFORM_REQUEST_INVESTIGATION_UUID)).findFirst()
			        .ifPresent(e -> msfCoreService.saveTestOrders(e));
		}
	}
}
