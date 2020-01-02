package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoveElementTest {

  int[] test1 = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 5, 6};
  int[] test2 = {1, 2, 3, 4, 5};
  int[] test3 = {1, 0, 1, 0, 1, 2, 3};
  int[] leetTest = {3, 2, 2, 3};
  RemoveElement removeTest = new RemoveElement();

  @Test
  public void removeElementToBack() {
    assertEquals(9, removeTest.removeElementToBack(test1, 2));
    assertEquals(5, removeTest.removeElementToBack(test2, 7));
    assertEquals(4, removeTest.removeElementToBack(test3, 1));
    assertEquals(2, removeTest.removeElementToBack(leetTest, 3));
  }

  @Test
  public void removeElementSlideDown() {
    assertEquals(9, removeTest.removeElementSlideDown(test1, 2));
    assertEquals(5, removeTest.removeElementSlideDown(test2, 7));
    assertEquals(4, removeTest.removeElementSlideDown(test3, 1));
    assertEquals(2, removeTest.removeElementSlideDown(leetTest, 3));
  }
}