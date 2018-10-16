package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.EncounterTypeDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class EncounterTypes {
    public static EncounterTypeDescriptor MSF_NCD_BASELINE_ENCOUNTER_TYPE = new EncounterTypeDescriptor() {
        @Override
        public String name() {
            return "NCD Baseline";
        }

        @Override
        public String description() {
            return "Baseline NCD Assessment encounter type";
        }

        public String uuid() {
            return MSFCoreConfig.ENCOUNTER_TYPE_UUID_BASELINE;
        }
    };

    public static EncounterTypeDescriptor MSF_NCD_FOLLOWUP_ENCOUNTER_TYPE = new EncounterTypeDescriptor() {
        @Override
        public String name() {
            return "NCD Follow-up";
        }

        @Override
        public String description() {
            return "NCD Followup encounter type";
        }

        public String uuid() {
            return MSFCoreConfig.ENCOUNTER_TYPE_UUID_FOLLOWUP;
        }
    };

    public static EncounterTypeDescriptor MSF_NCD_EXIT_ENCOUNTER_TYPE = new EncounterTypeDescriptor() {
        @Override
        public String name() {
            return "NCD Exit";
        }

        @Override
        public String description() {
            return "Exit ncd encounter type";
        }

        public String uuid() {
            return MSFCoreConfig.ENCOUNTER_TYPE_UUID_EXIT;
        }
    };
}
