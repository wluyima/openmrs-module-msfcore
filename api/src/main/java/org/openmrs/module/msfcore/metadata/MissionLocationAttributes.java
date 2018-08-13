package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeTypeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class MissionLocationAttributes {
  public static LocationAttributeDescriptor LEBANON = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.LEBANON_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return MissionLocations.LEBANON;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "LBN";
    }
  };

  public static LocationAttributeDescriptor UKRAINE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.UKRAINE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return MissionLocations.UKRAINE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "UKR";
    }
  };
}
