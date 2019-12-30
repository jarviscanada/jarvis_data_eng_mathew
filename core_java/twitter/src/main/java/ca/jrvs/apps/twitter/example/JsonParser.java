package ca.jrvs.apps.twitter.example;

import ca.jrvs.apps.twitter.example.dto.Company;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

public class JsonParser {

  //JSON string that we're testing the parser against
  public static final String companyStr = "{\n"
      + "   \"symbol\":\"AAPL\",\n"
      + "   \"companyName\":\"Apple Inc.\",\n"
      + "   \"exchange\":\"Nasdaq Global Select\",\n"
      + "   \"description\":\"Apple Inc is designs, manufactures and markets mobile communication and media devices and personal computers, and sells a variety of related software, services, accessories, networking solutions and third-party digital content and applications.\",\n"
      + "   \"CEO\":\"Timothy D. Cook\",\n"
      + "   \"sector\":\"Technology\",\n"
      + "   \"financials\":[\n"
      + "      {\n"
      + "         \"reportDate\":\"2018-12-31\",\n"
      + "         \"grossProfit\":32031000000,\n"
      + "         \"costOfRevenue\":52279000000,\n"
      + "         \"operatingRevenue\":84310000000,\n"
      + "         \"totalRevenue\":84310000000,\n"
      + "         \"operatingIncome\":23346000000,\n"
      + "         \"netIncome\":19965000000\n"
      + "      },\n"
      + "      {\n"
      + "         \"reportDate\":\"2018-09-30\",\n"
      + "         \"grossProfit\":24084000000,\n"
      + "         \"costOfRevenue\":38816000000,\n"
      + "         \"operatingRevenue\":62900000000,\n"
      + "         \"totalRevenue\":62900000000,\n"
      + "         \"operatingIncome\":16118000000,\n"
      + "         \"netIncome\":14125000000\n"
      + "      }\n"
      + "   ],\n"
      + "   \"dividends\":[\n"
      + "      {\n"
      + "         \"exDate\":\"2018-02-09\",\n"
      + "         \"paymentDate\":\"2018-02-15\",\n"
      + "         \"recordDate\":\"2018-02-12\",\n"
      + "         \"declaredDate\":\"2018-02-01\",\n"
      + "         \"amount\":0.63\n"
      + "      },\n"
      + "      {\n"
      + "         \"exDate\":\"2017-11-10\",\n"
      + "         \"paymentDate\":\"2017-11-16\",\n"
      + "         \"recordDate\":\"2017-11-13\",\n"
      + "         \"declaredDate\":\"2017-11-02\",\n"
      + "         \"amount\":0.63\n"
      + "      }\n"
      + "   ]\n"
      + "}";

  public JsonParser() {
  }

  /**
   * Converts a JSON string into an object. This assumes that the object is set up to work with
   * Jackson.
   * @param jsonString A JSON formatted string containing object data
   * @param clazz A class reference to convert the string into
   * @param <T> A generic class. Java will infer this
   * @return an object of the class given by clazz
   */
  public <T> T fromJson(String jsonString, Class<T> clazz) {
    T parsedJson = null;
    ObjectMapper objMap = new ObjectMapper();
    try {
      parsedJson = objMap.readValue(jsonString, clazz);
    } catch (IOException ex) {
      System.out.println("Could not parse JSON into this class type\n" + ex.getMessage());
    }
    return parsedJson;
  }

  /**
   * Converts an Object into a JSON-formatted string. This assumes the object has proper Jackson
   * annotations.
   * @param obj the object to convert
   * @param pretty Flag indicating pretty output or single-line
   * @param includeNulls If set to false, null fields will not be included in the output
   * @param <T> Generic object type, Java will infer this
   * @return A JSON-formatted string representation of the given object
   */
  public <T> String toJson(T obj, boolean pretty, boolean includeNulls) {
    String jsonString = null;
    ObjectMapper objMap = new ObjectMapper();
    if (pretty) {
      objMap.enable(SerializationFeature.INDENT_OUTPUT);
    }
    if (!includeNulls) {
      objMap.setSerializationInclusion(Include.NON_NULL);
    }
    try {
      jsonString = objMap.writeValueAsString(obj);
    } catch (JsonProcessingException jspex) {
      System.out.println("JSON processing failed\n" + jspex.getMessage());
    }
    return jsonString;
  }

  public static void main(String[] args) {
    JsonParser jsp = new JsonParser();
    Company c = jsp.fromJson(companyStr, Company.class);
    System.out.println(
        "Checking a few fields to see if object was parsed\n" + c.getCompanyName() + " " + c
            .getSymbol() + " " + c.getDescription());
    String jsonified = jsp.toJson(c, true, true);
    System.out.println(
        "We've converted the JSON-parsed object back into a string, only pretty\n" + jsonified);
  }
}