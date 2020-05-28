package ca.jrvs.practice.codingChallenge.arrays;

import java.util.Arrays;

/**
 * Ticket: https://www.notion.so/Remove-Element-e7fab49d872d4defaa6efd1e4f5d095f
 */
public class RemoveElement {

  /**
   * Removes all of a specified element from an array by moving the element to the back of the array
   * then moving the effective back of the array forward by 1. Time complexity is O(n) as we iterate
   * through the array once.
   *
   * @param nums Array of ints to "remove" values from
   * @param val  an int to remove all instances of from the array
   * @return the new length of the array.
   */
  public int removeElementToBack(int[] nums, int val) {
    int backOfArray = nums.length;

    for (int i = 0; i < backOfArray; i++) {
      if (nums[i] == val) {
        int swap = nums[i];
        nums[i] = nums[--backOfArray];
        nums[backOfArray] = swap;
        i--;
      }
    }
    return backOfArray;
  }

  /**
   * Removes all instances of a specified element from an array by shifting every element after it
   * down by one index, then back-stepping. Time complexity is O(n^2) since we have nested for loops
   * iterating through the array.
   *
   * @param nums array of ints to remove some value from
   * @param val  the value to remove from the array
   * @return the new length of the array
   */
  public int removeElementSlideDown(int[] nums, int val) {
    int newLen = nums.length;
    for (int i = 0; i < newLen; i++) {
      if (nums[i] == val) {
        for (int j = i; j < newLen - 1; j++) {
          nums[j] = nums[j + 1];
        }
        newLen--;
        i--;
      }
    }
    System.out.println(Arrays.toString(nums));
    return newLen;
  }
}
