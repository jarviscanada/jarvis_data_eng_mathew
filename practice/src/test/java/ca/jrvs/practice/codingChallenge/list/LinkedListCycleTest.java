package ca.jrvs.practice.codingChallenge.list;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.jrvs.practice.codingChallenge.list.LinkedListCycle.ListNode;
import org.junit.Test;

public class LinkedListCycleTest {

  ListNode a = new ListNode(1);
  ListNode b = new ListNode(2);
  ListNode c = new ListNode(3);
  ListNode d = new ListNode(4);
  LinkedListCycle cycleTest = new LinkedListCycle();

  @Test
  public void hasCycle_fullCircle() {
    a.next = b;
    b.next = c;
    c.next = d;
    d.next = a;
    assertTrue(cycleTest.hasCycle(a));
    assertTrue(cycleTest.hasCycle(b));
    assertTrue(cycleTest.hasCycle(c));
    assertTrue(cycleTest.hasCycle(d));
  }

  @Test
  public void hasCycle_SelfLoop() {
    a.next = a;
    assertTrue(cycleTest.hasCycle(a));
  }

  @Test
  public void hasCycle_PartialLoop() {
    a.next = b;
    b.next = c;
    c.next = b;
    assertTrue(cycleTest.hasCycle(a));
    assertTrue(cycleTest.hasCycle(b));
    assertTrue(cycleTest.hasCycle(c));
  }

  @Test
  public void hasCycle_NoCycle() {
    a.next = b;
    b.next = c;
    c.next = d;
    d.next = null;
    assertFalse(cycleTest.hasCycle(a));
    assertFalse(cycleTest.hasCycle(b));
    assertFalse(cycleTest.hasCycle(c));
    assertFalse(cycleTest.hasCycle(d));
  }

  @Test
  public void hasCycle_Null() {
    assertFalse(cycleTest.hasCycle(null));
  }
}