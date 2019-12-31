package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

public class ContainsDuplicateTest {

  static int[] dupe1;
  static int[] dupe2;
  static int[] noDupe1;
  static int[] noDupe2;
  static int[] oneVal;
  static ContainsDuplicate test;

  @BeforeClass
  public static void makeArrays() {
    dupe1 = new int[]{1, 2, 3, 4, 5, 1};
    dupe2 = new int[]{1, 1, 2, 2, 3, 3};
    noDupe1 = new int[]{9, 8, 7, 6, 5};
    noDupe2 = new int[]{2, 4, 6, 8, 10};
    oneVal = new int[]{5};
    test = new ContainsDuplicate();
  }

  @Test
  public void containsDuplicateScan() {
    assertTrue(test.containsDuplicateScan(dupe1));
    assertTrue(test.containsDuplicateScan(dupe2));
    assertFalse(test.containsDuplicateScan(noDupe1));
    assertFalse(test.containsDuplicateScan(noDupe2));
    assertFalse(test.containsDuplicateScan(oneVal));
  }

  @Test
  public void containsDuplicateSet() {
    assertTrue(test.containsDuplicateSet(dupe1));
    assertTrue(test.containsDuplicateSet(dupe2));
    assertFalse(test.containsDuplicateSet(noDupe1));
    assertFalse(test.containsDuplicateSet(noDupe2));
    assertFalse(test.containsDuplicateSet(oneVal));
  }

  @Test
  public void containsDuplicateOneLine() {
    assertTrue(test.containsDuplicateOneLine(dupe1));
    assertTrue(test.containsDuplicateOneLine(dupe2));
    assertFalse(test.containsDuplicateOneLine(noDupe1));
    assertFalse(test.containsDuplicateOneLine(noDupe2));
    assertFalse(test.containsDuplicateOneLine(oneVal));
  }
}