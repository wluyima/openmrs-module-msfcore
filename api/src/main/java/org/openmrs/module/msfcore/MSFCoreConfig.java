/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore;

import org.springframework.stereotype.Component;

/**
 * Contains module's config.
 */
@Component("msfcore.MSFCoreConfig")
// TODO perhaps expose some of these through settings/global properties
public class MSFCoreConfig {

  public final static String MODULE_PRIVILEGE = "MSF Core Privilege";

  public final static String REGISTRATION_APP_EXTENSION_ID = "referenceapplication.registrationapp.registerPatient";

  public final static String MSF_REGISTRATION_APP_EXTENSION_ID = "msfcore.registrationapp";

  public final static String GP_COUNTRY_CODE = "msfcore.countryCode";

  public final static String GP_DEFAULT_LOCATION = "default_location";

  public final static String LOCATION_ATTR_TYPE_CODE_UUID = "5a504478-1701-49da-9d8b-3e1d12ab6c5a";

  public final static String PATIENT_ID_TYPE_MSF_UUID = "0t504478-1701-49da-9d8b-3e1d12ab6c5b";

  public final static String PATIENT_ID_TYPE_SOURCE_MSF_UUID = "9a504478-1701-49da-9d8b-3e1d12ab6c5z";

  public final static String MSF_ID_BASE_CHARACTER_SET = "0123456789";

  public final static String NATIONALITY_CONCEPT_UUID = "4a504478-1701-49da-9d8b-3e1d12ab6e3f";

  public final static String OTHER_NATIONALITY_CONCEPT_UUID = "c901e362-0499-48a0-9a07-74e2d71bcd90";

  public final static String MARITAL_STATUS_CONCEPT_UUID = "3fb0d2d1-05ff-4390-8ec7-2e4665853f0e";

  public final static String EMPLOYMENT_STATUS_CONCEPT_UUID = "82494b0f-0049-44ba-b166-13ab352fa408";

  public final static String NATIONALITY_PERSON_ATTRIBUTE_UUID = "4a504478-1701-49da-9d8b-3e1d12ab6c5o";

  public final static String OTHER_NATIONALITY_PERSON_ATTRIBUTE_UUID = "4a504478-1701-49da-9d8b-3e1d12ab6c5p";

  public final static String MARITAL_STATUS_PERSON_ATTRIBUTE_UUID = "4a504478-1701-49da-9d8b-3e1d12ab6c5q";

  public final static String EMPLOYMENT_STATUS_PERSON_ATTRIBUTE_UUID = "4a504478-1701-49da-9d8b-3e1d12ab6c5r";

  public final static String DATE_OF_ARRIVAL_PERSON_ATTRIBUTE_UUID = "d03313d1-12fc-4b0b-ba6c-c4873bee3be6";

  // TODO use
  // org.openmrs.module.registrationcore.RegistrationCoreConstants#GP_OPENMRS_IDENTIFIER_SOURCE_ID
  public final static String GP_OPENMRS_IDENTIFIER_SOURCE_ID = "registrationcore.identifierSourceId";

  public final static String LOCATION_TAG_UUID_MISSION = "76433e9a-8b93-11e8-9eb6-529269fb1459";

  public final static String LOCATION_TAG_UUID_PROJECT = "76433e9a-8b93-11e8-9eb6-529269fb1460";

  public final static String LOCATION_TAG_UUID_CLINIC = "76433e9a-8b93-11e8-9eb6-529269fb1461";

  public final static String LOCATION_LEBANON_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1459";

  public final static String LOCATION_UKRAINE_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1460";

  public final static String LEBANON_LOCATION_ATTRIBUTE_UUID = "e02d72fa-8b96-11e8-9eb6-529269fb1459";

  public final static String UKRAINE_LOCATION_ATTRIBUTE_UUID = "e02d72fa-8b96-11e8-9eb6-529269fb1460";

  public final static String LOCATION_BEKAA_VALLEY_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1461";

  public final static String BEKAA_VALLEY_LOCATION_ATTRIBUTE_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1462";

  public final static String LOCATION_TRIPOLI_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1463";

  public final static String TRIPOLI_LOCATION_ATTRIBUTE_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1464";

  public final static String LOCATION_WEST_DONETSK_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1465";

  public final static String WEST_DONETSK_LOCATION_ATTRIBUTE_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1466";

  public final static String LOCATION_MARIUPOL_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1467";

  public final static String MARIUPOL_LOCATION_ATTRIBUTE_UUID = "4b1e44b6-8b94-11e8-9eb6-529269fb1468";

  public final static String AARSAL_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1459";

  public final static String BAALBAK_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1460";

  public final static String HERMEL_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1461";

  public final static String MAJDAL_ANJAR_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1462";

