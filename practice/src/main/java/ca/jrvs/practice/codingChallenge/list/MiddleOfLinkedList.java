package ca.jrvs.practice.codingChallenge.list;

import ca.jrvs.practice.codingChallenge.list.LinkedListCycle.ListNode;

public class MiddleOfLinkedList {

  public ListNode middleNode(ListNode head) {
    ListNode fast = head;
    ListNode slow = head;

    if (fast == null) {
      return null;
    }
    do {
      fast = fast.next;
      if (fast == null) {
        return slow;
      }
      fast = fast.next;
      slow = slow.next;
    } while (fast != null);
    return slow;
  }
}
