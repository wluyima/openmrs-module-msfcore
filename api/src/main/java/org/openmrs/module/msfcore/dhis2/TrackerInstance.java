package org.openmrs.module.msfcore.dhis2;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrackerInstance {
    private String url;
    private String program;
    private String programStage;
    private String trackedEntity;
    private Data data;

    public TrackerInstance() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getProgramStage() {
        return programStage;
    }

    public void setProgramStage(String programStage) {
        this.programStage = programStage;
    }

    public String getTrackedEntity() {
        return trackedEntity;
    }

    public void setTrackedEntity(String trackedEntity) {
        this.trackedEntity = trackedEntity;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

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
