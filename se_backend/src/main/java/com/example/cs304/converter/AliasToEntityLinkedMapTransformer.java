package com.example.cs304.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.transform.ResultTransformer;

import java.util.LinkedHashMap;
import java.util.List;

public class AliasToEntityLinkedMapTransformer implements ResultTransformer {

    public static final AliasToEntityLinkedMapTransformer INSTANCE = new AliasToEntityLinkedMapTransformer();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>(tuple.length);
        for (int i = 0; i < tuple.length; i++) {
            String alias = aliases[i];
            if (alias != null) {
                if (alias.equals("time") || alias.equals("professor_list")) {
                    try {
                        result.put(alias, objectMapper.readTree((String) tuple[i]));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    result.put(alias, tuple[i]);
                }
            }
        }
        return result;
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}