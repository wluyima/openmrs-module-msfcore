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
//TODO perhaps expose some of these through settings/global properties
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

  //TODO use org.openmrs.module.registrationcore.RegistrationCoreConstants#GP_OPENMRS_IDENTIFIER_SOURCE_ID
  public final static String GP_OPENMRS_IDENTIFIER_SOURCE_ID = "registrationcore.identifierSourceId";
}