  public final static String ABDEH_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1463";

  public final static String DAZ_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1464";

  public final static String BEREZOVE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1465";

  public final static String KAMIANKA_2_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1466";

  public final static String KURAKHOVE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1467";

  public final static String MAXIMILIANOVKA_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1468";

  public final static String NETAILOVE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1469";

  public final static String NOVOSELIVKA_DRUHA_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1470";

  public final static String OPYTNE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1471";

  public final static String TARAMCHUK_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1472";

  public final static String VODYANE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1473";

  public final static String BERDIANSKOE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1474";

  public final static String GRANITNE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1475";

  public final static String HOSPITAL_N_1_MARIUPOL_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1476";

  public final static String HOSPITAL_N_3_MARIUPOL_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1477";

  public final static String KAMIANKA_1_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1478";

  public final static String LEBEDINSKOE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1479";

  public final static String MIKOLAIVKA_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1480";

  public final static String NOVOSELIVKA_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1481";

  public final static String ORLOVSKE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1482";

  public final static String PASHKOVSKOGO_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1483";

  public final static String PAVLOPIL_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1484";

  public final static String PIONERSKE_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1485";

  public final static String PROHOROVKA_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1486";

  public final static String STAROGNATIVKA_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1487";

  public final static String STEPANIVKA_LOCATION_ATTRIBUTE_UUID = "77081024-8b9f-11e8-9eb6-529269fb1488";

  public final static String LOCATION_AARSAL_UUID = "87081024-8b9f-11e8-9eb6-529269fb1459";

  public final static String LOCATION_BAALBAK_UUID = "87081024-8b9f-11e8-9eb6-529269fb1460";

  public final static String LOCATION_HERMEL_UUID = "87081024-8b9f-11e8-9eb6-529269fb1461";

  public final static String LOCATION_MAJDAL_ANJAR_UUID = "87081024-8b9f-11e8-9eb6-529269fb1462";

  public final static String LOCATION_ABDEH_UUID = "87081024-8b9f-11e8-9eb6-529269fb1463";

  public final static String LOCATION_DAZ_UUID = "87081024-8b9f-11e8-9eb6-529269fb1464";

  public final static String LOCATION_BEREZOVE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1465";

  public final static String LOCATION_KAMIANKA_2_UUID = "87081024-8b9f-11e8-9eb6-529269fb1466";

  public final static String LOCATION_KURAKHOVE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1467";

  public final static String LOCATION_MAXIMILIANOVKA_UUID = "87081024-8b9f-11e8-9eb6-529269fb1468";

  public final static String LOCATION_NETAILOVE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1469";

  public final static String LOCATION_NOVOSELIVKA_DRUHA_UUID = "87081024-8b9f-11e8-9eb6-529269fb1470";

  public final static String LOCATION_OPYTNE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1471";

  public final static String LOCATION_TARAMCHUK_UUID = "87081024-8b9f-11e8-9eb6-529269fb1472";

  public final static String LOCATION_VODYANE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1473";

  public final static String LOCATION_BERDIANSKOE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1474";

  public final static String LOCATION_GRANITNE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1475";

  public final static String LOCATION_HOSPITAL_N_1_MARIUPOL_UUID = "87081024-8b9f-11e8-9eb6-529269fb1476";

  public final static String LOCATION_HOSPITAL_N_3_MARIUPOL_UUID = "87081024-8b9f-11e8-9eb6-529269fb1477";

  public final static String LOCATION_KAMIANKA_1_UUID = "87081024-8b9f-11e8-9eb6-529269fb1478";

  public final static String LOCATION_LEBEDINSKOE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1479";

  public final static String LOCATION_MIKOLAIVKA_UUID = "87081024-8b9f-11e8-9eb6-529269fb1480";

  public final static String LOCATION_NOVOSELIVKA_UUID = "87081024-8b9f-11e8-9eb6-529269fb1481";

  public final static String LOCATION_ORLOVSKE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1482";

  public final static String LOCATION_PASHKOVSKOGO_UUID = "87081024-8b9f-11e8-9eb6-529269fb1483";

  public final static String LOCATION_PAVLOPIL_UUID = "87081024-8b9f-11e8-9eb6-529269fb1484";

  public final static String LOCATION_PIONERSKE_UUID = "87081024-8b9f-11e8-9eb6-529269fb1485";

  public final static String LOCATION_PROHOROVKA_UUID = "87081024-8b9f-11e8-9eb6-529269fb1486";

  public final static String LOCATION_STAROGNATIVKA_UUID = "87081024-8b9f-11e8-9eb6-529269fb1487";

  public final static String LOCATION_STEPANIVKA_UUID = "87081024-8b9f-11e8-9eb6-529269fb1488";

}
