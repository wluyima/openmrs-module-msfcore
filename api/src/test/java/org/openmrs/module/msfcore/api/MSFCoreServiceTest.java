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

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.DropDownFieldOption;
import org.openmrs.module.msfcore.api.dao.MSFCoreDao;
import org.openmrs.module.msfcore.api.impl.MSFCoreServiceImpl;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This is a unit test, which verifies logic in MSFCoreService.
 */
public class MSFCoreServiceTest extends BaseModuleContextSensitiveTest {

  @InjectMocks
  MSFCoreServiceImpl msfCoreServiceImpl;

  @Mock
  MSFCoreDao dao;

  @Before
  public void setupMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Autowired
  MSFCoreService msfCoreService;

  @Test
  public void serviceNotNull() {
    assertNotNull(msfCoreServiceImpl);
    assertNotNull(msfCoreService);
    assertNotNull(Context.getService(MSFCoreService.class));
  }

  @Test
  public void getAllConceptAnswers_shouldReturnConceptsFromAnswers() {
    Assert.assertNotNull(Context.getService(MSFCoreService.class));
    List<Concept> answers = Context.getService(MSFCoreService.class).getAllConceptAnswers(
        Context.getConceptService().getConcept(4));
    Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(5)));
    Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(6)));
  }

  @Test
  public void getAllConceptAnswerNames_shouldReturnRightDropDownOptions() {
    Assert.assertNotNull(Context.getService(MSFCoreService.class));
    Concept concept = Context.getConceptService().getConcept(4);
    List<DropDownFieldOption> options = Context.getService(MSFCoreService.class)
        .getAllConceptAnswerNames(concept.getUuid());
    Assert.assertEquals("CIVIL STATUS", concept.getName().getName());
    Assert.assertEquals("5", options.get(0).getValue());
    Assert.assertEquals("SINGLE", options.get(0).getLabel());
    Assert.assertEquals("6", options.get(1).getValue());
    Assert.assertEquals("MARRIED", options.get(1).getLabel());
  }
}
