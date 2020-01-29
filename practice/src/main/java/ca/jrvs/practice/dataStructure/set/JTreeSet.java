package ca.jrvs.practice.dataStructure.set;

import java.util.Comparator;
import java.util.TreeMap;

public class JTreeSet<T> implements JSet<T> {

  private TreeMap<T, Object> elements;
  private static final Object DUMMY = new Object();

  public JTreeSet(Comparator<T> comparator) {
    elements = new TreeMap<>(comparator);
  }

  @Override
  public int size() {
    return elements.size();
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean contains(Object o) {
    if (o == null) {
      throw new NullPointerException();
    }
    T val = (T) o;
    return elements.containsKey(val);
  }

  @Override
  public boolean add(T t) {
    if (contains(t)) {
      return false;
    } else {
      return elements.put(t, DUMMY) == null;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean remove(Object o) {
    if (o == null) {
      throw new NullPointerException();
    } else if (contains(o)) {
      T val = (T) o;
      return elements.remove(val) != null;
    }
    return false;
  }

  @Override
  public void clear() {
    elements.clear();
  }
}
