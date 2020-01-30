package ca.jrvs.practice.sorting;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class JQuickSortTest {

  int[] arr = new int[]{5, 1, 3, 7, 9, 4, 6, 5, 2};
  int[] correct = new int[]{1, 2, 3, 4, 5, 5, 6, 7, 9};
  JQuickSort testQuickSort = new JQuickSort();

  @Test
  public void quickSort() {
    testQuickSort.quickSort(arr);
    assertArrayEquals(correct, arr);
  }
}