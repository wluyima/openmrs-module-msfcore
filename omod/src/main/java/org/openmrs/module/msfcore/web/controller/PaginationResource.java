package org.openmrs.module.msfcore.web.controller;

import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

public class PaginationResource extends BaseDataResource {
    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("fromResultNumber");
        description.addProperty("toResultNumber");
        description.addProperty("totalResultNumber");

        return description;
    }
}
