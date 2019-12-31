package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class ContainsDuplicate {

  /**
   * Check for duplicates by scanning the list from left to right, starting at the element we're
   * checking for duplicates of. Time complexity is O(n^2) since we're double-scanning the array
   *
   * @param nums an array of ints to scan for duplicates
   * @return true if there's a duplicate, false if not.
   */
  public boolean containsDuplicateScan(int[] nums) {
    for (int i = 0; i < nums.length - 1; i++) {
      for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] == nums[j]) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check for duplicates by using the uniqueness property of Sets. Time complexity is O(n), barring
   * bad hash and set growth, since array is only scanned once
   *
   * @param nums an array of ints to scan for duplicates
   * @return true if duplicate is found, false if not.
   */
  public boolean containsDuplicateSet(int[] nums) {
    Set<Integer> foundNums = new HashSet<>();
    for (int check : nums) {
      if (!foundNums.add(check)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Nonsense one-liner, because everyone loves one-liners, right? Creates a distinct IntStream,
   * then compares the element count to the source array. Distinct uses HashSet method, so time
   * complexity is O(n). Stream overhead makes this worse than pure Set method.
   *
   * @param nums Array of numbers to check for duplicates
   * @return true if a duplicate is found, false otherwise.
   */
  public boolean containsDuplicateStream(int[] nums) {
    return !(IntStream.of(nums).distinct().count() == nums.length);
  }
}
