package org.openmrs.module.msfcore.formaction.handler;

import org.openmrs.module.htmlformentry.FormEntrySession;

/**
 * Form Submission handlers for the MSF project should implement this class.
 * 
 * @author Edrisse
 */
public interface FormAction {

    void apply(String operation, FormEntrySession session);

}
