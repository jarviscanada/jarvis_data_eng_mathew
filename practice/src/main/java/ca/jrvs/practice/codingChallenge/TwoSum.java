package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

  /**
   * Naive approach. Checks every possible pair of values to see if they add up to the target sum.
   * Returns the first valid pairing. Time complexity is O(n^2) due to nested iteration of array.
   *
   * @param nums Array of ints to scan for valid twoSum
   * @param target The target sum we must check for pairs of
   * @return an array containing the two indices of a pair of values that sum up to the target
   */
  public int[] twoSumBruteForce(int[] nums, int target) {
    for (int i = 0; i < nums.length-1; i++) {
      for (int j = i+1; j < nums.length; j++) {
        if (nums[i] + nums[j] == target) {
          return new int[]{i, j};
        }
      }
    }
    return new int[] {-1,-1};
  }

  /**
   * Finds a pair of indices that sum up to the specified target value. This implementation assumes
   * that the array is sorted in ascending order. Time complexity is O(n) since each array element
   * is visited at most once.
   *
   * @param nums An array of ints for twoSum
   * @param target The target value to sum up using the given array
   * @return the two indices whose values sum up to the target
   */
  public int[] twoSumSorted(int[] nums, int target) {
    int firstIndex = 0;
    int secondIndex = nums.length - 1;
    while (firstIndex < secondIndex) {
      int sum = nums[firstIndex] + nums[secondIndex];
      if (sum == target) {
        return new int[]{firstIndex, secondIndex};
      } else if (sum < target) {
        firstIndex++;
      } else {
        secondIndex--;
      }
    }
    return new int[]{-1,-1};
  }

  /**
   * Finds two indices whose values sum up to the target value utilizing a Map. Time complexity is
   * O(n) because we only iterate through the array once.
   *
   * @param nums An array of ints to perform TwoSum on.
   * @param target The target value that two ints in the array must sum up to
   * @return Twi indices whose values sum up to the given target.
   */
  public int[] twoSumMap(int[] nums, int target) {
    Map<Integer, Integer> indexMap = new HashMap<>();
    int toFind;
    for (int i = 0; i < nums.length; i++) {
      toFind = target - nums[i];
      if (indexMap.containsKey(toFind)) {
        return new int[]{indexMap.get(toFind), i};
      } else {
        indexMap.put (nums[i], i);
      }
    }
    return new int[]{-1,-1};
  }
}
