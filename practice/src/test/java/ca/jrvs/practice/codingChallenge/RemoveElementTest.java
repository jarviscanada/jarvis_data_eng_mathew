package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RemoveElementTest {

  int[] test1 = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 5, 6};
  int[] test2 = {1, 2, 3, 4, 5};
  int[] test3 = {1, 0, 1, 0, 1, 2, 3};
  int[] leetTest = {3, 2, 2, 3};
  RemoveElement remove = new RemoveElement();

  @Test
  public void removeElementToBack() {
    assertEquals(9, remove.removeElementToBack(test1, 2));
    assertEquals(5, remove.removeElementToBack(test2, 7));
    assertEquals(4, remove.removeElementToBack(test3, 1));
    assertEquals(2, remove.removeElementToBack(leetTest, 3));
  }

  @Test
  public void removeElementSlideDown() {
    assertEquals(9, remove.removeElementSlideDown(test1, 2));
    assertEquals(5, remove.removeElementSlideDown(test2, 7));
    assertEquals(4, remove.removeElementSlideDown(test3, 1));
    assertEquals(2, remove.removeElementSlideDown(leetTest, 3));
  }
}