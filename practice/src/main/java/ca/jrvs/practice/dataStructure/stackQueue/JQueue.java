package ca.jrvs.practice.dataStructure.stackQueue;

public interface JQueue<T> {

  void enqueue(T obj); // Not add(T obj) to avoid conflict with LinkedJList

  T dequeue(); // Not remove() to avoid conflict with LinkedJList

  T peek();
}
