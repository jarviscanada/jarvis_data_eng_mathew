package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

// Leetcode specifics: Remove all dupes in-place. Return new effective length.
public class RemoveDuplicatesSortedArray {

  /**
   * Removes duplicate values from a sorted array of ints by copying over the duplicate value.
   * Time complexity is O(n^2) in the worst case since we may need to copy the array on every
   * iteration. The average time complexity is O(n).
   *
   * @param nums A sorted array of ints to remove duplicates from.
   * @return The new effective length of the array.
   */
  public int removeDuplicatesSortedCopyOver(int[] nums) {
    int newLen = nums.length;
    for (int i = 0; i < newLen - 1; i++) {
      if (nums[i] == nums[i+1]) {
        System.arraycopy(nums, i+1, nums, i, nums.length - (i + 1));
        newLen--;
        i--;
      }
    }
    return newLen;
  }

  /**
   * Removes duplicate values from a sorted int array by checking for breaks in streaks of
   * duplicates, then placing that value next to the last non-duplicate value. For this algorithm,
   * a duplicate is considered any instance of a value besides the first. Time complexity is O(n)
   * since the array is only iterated once.
   *
   * @param nums Sorted array of ints to remove duplicates from
   * @return New effective length of the array
   */
  public int removeDuplicatesSortedMoveToEnd(int[] nums) {
    int newLen = 0;
    for (int i = 0; i < nums.length-1; i++) {
      if (nums[i] != nums[i+1]) {
        nums[++newLen] = nums[i+1];
      }
    }
    newLen++;
    return newLen;
  }

}
