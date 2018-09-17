package org.openmrs.module.msfcore.dhis2;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleJSON extends LinkedHashMap<String, Object> {
    
    private static final long serialVersionUID = 8610995586264168328L;

    public SimpleJSON() {
        super();
    }

    /**
     * Puts a property in this map, and returns the map itself (for chained
     * method calls)
     * 
     * @param key
     * @param value
     * @return
     */
    public SimpleJSON add(String key, Object value) {
        put(key, value);
        return this;
    }

    /**
     * Removes a property from the map, and returns the map itself (for chained
     * method calls)
     * 
     * @param key
     * @return
     */
    public SimpleJSON removeProperty(String key) {
        remove(key);
        return this;
    }

    /**
     * Creates an instance from the given json string.
     * 
     * @param json
     * @return the simpleObject
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static SimpleJSON parseJson(String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleJSON object = objectMapper.readValue(json, SimpleJSON.class);
        return object;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * 
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) super.get(key);
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.toString();
    }

    public static SimpleJSON readFromInputStream(InputStream inputStream) throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(inputStream, SimpleJSON.class);
    }
}
