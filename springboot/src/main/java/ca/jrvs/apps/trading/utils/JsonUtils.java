package ca.jrvs.apps.trading.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonUtils {

  public static <T> String toJsonString(T obj) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException jpex) {
      throw new IllegalArgumentException("Cannot serialize " + obj.getClass().getCanonicalName()
          + " to JSON string");
    }
  }

  public static <T> String toPrettyJsonString(T obj) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException jpex) {
      throw new IllegalArgumentException("Cannot serialize " + obj.getClass().getCanonicalName()
          + " to JSON string");
    }
  }

  public static <T> T parseJsonString(String json, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    try {
      return mapper.readValue(json, clazz);
    } catch (IOException ioex) {
      throw new IllegalArgumentException("Cannot parse JSON string as a "
          + clazz.getCanonicalName());
    }
  }

}
