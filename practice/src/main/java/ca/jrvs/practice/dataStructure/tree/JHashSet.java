package ca.jrvs.practice.dataStructure.tree;

import java.util.HashMap;

public class JHashSet<T> implements JSet<T> {

  private HashMap<T, Object> elements;
  private static final Object DUMMY = new Object();

  public JHashSet() {
    elements = new HashMap<>();
  }

  @Override
  public int size() {
    return elements.size();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean contains(Object o) {
    if (o == null) {
      return false;
    }
    T val = (T) o;
    return elements.containsKey(val);
  }

  @Override
  public boolean add(T t) {
    if (contains(t) || t == null) {
      return false;
    } else {
      return elements.put(t, DUMMY) == null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object o) {
    if (o == null) {
      return false;
    }
    T val = (T) o;
    if (elements.containsKey(val)) {
      elements.remove(val);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void clear() {
    elements.clear();
  }
}
