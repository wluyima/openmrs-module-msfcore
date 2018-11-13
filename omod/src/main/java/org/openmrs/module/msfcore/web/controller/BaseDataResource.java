package org.openmrs.module.msfcore.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.module.msfcore.Pagination.PaginationBuilder;
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

    Patient getPatientFromId(String patientId) {
        Patient patient;
        try {
            patient = Context.getPatientService().getPatient(Integer.valueOf(patientId));
        } catch (NumberFormatException e) {
            patient = Context.getPatientService().getPatientByUuid(patientId);
        }
        return patient;
    }

    PaginationBuilder buildPaginationFromContext(RequestContext context) {
        PaginationBuilder paginationBuilder = Pagination.builder();
        String fromItemNumber = context.getParameter("fromItemNumber");
        if (StringUtils.isNotBlank(fromItemNumber)) {
            paginationBuilder.fromItemNumber(Integer.valueOf(fromItemNumber));
        }
        String toItemNumber = context.getParameter("toItemNumber");
        if (StringUtils.isNotBlank(toItemNumber)) {
            if (toItemNumber.equals("all")) {
                paginationBuilder.toItemNumber(null);
            } else {
                paginationBuilder.toItemNumber(Integer.valueOf(toItemNumber));
            }
        }
        return paginationBuilder;
    }
}
