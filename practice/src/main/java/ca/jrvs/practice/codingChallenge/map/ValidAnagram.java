package ca.jrvs.practice.codingChallenge.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ticket: https://www.notion.so/Valid-Anagram-8cda52dde5f147ad910f5505872d69e5
 */
public class ValidAnagram {

  /**
   * Determines if two strings are valid anagrams of each other by sorting the chars in them, then
   * checking if each sorted char is the same in both of them. Time complexity is O(n log(n)) since
   * we're using Arrays.sort's QuickSort. Space complexity is O(n) since we're allocating two char
   * arrays for each string's letters.
   *
   * @param word1 The first word to check
   * @param word2 The second word to check
   * @return True if the two words are valid anagrams of each other, else false
   */
  public boolean validAnagramSorting(String word1, String word2) {
    if (word1.length() != word2.length()) {
      return false;
    }

    char[] letters1 = word1.toCharArray();
    char[] letters2 = word2.toCharArray();
    Arrays.sort(letters1);
    Arrays.sort(letters2);

    for (int i = 0; i < letters1.length; i++) {
      if (letters1[i] != letters2[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if two words are valid anagrams of each other by counting occurrences of letters in
   * the first word, then decrementing those occurrences when scanning the second word. Time
   * complexity is O(n) since we are only performing linear scans of each word. Space complexity is
   * O(1) since the map has a fixed maximum size of the charset being used, with Leetcode's
   * assumptions (Lower case ASCII only), that means 26 slots are needed.
   *
   * @param word1 The first word to check
   * @param word2 The second word to check
   * @return True of the two words are valid anagrams of each other, else false.
   */
  public boolean validAnagramMap(String word1, String word2) {
    if (word1.length() != word2.length()) {
      return false;
    }
    Map<Character, Integer> unusedLetters = new HashMap<>(27, 1);
    for (int i = 0; i < word1.length(); i++) {
      char c = word1.charAt(i);
      if (unusedLetters.containsKey(c)) {
        unusedLetters.put(c, unusedLetters.get(c) + 1);
      } else {
        unusedLetters.put(c, 1);
      }
    }

    for (int j = 0; j < word2.length(); j++) {
      char c = word2.charAt(j);
      if (unusedLetters.containsKey(c)) {
        unusedLetters.put(c, unusedLetters.get(c) - 1);
        if (unusedLetters.get(c) < 0) {
          return false;
        }
      } else {
        return false;
      }
    }

    for (int n : unusedLetters.values()) {
      if (n != 0) {
        return false;
      }
    }
    return true;
  }
}
