package org.openmrs.module.msfcore.dhis2;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.msfcore.api.DHISService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class OpenMRSToDHISTest {
    SimpleJSON json = null;

    @Before
    public void setup() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
        json = SimpleJSON.readFromInputStream(new FileInputStream(new File(getClass().getClassLoader().getResource(
                        DHISService.FILENAME_OPTION_SETS_JSON).getFile())));
    }

    @Test
    public void getGender_ShouldRetrieveRightGenderFromJson() throws JsonParseException, JsonMappingException, IOException {
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "F"), is("female"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "Female"), is("female"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "female"), is("female"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "FEMALE"), is("female"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "M"), is("male"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "Male"), is("male"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "MALE"), is("male"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "male"), is("male"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "anythingelse"), is("other"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "other"), is("other"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json, "O"), is("other"));
        // return null if blank or null json
        assertNull(OpenMRSToDHIS.getGenderFromOptionSets(json, ""));
        assertNull(OpenMRSToDHIS.getGenderFromOptionSets(json, null));
        assertNull(OpenMRSToDHIS.getGenderFromOptionSets(null, ""));
        assertNull(OpenMRSToDHIS.getGenderFromOptionSets(null, null));
        assertNull(OpenMRSToDHIS.getGenderFromOptionSets(null, "F"));
    }

    @Test
    public void getGender_ShouldRetrieveNoGenderFromJsonIfCodeIsBlank() throws JsonParseException, JsonMappingException, IOException {
        SimpleJSON json2 = SimpleJSON
                        .parseJson("{\"optionSets\":[{\"name\":\"Sex\",\"options\":[{\"code\":\"male\",\"name\":\"Male\"},{\"name\":\"Female\"},{\"code\":\"other\",\"name\":\"Other\"}]}]}");
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json2, "M"), is("male"));
        assertThat(OpenMRSToDHIS.getGenderFromOptionSets(json2, "O"), is("other"));
        assertNull(OpenMRSToDHIS.getGenderFromOptionSets(json2, "F"));
    }

    @Test
    public void getMatchedConcept_shouldRetrieveRightOptionValue() throws JsonParseException, JsonMappingException, IOException {
        assertThat(OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(json, "Provenance", "Buffer Zone resident"),
                        is("buffer_zone_resident"));
        assertThat(OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(json, "Provenance", "Local"), is("local"));
        assertThat(OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(json, "Provenance", "IDP"), is("idp"));
        assertThat(OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(json, "Nationality", "Afghanistan"), is("afghanistan"));
        assertThat(OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(json, "Nationality", "Democratic Republic of Congo"), is("drc"));
        assertThat(OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(json, "Nationality", "lebanese returnee"), is("lebanese_returnee"));
        assertThat(OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(json, "Marital status", "Single"), is("single"));
        assertThat(OpenMRSToDHIS.getMatchedConceptCodeFromOptionSets(json, "Condition of living", "In a tent and / or moskito net"),
                        is("in_a_tent_and_or_moskito_net"));
    }
}
