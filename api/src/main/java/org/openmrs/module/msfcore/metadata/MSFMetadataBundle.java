package org.openmrs.module.msfcore.metadata;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

@Component
public class MSFMetadataBundle extends AbstractMetadataBundle {

  @Override
  public void install() throws Exception {
    log.info("Installing PersonAttributeTypes");
    install(PersonAttributeTypes.NATIONALITY);
    install(PersonAttributeTypes.OTHER_NATIONALITY);
    install(PersonAttributeTypes.MARITAL_STATUS);
    install(PersonAttributeTypes.EMPLOYMENT_STATUS);

    log.info("Installing OtherAttributeTypes");
    install(OtherAttributeTypes.LOCATION_CODE);

    log.info("Installing PatientIdentifierTypes");
    install(PatientIdentifierTypes.MSF_PRIMARY_ID_TYPE);
  }

}
