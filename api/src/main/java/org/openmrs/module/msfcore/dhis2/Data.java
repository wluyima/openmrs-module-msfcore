package org.openmrs.module.msfcore.dhis2;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private Parameters parameters;
    // attribute uid:value
    private Map<String, String> attributes = new HashMap<String, String>();
    // dataElement uid:value
    private Map<String, String> dataElements = new HashMap<String, String>();

    public Data() {
    }

    public Parameters getParameters() {
        return parameters;
    }
    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }
    public Map<String, String> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
    public Map<String, String> getDataElements() {
        return dataElements;
    }
    public void setDataElements(Map<String, String> dataElements) {
        this.dataElements = dataElements;
    }

}
