package ca.jrvs.practice.dataStructure.tree;

public interface JSet<T> {

  int size();

  boolean contains(Object o);

  boolean add(T t);

  boolean remove(Object o);

  void clear();

}
