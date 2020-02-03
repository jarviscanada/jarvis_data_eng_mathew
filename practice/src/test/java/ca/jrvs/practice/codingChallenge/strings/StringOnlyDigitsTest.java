package ca.jrvs.practice.codingChallenge.strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringOnlyDigitsTest {

  String good = "3141596254";
  String decimal = "3.14159";
  String comma = "123,456,789";
  String roman = "XIV";
  StringOnlyDigits testStringOnlyDigits = new StringOnlyDigits();

  @Test
  public void stringOnlyDigits() {
    assertTrue(testStringOnlyDigits.stringOnlyDigits(good));
    assertFalse(testStringOnlyDigits.stringOnlyDigits(decimal));
    assertFalse(testStringOnlyDigits.stringOnlyDigits(comma));
    assertFalse(testStringOnlyDigits.stringOnlyDigits(roman));
  }

  @Test
  public void stringOnlyDigitsApi() {
    assertTrue(testStringOnlyDigits.stringOnlyDigitsApi(good));
    assertFalse(testStringOnlyDigits.stringOnlyDigitsApi(decimal));
    assertFalse(testStringOnlyDigits.stringOnlyDigitsApi(comma));
    assertFalse(testStringOnlyDigits.stringOnlyDigitsApi(roman));
  }

  @Test
  public void stringOnlyDigitsRegex() {
    assertTrue(testStringOnlyDigits.stringOnlyDigitsRegex(good));
    assertFalse(testStringOnlyDigits.stringOnlyDigitsRegex(decimal));
    assertFalse(testStringOnlyDigits.stringOnlyDigitsRegex(comma));
    assertFalse(testStringOnlyDigits.stringOnlyDigitsRegex(roman));
  }
}