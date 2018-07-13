package org.openmrs.module.msfcore.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class MSFCoreServiceTest extends BaseModuleContextSensitiveTest {
	
	@Test
	public void getAllConceptAnswers_shouldReturnConceptsFromAnswers() {
		Assert.assertNotNull(Context.getService(MSFCoreService.class));
		List<Concept> answers = Context.getService(MSFCoreService.class).getAllConceptAnswers(
		    Context.getConceptService().getConcept(4));
		Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(5)));
		Assert.assertTrue(answers.contains(Context.getConceptService().getConcept(6)));
	}
}
