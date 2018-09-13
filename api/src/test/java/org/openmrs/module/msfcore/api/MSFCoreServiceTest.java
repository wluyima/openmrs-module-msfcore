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

import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.MSFCoreConfig;
import org.openmrs.module.msfcore.dhis2.PatientTrackableAttributes;
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
        String url = "https://localhost/5000/tracker";
        Context.getAdministrationService().setGlobalProperty(MSFCoreConfig.GP_OPENHIM_TRACKER_URL, url);
        assertThat(Context.getAdministrationService().getGlobalProperty(MSFCoreConfig.GP_OPENHIM_TRACKER_URL), is(url));
        assertThat(Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_OPENHIM_USERNAME), is("user"));
        assertThat(Context.getRuntimeProperties().getProperty(MSFCoreConfig.PROP_OPENHIM_PASSWORD), is("pass"));
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
    public void getDHISMappings_shouldLoadPropertiesWell() {
        Properties props = Context.getService(MSFCoreService.class).getDHISMappings();
        assertThat(props.getProperty(PatientTrackableAttributes.AGE_IN_YEARS.name()), is("ad4FRjjhWlG"));
        assertThat(props.getProperty(PatientTrackableAttributes.INITIALS_OF_CASE_MANAGER.name()), is("zurgr3r74AF"));
        assertThat(props.getProperty(PatientTrackableAttributes.SEX.name()), is("BIrTkunTimU"));
    }

}
