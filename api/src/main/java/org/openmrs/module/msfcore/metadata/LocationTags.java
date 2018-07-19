package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.LocationTagDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class LocationTags {
  public static LocationTagDescriptor CLINIC = new LocationTagDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.LOCATION_TAG_UUID_CLINIC;
    }

    @Override
    public String name() {
      return "Center (Clinic)";
    }

    @Override
    public String description() {
      return "";
    }
  };

  public static LocationTagDescriptor PROJECT = new LocationTagDescriptor() {

    @Override
    public String uuid() {
      return MSFCoreConfig.LOCATION_TAG_UUID_PROJECT;
    }

    @Override
    public String name() {
      return "Project";
    }

    @Override
    public String description() {
      return "";
    }
  };

  public static LocationTagDescriptor MISSION = new LocationTagDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.LOCATION_TAG_UUID_MISSION;
    }

    @Override
    public String name() {
      return "Mission";
    }

    @Override
    public String description() {
      return "";
    }
  };

}
