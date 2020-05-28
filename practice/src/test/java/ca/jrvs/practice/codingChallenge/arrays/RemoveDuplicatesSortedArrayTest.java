package ca.jrvs.practice.codingChallenge.arrays;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoveDuplicatesSortedArrayTest {

  int[] test1 = {1, 1, 2, 3};
  int[] test2 = {1, 1, 1, 1, 1, 2};
  int[] test3 = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6};
  RemoveDuplicatesSortedArray removeDuplicatesTest = new RemoveDuplicatesSortedArray();

  @Test
  public void removeDuplicatesSortedCopyOver() {
    assertEquals(3, removeDuplicatesTest.removeDuplicatesSortedCopyOver(test1));
    assertEquals(2, removeDuplicatesTest.removeDuplicatesSortedCopyOver(test2));
    assertEquals(6, removeDuplicatesTest.removeDuplicatesSortedCopyOver(test3));
  }

  @Test
  public void removeDuplicatesSortedMoveToEnd() {
    assertEquals(3, removeDuplicatesTest.removeDuplicatesSortedMoveToEnd(test1));
    assertEquals(2, removeDuplicatesTest.removeDuplicatesSortedMoveToEnd(test2));
    assertEquals(6, removeDuplicatesTest.removeDuplicatesSortedMoveToEnd(test3));
  }
}