package ca.jrvs.practice.codingChallenge.list;

import ca.jrvs.practice.codingChallenge.list.LinkedListCycle.ListNode;

public class ReverseLinkedList {

  /**
   * Uses Three pointers to crawl along the List and reverse the ListNode.next of each node found.
   * To swap the last node, perform one swap after the While loop ends. Time complexity is O(n) as
   * the list is iterated once. Space complexity is O(1) since we only allocate 3 pointers.
   *
   * @param head The Head of the linked list we want to reverse.
   * @return The new Head of the Linked list.
   */
  public ListNode reverseLinkedListIter(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode front = head.next;
    ListNode middle = head;
    ListNode back = null;
    while (front != null) {
      middle.next = back;
      back = middle;
      middle = front;
      front = front.next;
    }
    middle.next = back;
    return middle;
  }

  /**
   * Reverse a Linked List using recursion. Finds the last node and passes it up through the call
   * stack as the new Head, then reverses each "next" node's next pointer as we climb out of the
   * recursion. Time complexity is O(n) since we're iterating linearly through the list. Space
   * complexity is O(n) due to the call stack from recursion.
   *
   * @param head The head of the list to reverse
   * @return The new head of the reversed list
   */
  public ListNode reverseLinkedListRecursive(ListNode head) {
    if (head == null || head.next == null) {
      return head;
    }
    ListNode node = reverseLinkedListRecursive(head.next);
    head.next.next = head;
    head.next = null;
    return node;
  }
}
