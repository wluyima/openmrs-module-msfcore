package org.openmrs.module.msfcore.metadata;

import java.util.Arrays;
import java.util.List;

import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationTagDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class MissionLocations {
    public static LocationDescriptor LEBANON = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_LEBANON_UUID;
        }

        @Override
        public String name() {
            return "Lebanon";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.MISSION);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(MissionLocationAttributes.LEBANON_CODE, MissionLocationAttributes.LEBANON_UID);
        }
    };

    public static LocationDescriptor UKRAINE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_UKRAINE_UUID;
        }

        @Override
        public String name() {
            return "Ukraine";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.MISSION);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(MissionLocationAttributes.UKRAINE_CODE, MissionLocationAttributes.UKRAINE_UID);
        }
    };
}
