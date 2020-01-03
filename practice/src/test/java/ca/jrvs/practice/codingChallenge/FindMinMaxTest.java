package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class FindMinMaxTest {

  int[] test1 = {1,2,3};
  int[] test2 = {};
  int[] test3 = {5};
  int[] test4 = {1,1,1,1,1};
  int[] test5 = {Integer.MAX_VALUE, Integer.MIN_VALUE};

  FindMinMax findMinMax = new FindMinMax();

  @Test
  public void findMinMaxForeach() {
    assertArrayEquals(new int[]{1,3},findMinMax.findMinMaxForeach(test1));
    assertArrayEquals(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE},
        findMinMax.findMinMaxForeach(test2));
    assertArrayEquals(new int[]{5,5},findMinMax.findMinMaxForeach(test3));
    assertArrayEquals(new int[]{1,1},findMinMax.findMinMaxForeach(test4));
    assertArrayEquals(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE},
        findMinMax.findMinMaxForeach(test5));
  }

  @Test
  public void findMinMaxStream() {
    assertArrayEquals(new int[]{1,3},findMinMax.findMinMaxStream(test1));
    assertArrayEquals(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE},
        findMinMax.findMinMaxStream(test2));
    assertArrayEquals(new int[]{5,5},findMinMax.findMinMaxStream(test3));
    assertArrayEquals(new int[]{1,1},findMinMax.findMinMaxStream(test4));
    assertArrayEquals(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE},
        findMinMax.findMinMaxStream(test5));
  }

  @Test
  public void findMinMaxCollections() {
    assertArrayEquals(new int[]{1,3},findMinMax.findMinMaxCollections(test1));
    assertArrayEquals(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE},
        findMinMax.findMinMaxCollections(test2));
    assertArrayEquals(new int[]{5,5},findMinMax.findMinMaxCollections(test3));
    assertArrayEquals(new int[]{1,1},findMinMax.findMinMaxCollections(test4));
    assertArrayEquals(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE},
        findMinMax.findMinMaxCollections(test5));
  }
}