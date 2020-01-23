package ca.jrvs.practice.codingChallenge.stackQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QueueUsingStackTest {

  QueueUsingStack<Integer> testQueueStacks = new QueueUsingStack<>();

  @Test
  public void enqueueFlipFlop() {
    testQueueStacks.enqueueFlipFlop(1);
    testQueueStacks.enqueueFlipFlop(2);
    testQueueStacks.enqueueFlipFlop(3);
    testQueueStacks.enqueueFlipFlop(4);
    assertEquals(1, testQueueStacks.front.peek().intValue());
  }

  @Test
  public void dequeueFlipFlop() {
    enqueueFlipFlop();
    int value = testQueueStacks.dequeueFlipFlop();
    assertEquals(1, value);
    value = testQueueStacks.dequeueFlipFlop();
    assertEquals(2, value);
  }

  @Test
  public void enqueueSplitQueue() {
    testQueueStacks.enqueueSplitQueue(1);
    testQueueStacks.enqueueSplitQueue(2);
    testQueueStacks.enqueueSplitQueue(3);
    testQueueStacks.enqueueSplitQueue(4);
    assertEquals(1, testQueueStacks.peekSplitQueue().intValue());
  }

  @Test
  public void dequeueSplitQueue() {
    enqueueSplitQueue();
    int value = testQueueStacks.dequeueSplitQueue();
    assertEquals(1, value);
    value = testQueueStacks.dequeueSplitQueue();
    assertEquals(2, value);
  }

  @Test
  public void peekSplitQueue() {
    enqueueSplitQueue();
    assertEquals(1, testQueueStacks.peekSplitQueue().intValue());
  }

  @Test
  public void isEmpty() {
    assertTrue(testQueueStacks.isEmpty());
    enqueueSplitQueue();
    assertFalse(testQueueStacks.isEmpty());
  }
}