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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.CareSetting;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.Obs;
import org.openmrs.OrderType;
import org.openmrs.Provider;
import org.openmrs.TestOrder;
import org.openmrs.api.EncounterService;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.idgen.SequentialIdentifierGenerator;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.MSFCoreUtils;
import org.openmrs.module.msfcore.SimpleJSON;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.module.msfcore.api.dao.MSFCoreDao;
import org.openmrs.module.msfcore.id.MSFIdentifierGenerator;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MSFCoreServiceImpl extends BaseOpenmrsService implements MSFCoreService {

    private static final String ORDER_VOID_REASON = "Obs was voided";

    @Autowired
    private OrderService orderService;

    @Autowired
    private EncounterService encounterService;

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

    public List<DropDownFieldOption> getAllConceptAnswerNames(String uuid) {
        List<DropDownFieldOption> answerNames = new ArrayList<DropDownFieldOption>();
        for (Concept answer : getAllConceptAnswers(Context.getConceptService().getConceptByUuid(uuid))) {
            answerNames.add(new DropDownFieldOption(String.valueOf(answer.getId()), answer.getName().getName()));
        }
        return answerNames;
    }

    public boolean isConfigured() {
        boolean configured = false;
        Location defaultLocation = Context.getLocationService().getDefaultLocation();
        if (StringUtils.isNotBlank(getInstanceId()) && defaultLocation != null && getLocationCodeAttribute(defaultLocation) != null) {
            configured = true;
        }
        return configured;
    }

    public String getInstanceId() {
        String instanceId = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_INSTANCE_ID);
        return StringUtils.isBlank(instanceId) ? "" : instanceId;
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
        if (location == null) {
            return null;
        }
        return getLocationAttribute(getLocationCodeAttribute(location));
    }

    private String getLocationAttribute(LocationAttribute locAttribute) {
        if (locAttribute != null) {
            return locAttribute.getValue().toString();
        }
        return "";
    }

    public LocationAttribute getLocationCodeAttribute(Location location) {
        return getLocationAttribute(location, MSFCoreConfig.LOCATION_ATTR_TYPE_CODE_UUID);
    }

    private LocationAttribute getLocationAttribute(Location location, String attributeTypeUuid) {
        if (location != null) {
            List<LocationAttribute> attrributes = dao.getLocationAttributeByTypeAndLocation(Context.getLocationService()
                            .getLocationAttributeTypeByUuid(attributeTypeUuid), location);
            if (!attrributes.isEmpty()) {
                return attrributes.get(0);
            }
        }
        return null;
    }

    public void saveDefaultLocation(Location location) {
        setGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_DEFAULT_LOCATION_NAME, location.getName());
    }

    public void msfIdentifierGeneratorInstallation() {
        MSFIdentifierGenerator.installation();
    }

    public void saveSequencyPrefix(SequentialIdentifierGenerator generator) {
        dao.saveSequencyPrefix(generator);
    }

    private File getSync2ConfigFile() {
        File configFileFolder = OpenmrsUtil.getDirectoryInApplicationDataDirectory(MSFCoreConfig.CONFIGURATION_DIR);
        return new File(configFileFolder, MSFCoreConfig.SYNC2_NAME_OF_CUSTOM_CONFIGURATION);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void setGeneralPropertyInConfigJson(SimpleJSON json, String property, String value) {
        ((Map) json.get("general")).put(property, value);
    }

    // TODO using sync2 has bean failures
    public void overwriteSync2Configuration() {
        Location defaultLocation = Context.getLocationService().getDefaultLocation();
        ObjectMapper mapper = new ObjectMapper();
        try {
            SimpleJSON syncConfig = mapper.readValue(new FileInputStream(getClass().getClassLoader().getResource(
                            MSFCoreConfig.SYNC2_NAME_OF_CUSTOM_CONFIGURATION).getFile()), SimpleJSON.class);
            if (isConfigured()) {
                String localFeedUrl = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_SYNC_LOCAL_FEED_URL);
                String parentFeedUrl = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_SYNC_PARENT_FEED_URL);
                if (StringUtils.isNotBlank(localFeedUrl)) {
                    setGeneralPropertyInConfigJson(syncConfig, "localFeedLocation", localFeedUrl);
                }
                if (StringUtils.isNotBlank(parentFeedUrl)) {
                    setGeneralPropertyInConfigJson(syncConfig, "parentFeedLocation", parentFeedUrl);
                }
                setGeneralPropertyInConfigJson(syncConfig, "localInstanceId", (getInstanceId() + "_" + getLocationCode(defaultLocation))
                                .toLowerCase());
            }
            MSFCoreUtils.overWriteFile(getSync2ConfigFile(), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(syncConfig));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentLocationIdentity() {
        if (isConfigured()) {
            return Context.getMessageSourceService().getMessage("msfcore.currentLocationIdentity",
                            new Object[]{Context.getLocationService().getDefaultLocation().getName(), getInstanceId()}, null);
        }
        return "";
    }

    @Override
    public void saveTestOrders(Encounter encounter) {
        OrderType orderType = orderService.getOrderTypeByUuid(OrderType.TEST_ORDER_TYPE_UUID);
        Provider provider = encounter.getEncounterProviders().iterator().next().getProvider();
        CareSetting careSetting = orderService.getCareSettingByName(CareSetting.CareSettingType.OUTPATIENT.name());
        for (Obs obs : encounter.getObsAtTopLevel(true)) {
            if (!obs.getVoided() && obs.getOrder() == null) {
                Concept concept = obs.getConcept();
                TestOrder order = createTestOrder(encounter, orderType, provider, careSetting, concept);
                orderService.saveOrder(order, null);

                /*So, now we are supposed to link the test order with the obs by setting obs.setOrder() but that will not work
                because OpenMRS is not allowing to set Orders to and existing Obs. The methodo Obs.newInstance() that is 
                called behind the scenes when you update and Obs, does not copy over the Order. So what we do next is manually 
                copying the existing Obs and set the order on that copy that is not yet persisted. Then we manualy void the 
                current Obs and create the copy. I did try to change openmrs-core code to make Obs.newInstance() copy the Order 
                but that broke the test transferEncounter_shouldTransferAnEncounterWithObservationsButNotOrdersToGivenPatient 
                so I will just leave it alone.*/
                Obs newObs = Obs.newInstance(obs);
                newObs.setOrder(order);
                obs.setVoided(true);
                encounter.addObs(newObs);
            } else if (obs.getVoided() && obs.getOrder() != null && !obs.getOrder().getVoided()) {
                orderService.voidOrder(obs.getOrder(), ORDER_VOID_REASON);
            }
        }
        encounterService.saveEncounter(encounter);
    }

    private TestOrder createTestOrder(Encounter encounter, OrderType orderType, Provider provider, CareSetting careSetting, Concept concept) {
        TestOrder order = new TestOrder();
        order.setOrderType(orderType);
        order.setConcept(concept);
        order.setPatient(encounter.getPatient());
        order.setEncounter(encounter);
        order.setOrderer(provider);
        order.setCareSetting(careSetting);
        return order;
    }
}
