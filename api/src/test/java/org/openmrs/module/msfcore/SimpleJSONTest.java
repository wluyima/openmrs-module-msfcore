package org.openmrs.module.msfcore;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SimpleJSONTest {

    @Test
    @SuppressWarnings("rawtypes")
    public void get_shouldRetrieveAsSimpleJSON() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
        SimpleJSON json = SimpleJSON.readFromInputStream(new FileInputStream(new File(getClass().getClassLoader().getResource(
                        MSFCoreConfig.SYNC2_NAME_OF_CUSTOM_CONFIGURATION).getFile())));
        assertThat((String) ((Map) json.get("general")).get("localFeedLocation"), is(""));
        assertThat((String) ((Map) json.get("general")).get("parentFeedLocation"), is(""));
        assertNull(((Map) json.get("general")).get("localInstanceId"));
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void put_shouldPutOrReplace() throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
        SimpleJSON json = SimpleJSON.readFromInputStream(new FileInputStream(new File(getClass().getClassLoader().getResource(
                        MSFCoreConfig.SYNC2_NAME_OF_CUSTOM_CONFIGURATION).getFile())));
        assertNull(((Map) json.get("general")).get("localInstanceId"));
        assertTrue((Boolean) ((Map) json.get("push")).get("enabled"));
        ((Map) json.get("general")).put("localInstanceId", "id");
        ((Map) json.get("push")).put("enabled", false);
        assertThat((String) ((Map) json.get("general")).get("localInstanceId"), is("id"));
        assertFalse((Boolean) ((Map) json.get("push")).get("enabled"));
    }
}
