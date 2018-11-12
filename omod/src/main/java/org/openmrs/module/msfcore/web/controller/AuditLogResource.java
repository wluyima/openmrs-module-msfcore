package org.openmrs.module.msfcore.web.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.module.msfcore.api.AuditService;
import org.openmrs.module.msfcore.audit.AuditLog;
import org.openmrs.module.msfcore.audit.AuditLog.Event;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + MSFCoreRestController.NAMESPACE + "/auditlog", supportedClass = AuditLog.class, supportedOpenmrsVersions = {"2.2.*"})
public class AuditLogResource extends BaseDataResource {

    @Override
    protected AlreadyPaged<SimpleObject> doSearch(RequestContext context) throws ResponseException {
        Pagination pagination = buildPaginationFromContext(context).build();
        SimpleObject response = new SimpleObject();
        List<AuditLog> audits = Context.getService(AuditService.class).getAuditLogs(null, null, null, null, null, null, null, pagination);
        response.add("pagination", pagination);
        response.add("auditLogs", audits);
        return new AlreadyPaged<SimpleObject>(context, Arrays.asList(response), false);
    }

    @Override
    protected AlreadyPaged<AuditLog> doGetAll(RequestContext context) throws ResponseException {
        List<AuditLog> audits = Context.getService(AuditService.class).getAuditLogs(null, null, null, null, null, null, null, null);
        return new AlreadyPaged<AuditLog>(context, audits, false);
    }

    @Override
    public AuditLog newDelegate() {
        AuditLog delegate = new AuditLog();
        delegate.setDate(new Date());
        delegate.setUuid(UUID.randomUUID().toString());
        return delegate;
    }

    @Override
    public Object create(SimpleObject propertiesToCreate, RequestContext context) throws ResponseException {
        AuditLog delegate = (AuditLog) convert(propertiesToCreate);
        if (delegate.getUser() == null && !delegate.getEvent().equals(Event.LOGIN)) {
            delegate.setUser(Context.getAuthenticatedUser());
        }
        delegate = Context.getService(AuditService.class).getAuditLog(Context.getService(AuditService.class).saveAuditLog(delegate));
        SimpleObject ret = (SimpleObject) ConversionUtil.convertToRepresentation(delegate, context.getRepresentation());
        return ret;
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("date");
        description.addProperty("event");
        description.addProperty("detail");
        description.addProperty("patient", Representation.REF);
        description.addProperty("user", Representation.REF);
        description.addProperty("provider", Representation.REF);
        description.addProperty("location", Representation.REF);
        description.addProperty("uuid");
        return description;
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() throws ResourceDoesNotSupportOperationException {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addRequiredProperty("event");
        description.addRequiredProperty("detail");
        description.addProperty("patient");
        description.addProperty("user");
        description.addProperty("provider");
        description.addProperty("location");
        return description;
    }

}
