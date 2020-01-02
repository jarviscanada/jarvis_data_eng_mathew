package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

// Leetcode specifics: two sorted int arrays, merge arr2 into arr1
public class MergeSortedArrays {

  /**
   * Merge the contents of nums2 into nums1, then re-sort nums1. Time complexity is O(n log(n)) due
   * to quicksort.
   *
   * @param nums1 Larger int array to merge nums2 into. Must contain enough empty space to fit the
   *              contents of nums2. Must be sorted.
   * @param len1  Effective length of first array
   * @param nums2 Smaller int array to merge into nums1. Must be small enough to fit in nums1. Must
   *              be sorted.
   * @param len2  Effective length of nums2, may be just nums2.length
   */
  public void mergeSortedArraysReSort(int[] nums1, int len1, int[] nums2, int len2) {
    for (int i = 0; i < len2; i++) {
      nums1[len1 + i] = nums2[i];
    }
    Arrays.sort(nums1);
    System.out.println("Re-sort: " + Arrays.toString(nums1));
  }

  /**
   * Merge nums2 into nums1 over a single iteration while maintaining sort in nums1 by moving values
   * backwards in nums1 to make room for the value to merge in at the correct spot. Time Complexity
   * is O(mn) where m and n are the size of nums1 and nums2, since nums1 has some contents copied n
   * times.
   *
   * @param nums1 Larger array of ints to merge nums2 into. Must be large enough to accommodate all
   *              values in nums2 and must be sorted.
   * @param len1  The effective length of nums1
   * @param nums2 Smaller array of ints to merge into nums1. Must be able to fit fully in nums1 and
   *              must be sorted.
   * @param len2  The effective length of nums2
   */
  public void mergeSortedArraysCopying(int[] nums1, int len1, int[] nums2, int len2) {
    int index1 = 0;
    int index2 = 0;
    int checkVal;
    int mergeVal;
    while (index2 < len2 && index1 < nums1.length) {
      checkVal = nums1[index1];
      mergeVal = nums2[index2];
      if (mergeVal < checkVal) {
        System.arraycopy(nums1, index1, nums1, index1 + 1, len1 - index1);
        nums1[index1] = mergeVal;
        index2++;
        len1++;
      } else if (index1 >= len1) {
        nums1[index1] = mergeVal;
        index2++;
        len1++;
      }
      index1++;
    }
    System.out.println("Copying: " + Arrays.toString(nums1));
  }
}
