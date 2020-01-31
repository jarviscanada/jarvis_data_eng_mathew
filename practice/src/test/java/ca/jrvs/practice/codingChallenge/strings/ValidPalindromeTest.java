package ca.jrvs.practice.codingChallenge.strings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidPalindromeTest {

  ValidPalindrome testValidPalindrome = new ValidPalindrome();
  String validOdd = "racecar";
  String validEven = "abcddcba";
  String invalid = "apple";

  @Test
  public void validPalindromeTwoPointer() {
    assertTrue(testValidPalindrome.validPalindromeTwoPointer(validEven));
    assertTrue(testValidPalindrome.validPalindromeTwoPointer(validOdd));
    assertFalse(testValidPalindrome.validPalindromeTwoPointer(invalid));
  }

  @Test
  public void validPalindromeRecursion() {
    assertTrue(testValidPalindrome.validPalindromeRecursion(validEven));
    assertTrue(testValidPalindrome.validPalindromeRecursion(validOdd));
    assertFalse(testValidPalindrome.validPalindromeRecursion(invalid));
  }
}