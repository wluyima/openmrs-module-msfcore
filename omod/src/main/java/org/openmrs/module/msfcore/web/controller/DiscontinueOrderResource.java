package org.openmrs.module.msfcore.web.controller;

import java.util.Arrays;

import org.openmrs.Order;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + MSFCoreRestController.NAMESPACE + "/discontinueorder", supportedClass = Order.class, supportedOpenmrsVersions = {"2.2.*"})
public class DiscontinueOrderResource extends BaseDataResource {

    // GET /discontinueorder?uuid= will discontinue order and returned
    // discontinuing order
    @Override
    protected AlreadyPaged<Order> doSearch(RequestContext context) throws ResponseException {
        Order order = Context.getOrderService().getOrderByUuid(context.getParameter("uuid"));
        return new AlreadyPaged<Order>(context, Arrays.asList(order), false);
    }
}
