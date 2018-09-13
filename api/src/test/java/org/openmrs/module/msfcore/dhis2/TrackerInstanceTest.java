package org.openmrs.module.msfcore.dhis2;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrackerInstanceTest {

    @Test
    public void generateUrl_shouldGenerateRightURL() {
        assertThat(TrackerInstance.generateUrl("user", "password", "localhost"), is("http://user:password@localhost/"));
        assertThat(TrackerInstance.generateUrl("user", "password", "127.0.0.1"), is("http://user:password@127.0.0.1/"));
        assertThat(TrackerInstance.generateUrl("user", "password", "http://127.0.0.1"), is("http://user:password@127.0.0.1/"));
        assertThat(TrackerInstance.generateUrl("user", "password", "https://127.0.0.1"), is("http://user:password@127.0.0.1/"));
        assertThat(TrackerInstance.generateUrl("user", "password", "www.localhost"), is("http://user:password@localhost/"));
    }

    @Test
    public void prepopulateFromJSON() throws JsonParseException, JsonMappingException, IOException {
        TrackerInstance trackerInstance = TrackerInstance.readFromInputStream(getClass().getClassLoader().getResourceAsStream(
                        "sampleTracker.json"));
        assertThat(trackerInstance.getUrl(), is("http://admin:district@192.168.2.203/"));
        assertThat(trackerInstance.getProgram(), is("vYTIxQVqbjr"));
        assertThat(trackerInstance.getProgramStage(), is("xSgs2Acybhy"));
        assertThat(trackerInstance.getTrackedEntity(), is("oZR9WrqUKCG"));
        assertThat(trackerInstance.getProgramStage(), is("xSgs2Acybhy"));
        assertThat(trackerInstance.getProgramStage(), is("xSgs2Acybhy"));
        assertThat(trackerInstance.getData().getParameters().getOrgUnit(), is("NW1LBPE39Ri"));
        assertThat(trackerInstance.getData().getParameters().getProgramDate(), is("2018-01-01"));
        assertThat(trackerInstance.getData().getParameters().getEventDate(), is("2018-01-12"));
        assertThat(trackerInstance.getData().getAttributes().get("OvhnJ28J2nC"), is("Daniel"));
        assertThat(trackerInstance.getData().getAttributes().get("cBLI3vlsv8w"), is("Joseph"));
        assertThat(trackerInstance.getData().getDataElements().size(), is(0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void generateJSONFromObject() throws IOException {
        TrackerInstance trackerInstance = TrackerInstance.readFromInputStream(getClass().getClassLoader().getResourceAsStream(
                        "sampleTracker.json"));
        ObjectMapper mapper = new ObjectMapper();
        assertThat(
                        mapper.readTree(IOUtils.toString(getClass().getClassLoader().getResourceAsStream("sampleTracker.json"),
                                        Charsets.UTF_8)), is(mapper.readTree(trackerInstance.toString())));
    }
}
