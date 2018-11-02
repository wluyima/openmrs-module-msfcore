package org.openmrs.module.msfcore.formaction;

import java.util.Arrays;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.formaction.handler.FormAction;
import org.springframework.stereotype.Component;

@Component
public class NCDProgramFormAction implements FormAction {

    private static final List<String> VALID_ENCOUNTER_TYPES = Arrays.asList(MSFCoreConfig.ENCOUNTER_TYPE_NCD_BASELINE_UUID,
                    MSFCoreConfig.ENCOUNTER_TYPE_NCD_EXIT_UUID);

    @Override
    public void apply(String operation, FormEntrySession session) {
        if (VALID_ENCOUNTER_TYPES.contains(session.getEncounter().getEncounterType().getUuid())) {
            Context.getService(MSFCoreService.class).manageNCDProgram(session.getEncounter());
        }
    }
}
