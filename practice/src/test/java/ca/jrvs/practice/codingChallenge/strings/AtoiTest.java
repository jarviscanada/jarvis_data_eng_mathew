package ca.jrvs.practice.codingChallenge.strings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class AtoiTest {

  Atoi testAtoi = new Atoi();

  @Test
  public void atoiBuiltIn() {
    assertEquals(5, testAtoi.atoiBuiltIn("5"));
    assertEquals(-22, testAtoi.atoiBuiltIn("-22"));
    assertEquals(0, testAtoi.atoiBuiltIn("five"));
    assertNotEquals(123456789, testAtoi.atoiBuiltIn("123,456,789"));
  }

  @Test
  public void atoi() {
    assertEquals(5, testAtoi.atoi("   5   "));
    assertEquals(-22, testAtoi.atoi("-22"));
    assertEquals(0, testAtoi.atoi("five"));
    assertEquals(123456789, testAtoi.atoi("123,456,789"));
    assertEquals(Integer.MIN_VALUE, testAtoi.atoi("-91283472332"));
    assertEquals(2147483647, testAtoi.atoi("2147483648"));
  }
}