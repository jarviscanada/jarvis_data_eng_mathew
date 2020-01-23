package ca.jrvs.practice.codingChallenge.stackQueue;

import java.util.Stack;

public class QueueUsingStack<T> {

  Stack<T> front = new Stack<>();
  Stack<T> back = new Stack<>();

  /**
   * Enqueue implementation using 2 Stacks utilizing a flip-flop technique. This implementation
   * assumes that the Stack is set up so the "queue head" is at the top of the stack. For insertion
   * we flip the stack over, push the element onto the flipped stack, then flip it back. Time
   * complexity is O(n) since each flip operation is 2n push and pop calls. Space complexity is O(n)
   * since we're storing all of the nodes in a method-level stack. Space complexity could also be
   * considered O(1) since the number of elements only increases by 1
   *
   * @param elem The element to insert
   */
  public void enqueueFlipFlop(T elem) {
    Stack<T> flipped = new Stack<>();
    if (front.isEmpty()) {
      front.push(elem);
    } else {
      while (!front.isEmpty()) {
        flipped.push(front.pop());
      }
      front.push(elem);
      while (!flipped.isEmpty()) {
        front.push(flipped.pop());
      }
    }
  }

  /**
   * Dequeue operation to pair with enqueueFlipFlop(T). Since we assume the top of the stack is the
   * "queue head" we can just pop it and we're done. Time complexity: O(1) since we're performing a
   * single pop operation, which is also O(1). Space Complexity: O(1) since we don't allocate any
   * extra variables.
   *
   * @return The value at the head of the queue
   */
  public T dequeueFlipFlop() {
    return front.pop();
  }

  /**
   * Enqueue implementation using two instance-level stacks, one which exposes the head of the queue
   * and one which exposes the tail. Since one stack exposes the tail, we can just push onto it to
   * enqueue. Time and space complexity are O(1) since push is constant time and no additional
   * memory is allocated.
   *
   * @param elem the element to add to the "queue"
   */
  public void enqueueSplitQueue(T elem) {
    back.push(elem);
  }

  /**
   * Dequeue implementation using two instance-level stacks. One stack exposes the queue head while
   * the other exposes the queue tail. This allows us to pop an element from the head stack to
   * dequeue, and grab more elements from the tail when head is emptied out. Amortized time
   * complexity on average is O(1)+. The most common case is that an element exists in head and it
   * is popped, being O(1), and the less common case is that head is empty and needs to flip tail
   * into itself, which is O(n).
   *
   * @return The element at the head of the "queue"
   */
  public T dequeueSplitQueue() {
    if (front.isEmpty()) {
      refill();
    }
    return front.pop();
  }

  private void refill() {
    if (back.isEmpty()) {
      throw new IndexOutOfBoundsException();
    } else {
      while (!back.isEmpty()) {
        front.push(back.pop());
      }
    }
  }

  public T peekSplitQueue() {
    if (front.isEmpty()) {
      refill();
    }
    return front.peek();
  }

  public boolean isEmpty() {
    return front.isEmpty() && back.isEmpty();
  }
}
