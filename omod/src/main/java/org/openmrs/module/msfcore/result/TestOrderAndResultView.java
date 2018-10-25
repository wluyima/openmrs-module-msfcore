package org.openmrs.module.msfcore.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestOrderAndResultView {

    public static final String PENDING = "_PENDING";

    public static final String CANCELLED = "_CANCELLED";

    private Integer testId;

    private String testName;

    private String testResult;

    private String unitOfMeasure;

    private String range;

    private String orderDate;

    private String sampleDate;

    private String resultDate;
}
