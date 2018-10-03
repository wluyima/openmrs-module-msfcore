package org.openmrs.module.msfcore.metadata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.module.metadatadeploy.descriptor.ProgramDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.ProgramWorkflowDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class Programs {
    public static ProgramDescriptor NCD = new ProgramDescriptor() {

        @Override
        public String uuid() {
            return MSFCoreConfig.NCD_PROGRAM_UUID;
        }

        @Override
        public String name() {
            return "NCD";
        }

        @Override
        public String description() {
            return "Non Communicable diseases (NCD) program for MSF";
        }

        @Override
        public String conceptUuid() {
            return MSFCoreConfig.NCD_PROGRAM_CONCEPT_UUID;
        }

        @Override
        public String outcomesConceptUuid() {
            return MSFCoreConfig.NCD_PROGRAM_OUTCOMES_CONCEPT_UUID;
        }

        @Override
        public Set<ProgramWorkflowDescriptor> workflows() {
            return new HashSet<ProgramWorkflowDescriptor>(Arrays.asList(ProgramStages.STAGES));
        }
    };
}
