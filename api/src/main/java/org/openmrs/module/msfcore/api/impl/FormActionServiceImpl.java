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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.openmrs.Allergies;
import org.openmrs.CareSetting;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.OrderFrequency;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.TestOrder;
import org.openmrs.api.EncounterService;
import org.openmrs.api.OrderService;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.api.FormActionService;
import org.openmrs.module.msfcore.api.dao.MSFCoreDao;
import org.openmrs.module.msfcore.api.util.AllergyUtils;

public class FormActionServiceImpl extends BaseOpenmrsService implements FormActionService {

    private static final String ORDER_VOID_REASON = "Obs was voided";
    private static final String LAB_ORDER_SET_UUID = "23281464-74d8-47d9-9a39-7a1f1d7caa49";

    @SuppressWarnings("serial")
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
    public void saveTestOrders(Encounter encounter) {
        OrderService orderService = Context.getOrderService();
        EncounterService encounterService = Context.getEncounterService();
        OrderType orderType = orderService.getOrderTypeByUuid(OrderType.TEST_ORDER_TYPE_UUID);
        Provider provider = encounter.getEncounterProviders().iterator().next().getProvider();
        CareSetting careSetting = orderService.getCareSettingByName(CareSetting.CareSettingType.OUTPATIENT.name());
        List<Obs> allObs = new ArrayList<Obs>(encounter.getAllObs(true));

        List<Order> orders = encounter.getOrdersWithoutOrderGroups();
        Concept labOrdersSetConcept = Context.getConceptService().getConceptByUuid(LAB_ORDER_SET_UUID);

        for (Obs obs : allObs) {

            // check for test order observations only (exludes results)
            if (!labOrdersSetConcept.getSetMembers().contains(obs.getConcept())) {
                break;
            }

            boolean isOrderFound = false;
            for (Order order : orders) {

                // look for existing order for that obs
                if (obs.getConcept().equals(order.getConcept())) {
                    isOrderFound = true;

                    // ignore fulfilled orders
                    if (isOrderFulfilled(encounter.getPatient(), order)) {
                        break;
                    }

                    // void order if obs is voided
                    if (obs.getVoided().equals(true)) {
                        orderService.voidOrder(order, ORDER_VOID_REASON);
                        break;
                    }
                }
            }

            // if not orders were found, create an order for this obs
            if (!isOrderFound && obs.getVoided().equals(false)) {
                TestOrder order = createTestOrder(encounter, orderType, provider, careSetting, obs.getConcept());
                orderService.saveOrder(order, null);
            }
        }

        encounterService.saveEncounter(encounter);
    }

    private boolean isOrderFulfilled(Patient patient, Order order) {
        return dao.getObservationsByPersonAndOrderAndConcept(patient, order, null).size() > 0;
    }

    private TestOrder createTestOrder(Encounter encounter, OrderType orderType, Provider provider, CareSetting careSetting, Concept concept) {
        TestOrder order = new TestOrder();
        order.setOrderType(orderType);
        order.setConcept(concept);
        order.setPatient(encounter.getPatient());
        order.setEncounter(encounter);
        order.setOrderer(provider);
        order.setCareSetting(careSetting);
        encounter.addOrder(order);
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
                final DrugOrder order = this.createDrugOrder(encounter, orderType, provider, careSetting, observations, drug);
                updateEncounter(encounter, group, observations, order);
            }
            if (group.getOrder() != null) {
                group.getOrder().setVoided(true);
            }
        }
        encounterService.saveEncounter(encounter);
    }

    private void updateEncounter(Encounter encounter, final Obs group, final Set<Obs> observations, final DrugOrder order) {
		encounter.addOrder(order);
		final Obs newGroup = Obs.newInstance(group);
		newGroup.setOrder(order);
		group.setVoided(true);
		observations.forEach(o -> o.setVoided(true));
		encounter.addObs(newGroup);
	}
    @Override
    public Allergies saveAllergies(Encounter encounter) {
        return Context.getRegisteredComponents(AllergyUtils.class).get(0).saveAllergies(encounter);
    }

    private DrugOrder createDrugOrder(Encounter encounter, OrderType orderType, Provider provider, CareSetting careSetting,
                    Set<Obs> observations, Drug drug) {
        final DrugOrder order = new DrugOrder();
        order.setOrderType(orderType);
        order.setPatient(encounter.getPatient());
        order.setEncounter(encounter);
        order.setOrderer(provider);
        order.setCareSetting(careSetting);
        order.setDrug(drug);
        setOrderFields(observations, order);
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
