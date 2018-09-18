package org.openmrs.module.msfcore.dhis2;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
public class TrackerInstance {
    private String url;
    private String program;
    private String programStage;
    private String trackedEntity;
    private Data data;

    /*
     * Generates a url that looks like; "http://admin:district@localhost/"
     */
    public static String generateUrl(String username, String password, String host) {
        host = host.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        return "http://" + username + ":" + password + "@" + host + "/";
    }

    public static TrackerInstance readFromInputStream(InputStream inputStream) throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(inputStream, TrackerInstance.class);
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.toString();
    }

}
