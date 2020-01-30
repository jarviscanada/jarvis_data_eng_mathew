package ca.jrvs.practice.search;

import java.util.Optional;

public class BinarySearch {

  /**
   * Finds the index of the target value in the given array using an iterative binary search.
   *
   * @param arr    The array to search
   * @param target The value to look for
   * @param <T>    The type of the array and value
   * @return An Optional which contains either the index of target, or Optional.empty()
   */
  public static <T extends Comparable<T>> Optional<Integer> binarySearchIter(T[] arr, T target) {
    int mid = arr.length / 2;
    int start = 0;
    int end = arr.length;
    T midVal;

    while (start < end && mid < arr.length) {
      midVal = arr[mid];

      if (target.compareTo(midVal) < 0) {
        end = mid - 1;
      } else if (target.compareTo(midVal) > 0) {
        start = mid + 1;
      } else {
        return Optional.of(mid);
      }
      mid = ((end - start) / 2) + start;
    }
    return Optional.empty();
  }

  public static <T extends Comparable<T>> Optional<Integer> binarySearchRecurse(T[] arr, T target) {
    int start = 0;
    int end = arr.length;
    return binarySearchRecurse(arr, target, start, end);
  }

  private static <T extends Comparable<T>> Optional<Integer> binarySearchRecurse(T[] arr, T target,
      int start, int end) {
    if (start >= end) {
      return Optional.empty();
    }
    int mid = ((end - start) / 2) + start;
    T midVal = arr[mid];
    if (target.compareTo(midVal) < 0) {
      return binarySearchRecurse(arr, target, start, mid - 1);
    } else if (target.compareTo(midVal) > 0) {
      return binarySearchRecurse(arr, target, mid + 1, end);
    } else {
      return Optional.of(mid);
    }
  }

}
