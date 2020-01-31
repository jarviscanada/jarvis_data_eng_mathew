package ca.jrvs.practice.codingChallenge.strings;

/**
 * Ticket: https://www.notion.so/Rotate-String-3882a2a2f32048f6a676b480dc91e2d7
 */
public class RotateString {

  /**
   * Check if a string is a "rotation" of another string. Since a string appended with itself
   * contains all possible rotations of that string, we can check if the other string is contained
   * in the first. Time complexity is O(n^2) since we have to scan a string of length n, n times.
   * Space complexity is O(n) to acommodate the appended string.
   *
   * @param string1 The first string to check for rotational equality
   * @param string2 The second string to check for rotational equality
   * @return True if the strings can be equal through rotation, else false
   */
  public boolean rotateString(String string1, String string2) {
    if (string2 == null || string1 == null || string1.length() != string2.length()) {
      return false;
    }
    if (string1.length() == 0) {
      return true;
    }
    return (string1 + string1).contains(string2);
  }

}
