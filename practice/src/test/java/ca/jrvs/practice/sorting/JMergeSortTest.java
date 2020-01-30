package ca.jrvs.practice.sorting;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class JMergeSortTest {

  JMergeSort testMergeSort = new JMergeSort();
  int[] values = new int[]{7, 4, 1, 9, 6, 3, 8, 5, 2, 5};
  int[] correct = new int[]{1, 2, 3, 4, 5, 5, 6, 7, 8, 9};

  @Test
  public void mergeSort() {
    testMergeSort.mergeSort(values);
    assertArrayEquals(correct, values);
  }
}