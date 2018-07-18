package org.openmrs.module.msfcore.metadata;

import org.openmrs.Concept;
import org.openmrs.module.metadatadeploy.descriptor.PersonAttributeTypeDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class PersonAttributeTypes {

  public static PersonAttributeTypeDescriptor NATIONALITY = new PersonAttributeTypeDescriptor() {
    @Override
    public double sortWeight() {
      return 0;
    }

    @Override
    public Class<?> format() {
      return Concept.class;
    }

    @Override
    public String name() {
      return "Nationality";
    }

    @Override
    public String description() {
      return "Nationality of the person";
    }

    public String uuid() {
      return MSFCoreConfig.NATIONALITY_PERSON_ATTRIBUTE_UUID;
    }

    @Override
    public boolean searchable() {
      return true;
    }
  };

  public static PersonAttributeTypeDescriptor OTHER_NATIONALITY = new PersonAttributeTypeDescriptor() {
    @Override
    public double sortWeight() {
      return 0;
    }

    @Override
    public Class<?> format() {
      return Concept.class;
    }

    @Override
    public String name() {
      return "Other Nationality";
    }

    @Override
    public String description() {
      return "Nationality - if other, specify: of a person";
    }

    public String uuid() {
      return MSFCoreConfig.OTHER_NATIONALITY_PERSON_ATTRIBUTE_UUID;
    }

    @Override
    public boolean searchable() {
      return true;
    }
  };

  public static PersonAttributeTypeDescriptor MARITAL_STATUS = new PersonAttributeTypeDescriptor() {
    @Override
    public double sortWeight() {
      return 0;
    }

    @Override
    public Class<?> format() {
      return Concept.class;
    }

    @Override
    public String name() {
      return "Marital Status";
    }

    @Override
    public String description() {
      return "Marital status of this person";
    }

    public String uuid() {
      return MSFCoreConfig.MARITAL_STATUS_PERSON_ATTRIBUTE_UUID;
    }

    @Override
    public boolean searchable() {
      return true;
    }
  };

  public static PersonAttributeTypeDescriptor EMPLOYMENT_STATUS = new PersonAttributeTypeDescriptor() {
    @Override
    public double sortWeight() {
      return 0;
    }

    @Override
    public Class<?> format() {
      return Concept.class;
    }

    @Override
    public String name() {
      return "Employment Status";
    }

    @Override
    public String description() {
      return "Employment status of this person";
    }

    public String uuid() {
      return MSFCoreConfig.EMPLOYMENT_STATUS_PERSON_ATTRIBUTE_UUID;
    }

    @Override
    public boolean searchable() {
      return true;
    }
  };
}
