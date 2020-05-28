package ca.jrvs.practice.dataStructure.stackQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LinkedJListJDequeTest {

  LinkedJListJDeque<Integer> testDeque;

  @Before
  public void setup() {
    testDeque = new LinkedJListJDeque<>();
  }

  @Test
  public void enqueue() {
    for (int i = 1; i <= 5; i++) {
      testDeque.enqueue(i);
      assertEquals(i, testDeque.size());
      assertEquals(1, testDeque.peek().intValue());
    }
  }

  @Test
  public void dequeue_OneElemList() {
    testDeque.enqueue(1);
    int deQ = testDeque.dequeue();
    assertEquals(1, deQ);
    assertEquals(0, testDeque.size());
    assertNull(testDeque.peek());
  }

  @Test
  public void dequeue() {
    testDeque.enqueue(1);
    testDeque.enqueue(2);
    testDeque.enqueue(3);
    int deQ = testDeque.dequeue();
    assertEquals(1, deQ);
    assertEquals(2, testDeque.size());
    assertEquals(2, testDeque.peek().intValue());
    deQ = testDeque.dequeue();
    assertEquals(2, deQ);
    assertEquals(1, testDeque.size());
    assertEquals(3, testDeque.peek().intValue());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void dequeue_Empty() {
    testDeque.dequeue();
  }

  @Test
  public void push() {
    for (int i = 1; i <= 5; i++) {
      testDeque.push(i);
      assertEquals(i, testDeque.size());
      assertEquals(1, testDeque.peek().intValue());
    }
  }

  @Test
  public void pop_OneElemList() {
    testDeque.push(1);
    int popped = testDeque.pop();
    assertEquals(1, popped);
    assertNull(testDeque.peek());
    assertEquals(0, testDeque.size());
  }

  @Test
  public void pop() {
    testDeque.push(1);
    testDeque.push(2);
    testDeque.push(3);

    int popped = testDeque.pop();
    assertEquals(3, popped);
    assertEquals(2, testDeque.size());
    assertEquals(1, testDeque.peek().intValue());

    popped = testDeque.pop();
    assertEquals(2, popped);
    assertEquals(1, testDeque.size());
    assertEquals(1, testDeque.peek().intValue());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void pop_Empty() {
    testDeque.pop();
  }

  @Test
  public void peek() {
    assertNull(testDeque.peek());
    testDeque.push(1);
    assertEquals(1, testDeque.peek().intValue());
    testDeque.push(2);
    testDeque.push(3);
    testDeque.enqueue(4);
    assertEquals(1, testDeque.peek().intValue());
    testDeque.dequeue();
    assertEquals(2, testDeque.peek().intValue());
    testDeque.pop();
    assertEquals(2, testDeque.peek().intValue());
  }
}