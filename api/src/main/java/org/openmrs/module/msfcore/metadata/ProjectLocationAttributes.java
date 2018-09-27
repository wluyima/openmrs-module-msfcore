package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeTypeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class ProjectLocationAttributes {
    public static LocationAttributeDescriptor BEKAA_VALLEY_CODE = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_CODE_ATTRIBUTE_BEKAA_VALLEY_UUID;
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
            return "LB142";
        }
    };

    public static LocationAttributeDescriptor BEKAA_VALLEY_UID = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_UID_ATTRIBUTE_BEKAA_VALLEY_UUID;
        }

        @Override
        public LocationDescriptor location() {
            return ProjectLocations.BEKAA_VALLEY;
        }

        @Override
        public LocationAttributeTypeDescriptor type() {
            return LocationAttributeTypes.LOCATION_UID;
        }

        @Override
        public String value() {
            return "MsC3UVZeLB8";
        }
    };

    public static LocationAttributeDescriptor TRIPOLI_CODE = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_CODE_ATTRIBUTE_TRIPOLI_UUID;
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
            return "LB141";
        }
    };

    public static LocationAttributeDescriptor TRIPOLI_UID = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_UID_ATTRIBUTE_TRIPOLI_UUID;
        }

        @Override
        public LocationDescriptor location() {
            return ProjectLocations.TRIPOLI;
        }

        @Override
        public LocationAttributeTypeDescriptor type() {
            return LocationAttributeTypes.LOCATION_UID;
        }

        @Override
        public String value() {
            return "jJIq13cKMBb";
        }
    };

    public static LocationAttributeDescriptor WEST_DONETSK_CODE = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_CODE_ATTRIBUTE_WEST_DONETSK_UUID;
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
            return "UA120";
        }
    };

    public static LocationAttributeDescriptor WEST_DONETSK_UID = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_UID_ATTRIBUTE_WEST_DONETSK_UUID;
        }

        @Override
        public LocationDescriptor location() {
            return ProjectLocations.WEST_DONETSK;
        }

        @Override
        public LocationAttributeTypeDescriptor type() {
            return LocationAttributeTypes.LOCATION_UID;
        }

        @Override
        public String value() {
            return "EjRjBuZGiPI";
        }
    };

    public static LocationAttributeDescriptor MARIUPOL_CODE = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_CODE_ATTRIBUTE_MARIUPOL_UUID;
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
            return "UA1202";
        }
    };

    public static LocationAttributeDescriptor MARIUPOL_UID = new LocationAttributeDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_UID_ATTRIBUTE_MARIUPOL_UUID;
        }

        @Override
        public LocationDescriptor location() {
            return ProjectLocations.MARIUPOL;
        }

        @Override
        public LocationAttributeTypeDescriptor type() {
            return LocationAttributeTypes.LOCATION_UID;
        }

        @Override
        public String value() {
            return "dGqOiqOuChv";
        }
    };
}
