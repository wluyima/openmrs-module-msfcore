package org.openmrs.module.msfcore.submissionaction.handler;

import org.openmrs.module.htmlformentry.FormEntrySession;

/**
 * Form Submission handlers for the MSF project should implement this class.
 * 
 * @author Edrisse
 */
public interface MsfSubmissionAction {
	
	void apply(String operation, FormEntrySession session);
	
}
