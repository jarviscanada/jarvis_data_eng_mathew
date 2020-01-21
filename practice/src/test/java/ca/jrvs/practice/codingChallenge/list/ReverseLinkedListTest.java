package ca.jrvs.practice.codingChallenge.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import ca.jrvs.practice.codingChallenge.list.LinkedListCycle.ListNode;
import org.junit.Before;
import org.junit.Test;

public class ReverseLinkedListTest {

  ListNode node1;
  ListNode node2;
  ListNode node3;
  ReverseLinkedList reverseLinkedList;

  @Before
  public void setUp() throws Exception {
    reverseLinkedList = new ReverseLinkedList();
    node1 = new ListNode(1);
    node2 = new ListNode(2);
    node3 = new ListNode(3);
    node1.next = node2;
    node2.next = node3;
    node3.next = null;
  }

  @Test
  public void reverseLinkedListIter() {
    ListNode head = node1;
    head = reverseLinkedList.reverseLinkedListIter(head);
    assertEquals(node3.val, head.val);
    assertEquals(node2.val, head.next.val);
  }

  @Test
  public void reverseLinkedListIter_Single() {
    node1.next = null;
    node1 = reverseLinkedList.reverseLinkedListIter(node1);
    assertNull(node1.next);
  }

  @Test
  public void reverseLinkedListRecursive() {
    ListNode head = node1;
    head = reverseLinkedList.reverseLinkedListRecursive(head);
    assertEquals(head.val, node3.val);
    assertEquals(head.next.val, node2.val);
  }

  @Test
  public void reverseLinkedListRecursive_Single() {
    node1.next = null;
    node1 = reverseLinkedList.reverseLinkedListRecursive(node1);
    assertNull(node1.next);
  }
}