package org.openmrs.module.msfcore.fragment.controller;

import java.util.List;

import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.OrderService;
import org.openmrs.module.msfcore.result.ResultsData;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class ResultsFragmentController {
    public void controller(FragmentModel fragmentModel, @RequestParam("patientId") Patient patient,
                    @SpringBean("orderService") OrderService orderService, @RequestParam("category") String category) {
        ResultsData resultsData = null;
        if (patient != null) {
            resultsData = new ResultsData();
            resultsData.setPatient(patient);
            if (category.matches("drugList|labResults")) {
                List<Order> orders = orderService.getAllOrdersByPatient(patient);
                resultsData.addOrders(orders, ResultsData.parseCategory(category));
            }
        }
        fragmentModel.addAttribute("resultsData", resultsData);
    }
}
