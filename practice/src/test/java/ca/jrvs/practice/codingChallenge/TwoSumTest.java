package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class TwoSumTest {

  int[] test1 = {1,2,3,4};
  int[] test2 = {3,3};
  int target1 = 5;
  int target2 = 6;
  TwoSum twoSumTest = new TwoSum();

  @Test
  public void twoSumBruteForce() {
    assertArrayEquals(new int[]{0,3}, twoSumTest.twoSumBruteForce(test1, target1));
    assertArrayEquals(new int[]{0,1}, twoSumTest.twoSumBruteForce(test2, target2));
  }

  @Test
  public void twoSumSorting() {
    assertArrayEquals(new int[]{0,3}, twoSumTest.twoSumSorted(test1, target1));
    assertArrayEquals(new int[]{0,1}, twoSumTest.twoSumSorted(test2, target2));
  }

  @Test
  public void twoSumMap() {
    assertArrayEquals(new int[]{1,2}, twoSumTest.twoSumMap(test1, target1));
    assertArrayEquals(new int[]{0,1}, twoSumTest.twoSumMap(test2, target2));
  }
}