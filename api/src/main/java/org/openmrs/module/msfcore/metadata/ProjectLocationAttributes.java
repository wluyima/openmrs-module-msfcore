package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeTypeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class ProjectLocationAttributes {
  public static LocationAttributeDescriptor BEKAA_VALLEY = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.BEKAA_VALLEY_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ProjectLocations.BEKAA_VALLEY;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "BV";
    }
  };

  public static LocationAttributeDescriptor TRIPOLI = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.TRIPOLI_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ProjectLocations.TRIPOLI;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "TRI";
    }
  };

  public static LocationAttributeDescriptor WEST_DONETSK = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.WEST_DONETSK_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ProjectLocations.WEST_DONETSK;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "WD";
    }
  };

  public static LocationAttributeDescriptor MARIUPOL = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.MARIUPOL_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ProjectLocations.MARIUPOL;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "MAR";
    }
  };
}
