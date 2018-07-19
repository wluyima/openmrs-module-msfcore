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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;
import org.openmrs.module.BaseModuleActivator;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.emrapi.EmrApiConstants;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.service.IdentifierSourceService;
import org.openmrs.module.metadatadeploy.api.MetadataDeployService;
import org.openmrs.module.metadatamapping.MetadataTermMapping;
import org.openmrs.module.metadatamapping.api.MetadataMappingService;
import org.openmrs.module.msfcore.id.MSFIdentifierGenerator;
import org.openmrs.module.msfcore.metadata.MSFMetadataBundle;
import org.openmrs.module.msfcore.metadata.PatientIdentifierTypes;
import org.openmrs.module.referencemetadata.ReferenceMetadataConstants;

/**
 * This class contains the logic that is run every time this module is either
 * started or shutdown
 */
public class MSFCoreActivator extends BaseModuleActivator {

  private Log log = LogFactory.getLog(getClass());

  /**
   * @see #started()
   */
  public void started() {
    log.info("Started MSF Core Module");

    log.info("Disabling default reference application registration app");
    Context.getService(AppFrameworkService.class).disableApp(MSFCoreConfig.REGISTRATION_APP_EXTENSION_ID);

    log.info("Installing MSF metadata");
    Context.getService(MetadataDeployService.class)
        .installBundle(Context.getRegisteredComponents(MSFMetadataBundle.class).get(0));

    log.info("Installation and configuration of default MSF Identifier");
    MSFIdentifierGenerator.installation();

    configureToMSFPatientPrimaryIdentifier();

  }

  private void configureToMSFPatientPrimaryIdentifier() {
    log.info("Un requiring OpenMRS ID if not done");
    PatientIdentifierType openmrsIdType = Context.getPatientService()
        .getPatientIdentifierTypeByName(ReferenceMetadataConstants.OPENMRS_ID_NAME);
    if (openmrsIdType.getRequired()) {
      openmrsIdType.setRequired(false);
      Context.getPatientService().savePatientIdentifierType(openmrsIdType);
    }

    log.info("Configuting primary Identifier");
    MetadataMappingService metadataMappingService = Context.getService(MetadataMappingService.class);
    MetadataTermMapping primaryIdentifierTypeMapping = metadataMappingService.getMetadataTermMapping(
        EmrApiConstants.EMR_METADATA_SOURCE_NAME, EmrApiConstants.PRIMARY_IDENTIFIER_TYPE);
    PatientIdentifierType msfIdType = Context.getPatientService()
        .getPatientIdentifierTypeByUuid(PatientIdentifierTypes.MSF_PRIMARY_ID_TYPE.uuid());
    if (!msfIdType.getUuid().equals(primaryIdentifierTypeMapping.getMetadataUuid())) {
      primaryIdentifierTypeMapping.setMappedObject(msfIdType);
      metadataMappingService.saveMetadataTermMapping(primaryIdentifierTypeMapping);
    }

    log.info("Setting primary identifier source");
    IdentifierSource msfIdSource = Context.getService(IdentifierSourceService.class)
        .getIdentifierSourceByUuid(MSFCoreConfig.PATIENT_ID_TYPE_SOURCE_MSF_UUID);
    if (msfIdSource != null) {
      Context.getAdministrationService().updateGlobalProperty(MSFCoreConfig.GP_OPENMRS_IDENTIFIER_SOURCE_ID,
          String.valueOf(msfIdSource.getId()));
    }
  }

  private void configureToOpenMRSPatientPrimaryIdentifier() {
    log.info("Requiring OpenMRS ID if not done");
    PatientIdentifierType openmrsIdType = Context.getPatientService()
        .getPatientIdentifierTypeByName(ReferenceMetadataConstants.OPENMRS_ID_NAME);
    PatientIdentifierType msfIdType = Context.getPatientService()
            .getPatientIdentifierTypeByUuid(PatientIdentifierTypes.MSF_PRIMARY_ID_TYPE.uuid());
    if (!openmrsIdType.getRequired()) {
      openmrsIdType.setRequired(true);
      Context.getPatientService().savePatientIdentifierType(openmrsIdType);
    }
    if (msfIdType.getRequired()) {
      msfIdType.setRequired(false);
      Context.getPatientService().savePatientIdentifierType(msfIdType);
    }

    log.info("Configuting primary Identifier");
    MetadataMappingService metadataMappingService = Context.getService(MetadataMappingService.class);
    MetadataTermMapping primaryIdentifierTypeMapping = metadataMappingService.getMetadataTermMapping(
        EmrApiConstants.EMR_METADATA_SOURCE_NAME, EmrApiConstants.PRIMARY_IDENTIFIER_TYPE);
    if (!openmrsIdType.getUuid().equals(primaryIdentifierTypeMapping.getMetadataUuid())) {
      primaryIdentifierTypeMapping.setMappedObject(openmrsIdType);
      metadataMappingService.saveMetadataTermMapping(primaryIdentifierTypeMapping);
    }

    log.info("Setting primary identifier source");
    IdentifierSource sourceForPrimaryType = Context.getService(IdentifierSourceService.class)
        .getAutoGenerationOption(openmrsIdType).getSource();
    if (sourceForPrimaryType != null) {
      Context.getAdministrationService().updateGlobalProperty(MSFCoreConfig.GP_OPENMRS_IDENTIFIER_SOURCE_ID,
          String.valueOf(sourceForPrimaryType.getId()));
    }
  }

  /**
   * @see #shutdown()
   */
  public void shutdown() {
    log.info("Shutdown MSF Core");
  }

  @Override
  public void willStop() {
    Context.getService(AppFrameworkService.class).disableApp(MSFCoreConfig.MSF_REGISTRATION_APP_EXTENSION_ID);
    Context.getService(AppFrameworkService.class).enableApp(MSFCoreConfig.REGISTRATION_APP_EXTENSION_ID);

    configureToOpenMRSPatientPrimaryIdentifier();
    super.willStop();
  }

}
