package ca.jrvs.practice.codingChallenge;

// Jarvis Coding Challenge: Given some string, print char followed by int value of char
// ASCII only, no whitespace or numbers in input. No collections allowed.
public class PrintLetterNumber {

  /**
   * Inserts the alphabet-relative index of each char in a string next to its respective char such
   * that a = 1, b = 2, A = 27, B = 28, and so on. No numbers or whitespace are expected. Time
   * complexity is O(n) due to linear array scanning.
   *
   * @param input An input string
   */
  public void printLetterNumber(String input) {
    char[] chars = input.toCharArray();
    StringBuilder builder = new StringBuilder();

    for (char c : chars) {
      builder.append(c);
      int charVal = c;
      if (charVal >= 'a') {
        charVal -= 'a' - 1;
      } else {
        charVal -= 'A' - 27;
      }
      builder.append(charVal);
    }
    System.out.println(builder.toString());
  }
}
