package ca.jrvs.practice.codingChallenge.strings;

import java.util.regex.Pattern;

/**
 * Ticket: https://www.notion.so/Check-if-a-String-contains-only-digits-40448aa229a6406bb486288862102b03
 * For the sake of this challenge, absolutely no other symbols are allowed
 */
public class StringOnlyDigits {

  /**
   * Determines if a string contains only digits by checking if each character in the string falls
   * outside the range of ascii char values between 0 and 9 inclusive. Runtime complexity is O(n)
   * since we are performing a linear scan of the string with two comparisons on each char. Space
   * complexity is O(1) since we only hold onto one char at a time.
   *
   * @param s The string to check
   * @return True if the string contains only digits, else false
   */
  public boolean stringOnlyDigits(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) < '0' || s.charAt(i) > '9') {
        return false;
      }
    }
    return true;
  }

  /**
   * Determine if a string contains only digits by checking if each character is a digit using
   * Character.isDigit(x). Slower than above, but has wider coverage. Time complexity is O(n) since
   * isDigit is constant time and we're linearly scanning a string. Space complexity is O(1) since
   * we only hold onto one char at a time
   *
   * @param s The string to check
   * @return True if all chars in the string are digits, otherwise false
   */
  public boolean stringOnlyDigitsApi(String s) {
    for (int i = 0; i < s.length(); i++) {
      if (!Character.isDigit(s.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Determines if a string contains only characters by utilizing regex. Time complexity is O(n) due
   * to regex performing a linear scan of the string. Space complexity is O(1) since the only extra
   * objects defined are the Pattern and the Matcher. This method is likely slower than both
   * previous approaches.
   *
   * @param s The string to check
   * @return True if the string contains only digits, otherwise false
   */
  public boolean stringOnlyDigitsRegex(String s) {
    return !Pattern.compile("\\D").matcher(s).find();
  }
}
