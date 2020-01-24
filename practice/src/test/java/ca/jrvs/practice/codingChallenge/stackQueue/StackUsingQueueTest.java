package ca.jrvs.practice.codingChallenge.stackQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class StackUsingQueueTest {

  StackUsingQueue<Integer> testStackQueues;

  @Before
  public void setup() {
    testStackQueues = new StackUsingQueue<>();
  }

  @Test
  public void pushTwoQueue() {
    for (int i = 0; i < 5; i++) {
      testStackQueues.pushTwoQueue(i);
      assertEquals(i, testStackQueues.peekTwoQueue().intValue());
    }
  }

  @Test
  public void popTwoQueue() {
    pushTwoQueue();
    assertEquals(4, testStackQueues.popTwoQueue().intValue());
    assertEquals(3, testStackQueues.popTwoQueue().intValue());
    assertEquals(2, testStackQueues.popTwoQueue().intValue());
    assertEquals(1, testStackQueues.popTwoQueue().intValue());
    assertEquals(0, testStackQueues.popTwoQueue().intValue());

  }

  @Test
  public void pushSingleQueue() {
    for (int i = 0; i < 5; i++) {
      testStackQueues.pushSingleQueue(i);
      assertEquals(i, testStackQueues.peekSingleQeue().intValue());
    }
  }

  @Test
  public void popSingleQueue() {
    pushSingleQueue();
    assertEquals(4, testStackQueues.popSingleQueue().intValue());
    assertEquals(3, testStackQueues.popSingleQueue().intValue());
    assertEquals(2, testStackQueues.popSingleQueue().intValue());
    assertEquals(1, testStackQueues.popSingleQueue().intValue());
    assertEquals(0, testStackQueues.popSingleQueue().intValue());
  }

  @Test
  public void isEmptyTwoQueue() {
    assertTrue(testStackQueues.isEmptyTwoQueue());
    testStackQueues.pushTwoQueue(1);
    assertFalse(testStackQueues.isEmptyTwoQueue());
  }

  @Test
  public void isEmptySingleQueue() {
    assertTrue(testStackQueues.isEmptySingleQueue());
    testStackQueues.pushSingleQueue(1);
    assertFalse(testStackQueues.isEmptySingleQueue());
  }
}