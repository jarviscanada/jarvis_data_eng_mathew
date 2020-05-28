package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.Collection;

public class ArrayJList<T> implements JList<T> {

  private Object[] elements;
  private static final int DEFAULT_INITIAL_CAPACITY = 15;
  private static final float GROWTH_FACTOR = 1.75f;
  private int size = 0;
  private int capacity;

  public ArrayJList() {
    elements = new Object[DEFAULT_INITIAL_CAPACITY];
    capacity = DEFAULT_INITIAL_CAPACITY;
  }

  public ArrayJList(int initialCapacity) {
    if (initialCapacity > 0) {
      elements = new Object[initialCapacity];
      capacity = initialCapacity;
    } else {
      throw new NegativeArraySizeException();
    }
  }

  public ArrayJList(Collection<T> collection) {
    elements = collection.toArray();
    capacity = collection.size();
    size = capacity;
  }

  private void grow(){
    int newCapacity = Math.round(capacity * GROWTH_FACTOR);
    if (newCapacity <= 0) {
      throw new NegativeArraySizeException("ArrayList capacity overflow");
    }
    Object[] oldArray = elements;
    elements = new Object[newCapacity];
    System.arraycopy(oldArray, 0, elements, 0, oldArray.length);
    capacity = newCapacity;
  }

  @Override
  public boolean add(T t) {
    if (size >= capacity) {
      grow();
    }
    elements[size++] = t;
    return true;
  }

  @Override
  public Object[] toArray() {
    return Arrays.copyOf(elements, size);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int indexOf(T t) {
    for (int i = 0; i < elements.length; i ++) {
      if (t.equals(elements[i])) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public boolean contains(T t) {
    return indexOf(t) >= 0;
  }

  @SuppressWarnings("unchecked")
  @Override
  public T get(int index) {
    if (index >= 0 && index <= size) {
      return (T)this.elements[index];
    } else {
      throw new IndexOutOfBoundsException();
    }
  }

  @Override
  public T remove(int index) {
    T t = get(index);
    System.arraycopy(elements, index+1, elements, index, size - index - 1);
    elements[--size] = null;
    return t;
  }

  @Override
  public void clear() {
    elements = new Object[capacity];
    size = 0;
  }
}
