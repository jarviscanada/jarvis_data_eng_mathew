package ca.jrvs.practice.sorting;

import java.util.Arrays;

public class JMergeSort {

  public void mergeSort(int[] values) {
    System.out.println(Arrays.toString(values));
    mergeSort(values, values.length / 2);
  }

  private void mergeSort(int[] arr, int middle) {
    if (middle < 1) {
      return;
    }

    int[] left = new int[middle];
    int[] right = new int[arr.length - middle];
    int j = 0;

    for (int i = 0; i < left.length; i++) {
      left[i] = arr[j++];
    }
    for (int i = 0; i < right.length; i++) {
      right[i] = arr[j++];
    }

    mergeSort(left, left.length / 2);
    mergeSort(right, right.length / 2);
    merge(arr, left, right);
    System.out.println(Arrays.toString(arr));
  }

  private void merge(int[] dest, int[] left, int[] right) {
    int i = 0;
    int j = 0;
    int k = 0;

    while (j < left.length && k < right.length) {
      if (left[j] <= right[k]) {
        dest[i++] = left[j++];
      } else if (left[j] > right[k]) {
        dest[i++] = right[k++];
      }
    }
    while (j < left.length) {
      dest[i++] = left[j++];
    }
    while (k < right.length) {
      dest[i++] = right[k++];
    }
  }
}
