package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeTypeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class MissionLocationAttributes {
    public static LocationAttributeDescriptor LEBANON_CODE = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_CODE_ATTRIBUTE_LEBANON_UUID;
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
            return "LB";
        }
    };

    public static LocationAttributeDescriptor LEBANON_UID = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_UID_ATTRIBUTE_LEBANON_UUID;
        }

        @Override
        public LocationDescriptor location() {
            return MissionLocations.LEBANON;
        }

        @Override
        public LocationAttributeTypeDescriptor type() {
            return LocationAttributeTypes.LOCATION_UID;
        }

        @Override
        public String value() {
            return "nNl9HH1r6LU";
        }
    };

    public static LocationAttributeDescriptor UKRAINE_CODE = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_CODE_ATTRIBUTE_UKRAINE_UUID;
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
            return "UA";
        }
    };

    public static LocationAttributeDescriptor UKRAINE_UID = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_UID_ATTRIBUTE_UKRAINE_UUID;
        }

        @Override
        public LocationDescriptor location() {
            return MissionLocations.UKRAINE;
        }

        @Override
        public LocationAttributeTypeDescriptor type() {
            return LocationAttributeTypes.LOCATION_UID;
        }

        @Override
        public String value() {
            return "Mh2t5WT9vo7";
        }
    };
}
