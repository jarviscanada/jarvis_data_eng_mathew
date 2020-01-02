package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class FindDuplicateTest {

  int[] leetTest = {1,3,4,2,2};
  int[] test1 = {1,2,3,3,3,5};
  int[] test2 = {1,5,9};
  int[] test3 = {1,1,2,2,3,3};
  int[] test4 = {1,2,3,4,1};
  
  FindDuplicate findDuplicateTest = new FindDuplicate();

  @Test
  public void findDuplicateSort() {
    assertEquals(2, findDuplicateTest.findDuplicateSort(leetTest));
    assertEquals(3, findDuplicateTest.findDuplicateSort(test1));
    assertEquals(-1, findDuplicateTest.findDuplicateSort(test2));
    assertEquals(1, findDuplicateTest.findDuplicateSort(test3));
    assertEquals(1, findDuplicateTest.findDuplicateSort(test4));
  }

  @Test
  public void findDuplicateSet() {
    assertEquals(2, findDuplicateTest.findDuplicateSet(leetTest));
    assertEquals(3, findDuplicateTest.findDuplicateSet(test1));
    assertEquals(-1, findDuplicateTest.findDuplicateSet(test2));
    assertEquals(1, findDuplicateTest.findDuplicateSet(test3));
    assertEquals(1, findDuplicateTest.findDuplicateSet(test4));
  }
}