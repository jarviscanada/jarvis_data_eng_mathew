package ca.jrvs.practice.codingChallenge.arrays;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class GetDuplicatedCharsTest {

  String test1 = "aaaaA";
  String test2 = "";
  String test3 = "The quick brown fox jumps over a lazy dog";
  String test4 = "_101 Dalmations!!!_";

  GetDuplicatedChars getDuplicatedChars = new GetDuplicatedChars();

  @Test
  public void getDuplicateCharsSet() {
    assertArrayEquals(new char[]{'a'}, getDuplicatedChars.getDuplicateCharsSet(test1));
    assertArrayEquals(new char[]{}, getDuplicatedChars.getDuplicateCharsSet(test2));
    assertArrayEquals(new char[]{'o', 'u', 'e', 'r', 'a'},
        getDuplicatedChars.getDuplicateCharsSet(test3));
    assertArrayEquals(new char[]{'a'}, getDuplicatedChars.getDuplicateCharsSet(test4));

  }
}