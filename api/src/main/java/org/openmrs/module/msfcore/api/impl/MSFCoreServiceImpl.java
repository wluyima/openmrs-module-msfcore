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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Allergies;
import org.openmrs.CareSetting;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.LocationAttribute;
import org.openmrs.Obs;
import org.openmrs.OrderFrequency;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflowState;
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
import org.openmrs.module.msfcore.api.util.AllergenUtils;
import org.openmrs.module.msfcore.id.MSFIdentifierGenerator;
import org.openmrs.util.OpenmrsConstants;
import org.openmrs.util.OpenmrsUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MSFCoreServiceImpl extends BaseOpenmrsService implements MSFCoreService {

    private static final String ORDER_VOID_REASON = "Obs was voided";

    private static final Map<String, Integer> DURATION_UNIT_CONCEPT_UUID_TO_NUMBER_OF_DAYS = new HashMap<String, Integer>() {
        {
            this.put("1072AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", 1);
            this.put("1073AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", 7);
            this.put("1074AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", 30);
        }
    };

    MSFCoreDao dao;

    /**
     * Injected in moduleApplicationContext.xml
     */
    public void setDao(MSFCoreDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Concept> getAllConceptAnswers(Concept question) {
        return this.dao.getAllConceptAnswers(question);
    }

    @Override
    public List<DropDownFieldOption> getAllConceptAnswerNames(String uuid) {
        final List<DropDownFieldOption> answerNames = new ArrayList<>();
        for (final Concept answer : this.getAllConceptAnswers(Context.getConceptService().getConceptByUuid(uuid))) {
            answerNames.add(new DropDownFieldOption(String.valueOf(answer.getId()), answer.getName().getName()));
        }
        return answerNames;
    }
    @Override
    public boolean isConfigured() {
        boolean configured = false;
        final Location defaultLocation = Context.getLocationService().getDefaultLocation();
        if (StringUtils.isNotBlank(this.getInstanceId()) && (defaultLocation != null)
                        && (this.getLocationCodeAttribute(defaultLocation) != null)) {
            configured = true;
        }
        return configured;
    }

    @Override
    public String getInstanceId() {
        final String instanceId = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_INSTANCE_ID);
        return StringUtils.isBlank(instanceId) ? "" : instanceId;
    }

    @Override
    public void saveInstanceId(String instanceId) {
        this.setGlobalProperty(MSFCoreConfig.GP_INSTANCE_ID, instanceId);
    }

    private void setGlobalProperty(String property, String propertyValue) {
        Context.getAdministrationService().setGlobalProperty(property, propertyValue);
    }

    @Override
    public List<Location> getMSFLocations() {
        final List<Location> locations = new ArrayList<>();
        locations.addAll(Context.getLocationService()
                        .getLocationsByTag(Context.getLocationService().getLocationTagByUuid(MSFCoreConfig.LOCATION_TAG_UUID_MISSION)));
        locations.addAll(Context.getLocationService()
                        .getLocationsByTag(Context.getLocationService().getLocationTagByUuid(MSFCoreConfig.LOCATION_TAG_UUID_PROJECT)));
        locations.addAll(Context.getLocationService()
                        .getLocationsByTag(Context.getLocationService().getLocationTagByUuid(MSFCoreConfig.LOCATION_TAG_UUID_CLINIC)));
        final Location defaultLocation = Context.getLocationService().getDefaultLocation();
        if (!locations.contains(defaultLocation)) {
            locations.add(defaultLocation);
        }
        return locations;
    }
    @Override
    public String getLocationCode(Location location) {
        if (location == null) {
            return null;
        }
        return this.getLocationAttribute(this.getLocationCodeAttribute(location));
    }

    private String getLocationAttribute(LocationAttribute locAttribute) {
        if (locAttribute != null) {
            return locAttribute.getValue().toString();
        }
        return "";
    }

    @Override
    public LocationAttribute getLocationCodeAttribute(Location location) {
        return this.getLocationAttribute(location, MSFCoreConfig.LOCATION_ATTR_TYPE_CODE_UUID);
    }

    private LocationAttribute getLocationAttribute(Location location, String attributeTypeUuid) {
        if (location != null) {
            final List<LocationAttribute> attrributes = this.dao.getLocationAttributeByTypeAndLocation(Context.getLocationService()
                            .getLocationAttributeTypeByUuid(attributeTypeUuid), location);
            if (!attrributes.isEmpty()) {
                return attrributes.get(0);
            }
        }
        return null;
    }

    @Override
    public void saveDefaultLocation(Location location) {
        this.setGlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_DEFAULT_LOCATION_NAME, location.getName());
    }

    @Override
    public void msfIdentifierGeneratorInstallation() {
        MSFIdentifierGenerator.installation();
    }

    @Override
    public void saveSequencyPrefix(SequentialIdentifierGenerator generator) {
        this.dao.saveSequencyPrefix(generator);
    }

    private File getSync2ConfigFile() {
        final File configFileFolder = OpenmrsUtil.getDirectoryInApplicationDataDirectory(MSFCoreConfig.CONFIGURATION_DIR);
        return new File(configFileFolder, MSFCoreConfig.SYNC2_NAME_OF_CUSTOM_CONFIGURATION);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void setGeneralPropertyInConfigJson(SimpleJSON json, String property, String value) {
        ((Map) json.get("general")).put(property, value);
    }

    // TODO using sync2 has bean failures
    @Override
    public void overwriteSync2Configuration() {
        final Location defaultLocation = Context.getLocationService().getDefaultLocation();
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final SimpleJSON syncConfig = mapper.readValue(new FileInputStream(this.getClass().getClassLoader().getResource(
                            MSFCoreConfig.SYNC2_NAME_OF_CUSTOM_CONFIGURATION).getFile()), SimpleJSON.class);
            if (this.isConfigured()) {
                final String localFeedUrl = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_SYNC_LOCAL_FEED_URL);
                final String parentFeedUrl = Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_SYNC_PARENT_FEED_URL);
                if (StringUtils.isNotBlank(localFeedUrl)) {
                    this.setGeneralPropertyInConfigJson(syncConfig, "localFeedLocation", localFeedUrl);
                }
                if (StringUtils.isNotBlank(parentFeedUrl)) {
                    this.setGeneralPropertyInConfigJson(syncConfig, "parentFeedLocation", parentFeedUrl);
                }
                this.setGeneralPropertyInConfigJson(syncConfig, "localInstanceId", (this.getInstanceId() + "_" + this
                                .getLocationCode(defaultLocation)).toLowerCase());
            }
            MSFCoreUtils.overWriteFile(this.getSync2ConfigFile(), mapper.writerWithDefaultPrettyPrinter().writeValueAsString(syncConfig));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentLocationIdentity() {
        if (this.isConfigured()) {
            return Context.getMessageSourceService().getMessage("msfcore.currentLocationIdentity",
                            new Object[]{Context.getLocationService().getDefaultLocation().getName(), this.getInstanceId()}, null);
        }
        return "";
    }

    @Override
    public PatientProgram generatePatientProgram(boolean enrollment, Map<String, ProgramWorkflowState> states,
                    PatientProgram patientProgram, Encounter encounter) {
        if ((patientProgram == null) || states.isEmpty()) {
            return null;
        }
        final Date date = new Date();
        ProgramWorkflowState stage = null;

        if (enrollment) {// enrollment is the first stage
            stage = states.get(MSFCoreConfig.WORKFLOW_STATE_UUID_ENROLL);
            final PatientState patientState = new PatientState();
            patientState.setPatientProgram(patientProgram);
            patientState.setState(stage);
            patientState.setStartDate(date);
            patientProgram.getStates().add(patientState);
            if (patientProgram.getDateCompleted() != null) {
                patientState.setStartDate(patientProgram.getDateCompleted());
                patientProgram.transitionToState(states.get(MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT), date);
            }
        } else {// transition to other stages after enrollment
            if (patientProgram.getStates().isEmpty()) {
                return null;
            }
            if ((encounter != null) && (encounter.getEncounterType() != null)) {
                if (encounter.getEncounterType().getUuid().equals(MSFCoreConfig.ENCOUNTER_TYPE_NCD_BASELINE_UUID)) {
                    stage = states.get(MSFCoreConfig.WORKFLOW_STATE_UUID_BASELINE_CONSULTATION);
                } else if (encounter.getEncounterType().getUuid().equals(MSFCoreConfig.ENCOUNTER_TYPE_NCD_FOLLOWUP_UUID)) {
                    stage = states.get(MSFCoreConfig.WORKFLOW_STATE_UUID_FOLLOWUP_CONSULTATION);
                } else if (encounter.getEncounterType().getUuid().equals(MSFCoreConfig.ENCOUNTER_TYPE_NCD_EXIT_UUID)) {
                    patientProgram.setDateCompleted(date);
                    stage = states.get(MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT);
                    for (final Obs o : encounter.getObs()) {
                        if (o.getConcept().getUuid().equals(MSFCoreConfig.NCD_PROGRAM_OUTCOMES_CONCEPT_UUID)) {
                            patientProgram.setOutcome(o.getValueCoded());
                        }
                    }
                }
                if ((stage != null) && !patientProgram.getCurrentStates().iterator().next().getState().equals(stage)) {
                    patientProgram.transitionToState(stage, date);
                }
            }
        }
        if ((patientProgram != null) && !patientProgram.getStates().isEmpty()) {
            return patientProgram;
        } else {
            return null;
        }
    }

    @Override
    public Map<String, ProgramWorkflowState> getMsfStages() {
        final Map<String, ProgramWorkflowState> stages = new HashMap<>();
        stages.put(MSFCoreConfig.WORKFLOW_STATE_UUID_ENROLL,
                        Context.getProgramWorkflowService().getStateByUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_ENROLL));
        stages.put(MSFCoreConfig.WORKFLOW_STATE_UUID_BASELINE_CONSULTATION,
                        Context.getProgramWorkflowService().getStateByUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_BASELINE_CONSULTATION));
        stages.put(MSFCoreConfig.WORKFLOW_STATE_UUID_FOLLOWUP_CONSULTATION,
                        Context.getProgramWorkflowService().getStateByUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_FOLLOWUP_CONSULTATION));
        stages.put(MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT,
                        Context.getProgramWorkflowService().getStateByUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT));
        return stages;
    }
    @Override
    public void manageNCDProgram(Encounter encounter) {
        final Patient patient = encounter.getPatient();
        final Program ncdPrgram = Context.getProgramWorkflowService().getProgramByUuid(MSFCoreConfig.NCD_PROGRAM_UUID);
        PatientProgram patientProgram = null;
        final List<PatientProgram> patientPrograms = Context.getProgramWorkflowService().getPatientPrograms(patient, ncdPrgram, null, null,
                        null, null, false);
        // TODO if patientPrograms is empty, first auto enroll?
        if (patientPrograms.isEmpty()) {
            final PatientProgram pp = new PatientProgram();
            pp.setPatient(patient);
            pp.setProgram(ncdPrgram);
            pp.setDateEnrolled(new Date());
            patientPrograms.add(Context.getProgramWorkflowService().savePatientProgram(
                            this.generatePatientProgram(true, this.getMsfStages(), pp, null)));
        }
        patientProgram = patientPrograms.get(0);
        if (patientProgram != null) {
            patientProgram = this.generatePatientProgram(false, this.getMsfStages(), patientProgram, encounter);
            if (patientProgram != null) {
                Context.getProgramWorkflowService().savePatientProgram(patientProgram);
            }
        }
    }

    @Override
    public void saveTestOrders(Encounter encounter) {
        final OrderService orderService = Context.getOrderService();
        final EncounterService encounterService = Context.getEncounterService();
        final OrderType orderType = orderService.getOrderTypeByUuid(OrderType.TEST_ORDER_TYPE_UUID);
        final Provider provider = encounter.getEncounterProviders().iterator().next().getProvider();
        final CareSetting careSetting = orderService.getCareSettingByName(CareSetting.CareSettingType.OUTPATIENT.name());
        final List<Obs> allObs = new ArrayList<>(encounter.getAllObs(true));
        for (final Obs obs : allObs) {
            if (!obs.getVoided() && (obs.getOrder() == null)) {
                final Concept concept = obs.getConcept();
                final TestOrder order = this.createTestOrder(encounter, orderType, provider, careSetting, concept);
                orderService.saveOrder(order, null);
                final Obs newObs = Obs.newInstance(obs);
                newObs.setOrder(order);
                obs.setVoided(true);
                encounter.addObs(newObs);
            } else if (obs.getVoided() && (obs.getOrder() != null) && !obs.getOrder().getVoided()) {
                orderService.voidOrder(obs.getOrder(), ORDER_VOID_REASON);
            }
        }
        encounterService.saveEncounter(encounter);
    }
    private TestOrder createTestOrder(Encounter encounter, OrderType orderType, Provider provider, CareSetting careSetting, Concept concept) {
        final TestOrder order = new TestOrder();
        order.setOrderType(orderType);
        order.setConcept(concept);
        order.setPatient(encounter.getPatient());
        order.setEncounter(encounter);
        order.setOrderer(provider);
        order.setCareSetting(careSetting);
        return order;
    }

    @Override
    public void saveDrugOrders(Encounter encounter) {
        final OrderService orderService = Context.getOrderService();
        final EncounterService encounterService = Context.getEncounterService();
        final OrderType orderType = orderService.getOrderTypeByUuid(OrderType.DRUG_ORDER_TYPE_UUID);
        final Provider provider = encounter.getEncounterProviders().iterator().next().getProvider();
        final CareSetting careSetting = orderService.getCareSettingByName(CareSetting.CareSettingType.OUTPATIENT.name());
        final Set<Obs> groups = encounter.getObsAtTopLevel(true);
        for (final Obs group : groups) {
            if (!group.getVoided()) {
                final Set<Obs> observations = group.getGroupMembers();
                final Concept medication = this.getObsConceptValueByConceptUuid(MSFCoreConfig.CONCEPT_PRESCRIBED_MEDICATION_UUID,
                                observations);
                final Drug drug = this.dao.getDrugByConcept(medication);
                final DrugOrder order = this.createDrugOrder(encounter, orderType, provider, careSetting, observations);
                order.setDrug(drug);
                encounter.addOrder(order);
                final Obs newGroup = Obs.newInstance(group);
                newGroup.setOrder(order);
                group.setVoided(true);
                observations.forEach(o -> o.setVoided(true));
                encounter.addObs(newGroup);
            }
            if (group.getOrder() != null) {
                group.getOrder().setVoided(true);
            }
        }
        encounterService.saveEncounter(encounter);
    }
    @Override
    public Allergies saveAllergies(Encounter encounter) {
        return Context.getRegisteredComponents(AllergenUtils.class).get(0).createAllergies(encounter);
    }

    private DrugOrder createDrugOrder(Encounter encounter, OrderType orderType, Provider provider, CareSetting careSetting,
                    Set<Obs> observations) {
        final DrugOrder order = new DrugOrder();
        order.setOrderType(orderType);
        order.setPatient(encounter.getPatient());
        order.setEncounter(encounter);
        order.setOrderer(provider);
        order.setCareSetting(careSetting);
        this.setOrderFields(observations, order);
        return order;
    }

    private void setOrderFields(Set<Obs> observations, DrugOrder order) {
        final Double dose = this.getObsNumericValueByConceptUuid(MSFCoreConfig.CONCEPT_DOSE_UUID, observations);
        final Double duration = this.getObsNumericValueByConceptUuid(MSFCoreConfig.CONCEPT_DURATION_UUID, observations);
        final Concept frequencyConcept = this.getObsConceptValueByConceptUuid(MSFCoreConfig.CONCEPT_FREQUENCY_UUID, observations);
        final Concept durationUnit = this.getObsConceptValueByConceptUuid(MSFCoreConfig.CONCEPT_DURATION_UNIT_UUID, observations);
        final Concept doseUnit = this.getObsConceptValueByConceptUuid(MSFCoreConfig.CONCEPT_DOSE_UNIT_UUID, observations);
        final OrderFrequency orderFrequency = Context.getOrderService().getOrderFrequencyByConcept(frequencyConcept);
        final Concept route = Context.getConceptService().getConceptByUuid(MSFCoreConfig.CONCEPT_ROUTE_ORAL_UUID);
        final String administrationInstructions = this.getObsValueTextByConceptUuid(MSFCoreConfig.CONCEPT_ADMINISTRATION_INSTRUCTIONS_UUID,
                        observations);
        order.setDose(dose);
        order.setDoseUnits(doseUnit);
        order.setDuration(duration.intValue());
        order.setDurationUnits(durationUnit);
        order.setFrequency(orderFrequency);
        order.setNumRefills(0);
        order.setQuantity(this.calculateQuantity(dose, duration, durationUnit, orderFrequency));
        order.setQuantityUnits(doseUnit);
        order.setRoute(route);
        order.setDosingInstructions(administrationInstructions);
        order.setInstructions(administrationInstructions);
    }

    private Double calculateQuantity(Double dose, Double duration, Concept durationUnit, OrderFrequency orderFrequency) {
        final BigDecimal durationUnitDays = BigDecimal.valueOf(DURATION_UNIT_CONCEPT_UUID_TO_NUMBER_OF_DAYS.get(durationUnit.getUuid()));
        final BigDecimal timesPerDay = BigDecimal.valueOf(orderFrequency.getFrequencyPerDay());
        final BigDecimal doseBigDecimal = BigDecimal.valueOf(dose);
        final BigDecimal durationBigDecimal = BigDecimal.valueOf(duration);
        return doseBigDecimal.multiply(timesPerDay).multiply(durationBigDecimal).multiply(durationUnitDays).doubleValue();
    }

    private Concept getObsConceptValueByConceptUuid(String conceptUuid, Set<Obs> observations) {
        final Optional<Obs> obs = this.getObservationByConceptUuid(conceptUuid, observations);
        return obs.isPresent() ? obs.get().getValueCoded() : null;
    }

    private Double getObsNumericValueByConceptUuid(String conceptUuid, Set<Obs> observations) {
        final Optional<Obs> obs = this.getObservationByConceptUuid(conceptUuid, observations);
        return obs.isPresent() ? obs.get().getValueNumeric() : null;
    }

    private String getObsValueTextByConceptUuid(String conceptUuid, Set<Obs> observations) {
        final Optional<Obs> obs = this.getObservationByConceptUuid(conceptUuid, observations);
        return obs.isPresent() ? obs.get().getValueText() : null;
    }

    private Optional<Obs> getObservationByConceptUuid(String conceptUuid, Set<Obs> observations) {
        return observations.stream().filter(o -> o.getConcept().getUuid().equals(conceptUuid)).findAny();
    }
}
