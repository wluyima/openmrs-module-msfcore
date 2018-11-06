package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.PrivilegeDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

/**
 * TODO Fix: & probably deploy all roles and privileges as pihcore does
 */
public class Privileges {
    public static PrivilegeDescriptor ENROLL_IN_PROGRAM = new PrivilegeDescriptor() {
        public String uuid() {
            return MSFCoreConfig.PRIVILEGE_ENROLL_IN_PROGRAM;
        }

        public String privilege() {
            return "Task: coreapps.enrollInProgram";
        }

        public String description() {
            return "Ability to enroll a patient in a program via the program status widget";
        }
    };
}
