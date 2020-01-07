package ca.jrvs.apps.trading.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonUtils {

  /**
   * Serializes an object to a JSON String.
   *
   * @param obj The Object to serialize
   * @param <T> The class of the object being serialized
   * @return a JSON string representation of obj
   */
  public static <T> String toJsonString(T obj) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException jpex) {
      throw new IllegalArgumentException("Cannot serialize " + obj.getClass().getCanonicalName()
          + " to JSON string");
    }
  }

  /**
   * Serializes an object to a pretty JSON String.
   *
   * @param obj Object to serialize
   * @param <T> Class of the object being serialized
   * @return A JSON string representation of obj
   */
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

  /**
   * Parses a JSON string, producing an object from its data.
   *
   * @param json  The JSON string to parse
   * @param clazz The class to parse the JSON string as
   * @param <T>   The class to parse the JSON string as
   * @return A new object of type T based on the JSON string
   */
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
