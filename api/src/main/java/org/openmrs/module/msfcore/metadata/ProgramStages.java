package org.openmrs.module.msfcore.metadata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.module.metadatadeploy.descriptor.ProgramWorkflowDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.ProgramWorkflowStateDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class ProgramStages {
    public static ProgramWorkflowDescriptor STAGES = new ProgramWorkflowDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.PROGRAM_WORKFLOW_UUID_STAGE;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.CONCEPT_UUID_PROGRAM_WORKFLOW_STAGE;
        }

        @Override
        public String name() {
            return "Stage";
        }

        @Override
        public Set<ProgramWorkflowStateDescriptor> states() {
            return new HashSet<ProgramWorkflowStateDescriptor>(Arrays.asList(ENROLL, BASELINE_CONSULTATION, FOLLOWUP_CONSULTATION, EXIT));
        }

    };

    private static ProgramWorkflowStateDescriptor ENROLL = new ProgramWorkflowStateDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.WORKFLOW_STATE_UUID_ENROLL;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.CONCEPT_UUID_PROGRAM_WORKFLOW_STATE_ENROLL;
        }

        @Override
        public String name() {
            return "Enroll";
        }

        @Override
        public Boolean initial() {
            return true;
        }

        @Override
        public Boolean terminal() {
            return false;
        }

    };

    private static ProgramWorkflowStateDescriptor BASELINE_CONSULTATION = new ProgramWorkflowStateDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.WORKFLOW_STATE_UUID_BASELINE_CONSULTATION;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.CONCEPT_UUID_PROGRAM_WORKFLOW_STATE_BASELINE_CONSULTATION;
        }

        @Override
        public String name() {
            return "Baseline consultation";
        }

        @Override
        public Boolean initial() {
            return false;
        }

        @Override
        public Boolean terminal() {
            return false;
        }
    };

    private static ProgramWorkflowStateDescriptor FOLLOWUP_CONSULTATION = new ProgramWorkflowStateDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.WORKFLOW_STATE_UUID_FOLLOWUP_CONSULTATION;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.CONCEPT_UUID_PROGRAM_WORKFLOW_STATE_FOLLOWUP_CONSULTATION;
        }

        @Override
        public String name() {
            return "Follow-up Consultation";
        }

        @Override
        public Boolean initial() {
            return false;
        }

        @Override
        public Boolean terminal() {
            return false;
        }
    };

    private static ProgramWorkflowStateDescriptor EXIT = new ProgramWorkflowStateDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT;
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.CONCEPT_UUID_PROGRAM_WORKFLOW_STATE_EXIT;
        }

        @Override
        public String name() {
            return "Exit";
        }

        @Override
        public Boolean initial() {
            return false;
        }

        @Override
        public Boolean terminal() {
            return true;
        }
    };
}
