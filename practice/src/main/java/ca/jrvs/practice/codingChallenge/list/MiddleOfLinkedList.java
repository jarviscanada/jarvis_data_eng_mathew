package ca.jrvs.practice.codingChallenge.list;

import ca.jrvs.practice.codingChallenge.list.LinkedListCycle.ListNode;

/**
 * Ticket: https://www.notion.so/Middle-of-the-Linked-List-f0678aadaa1841aca3faed01d0516e0a
 */
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
