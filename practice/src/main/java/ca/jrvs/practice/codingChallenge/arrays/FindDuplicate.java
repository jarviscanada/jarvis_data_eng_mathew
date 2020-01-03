package ca.jrvs.practice.codingChallenge.arrays;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Ticket: https://www.notion.so/Find-the-Duplicate-Number-e2a7af9dac79464ab0e18c55204a8558
 */
public class FindDuplicate {

  /**
   * Finds the first duplicate value in the array by sorting it, then comparing each value to its
   * right neighbor. The last value need not be explicitly checked. Time complexity is O(n log(n)),
   * due to Arrays.sort using a variant of Quicksort (which is in-place).
   *
   * @param nums An array of ints to check for a duplicate in
   * @return the duplicated value, or -1 if no duplicate exists (all array elements are >= 1)
   */
  public int findDuplicateSort(int[] nums) {
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 1; i++) {
      if (nums[i] == nums[i + 1]) {
        return nums[i];
      }
    }
    return -1;
  }

  /**
   * Finds the first duplicate value in an array using Set's uniqueness property. Set returns false
   * if an add would violate uniqueness, which means we found a dupe. Time complexity is O(n), if we
   * assume a good hash spread and minimal Set growth.
   *
   * @param nums Array of ints to search for a duplicate in
   * @return The duplicated element, or -1 if none found (all array elements are >= 1)
   */
  public int findDuplicateSet(int[] nums) {
    Set<Integer> found = new HashSet<>();
    for (int x : nums) {
      if (!found.add(x)) {
        return x;
      }
    }
    return -1;
  }

}
