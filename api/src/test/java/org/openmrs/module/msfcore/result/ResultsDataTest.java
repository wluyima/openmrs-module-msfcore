package org.openmrs.module.msfcore.result;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.msfcore.api.MSFCoreService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class ResultsDataTest extends BaseModuleContextSensitiveTest {

    @Test
    public void addRetriedResults_shouldAddLabTestOrders() {
        Assert.assertNotNull(Context.getService(MSFCoreService.class));
    }
}
