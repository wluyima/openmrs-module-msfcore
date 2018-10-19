package org.openmrs.module.msfcore.web.controller;

import java.io.IOException;
import java.util.Arrays;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.Pagination;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Resource(name = RestConstants.VERSION_1 + MSFCoreRestController.NAMESPACE + "/resultData", supportedClass = ResultsData.class, supportedOpenmrsVersions = {"2.2.*"})
public class ResultsDataResource extends BaseDataResource {

    @Override
    protected AlreadyPaged<ResultsData> doSearch(RequestContext context) throws ResponseException {
        Patient patient = Context.getPatientService().getPatientByUuid(context.getParameter("patientUuid"));
        ResultsDataBuilder resultsDataBuilder = ResultsData.builder().patient(patient);
        if (patient != null) {
            try {
                PaginationBuilder paginationBuilder = Pagination.builder();
                String body = getRequestBody(context);
                // TODO set paginationBuilder from body
                ResultsData resultsData = resultsDataBuilder.resultCategory(ResultsData.parseCategory(context.getParameter("category")))
                                .pagination(paginationBuilder.build()).build();
                resultsData.addRetriedResults();
                return new AlreadyPaged<ResultsData>(context, Arrays.asList(resultsData), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new AlreadyPaged<ResultsData>(context, Arrays.asList(resultsDataBuilder.build()), false);
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation representation) {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("patient");
        description.addProperty("keys");
        description.addProperty("pagination");
        description.addProperty("resultCategory");
        description.addProperty("results");
        description.addProperty("filters");
        description.addProperty("actions");

        return description;
    }
}
