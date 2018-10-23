package org.openmrs.module.msfcore.web.controller;

import java.io.BufferedReader;
import java.io.IOException;

import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.Retrievable;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BaseDataResource extends DataDelegatingCrudResource implements Retrievable {

    @Override
    public Object newDelegate() {
        return null;
    }

    @Override
    public Object save(Object delegate) {
        return null;
    }

    @Override
    public Object getByUniqueId(String uniqueId) {
        return null;
    }

    @Override
    protected void delete(Object delegate, String reason, RequestContext context) throws ResponseException {
    }

    @Override
    public void purge(Object delegate, RequestContext context) throws ResponseException {
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        return null;
    }

    String getRequestBody(RequestContext context) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = context.getRequest().getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}