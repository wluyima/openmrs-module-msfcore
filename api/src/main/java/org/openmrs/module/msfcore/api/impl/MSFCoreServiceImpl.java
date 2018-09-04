/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * 
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.msfcore.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.LocationAttributeType;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.idgen.IdentifierSource;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.api.dao.MSFCoreDao;
import org.openmrs.util.OpenmrsConstants;

public class MSFCoreServiceImpl extends BaseOpenmrsService implements MSFCoreService {

    MSFCoreDao dao;

    /**
     * Injected in moduleApplicationContext.xml
     */
    public void setDao(MSFCoreDao dao) {
        this.dao = dao;
    }

    public List<Concept> getAllConceptAnswers(Concept question) {
        return dao.getAllConceptAnswers(question);
    }

    public List<LocationAttribute> getLocationAttributeByTypeAndLocation(LocationAttributeType type, Location location) {
        return dao.getLocationAttributeByTypeAndLocation(type, location);
    }

    public IdentifierSource updateIdentifierSource(SequentialIdentifierGenerator identifierSource) throws APIException {
        return dao.updateIdentifierSource(identifierSource);
    }

    public List<DropDownFieldOption> getAllConceptAnswerNames(String uuid) {
        List<DropDownFieldOption> answerNames = new ArrayList<DropDownFieldOption>();
        for (Concept answer : getAllConceptAnswers(Context.getConceptService().getConceptByUuid(uuid))) {
            answerNames.add(new DropDownFieldOption(String.valueOf(answer.getId()), answer.getName().getName()));
        }
        return answerNames;
    }

    public boolean configured() {
        boolean configured = false;
        Location defaultLocation = Context.getLocationService().getDefaultLocation();
        if (StringUtils.isNotBlank(instanceId()) && defaultLocation != null && getLocationCodeAttribute(defaultLocation) != null) {
            configured = true;
        }
        return configured;
    }

    public String instanceId() {
        return Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_INSTANCE_ID);
    }

    public void saveInstanceId(String instanceId) {
        setGlobalProperty(MSFCoreConfig.GP_INSTANCE_ID, instanceId);
    }

    private void setGlobalProperty(String property, String propertyValue) {
        Context.getAdministrationService().setGlobalProperty(property, propertyValue);
    }

    public List<Location> getMSFLocations() {
        List<Location> locations = new ArrayList<Location>();
        locations.addAll(Context.getLocationService().getLocationsByTag(
                        Context.getLocationService().getLocationTagByUuid(MSFCoreConfig.LOCATION_TAG_UUID_MISSION)));
        locations.addAll(Context.getLocationService().getLocationsByTag(
                        Context.getLocationService().getLocationTagByUuid(MSFCoreConfig.LOCATION_TAG_UUID_PROJECT)));
        locations.addAll(Context.getLocationService().getLocationsByTag(
                        Context.getLocationService().getLocationTagByUuid(MSFCoreConfig.LOCATION_TAG_UUID_CLINIC)));
        Location defaultLocation = Context.getLocationService().getDefaultLocation();
        if (!locations.contains(defaultLocation)) {
            locations.add(defaultLocation);
        }
        return locations;
    }

    public String getLocationCode(Location location) {
        LocationAttribute locationCode = getLocationCodeAttribute(location);
        if (locationCode != null) {
            return locationCode.getValue().toString();
        }
        return "";
    }

    public LocationAttribute getLocationCodeAttribute(Location location) {
        if (location != null) {
            List<LocationAttribute> codes = getLocationAttributeByTypeAndLocation(Context.getLocationService()
                            .getLocationAttributeTypeByUuid(MSFCoreConfig.LOCATION_ATTR_TYPE_CODE_UUID), location);
            if (codes.size() > 0) {
                return codes.get(0);
            }
        }
        return null;
    }

    public void saveDefaultLocation(Location location) {
        setGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_DEFAULT_LOCATION_NAME, location.getName());
    }

}
