package ca.jrvs.practice.codingChallenge.set;

import java.util.HashSet;

/**
 * Ticket: https://www.notion.so/Missing-Number-6355da5ed2984944add1c8a37ba8b4be
 */
public class MissingNumber {

  /**
   * Find the missing number by finding the difference between the sum of the complete sequence and
   * the incomplete sequence (missingNumber = sumComplete - sumIncomplete). Time complexity is O(n)
   * since we iterate linearly through the sequence. Space complexity is O(1) since we only allocate
   * ints. This approach may not work in overflow cases.
   *
   * @param nums The sequence to check for a missing number
   * @return The number missing from the sequence
   */
  public int missingNumberSumAll(int[] nums) {
    int actual = 0;
    int expected = 0;
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    for (int n : nums) {
      min = Math.min(n, min);
      max = Math.max(n, max);
      actual += n;
    }
    for (int i = min; i < max; i++) {
      expected += i;
    }
    expected += max; // because Integer.MAX_VALUE bing the max breaks the loop
    return expected - actual;
  }

  /**
   * Finds a missing number in a sequence by scanning the sequence into a Set, then checking the
   * full sequence for membership in the set. The value not in the set is the missing number. Time
   * complexity is O(n) since we are performing linear scans and HashSet lookup time is O(1) Space
   * complexity is O(n) since we're creating a set of n elements.
   *
   * @param nums The sequence of numbers to check.
   * @return The missing value in the sequence
   */
  public int missingNumberSet(int[] nums) {
    HashSet<Integer> encountered = new HashSet<>();
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    for (int n : nums) {
      min = Math.min(min, n);
      max = Math.max(max, n);
      encountered.add(n);
    }

    for (int i = min; i <= max; i++) {
      if (!encountered.contains(i)) {
        return i;
      }
    }
    return 0;
  }

  /**
   * Determine the missing number in a sequence using Gauss' formula to determine the expected sum
   * of the sequence, then returning the difference of that and the actual sum of the sequence. This
   * solution is vulnerable to overflow and also assumes that the sequence starts at 0
   * <p>
   * Time complexity is O(n) since we still need to scan the input array. Space complexity is O(1)
   * since we only allocate two ints.
   *
   * @param nums The sequence to check
   * @return The missing value in the sequence
   */
  public int missingNumberGaussFormula(int[] nums) {
    int expected = (((nums.length) * (nums.length + 1)) / 2);
    int actual = 0;
    for (int n : nums) {
      actual += n;
    }
    return expected - actual;
  }

}
