package org.openmrs.module.msfcore.web.controller;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
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

@Resource(name = RestConstants.VERSION_1 + MSFCoreRestController.NAMESPACE + "/resultData", supportedClass = ResultsData.class, supportedOpenmrsVersions = {"2.2.*"})
public class ResultsDataResource extends BaseDataResource {

    @Override
    protected AlreadyPaged<ResultsData> doSearch(RequestContext context) throws ResponseException {
        Patient patient;
        try {
            patient = Context.getPatientService().getPatient(Integer.valueOf(context.getParameter("patientId")));
        } catch (NumberFormatException e) {
            patient = Context.getPatientService().getPatientByUuid(context.getParameter("patientId"));
        }
        ResultsDataBuilder resultsDataBuilder = ResultsData.builder().patient(patient);
        if (patient != null) {
            PaginationBuilder paginationBuilder = Pagination.builder();
            // TODO set paginationBuilder from body
            String fromResultNumber = context.getParameter("fromResultNumber");
            if (StringUtils.isNotBlank(fromResultNumber)) {
                paginationBuilder.fromResultNumber(Integer.valueOf(fromResultNumber));
            }
            String toResultNumber = context.getParameter("toResultNumber");
            if (StringUtils.isNotBlank(toResultNumber)) {
                if(toResultNumber.equals("all")) {
                    paginationBuilder.toResultNumber(null);
                } else {
                    paginationBuilder.toResultNumber(Integer.valueOf(toResultNumber));
                }
            }
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

        return description;
    }
}
