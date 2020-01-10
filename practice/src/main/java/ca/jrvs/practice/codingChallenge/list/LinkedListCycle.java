package ca.jrvs.practice.codingChallenge.list;

public class LinkedListCycle {

  /**
   * Uses two pointers to iterate through the list. The first pointer traverses two nodes at a time
   * while the second pointer traverses one node at a time. If the two pointers wind up on the same
   * node, that means a loop has been found in the LinkedList. Time complexity is O(n) as it will
   * take at most n iterations for the slow pointer to visit every node and it is impossible for the
   * fast pointer to have passed the slow one by then.
   *
   * @param head a ListNode that is part of a singly-linked LinkedList
   * @return True of a cycle is found in the LinkedList
   */
  public boolean hasCycle(ListNode head) {
    ListNode node1 = head;
    ListNode node2 = head;

    if (head == null) {
      return false;
    }
    do {
      if (node1.next != null && node1.next.next != null) {
        node1 = node1.next.next;
      } else {
        return false;
      }
      node2 = node2.next;
    } while (node1 != node2);
    return true;
  }

  static class ListNode {

    int val;
    ListNode next;

    ListNode(int val) {
      this.val = val;
      next = null;
    }
  }
}
