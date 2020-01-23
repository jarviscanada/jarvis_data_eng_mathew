package ca.jrvs.practice.dataStructure.stackQueue;

import ca.jrvs.practice.dataStructure.list.LinkedJList;
import java.util.NoSuchElementException;

public class LinkedJListJDeque<T> extends LinkedJList<T> implements JDeque<T> {

  @Override
  public void enqueue(T obj) {
    add(obj);
  }

  @Override
  public T dequeue() {
    return remove(0);
  }

  @Override
  public void push(T obj) {
    enqueue(obj);
  }

  @Override
  public T pop() {
    if (tail == null) {
      throw new NoSuchElementException();
    }
    Node<T> end = tail;
    tail = tail.prev;
    end.next = null;
    if (tail == null) {
      head = null;
    } else {
      tail.next = null;
    }
    size--;
    return end.value;
  }

  @Override
  public T peek() {
    return head == null ? null : head.value;
  }
}
