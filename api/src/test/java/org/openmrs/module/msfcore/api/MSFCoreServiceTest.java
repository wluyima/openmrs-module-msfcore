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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.AllergenType;
import org.openmrs.Allergies;
import org.openmrs.CareSetting.CareSettingType;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.ProgramWorkflow;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * This is a unit test, which verifies logic in MSFCoreService.
 */
public class MSFCoreServiceTest extends BaseModuleContextSensitiveTest {

    @Override
    public Properties getRuntimeProperties() {
        final Properties props = super.getRuntimeProperties();
        props.put(MSFCoreConfig.PROP_OPENHIM_USERNAME, "user");
        props.put(MSFCoreConfig.PROP_OPENHIM_PASSWORD, "pass");
        props.put(MSFCoreConfig.PROP_DHIS2_USERNAME, "admin");
        props.put(MSFCoreConfig.PROP_DHIS2_PASSWORD, "password");
        return props;
    }

    @Test
    public void openHimPropertiesShouldGetInitialised() {
        final String url = "http://localhost/5001/tracker";
        Context.getAdministrationService().setGlobalProperty(MSFCoreConfig.GP_OPENHIM_TRACKER_URL, url);
        assertThat(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_OPENHIM_TRACKER_URL), is(url));
        assertThat(Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_DHIS2_USERNAME), is("admin"));
        assertThat(Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_DHIS2_PASSWORD), is("password"));
    }

    @Test
    public void getAllConceptAnswers_shouldReturnConceptsFromAnswers() {
        Assert.assertNotNull(Context.getService(MSFCoreService.class));
        final List<Concept> answers = Context.getService(MSFCoreService.class).getAllConceptAnswers(
                        Context.getConceptService().getConcept(4));
        Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(5)));
        Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(6)));
    }

    @Test
    public void getAllConceptAnswerNames_shouldReturnRightDropDownOptions() {
        Assert.assertNotNull(Context.getService(MSFCoreService.class));
        final Concept concept = Context.getConceptService().getConcept(4);
        final List<DropDownFieldOption> options = Context.getService(MSFCoreService.class).getAllConceptAnswerNames(concept.getUuid());
        Assert.assertEquals("CIVIL STATUS", concept.getName().getName());
        Assert.assertEquals("5", options.get(0).getValue());
        Assert.assertEquals("SINGLE", options.get(0).getLabel());
        Assert.assertEquals("6", options.get(1).getValue());
        Assert.assertEquals("MARRIED", options.get(1).getLabel());
    }

    @Test
    public void generatePatientProgram_returnNullIfPatientProgramIsNull() {
        Assert.assertNull(this.generatePatientProgram(true, null, null));
        Assert.assertNull(this.generatePatientProgram(false, null, null));
    }

    @Test
    public void generatePatientProgram_returnNullIfStagesAreNull() {
        Assert.assertNull(this.generatePatientProgram(true, new PatientProgram(14), null));
        Assert.assertNull(this.generatePatientProgram(false, new PatientProgram(14), null));
    }

    @Test
    public void generatePatientProgram_testEnrollement() {
        final ProgramWorkflowState enrollStage = new ProgramWorkflowState();
        enrollStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_ENROLL);
        final ProgramWorkflowState baselineStage = new ProgramWorkflowState();
        baselineStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_BASELINE_CONSULTATION);
        final ProgramWorkflowState followUpStage = new ProgramWorkflowState();
        followUpStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_FOLLOWUP_CONSULTATION);
        final ProgramWorkflowState exitStage = new ProgramWorkflowState();
        exitStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT);
        final PatientProgram pp = new PatientProgram();

        PatientProgram patientProgram = this.generatePatientProgram(true, pp, null, enrollStage, baselineStage, followUpStage, exitStage);

        assertThat(patientProgram.getStates().size(), is(1));
        assertThat(patientProgram.getStates().iterator().next().getState(), is(enrollStage));

        // enroll and exit patient from program if dateCompleted is set
        pp.setDateCompleted(new Date());
        pp.getStates().clear();
        patientProgram = this.generatePatientProgram(true, pp, null, enrollStage, baselineStage, followUpStage, exitStage);
        assertThat(patientProgram.getStates().size(), is(2));
        assertThat(this.stagesContainState(patientProgram.getStates(), enrollStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), exitStage), is(true));
    }

    @Test
    public void generatePatientProgram_testFormSubmissionWithoutBaselineOrFormOrPatientProgram() {
        final ProgramWorkflowState enrollStage = new ProgramWorkflowState();
        enrollStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_ENROLL);
        final ProgramWorkflowState baselineStage = new ProgramWorkflowState();
        baselineStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_BASELINE_CONSULTATION);
        final ProgramWorkflowState followUpStage = new ProgramWorkflowState();
        followUpStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_FOLLOWUP_CONSULTATION);
        final ProgramWorkflowState exitStage = new ProgramWorkflowState();
        exitStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT);
        final Encounter encounter = new Encounter(3);
        encounter.setForm(new Form());
        final PatientProgram patientProgram = this.generatePatientProgram(false, null, null, enrollStage, baselineStage, followUpStage,
                        exitStage);
        final PatientProgram patientProgram1 = this.generatePatientProgram(false, null, encounter, enrollStage, baselineStage,
                        followUpStage, exitStage);
        final PatientProgram patientProgram2 = this.generatePatientProgram(false, new PatientProgram(4), null, enrollStage, baselineStage,
                        followUpStage, exitStage);

        Assert.assertNull(patientProgram);
        Assert.assertNull(patientProgram1);
        Assert.assertNull(patientProgram2);
    }

    private EncounterType newEncounterType(String uuid) {
        final EncounterType encType = new EncounterType();
        encType.setUuid(uuid);
        return encType;
    }

    @Test
    public void generatePatientProgram_testFormsAfterEnrollement() {
        final ProgramWorkflowState enrollStage = new ProgramWorkflowState();
        enrollStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_ENROLL);
        final ProgramWorkflowState baselineStage = new ProgramWorkflowState();
        baselineStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_BASELINE_CONSULTATION);
        baselineStage.setInitial(true);
        final ProgramWorkflowState followUpStage = new ProgramWorkflowState();
        followUpStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_FOLLOWUP_CONSULTATION);
        final ProgramWorkflowState exitStage = new ProgramWorkflowState();
        exitStage.setUuid(MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT);
        exitStage.setTerminal(true);
        final Encounter encounter = new Encounter(3);

        // starting with any other stage besides enrollment should return null
        encounter.setEncounterType(this.newEncounterType(MSFCoreConfig.ENCOUNTER_TYPE_NCD_BASELINE_UUID));
        PatientProgram patientProgram = this.generatePatientProgram(false, new PatientProgram(), encounter, enrollStage, baselineStage,
                        followUpStage, exitStage);
        Assert.assertNull(patientProgram);

        patientProgram = this
                        .generatePatientProgram(true, new PatientProgram(), null, enrollStage, baselineStage, followUpStage, exitStage);

        assertThat(patientProgram.getStates().size(), is(1));
        assertThat(patientProgram.getStates().iterator().next().getState(), is(enrollStage));
        assertThat(this.stagesContainState(patientProgram.getStates(), baselineStage), is(false));

        encounter.setEncounterType(this.newEncounterType(MSFCoreConfig.ENCOUNTER_TYPE_NCD_BASELINE_UUID));
        patientProgram = this
                        .generatePatientProgram(false, patientProgram, encounter, enrollStage, baselineStage, followUpStage, exitStage);
        assertThat(patientProgram.getStates().size(), is(2));
        assertThat(this.stagesContainState(patientProgram.getStates(), enrollStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), baselineStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), followUpStage), is(false));

        encounter.setEncounterType(this.newEncounterType(MSFCoreConfig.ENCOUNTER_TYPE_NCD_FOLLOWUP_UUID));
        patientProgram = this
                        .generatePatientProgram(false, patientProgram, encounter, enrollStage, baselineStage, followUpStage, exitStage);
        assertThat(patientProgram.getStates().size(), is(3));
        assertThat(this.stagesContainState(patientProgram.getStates(), enrollStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), baselineStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), followUpStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), exitStage), is(false));

        encounter.setEncounterType(this.newEncounterType(MSFCoreConfig.ENCOUNTER_TYPE_NCD_EXIT_UUID));
        patientProgram = this
                        .generatePatientProgram(false, patientProgram, encounter, enrollStage, baselineStage, followUpStage, exitStage);
        assertThat(patientProgram.getStates().size(), is(4));
        assertThat(this.stagesContainState(patientProgram.getStates(), enrollStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), baselineStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), followUpStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), exitStage), is(true));

        // exiting should set outcome when obs exists
        encounter.setEncounterType(this.newEncounterType(MSFCoreConfig.ENCOUNTER_TYPE_NCD_EXIT_UUID));
        final Obs outcome = new Obs();
        final Concept qn = new Concept();
        qn.setUuid(MSFCoreConfig.NCD_PROGRAM_OUTCOMES_CONCEPT_UUID);
        final Concept an = new Concept();
        an.setConceptId(1000);
        outcome.setConcept(qn);
        outcome.setValueCoded(an);
        encounter.addObs(outcome);
        patientProgram = this
                        .generatePatientProgram(false, patientProgram, encounter, enrollStage, baselineStage, followUpStage, exitStage);
        assertThat(patientProgram.getStates().size(), is(5));// exit is repeated
        assertThat(this.stagesContainState(patientProgram.getStates(), baselineStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), followUpStage), is(true));
        assertThat(this.stagesContainState(patientProgram.getStates(), exitStage), is(true));
        assertThat(patientProgram.getOutcome().getConceptId(), is(1000));
    }

    private boolean stagesContainState(final Set<PatientState> stages, final ProgramWorkflowState state) {
        for (final PatientState stage : stages) {
            if (stage.getState().equals(state)) {
                return true;
            }
        }
        return false;
    }

    private PatientProgram generatePatientProgram(boolean enrollment, PatientProgram patientProgram,
			Encounter encounter, ProgramWorkflowState... states) {
		final Map<String, ProgramWorkflowState> stages = new HashMap<>();

		// hack to avoid invalid property setting issues
		if (states.length > 0) {
			for (final ProgramWorkflowState stage : states) {
				stage.setConcept(Context.getConceptService().getConcept(22));
				stage.setProgramWorkflow(new ProgramWorkflow());
				if (stage.getInitial() == null) {
					stage.setInitial(false);
				}
				if (stage.getTerminal() == null) {
					stage.setTerminal(false);
				}
			}
			stages.put(MSFCoreConfig.WORKFLOW_STATE_UUID_ENROLL, states[0]);
			stages.put(MSFCoreConfig.WORKFLOW_STATE_UUID_BASELINE_CONSULTATION, states[1]);
			stages.put(MSFCoreConfig.WORKFLOW_STATE_UUID_FOLLOWUP_CONSULTATION, states[2]);
			stages.put(MSFCoreConfig.WORKFLOW_STATE_UUID_EXIT, states[3]);
		}
		return Context.getService(MSFCoreService.class).generatePatientProgram(enrollment, stages, patientProgram,
				encounter);
	}
    @Test
	public void saveTestOrders_shouldCreateTestOrders() throws Exception {
		this.executeDataSet("MSFCoreService.xml");

		final Encounter encounter = Context.getEncounterService().getEncounterByUuid("27126dd0-04a4-4f3b-91ae-66c4907f6e5f");
		final MSFCoreService service = Context.getService(MSFCoreService.class);

		// Order 1 is linked to a voided obs so it should be voided
		Assert.assertFalse(Context.getOrderService().getOrder(1).getVoided());
		service.saveTestOrders(encounter);
		Assert.assertTrue(Context.getOrderService().getOrder(1).getVoided());

		final Encounter loadedEncounter = Context.getEncounterService().getEncounter(encounter.getId());
		final List<Obs> obs = new ArrayList<>(loadedEncounter.getObs());
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
        this.executeDataSet("MSFCoreService.xml");
        final Encounter encounter = Context.getEncounterService().getEncounterByUuid("a131a0c9-e550-47da-a8d1-0eaa269cb3gh");
        Context.getService(MSFCoreService.class).saveDrugOrders(encounter);
        Assert.assertEquals(1, encounter.getOrders().size());
        final DrugOrder drugOrder = (DrugOrder) encounter.getOrders().iterator().next();
        Assert.assertNotNull(drugOrder.getId());
        Assert.assertEquals(36, drugOrder.getDrug().getId().intValue());
        Assert.assertEquals(14D, drugOrder.getQuantity().doubleValue(), 0);
        Assert.assertEquals("Take after meals", drugOrder.getDosingInstructions());
        Assert.assertEquals("Take after meals", drugOrder.getInstructions());
    }

    @Test
	public void saveDrugOrders_shouldVoidOrderWhenEntryIsRemoved() throws Exception {
		this.executeDataSet("saveDrugOrders_shouldVoidOrderWhenEntryIsRemoved.xml");
		Encounter encounter = Context.getEncounterService().getEncounterByUuid("a131a0c9-e550-47da-a8d1-0eaa269cb3gh");

		// initially we should have 2 non voided orders for both concepts
		List<Order> activeOrders = encounter.getOrders().stream().filter(o -> !o.getVoided())
				.collect(Collectors.toList());
		Assert.assertEquals(2, activeOrders.size());
		Assert.assertTrue(
				activeOrders.stream().filter(o -> o.getConcept().getId().equals(1000021)).findAny().isPresent());
		Assert.assertTrue(activeOrders.stream().filter(o -> o.getConcept().getId().equals(924)).findAny().isPresent());

		Context.getService(MSFCoreService.class).saveDrugOrders(encounter);

		// then just one active order for concept 1000021
		encounter = Context.getEncounterService().getEncounterByUuid("a131a0c9-e550-47da-a8d1-0eaa269cb3gh");
		activeOrders = encounter.getOrders().stream().filter(o -> !o.getVoided()).collect(Collectors.toList());
		Assert.assertEquals(1, activeOrders.size());
		Assert.assertTrue(
				activeOrders.stream().filter(o -> o.getConcept().getId().equals(1000021)).findAny().isPresent());
	}
    @Test
	public void saveAllergies_shouldCreateAllergies() throws Exception {
		this.executeDataSet("MSFCoreService.xml");

		final Encounter encounter = Context.getEncounterService()
				.getEncounterByUuid("992b696b-529c-4ded-b7f7-4876fb4f2936");
		final MSFCoreService service = Context.getService(MSFCoreService.class);

		final Allergies allergies = service.saveAllergies(encounter);
		Assert.assertNotNull(allergies);
		Assert.assertEquals(3, allergies.size());
		allergies.forEach(a -> Assert.assertTrue(a.getReactions().size() > 0));
		allergies.forEach(a -> Assert.assertTrue(Arrays
				.asList(AllergenType.DRUG, AllergenType.FOOD, AllergenType.ENVIRONMENT).contains(a.getAllergenType())));
		allergies.forEach(a -> a.getReactions().forEach(r -> Assert.assertTrue(
				Arrays.asList(120148, 143264, 139581, 121629, 121677).contains(r.getReaction().getConceptId()))));

	}
    @Test(expected = IllegalArgumentException.class)
    public void saveAllergies_shouldThrowsExceptionSavingAllergies() throws Exception {
        this.executeDataSet("MSFCoreService.xml");

        final Encounter encounter = Context.getEncounterService().getEncounterByUuid("84fcb082-e670-11e8-8661-0b65d193bf03");
        final MSFCoreService service = Context.getService(MSFCoreService.class);
        service.saveAllergies(encounter);
    }
}
