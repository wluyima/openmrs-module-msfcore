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
            return Arrays.asList(ClinicLocationAttributes.AARSAL);
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
            return Arrays.asList(ClinicLocationAttributes.BAALBAK);
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
            return Arrays.asList(ClinicLocationAttributes.HERMEL);
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
            return Arrays.asList(ClinicLocationAttributes.MAJDAL_ANJAR);
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
            return Arrays.asList(ClinicLocationAttributes.ABDEH);
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
            return Arrays.asList(ClinicLocationAttributes.DAZ);
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
            return Arrays.asList(ClinicLocationAttributes.BEREZOVE);
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
            return Arrays.asList(ClinicLocationAttributes.KAMIANKA_2);
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
            return Arrays.asList(ClinicLocationAttributes.KURAKHOVE);
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
            return Arrays.asList(ClinicLocationAttributes.MAXIMILIANOVKA);
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
            return Arrays.asList(ClinicLocationAttributes.NETAILOVE);
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
            return Arrays.asList(ClinicLocationAttributes.NOVOSELIVKA_DRUHA);
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
            return Arrays.asList(ClinicLocationAttributes.OPYTNE);
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
            return Arrays.asList(ClinicLocationAttributes.TARAMCHUK);
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
            return Arrays.asList(ClinicLocationAttributes.VODYANE);
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
            return Arrays.asList(ClinicLocationAttributes.BERDIANSKOE);
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
            return Arrays.asList(ClinicLocationAttributes.GRANITNE);
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
            return Arrays.asList(ClinicLocationAttributes.HOSPITAL_N_1_MARIUPOL);
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
            return Arrays.asList(ClinicLocationAttributes.HOSPITAL_N_3_MARIUPOL);
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
            return Arrays.asList(ClinicLocationAttributes.KAMIANKA_1);
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
            return Arrays.asList(ClinicLocationAttributes.LEBEDINSKOE);
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
            return Arrays.asList(ClinicLocationAttributes.MIKOLAIVKA);
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
            return Arrays.asList(ClinicLocationAttributes.NOVOSELIVKA);
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
            return Arrays.asList(ClinicLocationAttributes.ORLOVSKE);
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
            return Arrays.asList(ClinicLocationAttributes.PASHKOVSKOGO);
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
            return Arrays.asList(ClinicLocationAttributes.PAVLOPIL);
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
            return Arrays.asList(ClinicLocationAttributes.PIONERSKE);
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
            return Arrays.asList(ClinicLocationAttributes.PROHOROVKA);
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
            return Arrays.asList(ClinicLocationAttributes.STAROGNATIVKA);
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
            return Arrays.asList(ClinicLocationAttributes.STEPANIVKA);
        }

        @Override
        public LocationDescriptor parent() {
            return ProjectLocations.MARIUPOL;
        }
    };

}
