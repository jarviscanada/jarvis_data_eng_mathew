package ca.jrvs.practice.codingChallenge.strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RotateStringTest {

  RotateString testRotateString = new RotateString();
  String string1 = "rotateme";
  String string2 = "potato";
  String rotated1 = "temerota";
  String rotated2 = "tatopo";

  @Test
  public void rotateString() {
    assertTrue(testRotateString.rotateString(string1, rotated1));
    assertTrue(testRotateString.rotateString(string2, rotated2));
    assertTrue(testRotateString.rotateString("", ""));
    assertFalse(testRotateString.rotateString("a", "ab"));
    assertFalse(testRotateString.rotateString("abcde", "fghijk"));
    assertFalse(testRotateString.rotateString("abcefghijk", "abcdkfgihj"));
  }
}