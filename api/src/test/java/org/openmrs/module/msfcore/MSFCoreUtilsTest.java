package org.openmrs.module.msfcore;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MSFCoreUtilsTest {

    @Test
    public void isValidUrl_shouldValidateUrls() {
        assertTrue(MSFCoreUtils.isValidUrl("http://localhost:8080/openmrs"));
        assertTrue(MSFCoreUtils.isValidUrl("https://localhost:8080/openmrs"));
        assertTrue(MSFCoreUtils.isValidUrl("http://127.0.0.1:8080/openmrs"));
    }

    @Test
    public void generateUrl_shouldWorkWell() {
        assertThat(MSFCoreUtils.generateUrl("http://localhost:8080/openmrs").getHost(), is("localhost"));
        assertThat(MSFCoreUtils.generateUrl("http://127.0.0.1:8080/openmrs").getHost(), is("127.0.0.1"));
        assertThat(MSFCoreUtils.generateUrl("http://localhost:8081/openmrs").getPort(), is(8081));
        assertThat(MSFCoreUtils.generateUrl("https://127.0.0.1:8081/openmrs").getPath(), is("/openmrs"));
        assertThat(MSFCoreUtils.generateUrl("https://127.0.0.1:8081/openmrs").getProtocol(), is("https"));
    }
}
