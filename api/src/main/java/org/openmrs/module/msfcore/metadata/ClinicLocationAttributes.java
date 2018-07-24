package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationAttributeTypeDescriptor;
import org.openmrs.module.metadatadeploy.descriptor.LocationDescriptor;
import org.openmrs.module.msfcore.MSFCoreConfig;

public class ClinicLocationAttributes {
  public static LocationAttributeDescriptor AARSAL = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.AARSAL_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.AARSAL;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "AAR";
    }
  };

  public static LocationAttributeDescriptor BAALBAK = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.BAALBAK_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.BAALBAK;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "BAA";
    }
  };

  public static LocationAttributeDescriptor HERMEL = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.HERMEL_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.HERMEL;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "HER";
    }
  };

  public static LocationAttributeDescriptor MAJDAL_ANJAR = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.MAJDAL_ANJAR_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.MAJDAL_ANJAR;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "MA";
    }
  };

  public static LocationAttributeDescriptor ABDEH = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.ABDEH_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.ABDEH;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "ABD";
    }
  };

  public static LocationAttributeDescriptor DAZ = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.DAZ_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.DAZ;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "DAZ";
    }
  };

  public static LocationAttributeDescriptor BEREZOVE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.BEREZOVE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.BEREZOVE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "BER";
    }
  };

  public static LocationAttributeDescriptor KAMIANKA_2 = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.KAMIANKA_2_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.KAMIANKA_2;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "K2";
    }
  };

  public static LocationAttributeDescriptor KURAKHOVE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.KURAKHOVE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.KURAKHOVE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "KUR";
    }
  };

  public static LocationAttributeDescriptor MAXIMILIANOVKA = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.MAXIMILIANOVKA_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.MAXIMILIANOVKA;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "MAX";
    }
  };

  public static LocationAttributeDescriptor GRANITNE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.GRANITNE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.GRANITNE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "GRA";
    }
  };

  public static LocationAttributeDescriptor HOSPITAL_N_1_MARIUPOL = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.HOSPITAL_N_1_MARIUPOL_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.HOSPITAL_N_1_MARIUPOL;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "HN1";
    }
  };

  public static LocationAttributeDescriptor HOSPITAL_N_3_MARIUPOL = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.HOSPITAL_N_3_MARIUPOL_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.HOSPITAL_N_3_MARIUPOL;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "HN3";
    }
  };

  public static LocationAttributeDescriptor KAMIANKA_1 = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.KAMIANKA_1_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.KAMIANKA_1;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "K1";
    }
  };

  public static LocationAttributeDescriptor LEBEDINSKOE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.LEBEDINSKOE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.LEBEDINSKOE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "LEB";
    }
  };

  public static LocationAttributeDescriptor MIKOLAIVKA = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.MIKOLAIVKA_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.MIKOLAIVKA;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "MIK";
    }
  };

  public static LocationAttributeDescriptor NOVOSELIVKA = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.NOVOSELIVKA_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.NOVOSELIVKA;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "NOV";
    }
  };

  public static LocationAttributeDescriptor ORLOVSKE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.ORLOVSKE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.ORLOVSKE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "ORL";
    }
  };

  public static LocationAttributeDescriptor PASHKOVSKOGO = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.PASHKOVSKOGO_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.PASHKOVSKOGO;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "PASH";
    }
  };

  public static LocationAttributeDescriptor PAVLOPIL = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.PAVLOPIL_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.PAVLOPIL;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "PAV";
    }
  };

  public static LocationAttributeDescriptor PIONERSKE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.PIONERSKE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.PIONERSKE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "PIO";
    }
  };

  public static LocationAttributeDescriptor PROHOROVKA = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.PROHOROVKA_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.PROHOROVKA;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "PRO";
    }
  };

  public static LocationAttributeDescriptor STAROGNATIVKA = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.STAROGNATIVKA_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.STAROGNATIVKA;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "STA";
    }
  };

  public static LocationAttributeDescriptor STEPANIVKA = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.STEPANIVKA_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.STEPANIVKA;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "STE";
    }
  };

  public static LocationAttributeDescriptor BERDIANSKOE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.BERDIANSKOE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.BERDIANSKOE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "BERD";
    }
  };

  public static LocationAttributeDescriptor NETAILOVE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.NETAILOVE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.NETAILOVE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "NET";
    }
  };

  public static LocationAttributeDescriptor NOVOSELIVKA_DRUHA = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.NOVOSELIVKA_DRUHA_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.NOVOSELIVKA_DRUHA;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "ND";
    }
  };

  public static LocationAttributeDescriptor OPYTNE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.OPYTNE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.OPYTNE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "OPY";
    }
  };

  public static LocationAttributeDescriptor TARAMCHUK = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.TARAMCHUK_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.TARAMCHUK;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "TAR";
    }
  };

  public static LocationAttributeDescriptor VODYANE = new LocationAttributeDescriptor() {
    @Override
    public String uuid() {
      return MSFCoreConfig.VODYANE_LOCATION_ATTRIBUTE_UUID;
    }

    @Override
    public LocationDescriptor location() {
      return ClinicLocations.VODYANE;
    }

    @Override
    public LocationAttributeTypeDescriptor type() {
      return LocationAttributeTypes.LOCATION_CODE;
    }

    @Override
    public String value() {
      return "VOD";
    }
  };
}
