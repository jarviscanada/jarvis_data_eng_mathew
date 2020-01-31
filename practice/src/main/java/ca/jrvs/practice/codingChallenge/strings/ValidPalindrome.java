package ca.jrvs.practice.codingChallenge.strings;

/**
 * Ticket: https://www.notion.so/Valid-Palindrome-c653ee27ee55468daa55b2c5bf9da89e
 */
public class ValidPalindrome {

  /**
   * Determine if a string is a valid palindrome by scanning the string using two pointers, one that
   * moves forwards and one that moves backwards. If at any point, the characters are not equal,
   * return false. Time complexity is O(n) since this is a linear scan. Space complexity is O(n)
   * since we copy the string into a char array.
   *
   * @param string The potential palindrome to check
   * @return True if the string is a palindrome
   */
  public boolean validPalindromeTwoPointer(String string) {
    string = string.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    char[] letters = string.toCharArray();
    int i = 0;
    int j = letters.length - 1;
    while (i <= j) {
      if (letters[i++] != letters[j--]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Uses recursion to determine if a string is a palindrome. Each recursive call moves the indices
   * to compare towards the center of the array, until the two indices meet. Time complexity is O(n)
   * since this is still a linear scan of the string. Space complexity is O(n) due to recursion.
   * <p>
   * This is essentially the recursive form of the two-pointer solution.
   *
   * @param string The potential palindrome to check
   * @return True if the string is a palindrome
   */
  public boolean validPalindromeRecursion(String string) {
    string = string.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    return validPalindromeRecursion(string.toCharArray(), 0, string.length() - 1);
  }

  private boolean validPalindromeRecursion(char[] letters, int first, int second) {
    if (first < second) {
      return (letters[first] == letters[second]) &&
          validPalindromeRecursion(letters, first + 1, second - 1);
    } else {
      return letters[first] == letters[second];
    }
  }

}
