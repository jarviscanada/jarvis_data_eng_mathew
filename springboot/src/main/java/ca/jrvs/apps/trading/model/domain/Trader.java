package ca.jrvs.apps.trading.model.domain;

import ca.jrvs.apps.trading.model.Entity;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.sql.Date;
import java.time.LocalDate;

public class Trader implements Entity<Integer> {

  private int id;
  private String firstName;
  private String lastName;
  //@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Toronto")
  private Date dob;
  private String country;
  private String email;

  // This function is used to prevent Jackson from doing time zone conversion when de-serializing
  // incoming Trader JSON docs. This may still cause issues if the host is in a different time zone
  // than the server.
  @JsonSetter("dob")
  public void setDobWithLocalDate(LocalDate localDate) {
    dob = Date.valueOf(localDate);
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "Trader{"
        + "id=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", dob=" + dob
        + ", country='" + country + '\''
        + ", email='" + email + '\''
        + '}';
  }
}
