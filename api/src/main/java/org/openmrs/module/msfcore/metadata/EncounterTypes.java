package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.EncounterTypeDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class EncounterTypes {
    public static EncounterTypeDescriptor MSF_NCD_BASELINE_ENCOUNTER_TYPE = new EncounterTypeDescriptor() {
        @Override
        public String name() {
            return "NCD Baseline Encounter";
        }

        @Override
        public String description() {
            return "Baseline NCD Assessment";
        }

        public String uuid() {
            return MSFCoreConfig.MSF_NCD_BASELINE_ENCOUNTER_TYPE_UUID;
        }
    };

    public static EncounterTypeDescriptor MSF_NCD_FOLLOWUP_ENCOUNTER_TYPE = new EncounterTypeDescriptor() {
        @Override
        public String name() {
            return "NCD Follow-up Encounter";
        }

        @Override
        public String description() {
            return "NCD Followup Encounter";
        }

        public String uuid() {
            return MSFCoreConfig.MSF_NCD_FOLLOWUP_ENCOUNTER_TYPE_UUID;
        }
    };
}
