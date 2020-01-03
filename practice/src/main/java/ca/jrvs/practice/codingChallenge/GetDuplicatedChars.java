package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GetDuplicatedChars {

  /**
   * Scans a string for duplicate characters and returns an array containing each character that
   * appears two or more times within the string. Time complexity is O(n) from linear scanning of
   * a char array.
   *
   * @param input A string to scan for duplicate chars in
   * @return An array of chars that were duplicated in the string
   */
  public char[] getDuplicateCharsSet(String input) {
    Set<Character> uniqueChars = new HashSet<>();
    Set<Character> duplicateCharSet = new HashSet<>();
    StringBuilder duplicates = new StringBuilder();
    for (Character c : input.toCharArray()) {
      if (Character.isAlphabetic(c)) {
        if (!uniqueChars.add(c) && duplicateCharSet.add(c)) {
          duplicates.append(c);
        }
      }
    }
    System.out.println(Arrays.toString(duplicates.toString().toCharArray()));
    return duplicates.toString().toCharArray();
  }

}
