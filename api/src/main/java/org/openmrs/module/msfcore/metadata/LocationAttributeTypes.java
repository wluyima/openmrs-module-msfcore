package org.openmrs.module.msfcore.metadata;

import org.openmrs.customdatatype.datatype.FreeTextDatatype;
import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeTypeDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class LocationAttributeTypes {
    public static LocationAttributeTypeDescriptor LOCATION_CODE = new LocationAttributeTypeDescriptor() {
        @Override
        public String name() {
            return "Location Code";
        }

        @Override
        public String description() {
            return "Location code (initials or acronym) e.g: AB = Abia Clinic";
        }

        public String uuid() {
            return MSFCoreConfig.LOCATION_ATTR_TYPE_CODE_UUID;
        }

        @Override
        public Class<?> datatype() {
            return FreeTextDatatype.class;
        }
    };

    public static LocationAttributeTypeDescriptor LOCATION_UID = new LocationAttributeTypeDescriptor() {
        @Override
        public String name() {
            return "Location uid";
        }

        @Override
        public String description() {
            return "Location dhis2 uid";
        }

        public String uuid() {
            return MSFCoreConfig.LOCATION_ATTR_TYPE_UID_UUID;
        }

        @Override
        public Class<?> datatype() {
            return FreeTextDatatype.class;
        }
    };
}
