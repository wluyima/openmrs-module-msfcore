package org.openmrs.module.msfcore.metadata;

import java.util.Arrays;
import java.util.List;

import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationTagDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class ClinicLocations {
    public static LocationDescriptor AARSAL = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_AARSAL_UUID;
        }

        @Override
        public String name() {
            return "Aarsal";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.AARSAL_CODE, ClinicLocationAttributes.AARSAL_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.BEKAA_VALLEY;
        }

    };

    public static LocationDescriptor BAALBAK = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_BAALBAK_UUID;
        }

        @Override
        public String name() {
            return "Baalbak";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.BAALBAK_CODE, ClinicLocationAttributes.BAALBAK_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.BEKAA_VALLEY;
        }
    };

    public static LocationDescriptor HERMEL = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_HERMEL_UUID;
        }

        @Override
        public String name() {
            return "Hermel";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.HERMEL_CODE, ClinicLocationAttributes.HERMEL_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.BEKAA_VALLEY;
        }
    };

    public static LocationDescriptor MAJDAL_ANJAR = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_MAJDAL_ANJAR_UUID;
        }

        @Override
        public String name() {
            return "Majdal Anjar";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.MAJDAL_ANJAR_CODE, ClinicLocationAttributes.MAJDAL_ANJAR_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.BEKAA_VALLEY;
        }
    };

    public static LocationDescriptor ABDEH = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_ABDEH_UUID;
        }

        @Override
        public String name() {
            return "Abdeh";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.ABDEH_CODE, ClinicLocationAttributes.ABDEH_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.TRIPOLI;
        }
    };

    public static LocationDescriptor DAZ = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_DAZ_UUID;
        }

        @Override
        public String name() {
            return "Daz";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.DAZ_CODE, ClinicLocationAttributes.DAZ_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.TRIPOLI;
        }
    };

    public static LocationDescriptor BEREZOVE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_BEREZOVE_UUID;
        }

        @Override
        public String name() {
            return "Berezove";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.BEREZOVE_CODE, ClinicLocationAttributes.BEREZOVE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor KAMIANKA_2 = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_KAMIANKA_2_UUID;
        }

        @Override
        public String name() {
            return "Kamianka 2";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.KAMIANKA_2_CODE, ClinicLocationAttributes.KAMIANKA_2_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor KURAKHOVE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_KURAKHOVE_UUID;
        }

        @Override
        public String name() {
            return "Kurakhove";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.KURAKHOVE_CODE, ClinicLocationAttributes.KURAKHOVE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor MAXIMILIANOVKA = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_MAXIMILIANOVKA_UUID;
        }

        @Override
        public String name() {
            return "Maximilianovka";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.MAXIMILIANOVKA_CODE, ClinicLocationAttributes.MAXIMILIANOVKA_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor NETAILOVE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_NETAILOVE_UUID;
        }

        @Override
        public String name() {
            return "Netailove";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.NETAILOVE_CODE, ClinicLocationAttributes.NETAILOVE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor NOVOSELIVKA_DRUHA = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_NOVOSELIVKA_DRUHA_UUID;
        }

        @Override
        public String name() {
            return "Novoselivka Druha";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.NOVOSELIVKA_DRUHA_CODE, ClinicLocationAttributes.NOVOSELIVKA_DRUHA_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor OPYTNE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_OPYTNE_UUID;
        }

        @Override
        public String name() {
            return "Opytne";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.OPYTNE_CODE, ClinicLocationAttributes.OPYTNE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor TARAMCHUK = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_TARAMCHUK_UUID;
        }

        @Override
        public String name() {
            return "Taramchuk";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.TARAMCHUK_CODE, ClinicLocationAttributes.TARAMCHUK_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor VODYANE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_VODYANE_UUID;
        }

        @Override
        public String name() {
            return "Vodyane";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.VODYANE_CODE, ClinicLocationAttributes.VODYANE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.WEST_DONETSK;
        }
    };

    public static LocationDescriptor BERDIANSKOE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_BERDIANSKOE_UUID;
        }

        @Override
        public String name() {
            return "Berdianskoe";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.BERDIANSKOE_CODE, ClinicLocationAttributes.BERDIANSKOE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor GRANITNE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_GRANITNE_UUID;
        }

        @Override
        public String name() {
            return "Granitne";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.GRANITNE_CODE, ClinicLocationAttributes.GRANITNE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor HOSPITAL_N_1_MARIUPOL = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_HOSPITAL_N_1_MARIUPOL_UUID;
        }

        @Override
        public String name() {
            return "Hospital n.1 Mariupol";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.HOSPITAL_N_1_MARIUPOL_CODE, ClinicLocationAttributes.HOSPITAL_N_1_MARIUPOL_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor HOSPITAL_N_3_MARIUPOL = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_HOSPITAL_N_3_MARIUPOL_UUID;
        }

        @Override
        public String name() {
            return "Hospital n.3 Mariupol";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.HOSPITAL_N_3_MARIUPOL_CODE, ClinicLocationAttributes.HOSPITAL_N_3_MARIUPOL_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor KAMIANKA_1 = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_KAMIANKA_1_UUID;
        }

        @Override
        public String name() {
            return "Kamianka 1";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.KAMIANKA_1_CODE, ClinicLocationAttributes.KAMIANKA_1_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor LEBEDINSKOE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_LEBEDINSKOE_UUID;
        }

        @Override
        public String name() {
            return "Lebedinskoe";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.LEBEDINSKOE_CODE, ClinicLocationAttributes.LEBEDINSKOE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor MIKOLAIVKA = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_MIKOLAIVKA_UUID;
        }

        @Override
        public String name() {
            return "Mikolaivka";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.MIKOLAIVKA_CODE, ClinicLocationAttributes.MIKOLAIVKA_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor NOVOSELIVKA = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_NOVOSELIVKA_UUID;
        }

        @Override
        public String name() {
            return "Novoselivka";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.NOVOSELIVKA_CODE, ClinicLocationAttributes.NOVOSELIVKA_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor ORLOVSKE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_ORLOVSKE_UUID;
        }

        @Override
        public String name() {
            return "Orlovske";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.ORLOVSKE_CODE, ClinicLocationAttributes.ORLOVSKE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor PASHKOVSKOGO = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_PASHKOVSKOGO_UUID;
        }

        @Override
        public String name() {
            return "Pashkovskogo";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.PASHKOVSKOGO_CODE, ClinicLocationAttributes.PASHKOVSKOGO_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor PAVLOPIL = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_PAVLOPIL_UUID;
        }

        @Override
        public String name() {
            return "Pavlopil";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.PAVLOPIL_CODE, ClinicLocationAttributes.PAVLOPIL_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor PIONERSKE = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_PIONERSKE_UUID;
        }

        @Override
        public String name() {
            return "Pionerske";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.PIONERSKE_CODE, ClinicLocationAttributes.PIONERSKE_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor PROHOROVKA = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_PROHOROVKA_UUID;
        }

        @Override
        public String name() {
            return "Prohorovka";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.PROHOROVKA_CODE, ClinicLocationAttributes.PROHOROVKA_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor STAROGNATIVKA = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_STAROGNATIVKA_UUID;
        }

        @Override
        public String name() {
            return "Starognativka";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.STAROGNATIVKA_CODE, ClinicLocationAttributes.STAROGNATIVKA_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

    public static LocationDescriptor STEPANIVKA = new LocationDescriptor() {
        @Override
        public String uuid() {
            return MSFCoreConfig.LOCATION_STEPANIVKA_UUID;
        }

        @Override
        public String name() {
            return "Stepanivka";
        }

        @Override
        public String description() {
            return "";
        }

        @Override
        public List<LocationTagDescriptor> tags() {
            return Arrays.asList(LocationTags.CLINIC);
        }

        @Override
        public List<LocationAttributeDescriptor> attributes() {
            return Arrays.asList(ClinicLocationAttributes.STEPANIVKA_CODE, ClinicLocationAttributes.STEPANIVKA_UID);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

}
