package ca.jrvs.practice.codingChallenge.set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class MissingNumberTest {

  MissingNumber testMissingNumber = new MissingNumber();
  int[] seq1 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14, 15};
  int[] seq2 = new int[]{9, 5, 1, 2, 3, 6, 4, 7, 0};
  int[] seq3 = new int[]{Integer.MAX_VALUE - 2, Integer.MAX_VALUE};

  @Test
  public void missingNumberSumAll() {
    assertEquals(10, testMissingNumber.missingNumberSumAll(seq1));
    assertEquals(8, testMissingNumber.missingNumberSumAll(seq2));
    assertEquals(Integer.MAX_VALUE - 1, testMissingNumber.missingNumberSumAll(seq3));

  }

  @Test
  public void missingNumberSet() {
    assertEquals(10, testMissingNumber.missingNumberSet(seq1));
    assertEquals(8, testMissingNumber.missingNumberSet(seq2));
    assertEquals(Integer.MAX_VALUE - 1, testMissingNumber.missingNumberSet(seq3));
  }

  @Test
  public void missingNumberGaussFormula() {
    assertEquals(10, testMissingNumber.missingNumberGaussFormula(seq1));
    assertEquals(8, testMissingNumber.missingNumberGaussFormula(seq2));
    assertNotEquals(Integer.MAX_VALUE - 1, testMissingNumber.missingNumberGaussFormula(seq3));
  }
}