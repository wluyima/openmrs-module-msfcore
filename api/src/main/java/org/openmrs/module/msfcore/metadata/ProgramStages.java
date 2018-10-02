package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.ProgramWorkflowDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class ProgramStages {
    public static ProgramWorkflowDescriptor ENROLL = new ProgramWorkflowDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.NCD_PROGRAM_WORKFLOW_UUID_ENROLL;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.WORKFLOW_CONCEPT_UUID_ENROLL;
        }

        @Override
        public String name() {
            return "Enroll";
        }

    };

    public static ProgramWorkflowDescriptor BASELINE_CONSULTATION = new ProgramWorkflowDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.NCD_PROGRAM_WORKFLOW_UUID_BASELINE_CONSULTATION;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.WORKFLOW_CONCEPT_UUID_BASELINE_CONSULTATION;
        }

        @Override
        public String name() {
            return "Baseline consultation";
        }
    };

    public static ProgramWorkflowDescriptor INVESTIGATION_RESULTS = new ProgramWorkflowDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.NCD_PROGRAM_WORKFLOW_UUID_INVESTIGATION_RESULTS;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.WORKFLOW_CONCEPT_UUID_INVESTIGATION_RESULTS;
        }

        @Override
        public String name() {
            return "Investigation results";
        }
    };

    public static ProgramWorkflowDescriptor FOLLOWUP_CONSULTATION = new ProgramWorkflowDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.NCD_PROGRAM_WORKFLOW_UUID_FOLLOWUP_CONSULTATION;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.WORKFLOW_CONCEPT_UUID_FOLLOWUP_CONSULTATION;
        }

        @Override
        public String name() {
            return "Follow-up Consultation";
        }
    };

    public static ProgramWorkflowDescriptor EXIT = new ProgramWorkflowDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.NCD_PROGRAM_WORKFLOW_UUID_EXIT;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.WORKFLOW_CONCEPT_UUID_EXIT;
        }

        @Override
        public String name() {
            return "Exit";
        }
    };

    public static ProgramWorkflowDescriptor ACTIVE_COHORT = new ProgramWorkflowDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.NCD_PROGRAM_WORKFLOW_UUID_ACTIVE_COHORT;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.WORKFLOW_CONCEPT_UUID_ACTIVE_COHORT;
        }

        @Override
        public String name() {
            return "Active Cohort";
        }
    };
}
