package org.openmrs.module.msfcore.dhis2;

import java.util.HashMap;
import java.util.Map;

import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
public class Data {
    private Parameters parameters;
    // attribute uid:value
    private Map<String, String> attributes = new HashMap<String, String>();
    // dataElement uid:value
    private Map<String, String> dataElements = new HashMap<String, String>();
}
