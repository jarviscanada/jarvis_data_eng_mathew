package ca.jrvs.practice.dataStructure.stackQueue;

import ca.jrvs.practice.dataStructure.list.LinkedJList;

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
    return remove(size() - 1);
  }

  @Override
  public T peek() {
    try {
      return get(0);
    } catch (IndexOutOfBoundsException ex) {
      return null;
    }
  }
}
