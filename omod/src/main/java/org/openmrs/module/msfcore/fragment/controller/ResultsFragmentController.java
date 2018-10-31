package org.openmrs.module.msfcore.fragment.controller;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Patient;
import org.openmrs.api.OrderService;
import org.openmrs.module.msfcore.Pagination;
import org.openmrs.module.msfcore.Pagination.PaginationBuilder;
import org.openmrs.module.msfcore.result.ResultCategory;
import org.openmrs.module.msfcore.result.ResultsData;
import org.openmrs.module.msfcore.result.ResultsData.ResultsDataBuilder;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class ResultsFragmentController {
    public void controller(FragmentModel fragmentModel, FragmentConfiguration config, @RequestParam("patientId") Patient patient,
                    @SpringBean("orderService") OrderService orderService, @RequestParam("category") String category,
                    HttpServletRequest request) {
        ResultsData resultsData = null;
        if (patient != null) {
            fragmentModel.addAttribute("patient", patient);
            ResultCategory resCat = ResultsData.parseCategory(category);
            if (resCat.equals(ResultCategory.DRUG_LIST)) {
                fragmentModel.addAttribute("pageLabel", "msfcore.drugListHistory");
            } else if (resCat.equals(ResultCategory.LAB_RESULTS)) {
                fragmentModel.addAttribute("pageLabel", "msfcore.labResultsHistory");
            }

            ResultsDataBuilder resultsDataBuilder = ResultsData.builder().patient(patient);
            PaginationBuilder paginationBuilder = Pagination.builder();
            if (!config.containsKey("fromResultNumber")) {
                config.put("fromResultNumber", 0L);
            }
            paginationBuilder.fromResultNumber((Integer) config.get("fromResultNumber"));
            if (request.getAttribute("resultNumberPerPage") != null) {
                paginationBuilder.toResultNumber((Integer) config.get("fromResultNumber")
                                + (Integer) request.getAttribute("resultNumberPerPage"));
            } else if (config.containsKey("toResultNumber")) {
                paginationBuilder.toResultNumber((Integer) config.get("toResultNumber"));
            }
            resultsData = resultsDataBuilder.resultCategory(ResultsData.parseCategory(category)).pagination(paginationBuilder.build())
                            .build();
            resultsData.addRetrievedResults();
        }
        fragmentModel.addAttribute("resultsData", resultsData);
    }
}
