package org.openmrs.module.msfcore.formaction;

import org.openmrs.EncounterType;
import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.formaction.handler.FormAction;
import org.springframework.stereotype.Component;

@Component
public class NCDProgramFormAction implements FormAction {

    @Override
    public void apply(String operation, FormEntrySession session) {
        EncounterType encounterType = session.getEncounter().getEncounterType();
        if (encounterType != null && encounterType.getUuid()
                .matches(MSFCoreConfig.ENCOUNTER_TYPE_UUID_BASELINE + "|"
                        + MSFCoreConfig.ENCOUNTER_TYPE_UUID_FOLLOWUP + "|"
                        + MSFCoreConfig.ENCOUNTER_TYPE_UUID_EXIT)) {
            Context.getService(MSFCoreService.class).manageNCDProgram(session.getEncounter());
        }
    }
}
