package ca.jrvs.practice.codingChallenge.list;

import ca.jrvs.practice.codingChallenge.list.LinkedListCycle.ListNode;

/**
 * Ticket: https://www.notion.so/Nth-Node-From-End-of-List-101a96a4c9a746c18c1832a2491d71b9
 */
public class RemoveNthFromEnd {

  /**
   * Removes the Nth node from the end of the list by utilizing two pointers. The Lead pointer finds
   * the end of the list, while the Lag pointer stays N+1 nodes behind it. When Lead hits the end of
   * the list, Lag performs the removal. If Lag is never started, the Head is removed. In this
   * scenario, the "end" of the list is the null after the last node. Runtime complexity is O(n)
   * since we only iterate through the array once. Space complexity is O(1) since we only allocate
   * two pointers.
   *
   * @param head The head of a singly-linked list of Nodes.
   * @param n    The number of nodes back from the end of the list to remove.
   * @return The new List head.
   */
  public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode lead = head;
    ListNode lag = null;
    while (lead != null) {
      lead = lead.next;
      if (lag != null) {
        lag = lag.next;
      }
      if (n-- == 0) {
        lag = head;
      }
    }
    if (lag == null) {
      head = head.next;
    } else if (lag.next != null) {
      lag.next = lag.next.next;
    }
    return head;
  }

}
