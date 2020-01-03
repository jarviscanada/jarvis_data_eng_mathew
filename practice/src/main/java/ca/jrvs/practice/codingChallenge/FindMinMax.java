package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class FindMinMax {

  /**
   * Searches the input array for the largest and smallest values using a foreach loop. Time
   * complexity is O(n) due to linear search.
   *
   * @param nums Array of ints to search
   * @return Array of int containing the min and max value: [min_value, max_value]
   */
  public int[] findMinMaxForeach(int[] nums) {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    for (int x : nums) {
      if (x > max) {
        max = x;
      }
      if (x < min) {
        min = x;
      }
    }
    return new int[]{min, max};
  }

  /**
   * Finds the min and max values in the input array using Java 8's Stream API. Time complexity is
   * O(n) due to linear search.
   *
   * @param nums The int array to search for min/max values in.
   * @return An int array containing min/max values found: [min_value, max_value]
   */
  public int[] findMinMaxStream(int[] nums) {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    if (nums.length > 0) {
      IntStream minStream = Arrays.stream(nums);
      IntStream maxStream = Arrays.stream(nums);
      min = minStream.min().orElse(min);
      max = maxStream.max().orElse(max);
    }
    return new int[]{min, max};
  }

  /**
   * Searches the input array for the min and max value using Collections' min and max functions.
   * Time complexity is O(n) due to linear search and also manually boxing the int array for use
   * with Collections.
   *
   * @param nums An array of ints to search for min/max values
   * @return An array of ints containing the min and max values of the input [min_value, max_value]
   */
  public int[] findMinMaxCollections(int[] nums) {
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    if (nums.length > 0) {
      Integer[] boxedInts = new Integer[nums.length];
      for (int i = 0;i < nums.length; i++) {
        boxedInts[i] = nums[i];
      }
      List<Integer> numList = Arrays.asList(boxedInts);
      min = Collections.min(numList);
      max = Collections.max(numList);
    }
    return new int[]{min, max};
  }
}
