package ca.jrvs.practice.codingChallenge.stackQueue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidParenthesesTest {

  ValidParentheses validParentheses = new ValidParentheses();

  @Test
  public void validParentheses() {
    assertTrue(validParentheses.validParentheses("[]"));
    assertTrue(validParentheses.validParentheses("{}"));
    assertTrue(validParentheses.validParentheses("()"));
    assertTrue(validParentheses.validParentheses("()[]"));
    assertTrue(validParentheses.validParentheses("()[]{}"));
    assertTrue(validParentheses.validParentheses("({[]})"));
  }

  @Test
  public void validParentheses_Invalid() {
    assertFalse(validParentheses.validParentheses("[}"));
    assertFalse(validParentheses.validParentheses("(}"));
    assertFalse(validParentheses.validParentheses("{]"));
    assertFalse(validParentheses.validParentheses("{"));
    assertFalse(validParentheses.validParentheses("((())"));
    assertFalse(validParentheses.validParentheses("({)}"));
    assertFalse(validParentheses.validParentheses("[][]["));
  }

  @Test
  public void validParentheses_Empty() {
    assertTrue(validParentheses.validParentheses(""));
  }
}