package ca.jrvs.apps.twitter.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonUtil {

  /**
   * Converts a JSON document string into an object.
   *
   * @param json  the JSON Document string
   * @param clazz the class to return
   * @param <T>   The class to return
   * @return A class instance filled with the JSON Document's information
   */
  public static <T> T toObject(String json, Class<T> clazz) {
    ObjectMapper objMap = new ObjectMapper();
    objMap.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    try {
      return objMap.readValue(json, clazz);
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Creates a JSON Document based on the information present within the passed object.
   *
   * @param obj An object to convert to JSON
   * @param <T> A class reference
   * @return A JSON Document string equivalent to the object passed in
   */
  public static <T> String toJson(T obj) {
    ObjectMapper objMap = new ObjectMapper();
    try {
      return objMap.writeValueAsString(obj);
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Converts an object into a pretty JSON String
   *
   * @param obj the object to convert to JSON
   * @param <T> The object's class
   * @return a Pretty JSON String
   */
  public static <T> String toPrettyJson(T obj) {
    ObjectMapper objMap = new ObjectMapper();
    objMap.enable(SerializationFeature.INDENT_OUTPUT);
    try {
      return objMap.writeValueAsString(obj);
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}
