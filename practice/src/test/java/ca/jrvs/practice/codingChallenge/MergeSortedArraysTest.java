package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class MergeSortedArraysTest {

  int[] leetcode1 = {4, 5, 6, 0, 0, 0};
  int[] leetcode2 = {1, 2, 3};
  int leetcodeLen1 = 3;
  int leetcodeLen2 = 3;

  MergeSortedArrays mergeSortedArraysTest = new MergeSortedArrays();

  @Test
  public void mergeSortedArraysReSort() {
    mergeSortedArraysTest.mergeSortedArraysReSort(leetcode1, leetcodeLen1, leetcode2, leetcodeLen2);
    assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, leetcode1);
  }

  @Test
  public void mergeSortedArraysCopying() {
    mergeSortedArraysTest
        .mergeSortedArraysCopying(leetcode1, leetcodeLen1, leetcode2, leetcodeLen2);
    assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6}, leetcode1);
  }
}