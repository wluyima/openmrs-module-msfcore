package org.openmrs.module.msfcore.formaction.handler;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.context.Context;
import org.openmrs.module.htmlformentry.CustomFormSubmissionAction;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * When a user clicks a submit button on a form, this is the class that handles the action and
 * applies all handlers that implement the {@link FormAction} interface and are registred
 * as spring beans.
 */
@Component
public class HtmlFormPostSubmissionAction implements CustomFormSubmissionAction {

    private static final String MSF_OPERATION_PARAMETER_NAME = "msf.operation";

    @Override
    public void applyAction(FormEntrySession formEntrySession) {
        String operation = getRequest().getParameter(MSF_OPERATION_PARAMETER_NAME);
        for (FormAction action : Context.getRegisteredComponents(FormAction.class)) {
            action.apply(operation, formEntrySession);
        }
    }

    private HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        } else {
            throw new IllegalStateException("This really should not happen");
        }
    }

}
