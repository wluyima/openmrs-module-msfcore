package org.openmrs.module.msfcore.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.ConceptNumeric;
import org.openmrs.Order;
import org.openmrs.Order.FulfillerStatus;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.TestOrder;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.dao.MSFCoreDao;
import org.openmrs.module.msfcore.result.DateType;
import org.openmrs.module.msfcore.result.TestOrderAndResultView;
import org.springframework.stereotype.Component;

@Component
public class TestOrderAndResultService {

    public List<TestOrderAndResultView> getTestsAndResults(Patient patient, String testName, FulfillerStatus testOrderStatus,
                    Date dateFrom, Date dateTo, DateType dateType, int currentPage, int itemsPerPage) {
        new ArrayList<TestOrderAndResultView>();

        // Get all orders for the current patient
        // List<Order> orders = Context.getOrderService().getAllOrdersByPatient(patient);
        MSFCoreDao msfDao = new MSFCoreDao();
        List<Order> orders = msfDao.getOrders(patient, null, null, null);

        // Filter test orders
        List<Order> labOrders = extractTestOrders(orders);

        // TODO filter by testName, testStatus and dates

        // Transform test orders to a list of TestOrderAndResultView
        return transformToTestOrderAndResultViewList(labOrders);

        // TODO Apply pagination
    }

    public void saveTestResult(Patient patient, Integer testId, String result, String sampleDateString, String resultDateString)
                    throws Exception {
        // TODO Implement saving test result
    }

    public void removeTestOrder(Patient patient, Integer testId) {
        Order order = Context.getOrderService().getOrder(testId);
        Context.getOrderService().voidOrder(order, "");
    }

    private List<Order> extractTestOrders(List<Order> orders) {
        List<Order> labOrders = new ArrayList<Order>();
        for (Order order : orders) {
            if (order.getOrderType().getUuid().equals(OrderType.TEST_ORDER_TYPE_UUID)) {
                labOrders.add(order);
            }
        }

        return labOrders;
    }

    private List<TestOrderAndResultView> transformToTestOrderAndResultViewList(List<Order> orders) {
        List<TestOrderAndResultView> results = new ArrayList<TestOrderAndResultView>();
        for (Order order : orders) {
            TestOrder testOrder = (TestOrder) order;
            TestOrderAndResultView result = new TestOrderAndResultView();
            result.setTestId(testOrder.getOrderId());
            result.setTestName(testOrder.getConcept().getName().getName());
            result.setTestResult(getResult(testOrder));
            result.setUnitOfMeasure(getUnit(testOrder));
            result.setRange(getRange(testOrder));
            result.setOrderDate(Context.getDateFormat().format(testOrder.getDateCreated()));
            result.setSampleDate(getSampleDate(testOrder));
            result.setResultDate(getResultDate(testOrder));
            results.add(result);
        }
        return results;
    }

    private String getUnit(TestOrder testOrder) {
        if (testOrder.getConcept() instanceof ConceptNumeric) {
            ConceptNumeric conceptNumeric = (ConceptNumeric) testOrder.getConcept();
            return conceptNumeric.getUnits();
        } else {
            return "";
        }
    }

    private String getResult(TestOrder testOrder) {
        if (testOrder.getVoided()) {
            return TestOrderAndResultView.CANCELLED;
        } else {
            return TestOrderAndResultView.PENDING; // TODO implement mapping test result
        }
    }

    private String getSampleDate(TestOrder testOrder) {
        if (testOrder.getVoided()) {
            return TestOrderAndResultView.CANCELLED;
        } else {
            return TestOrderAndResultView.PENDING; // TODO implement mapping sample date
        }
    }

    private String getResultDate(TestOrder testOrder) {
        if (testOrder.getVoided()) {
            return TestOrderAndResultView.CANCELLED;
        } else {
            return TestOrderAndResultView.PENDING; // TODO implement mapping result date
        }
    }

    private String getRange(TestOrder testOrder) {
        if (testOrder.getConcept() instanceof ConceptNumeric) {
            ConceptNumeric conceptNumeric = (ConceptNumeric) testOrder.getConcept();
            return conceptNumeric.getHiAbsolute() + " - " + conceptNumeric.getLowAbsolute();
        } else {
            return "";
        }
    }
}
