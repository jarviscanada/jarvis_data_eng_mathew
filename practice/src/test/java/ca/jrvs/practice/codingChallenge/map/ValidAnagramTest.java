package ca.jrvs.practice.codingChallenge.map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidAnagramTest {

  ValidAnagram validAnagram = new ValidAnagram();
  final String anagram1 = "flow";
  final String anagram2 = "wolf";
  final String anagram3 = "fowl";
  final String different = "lame";
  final String bigWord = "humongous";

  @Test
  public void validAnagramSorting() {
    assertTrue(validAnagram.validAnagramSorting(anagram1, anagram2));
    assertTrue(validAnagram.validAnagramSorting(anagram1, anagram3));
    assertTrue(validAnagram.validAnagramSorting(anagram2, anagram3));
    assertFalse(validAnagram.validAnagramSorting(anagram1, different));
    assertFalse(validAnagram.validAnagramSorting(anagram2, different));
    assertFalse(validAnagram.validAnagramSorting(anagram3, different));
    assertFalse(validAnagram.validAnagramSorting(anagram1, bigWord));
  }

  @Test
  public void validAnagramMap() {
    assertTrue(validAnagram.validAnagramMap(anagram1, anagram2));
    assertTrue(validAnagram.validAnagramMap(anagram1, anagram3));
    assertTrue(validAnagram.validAnagramMap(anagram2, anagram3));
    assertFalse(validAnagram.validAnagramMap(anagram1, different));
    assertFalse(validAnagram.validAnagramMap(anagram2, different));
    assertFalse(validAnagram.validAnagramMap(anagram3, different));
    assertFalse(validAnagram.validAnagramMap(anagram1, bigWord));
  }
}