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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.AllergenType;
import org.openmrs.Allergies;
import org.openmrs.CareSetting.CareSettingType;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class FormActionServiceTest extends BaseModuleContextSensitiveTest {

    public void saveTestOrders_shouldCreateTestOrders() throws Exception {
        executeDataSet("MSFCoreService.xml");

        Encounter encounter = Context.getEncounterService()
                .getEncounterByUuid("27126dd0-04a4-4f3b-91ae-66c4907f6e5f");
        FormActionService service = Context.getService(FormActionService.class);

		// Order 1 is linked to a voided obs so it should be voided
		Assert.assertFalse(Context.getOrderService().getOrder(1).getVoided());
		service.saveTestOrders(encounter);
		Assert.assertTrue(Context.getOrderService().getOrder(1).getVoided());

        Encounter loadedEncounter = Context.getEncounterService().getEncounter(encounter.getId());
        List<Obs> obs = new ArrayList<>(loadedEncounter.getObs());
		Assert.assertEquals(3, obs.size());
		Assert.assertNotNull(obs.get(0).getOrder().getOrderId());
		Assert.assertEquals(obs.get(0).getConcept(), obs.get(0).getOrder().getConcept());
		Assert.assertEquals(obs.get(1).getConcept(), obs.get(1).getOrder().getConcept());
		Assert.assertEquals(obs.get(2).getConcept(), obs.get(2).getOrder().getConcept());
		Assert.assertEquals(CareSettingType.OUTPATIENT.name(),
				obs.get(0).getOrder().getCareSetting().getName().toUpperCase());
	}

    @Test
    public void saveDrugOrders_shouldCreateDrugOrders() throws Exception {
        executeDataSet("MSFCoreService.xml");
        Encounter encounter = Context.getEncounterService().getEncounterByUuid("a131a0c9-e550-47da-a8d1-0eaa269cb3gh");
        Context.getService(FormActionService.class).saveDrugOrders(encounter);
        Assert.assertEquals(1, encounter.getOrders().size());
        DrugOrder drugOrder = (DrugOrder) encounter.getOrders().iterator().next();
        Assert.assertNotNull(drugOrder.getId());
        Assert.assertEquals(36, drugOrder.getDrug().getId().intValue());
        Assert.assertEquals(14D, drugOrder.getQuantity().doubleValue(), 0);
        Assert.assertEquals("Take after meals", drugOrder.getDosingInstructions());
        Assert.assertEquals("Take after meals", drugOrder.getInstructions());
    }

    @Test
    public void saveDrugOrders_shouldVoidOrderWhenEntryIsRemoved() throws Exception {
        executeDataSet("saveDrugOrders_shouldVoidOrderWhenEntryIsRemoved.xml");
        Encounter encounter = Context.getEncounterService()
                .getEncounterByUuid("a131a0c9-e550-47da-a8d1-0eaa269cb3gh");

        // initially we should have 2 non voided orders for both concepts
        List<Order> activeOrders = encounter.getOrders().stream().filter(o -> !o.getVoided())
                .collect(Collectors.toList());
        Assert.assertEquals(2, activeOrders.size());
        Assert.assertTrue(activeOrders.stream().filter(o -> o.getConcept().getId().equals(1000021))
                .findAny().isPresent());
        Assert.assertTrue(activeOrders.stream().filter(o -> o.getConcept().getId().equals(924))
                .findAny().isPresent());

        Context.getService(FormActionService.class).saveDrugOrders(encounter);

        // then just one active order for concept 1000021
        encounter = Context.getEncounterService()
                .getEncounterByUuid("a131a0c9-e550-47da-a8d1-0eaa269cb3gh");
        activeOrders = encounter.getOrders().stream().filter(o -> !o.getVoided())
                .collect(Collectors.toList());
        Assert.assertEquals(1, activeOrders.size());
        Assert.assertTrue(activeOrders.stream().filter(o -> o.getConcept().getId().equals(1000021))
                .findAny().isPresent());
    }

    @Test
    public void saveAllergies_shouldCreateAllergies() throws Exception {
        executeDataSet("MSFCoreService.xml");

        Encounter encounter = Context.getEncounterService()
                .getEncounterByUuid("992b696b-529c-4ded-b7f7-4876fb4f2936");
        FormActionService service = Context.getService(FormActionService.class);

        Allergies allergies = service.saveAllergies(encounter);
        Assert.assertNotNull(allergies);
        Assert.assertEquals(3, allergies.size());
        allergies.forEach(a -> Assert.assertTrue(a.getReactions().size() > 0));
        allergies.forEach(a -> Assert.assertTrue(
                Arrays.asList(AllergenType.DRUG, AllergenType.FOOD, AllergenType.ENVIRONMENT)
                        .contains(a.getAllergenType())));
        allergies.forEach(a -> a.getReactions().forEach(
                r -> Assert.assertTrue(Arrays.asList(120148, 143264, 139581, 121629, 121677)
                        .contains(r.getReaction().getConceptId()))));
        allergies.forEach(a -> Assert.assertTrue(Arrays.asList(162543, 162538, 71617)
                .contains(a.getAllergen().getCodedAllergen().getId())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveAllergies_shouldThrowsExceptionSavingAllergies() throws Exception {
        executeDataSet("MSFCoreService.xml");

        Encounter encounter = Context.getEncounterService().getEncounterByUuid("84fcb082-e670-11e8-8661-0b65d193bf03");
        FormActionService service = Context.getService(FormActionService.class);
        service.saveAllergies(encounter);
    }

}
