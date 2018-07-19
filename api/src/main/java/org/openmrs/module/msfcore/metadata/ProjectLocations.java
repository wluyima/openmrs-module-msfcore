package org.openmrs.module.msfcore.metadata;

import java.util.Arrays;
import java.util.List;

import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationTagDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class ProjectLocations {
  public static LocationDescriptor BEKAA_VALLEY = new LocationDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.LOCATION_BEKAA_VALLEY_UUID;
    }

    @Override
    public String name() {
      return "BEKAA VALLEY";
    }

    @Override
    public String description() {
      return "";
    }

    @Override
    public List<LocationTagDescriptor> tags() {
      return Arrays.asList(LocationTags.MISSION, LocationTags.PROJECT);
    }

    @Override
    public List<LocationAttributeDescriptor> attributes() {
      return Arrays.asList(ProjectLocationAttributes.BEKAA_VALLEY);
    }

    @Override
    public LocationDescriptor parent() {
      return MissionLocations.LEBANON;
    }
  };

  public static LocationDescriptor TRIPOLI = new LocationDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.LOCATION_TRIPOLI_UUID;
    }

    @Override
    public String name() {
      return "TRIPOLI";
    }

    @Override
    public String description() {
      return "";
    }

    @Override
    public List<LocationTagDescriptor> tags() {
      return Arrays.asList(LocationTags.MISSION, LocationTags.PROJECT);
    }

    @Override
    public List<LocationAttributeDescriptor> attributes() {
      return Arrays.asList(ProjectLocationAttributes.TRIPOLI);
    }

    @Override
    public LocationDescriptor parent() {
      return MissionLocations.LEBANON;
    }
  };

  public static LocationDescriptor WEST_DONETSK = new LocationDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.LOCATION_WEST_DONETSK_UUID;
    }

    @Override
    public String name() {
      return "WEST DONETSK";
    }

    @Override
    public String description() {
      return "";
    }

    @Override
    public List<LocationTagDescriptor> tags() {
      return Arrays.asList(LocationTags.MISSION, LocationTags.PROJECT);
    }

    @Override
    public List<LocationAttributeDescriptor> attributes() {
      return Arrays.asList(ProjectLocationAttributes.WEST_DONETSK);
    }

    @Override
    public LocationDescriptor parent() {
      return MissionLocations.UKRAINE;
    }
  };

  public static LocationDescriptor MARIUPOL = new LocationDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.LOCATION_MARIUPOL_UUID;
    }

    @Override
    public String name() {
      return "Mariupol";
    }

    @Override
    public String description() {
      return "";
    }

    @Override
    public List<LocationTagDescriptor> tags() {
      return Arrays.asList(LocationTags.MISSION, LocationTags.PROJECT);
    }

    @Override
    public List<LocationAttributeDescriptor> attributes() {
      return Arrays.asList(ProjectLocationAttributes.MARIUPOL);
    }

    @Override
    public LocationDescriptor parent() {
      return MissionLocations.UKRAINE;
    }
  };
}
