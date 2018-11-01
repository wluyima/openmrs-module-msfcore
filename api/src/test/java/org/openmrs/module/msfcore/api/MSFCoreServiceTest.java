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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.CareSetting.CareSettingType;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * This is a unit test, which verifies logic in MSFCoreService.
 */
public class MSFCoreServiceTest extends BaseModuleContextSensitiveTest {

    @Override
    public Properties getRuntimeProperties() {
        Properties props = super.getRuntimeProperties();
        props.put(MSFCoreConfig.PROP_OPENHIM_USERNAME, "user");
        props.put(MSFCoreConfig.PROP_OPENHIM_PASSWORD, "pass");
        props.put(MSFCoreConfig.PROP_DHIS2_USERNAME, "admin");
        props.put(MSFCoreConfig.PROP_DHIS2_PASSWORD, "password");
        return props;
    }

    @Test
    public void openHimPropertiesShouldGetInitialised() {
        String url = "http://localhost/5001/tracker";
        Context.getAdministrationService().setGlobalProperty(MSFCoreConfig.GP_OPENHIM_TRACKER_URL, url);
        assertThat(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_OPENHIM_TRACKER_URL), is(url));
        assertThat(Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_DHIS2_USERNAME), is("admin"));
        assertThat(Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_DHIS2_PASSWORD), is("password"));
    }

    @Test
    public void getAllConceptAnswers_shouldReturnConceptsFromAnswers() {
        Assert.assertNotNull(Context.getService(MSFCoreService.class));
        List<Concept> answers = Context.getService(MSFCoreService.class).getAllConceptAnswers(Context.getConceptService().getConcept(4));
        Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(5)));
        Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(6)));
    }

    @Test
    public void getAllConceptAnswerNames_shouldReturnRightDropDownOptions() {
        Assert.assertNotNull(Context.getService(MSFCoreService.class));
        Concept concept = Context.getConceptService().getConcept(4);
        List<DropDownFieldOption> options = Context.getService(MSFCoreService.class).getAllConceptAnswerNames(concept.getUuid());
        Assert.assertEquals("CIVIL STATUS", concept.getName().getName());
        Assert.assertEquals("5", options.get(0).getValue());
        Assert.assertEquals("SINGLE", options.get(0).getLabel());
        Assert.assertEquals("6", options.get(1).getValue());
        Assert.assertEquals("MARRIED", options.get(1).getLabel());
    }

    @Test
    public void getOrders_shouldDefaultPaginationToFirst25ResultsWithNoResults() {
        Pagination pagination = Pagination.builder().build();
        List<Order> orders = Context.getService(MSFCoreService.class).getOrders(Context.getPatientService().getPatient(6),
                        Context.getOrderService().getOrderType(1), null, pagination);
        Assert.assertEquals(Integer.valueOf(0), pagination.getFromResultNumber());
        Assert.assertEquals(Integer.valueOf(25), pagination.getToResultNumber());
        Assert.assertEquals(Integer.valueOf(0), pagination.getTotalResultNumber());
        Assert.assertEquals(0, orders.size());
    }

    @Test
    public void getOrders_shouldDefaultPaginationToFirst25Results() {
        Pagination pagination = Pagination.builder().build();
        List<Order> orders = Context.getService(MSFCoreService.class).getOrders(Context.getPatientService().getPatient(7),
                        Context.getOrderService().getOrderType(1), null, pagination);
        Assert.assertEquals(Integer.valueOf(0), pagination.getFromResultNumber());
        Assert.assertEquals(Integer.valueOf(25), pagination.getToResultNumber());
        Assert.assertEquals(Integer.valueOf(2), pagination.getTotalResultNumber());
        Assert.assertEquals(2, orders.size());
    }

    @Test
    public void getOrders_shouldUseFromAndToAppropriatelyOrderingByCreationDateDescAndSetPaginationWell() {
        Pagination pagination = Pagination.builder().toResultNumber(1).build();
        List<Order> orders = Context.getService(MSFCoreService.class).getOrders(Context.getPatientService().getPatient(7),
                        Context.getOrderService().getOrderType(1), null, pagination);
        Assert.assertEquals(Integer.valueOf(0), pagination.getFromResultNumber());
        Assert.assertEquals(Integer.valueOf(1), pagination.getToResultNumber());
        Assert.assertEquals(Integer.valueOf(2), pagination.getTotalResultNumber());
        Assert.assertEquals(1, orders.size());
        Assert.assertEquals("111", orders.get(0).getOrderNumber());

        pagination = Pagination.builder().fromResultNumber(1).toResultNumber(2).build();
        orders = Context.getService(MSFCoreService.class).getOrders(Context.getPatientService().getPatient(7),
                        Context.getOrderService().getOrderType(1), null, pagination);
        Assert.assertEquals(Integer.valueOf(1), pagination.getFromResultNumber());
        Assert.assertEquals(Integer.valueOf(2), pagination.getToResultNumber());
        Assert.assertEquals(Integer.valueOf(2), pagination.getTotalResultNumber());
        Assert.assertEquals(1, orders.size());
        Assert.assertEquals("1", orders.get(0).getOrderNumber());
    }

    public void saveTestOrders_shouldCreateTestOrders() throws Exception {
        executeDataSet("MSFCoreService.xml");

        Encounter encounter = Context.getEncounterService().getEncounterByUuid("27126dd0-04a4-4f3b-91ae-66c4907f6e5f");
        MSFCoreService service = Context.getService(MSFCoreService.class);

        // Order 1 is linked to a voided obs so it should be voided
        Assert.assertFalse(Context.getOrderService().getOrder(1).getVoided());
        service.saveTestOrders(encounter);
        Assert.assertTrue(Context.getOrderService().getOrder(1).getVoided());

        Encounter loadedEncounter = Context.getEncounterService().getEncounter(encounter.getId());
        List<Obs> obs = new ArrayList<Obs>(loadedEncounter.getObs());
        Assert.assertEquals(3, obs.size());
        Assert.assertNotNull(obs.get(0).getOrder().getOrderId());
        Assert.assertEquals(obs.get(0).getConcept(), obs.get(0).getOrder().getConcept());
        Assert.assertEquals(obs.get(1).getConcept(), obs.get(1).getOrder().getConcept());
        Assert.assertEquals(obs.get(2).getConcept(), obs.get(2).getOrder().getConcept());
        Assert.assertEquals(CareSettingType.OUTPATIENT.name(), obs.get(0).getOrder().getCareSetting().getName().toUpperCase());
    }

    @Test
    public void getObservationsByPatientAndOrder() {
        executeDataSet("MSFCoreService.xml");

        Assert.assertEquals("207d0cc1-tt20-4bd6-8a0f-06b4ae1e53e0", Context.getService(MSFCoreService.class)
                        .getObservationsByPersonAndOrder(Context.getPersonService().getPerson(7), Context.getOrderService().getOrder(1))
                        .get(0).getUuid());
    }
}
