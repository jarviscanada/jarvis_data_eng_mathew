package ca.jrvs.practice.codingChallenge.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import ca.jrvs.practice.codingChallenge.list.LinkedListCycle.ListNode;
import org.junit.Before;
import org.junit.Test;

public class RemoveNthFromEndTest {

  ListNode head;
  ListNode node2;
  ListNode node3;
  ListNode node4;
  ListNode node5;
  RemoveNthFromEnd removeNthFromEnd;

  @Before
  public void setUp() throws Exception {
    head = new ListNode(1);
    node2 = new ListNode(2);
    node3 = new ListNode(3);
    node4 = new ListNode(4);
    node5 = new ListNode(5);
    removeNthFromEnd = new RemoveNthFromEnd();
  }

  @Test
  public void removeNthFromEnd() {
    head.next = node2;
    node2.next = node3;
    node3.next = node4;
    node4.next = node5;
    head = removeNthFromEnd.removeNthFromEnd(head, 2);
    assertEquals(node3.next, node5);
  }

  @Test
  public void removeNthFromEnd_Tail() {
    head.next = node2;
    node2.next = node3;
    head = removeNthFromEnd.removeNthFromEnd(head, 1);
    assertNull(node2.next);
  }

  @Test
  public void removeNthFromEnd_Head() {
    head.next = node2;
    node2.next = node3;
    node3.next = node4;
    head = removeNthFromEnd.removeNthFromEnd(head, 4);
    assertEquals(head, node2);
  }

  @Test
  public void removeNthFromEnd_OnlyNode() {
    head.next = null;
    head = removeNthFromEnd.removeNthFromEnd(head, 1);
    assertNull(head);
  }
}