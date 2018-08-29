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
      return MSFCoreConfig.PATIENT_ID_TYPE_PRIMARY_UUID;
    }

    @Override
    public boolean required() {
      return true;
    }
  };

  public static PatientIdentifierTypeDescriptor MSF_DRIVER_LICENSE_TYPE = new PatientIdentifierTypeDescriptor() {
    @Override
    public String name() {
      return "Driver's License Identifier type";
    }

    @Override
    public String description() {
      return "Driver's License id type";
    }

    @Override
    public String uuid() {
      return MSFCoreConfig.PATIENT_ID_TYPE_DRIVER_LICENSE_UUID;
    }
  };

  public static PatientIdentifierTypeDescriptor MSF_NATIONAL_ID_TYPE = new PatientIdentifierTypeDescriptor() {
    @Override
    public String name() {
      return "National ID type";
    }

    @Override
    public String description() {
      return "National ID type";
    }

    @Override
    public String uuid() {
      return MSFCoreConfig.PATIENT_ID_TYPE_NATIONAL_ID_UUID;
    }
  };

  public static PatientIdentifierTypeDescriptor MSF_INSURANCE_CARD_TYPE = new PatientIdentifierTypeDescriptor() {
    @Override
    public String name() {
      return "Insurance Card type";
    }

    @Override
    public String description() {
      return "Insurance Card type";
    }

    @Override
    public String uuid() {
      return MSFCoreConfig.PATIENT_ID_TYPE_INSURANCE_CARD_UUID;
    }
  };

  public static PatientIdentifierTypeDescriptor MSF_PASSPORT_TYPE = new PatientIdentifierTypeDescriptor() {
    @Override
    public String name() {
      return "Passport ID type";
    }

    @Override
    public String description() {
      return "Passport ID type";
    }

    @Override
    public String uuid() {
      return MSFCoreConfig.PATIENT_ID_TYPE_PASSPORT_UUID;
    }
  };

  public static PatientIdentifierTypeDescriptor MSF_UNHCR_ID_TYPE = new PatientIdentifierTypeDescriptor() {
    @Override
    public String name() {
      return "UNHCR ID type";
    }

    @Override
    public String description() {
      return "UNHCR ID type";
    }

    @Override
    public String uuid() {
      return MSFCoreConfig.PATIENT_ID_TYPE_UNHCR_ID_UUID;
    }
  };

  public static PatientIdentifierTypeDescriptor MSF_UNRWA_ID_TYPE = new PatientIdentifierTypeDescriptor() {
    @Override
    public String name() {
      return "UNRWA ID type";
    }

    @Override
    public String description() {
      return "UNRWA ID type";
    }

    @Override
    public String uuid() {
      return MSFCoreConfig.PATIENT_ID_TYPE_UNRWA_ID_UUID;
    }
  };

  public static PatientIdentifierTypeDescriptor MSF_OTHER_ID = new PatientIdentifierTypeDescriptor() {
    @Override
    public String name() {
      return "Other ID";
    }

    @Override
    public String description() {
      return "Other ID";
    }

    @Override
    public String uuid() {
      return MSFCoreConfig.PATIENT_ID_TYPE_OTHER_ID_UUID;
    }
  };

  public static PatientIdentifierTypeDescriptor OLD_PATIENT_ID =
      new PatientIdentifierTypeDescriptor() {

        @Override
        public String name() {
          return "Old Patient ID";
        }

        @Override
        public String description() {
          return "Old Patient ID";
        }

        public String uuid() {
          return MSFCoreConfig.PATIENT_ID_TYPE_OLD_PATIENT_ID_UUID;
        }
      };
}
