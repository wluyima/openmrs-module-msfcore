/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore.api;

import java.util.List;
import java.util.Properties;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.Patient;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.msfcore.DropDownFieldOption;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface MSFCoreService extends OpenmrsService {

    public List<Concept> getAllConceptAnswers(Concept question);

    public List<LocationAttribute> getLocationAttributeByTypeAndLocation(LocationAttributeType type, Location location);

    public List<DropDownFieldOption> getAllConceptAnswerNames(String uuid);

    public boolean configured();

    public String instanceId();

    public List<Location> getMSFLocations();

    public String getLocationCode(Location location);

    public void saveInstanceId(String instanceId);

    public LocationAttribute getLocationCodeAttribute(Location location);

    public void saveDefaultLocation(Location location);

    public void msfIdentifierGeneratorInstallation();

    public void saveSequencyPrefix(SequentialIdentifierGenerator generator);

    public String getLocationDHISUid(Location location);

    public LocationAttribute getLocationUidAttribute(Location location);

    public void transferDHISMappingsToDataDirectory();

    public Properties getDHISMappings();

    public String postTrackerInstanceThroughOpenHimForAPatient(Patient patient);
}
