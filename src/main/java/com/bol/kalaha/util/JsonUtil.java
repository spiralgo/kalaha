package com.bol.kalaha.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.metamodel.Type;

public class JsonUtil {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object asObject(final String content, Class<?> type) {
        try {
            return new ObjectMapper().readValue(content, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
