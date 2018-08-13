package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.PatientIdentifierTypeDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.id.LuhnMSFIdentifierValidator;
import org.openmrs.patient.IdentifierValidator;

public class PatientIdentifierTypes {
  public static PatientIdentifierTypeDescriptor MSF_PRIMARY_ID_TYPE = new PatientIdentifierTypeDescriptor() {
    @Override
    public String name() {
      return "MSF Primary Identifier type";
    }

    @Override
    public String description() {
      return "Default MSF id type";
    }

    @Override
    public Class<? extends IdentifierValidator> validator() {
      return LuhnMSFIdentifierValidator.class;
    }

    @Override
    public String uuid() {
      return MSFCoreConfig.PATIENT_ID_TYPE_MSF_UUID;
    }

    @Override
    public boolean required() {
      return true;
    }
  };
}
