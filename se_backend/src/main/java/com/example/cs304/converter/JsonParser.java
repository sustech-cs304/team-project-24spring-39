package com.example.cs304.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonParser {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void parseNestedJson(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                try {
                    Map<String, Object> nestedMap = mapper.readValue((String) value, new TypeReference<>() {
                    });
                    entry.setValue(nestedMap);
                    parseNestedJson(nestedMap);
                } catch (Exception e) {
                    System.out.println("Failed to parse JSON string: " + value);
//                    e.printStackTrace();
                    // Ignore, this means the string is not a JSON string
                }
            } else if (value instanceof Map) {
                parseNestedJson((Map<String, Object>) value);
            }
        }
    }
}