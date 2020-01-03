package ca.jrvs.practice.codingChallenge.arrays;

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
  static ContainsDuplicate duplicateTest;

  @BeforeClass
  public static void makeArrays() {
    dupe1 = new int[]{1, 2, 3, 4, 5, 1};
    dupe2 = new int[]{1, 1, 2, 2, 3, 3};
    noDupe1 = new int[]{9, 8, 7, 6, 5};
    noDupe2 = new int[]{2, 4, 6, 8, 10};
    oneVal = new int[]{5};
    duplicateTest = new ContainsDuplicate();
  }

  @Test
  public void containsDuplicateScan() {
    assertTrue(duplicateTest.containsDuplicateScan(dupe1));
    assertTrue(duplicateTest.containsDuplicateScan(dupe2));
    assertFalse(duplicateTest.containsDuplicateScan(noDupe1));
    assertFalse(duplicateTest.containsDuplicateScan(noDupe2));
    assertFalse(duplicateTest.containsDuplicateScan(oneVal));
  }

  @Test
  public void containsDuplicateSet() {
    assertTrue(duplicateTest.containsDuplicateSet(dupe1));
    assertTrue(duplicateTest.containsDuplicateSet(dupe2));
    assertFalse(duplicateTest.containsDuplicateSet(noDupe1));
    assertFalse(duplicateTest.containsDuplicateSet(noDupe2));
    assertFalse(duplicateTest.containsDuplicateSet(oneVal));
  }

  @Test
  public void containsDuplicateStream() {
    assertTrue(duplicateTest.containsDuplicateStream(dupe1));
    assertTrue(duplicateTest.containsDuplicateStream(dupe2));
    assertFalse(duplicateTest.containsDuplicateStream(noDupe1));
    assertFalse(duplicateTest.containsDuplicateStream(noDupe2));
    assertFalse(duplicateTest.containsDuplicateStream(oneVal));
  }
}