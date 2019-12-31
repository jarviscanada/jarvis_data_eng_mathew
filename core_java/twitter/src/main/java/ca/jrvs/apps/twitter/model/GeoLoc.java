package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoLoc {

  @JsonProperty("coordinates")
  private float[] coordinates;
  @JsonProperty("type")
  private String type;

  //Default Constructor
  public GeoLoc() {

  }

  public float[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(float[] coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
