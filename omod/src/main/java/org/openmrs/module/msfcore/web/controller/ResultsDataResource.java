package org.openmrs.module.msfcore.web.controller;

import java.util.Arrays;

import org.openmrs.Patient;
import org.openmrs.module.msfcore.Pagination.PaginationBuilder;
import org.openmrs.module.msfcore.result.ResultsData;
import org.openmrs.module.msfcore.result.ResultsData.ResultsDataBuilder;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + MSFCoreRestController.NAMESPACE + "/resultData", supportedClass = ResultsData.class, supportedOpenmrsVersions = {"2.2.*"})
public class ResultsDataResource extends BaseDataResource {

    @Override
    protected AlreadyPaged<ResultsData> doSearch(RequestContext context) throws ResponseException {
        Patient patient = getPatientFromId(context.getParameter("patientId"));
        ResultsDataBuilder resultsDataBuilder = ResultsData.builder().patient(patient);
        if (patient != null) {
            PaginationBuilder paginationBuilder = buildPaginationFromContext(context);
            ResultsData resultsData = resultsDataBuilder.resultCategory(ResultsData.parseCategory(context.getParameter("category")))
                            .pagination(paginationBuilder.build()).build();
            resultsData.addRetrievedResults();
            return new AlreadyPaged<ResultsData>(context, Arrays.asList(resultsData), false);
        }
        return new AlreadyPaged<ResultsData>(context, Arrays.asList(resultsDataBuilder.build()), false);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("patient", Representation.REF);
        description.addProperty("keys");
        description.addProperty("pagination");
        description.addProperty("resultCategory");
        description.addProperty("results");
        description.addProperty("filters");
        description.addProperty("dateFormatPattern");

        return description;
    }
}
