package ca.jrvs.practice.codingChallenge.strings;

public class Atoi {

  /**
   * Baseline for "atoi" to work against. Java's implementation of the atoi function does not handle
   * number separators or trim extra spaces, but it does complain if it overflows trying to build
   * the number. Time complexity is O(n) for linear string scan. Space complexity is O(1)
   *
   * @param string The string to turn into an int
   * @return The int represented by the String
   */
  public int atoiBuiltIn(String string) {
    try {
      return Integer.parseInt(string);
    } catch (NumberFormatException nfex) {
      return 0;
    }
  }

  /**
   * Construct an int by parsing a String. Assumes the number is at the start of the string after
   * trimming whitespace. The number is built by checking for a sign (if followed by something else)
   * then iterating through the string, multiplying the result by 10 then adding the encountered
   * digit, until a non-digit char is encountered or an overflow is detected. The result is made
   * negative if a '-' sign is detected at the start of the number.
   * <p>
   * Time complexity is O(n) since we're scanning linearly through a string. Space complexity is
   * O(1) since we are only storing 3 fixed-length values.
   *
   * @param string The string to parse as a base-10 int
   * @return The first valid int in the string, 0 if none found, or MAX_VALUE if an overflow would
   * occur, or MIN_VALUE if an underflow would occur
   */
  public int atoi(String string) {
    if (string == null) {
      return 0;
    }
    string = string.trim();
    if (string.length() < 1) {
      return 0;
    }
    int result = 0;
    char c = string.charAt(0);
    char sign = 0;
    if ((c == '-' || c == '+') && string.length() > 1) {
      sign = c;
      c = string.charAt(1);
    }
    if (!Character.isDigit(c)) {
      return 0;
    }
    for (int i = sign > 0 ? 1 : 0; i < string.length(); i++) {
      c = string.charAt(i);
      if (Character.isDigit(c)) {
        if (result > Integer.MAX_VALUE / 10) {
          return sign == '-' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        result *= 10;
        result += Character.digit(c, 10);
        if (result < 0) {
          return sign == '-' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
      } else if (c != ',') {
        break;
      }
    }
    return sign == '-' ? -result : result;
  }
}
