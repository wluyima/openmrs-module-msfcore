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

    // TODO: This was added to be able to test lab results in the patient summary. Will be replaced
    // by story MOO-290 Lab Orders and results List
    public static EncounterTypeDescriptor MSF_LAB_RESULTS_ENCOUNTER_TYPE = new EncounterTypeDescriptor() {
        @Override
        public String name() {
            return "Lab results";
        }

        @Override
        public String description() {
            return "Lab results";
        }

        public String uuid() {
            return MSFCoreConfig.MSF_LAB_RESULTS_ENCOUNTER_TYPE_UUID;
        }
    };
}
