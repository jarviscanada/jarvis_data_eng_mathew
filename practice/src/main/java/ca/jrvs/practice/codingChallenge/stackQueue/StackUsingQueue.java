package ca.jrvs.practice.codingChallenge.stackQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Ticket: https://www.notion.so/Implement-Stack-using-Queue-e1be07cb2bb94c3cbbcc02bf282644da
 */
public class StackUsingQueue<T> {

  private Queue<T> queue1 = new LinkedList<>();
  private Queue<T> queue2 = new LinkedList<>();

  /**
   * Push implementation using two Queues. If one queue is empty, push the element onto the other
   * Time and Space complexity are O(1) since we are performing 1 push and not declaring any extra
   * variables.
   *
   * @param elem the element to push
   */
  public void pushTwoQueue(T elem) {
    if (queue2.isEmpty()) {
      queue1.add(elem);
    } else {
      queue2.add(elem);
    }
  }

  /**
   * Pop implementation using two queues. Take every element but one out of the non-empty Queue and
   * put them into the empty one. The last element is removed and returned. Time complexity is O(n)
   * since we need to iterate through every stored element. Space complexity is O(1) since we don't
   * allocate any extra variables.
   *
   * @return The last element in the queues.
   */
  public T popTwoQueue() {
    int size;
    if (queue2.isEmpty()) {
      size = queue1.size();
      for (int i = 1; i < size; i++) {
        queue2.add(queue1.remove());
      }
      return queue1.remove();
    } else {
      size = queue2.size();
      for (int i = 1; i < size; i++) {
        queue1.add(queue2.remove());
      }
      return queue2.remove();
    }
  }

  /**
   * Push implemented with one queue. Queue and Stack both add elements to their Tails, so push and
   * add are the same. Time and Space complexity are O(1) because add is constant time and space
   *
   * @param elem The element to push
   */
  public void pushSingleQueue(T elem) {
    queue1.add(elem);
  }

  /**
   * Pop implemented using a single queue. Places elements at the back of the queue until it's gone
   * through every element but one, then removes and returns it. Time complexity is O(n) since we
   * have to iterate through every element to reach the last one. Space complexity is O(1) since we
   * don't declare any extra variables.
   *
   * @return The last element in the queue
   */
  public T popSingleQueue() {
    for (int i = 1; i < queue1.size(); i++) {
      queue1.add(queue1.remove());
    }
    return queue1.remove();
  }

  // Extra functions for Leetcode
  public T peekTwoQueue() {
    T elem = popTwoQueue();
    pushTwoQueue(elem);
    return elem;
  }

  public T peekSingleQeue() {
    T elem = popSingleQueue();
    pushSingleQueue(elem);
    return elem;
  }

  public boolean isEmptyTwoQueue() {
    return queue1.isEmpty() && queue2.isEmpty();
  }

  public boolean isEmptySingleQueue() {
    return queue1.isEmpty();
  }
}
