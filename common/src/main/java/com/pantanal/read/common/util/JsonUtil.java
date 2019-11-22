package com.pantanal.read.common.util;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper;
    private static final String EXCLUDE = "EXCLUDE";
    private static final String INCLUDE = "INCLUDE";
    //yyyy-MM-dd'T'HH:mm:ss.SSSZ
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @JsonFilter("EXCLUDE")
    interface Exclude {
    }

    @JsonFilter("INCLUDE")
    interface Include {
    }

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
    }


    /**
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * @param dateTimeFormatter
     * @return
     */
    public static ObjectMapper getObjectMapper(String dateTimeFormatter) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);
        objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormatter));
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormatter)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormatter)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));
        objectMapper.registerModule(javaTimeModule).registerModule(new ParameterNamesModule());
        return objectMapper;
    }

    /**
     * @param object
     * @return
     */
    public static String objectToString(Object object) {
        return objectToString(getObjectMapper(), object);
    }

    /**
     * @param objectMapper
     * @param object
     * @return
     */
    public static String objectToString(ObjectMapper objectMapper, Object object) {
        if (object == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("objectToString error! object:{}", object, e);
            return null;
        }
    }

    /**
     * @param object
     * @param exludeProperty
     * @param <T>
     * @return
     */
    public static <T extends JsonNode> T objectToJson(ObjectMapper objectMapper, Object object, String... exludeProperty) {
        if (object == null) {
            return null;
        }
        try {
            if (exludeProperty != null) {
                return filter(objectMapper.copy(), object.getClass(), exludeProperty, null).valueToTree(object);
            } else {
                return objectMapper.valueToTree(object);
            }
        } catch (Exception e) {
            log.error("objectToTree error! object:{}", object, e);
            return null;
        }
    }


    /**
     * @param object
     * @param exludeProperty
     * @param <T>
     * @return
     */
    public static <T extends JsonNode> T objectToJson(Object object, String... exludeProperty) {
        return objectToJson(getObjectMapper(), object, exludeProperty);
    }


    /**
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T stringToObject(String json, Class<T> type) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            log.error("stringToObject Class error! class:{}, json:{}", type, json, e);
            return null;
        }
    }

    /**
     * @param json
     * @return
     */
    public static JsonNode stringToJson(String json) {
        if (json == null) {
            return null;
        }
        try {
            return objectMapper.readTree(json);
        } catch (Exception e) {
            log.error("stringToTree to JsonNode error! json:{}", json, e);
            return null;
        }
    }


    /**
     * @param n
     * @param valueType
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(TreeNode n, Class<T> valueType) {
        if (n == null) {
            return null;
        }
        try {
            return objectMapper.treeToValue(n, valueType);
        } catch (Exception e) {
            log.error("treeToObject error! object:{}", n, e);
            return null;
        }
    }


    public static ObjectNode objectNode() {
        return objectMapper.createObjectNode();
    }


    public static ArrayNode arrayNode() {
        return objectMapper.createArrayNode();
    }

    /**
     * @param clazz
     * @param exclude property names join in ','
     * @param include property names join in ','
     * @return
     */
    private static ObjectMapper filter(ObjectMapper objectMapper, Class<?> clazz, String[] exclude, String[] include) {
        if (clazz == null) {
            return objectMapper;
        }
        if (include != null && include.length > 0) {
            objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(INCLUDE,
                    SimpleBeanPropertyFilter.filterOutAllExcept(include)));
            objectMapper.addMixIn(clazz, Include.class);
        }
        if (exclude != null && exclude.length > 0) {
            objectMapper.setFilterProvider(new SimpleFilterProvider().addFilter(EXCLUDE,
                    SimpleBeanPropertyFilter.serializeAllExcept(exclude)));
            objectMapper.addMixIn(clazz, Exclude.class);
        }
        return objectMapper;
    }
}
