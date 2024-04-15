//package com.example.cs304.converter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;
//import java.io.IOException;
//import java.util.Map;
//
//@Converter
//public class JsonToMapConverter implements AttributeConverter<Map<String, Object>, String> {
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public String convertToDatabaseColumn(Map<String, Object> attribute) {
//        try {
//            return objectMapper.writeValueAsString(attribute);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error converting map to JSON", e);
//        }
//    }
//
//    @Override
//    public Map<String, Object> convertToEntityAttribute(String dbData) {
//        try {
//            return objectMapper.readValue(dbData, Map.class);
//        } catch (IOException e) {
//            throw new RuntimeException("Error converting JSON to map", e);
//        }
//    }
//}