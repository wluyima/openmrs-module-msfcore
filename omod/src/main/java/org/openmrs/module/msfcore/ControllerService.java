package org.openmrs.module.msfcore;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.MSFCoreService;

public class ControllerService {
	
	public static List<DropDownFieldOption> getAllConceptAnswerNames(String uuid) {
		List<DropDownFieldOption> answerNames = new ArrayList<DropDownFieldOption>();
		for (Concept answer : Context.getService(MSFCoreService.class).getAllConceptAnswers(
		    Context.getConceptService().getConceptByUuid(uuid))) {
			answerNames.add(new DropDownFieldOption(String.valueOf(answer.getId()), answer.getName().getName()));
		}
		return answerNames;
	}
}
