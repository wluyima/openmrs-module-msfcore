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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.dhis2.PatientTrackableAttributes;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * This is a unit test, which verifies logic in {@link DHISService}.
 */
public class DHISServiceTest extends BaseModuleContextSensitiveTest {

    @Test
    public void serviceInitilisation() {
        assertNotNull(Context.getService(DHISService.class));
    }

    @Test
    public void getDHISMappings_shouldLoadPropertiesWell() {
        Properties props = Context.getService(DHISService.class).getDHISMappings();
        assertThat(props.getProperty(PatientTrackableAttributes.AGE_IN_YEARS.name()), is("ad4FRjjhWlG"));
        assertThat(props.getProperty(PatientTrackableAttributes.INITIALS_OF_CASE_MANAGER.name()), is("zurgr3r74AF"));
        assertThat(props.getProperty(PatientTrackableAttributes.SEX.name()), is("BIrTkunTimU"));
    }
}
