package ca.jrvs.practice.codingChallenge.math;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class SwapNumbersTest {

  SwapNumbers testSwapper = new SwapNumbers();
  int[] arr1 = new int[]{1, 2, 3};
  int[] arr2 = new int[]{9999, 654321};
  int[] arr3 = new int[]{Integer.MAX_VALUE - 10, 0, Integer.MAX_VALUE};

  @Test
  public void swapNumbersBitManip() {
    testSwapper.swapNumbersBitManip(arr1, 0, 2);
    assertArrayEquals(new int[]{3, 2, 1}, arr1);
    testSwapper.swapNumbersBitManip(arr2, 0, 1);
    assertArrayEquals(new int[]{654321, 9999}, arr2);
    testSwapper.swapNumbersBitManip(arr3, 0, 2);
    assertArrayEquals(new int[]{Integer.MAX_VALUE, 0, Integer.MAX_VALUE - 10}, arr3);
  }

  @Test
  public void swapNumbersMath() {
    testSwapper.swapNumbersMath(arr1, 0, 2);
    assertArrayEquals(new int[]{3, 2, 1}, arr1);
    testSwapper.swapNumbersMath(arr2, 0, 1);
    assertArrayEquals(new int[]{654321, 9999}, arr2);
    testSwapper.swapNumbersMath(arr3, 0, 2);
    assertArrayEquals(new int[]{Integer.MAX_VALUE, 0, Integer.MAX_VALUE - 10}, arr3);
  }
}