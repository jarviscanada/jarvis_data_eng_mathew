package ca.jrvs.practice.search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BinarySearchTest {

  Integer[] vals1 = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
  Integer[] vals2 = new Integer[]{2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
  Character[] chars = new Character[]{'a', 'b', 'c', 'd', 'e'};

  @Test
  public void binarySearchIter() {
    assertEquals(5, BinarySearch.binarySearchIter(vals1, 5).orElse(-1).intValue());
    assertEquals(3, BinarySearch.binarySearchIter(vals2, 8).orElse(-1).intValue());
    assertEquals(-1, BinarySearch.binarySearchIter(vals1, 99).orElse(-1).intValue());
    assertEquals(4, BinarySearch.binarySearchIter(chars, 'e').orElse(-1).intValue());
  }

  @Test
  public void binarySearchRecurse() {
    assertEquals(5, BinarySearch.binarySearchRecurse(vals1, 5).orElse(-1).intValue());
    assertEquals(3, BinarySearch.binarySearchRecurse(vals2, 8).orElse(-1).intValue());
    assertEquals(-1, BinarySearch.binarySearchRecurse(vals1, 99).orElse(-1).intValue());
    assertEquals(4, BinarySearch.binarySearchRecurse(chars, 'e').orElse(-1).intValue());
  }
}