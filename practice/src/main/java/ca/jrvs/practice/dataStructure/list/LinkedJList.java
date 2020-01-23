package ca.jrvs.practice.dataStructure.list;

import java.util.HashSet;
import java.util.Set;

public class LinkedJList<T> implements JList<T> {

  private int size = 0;
  private Node<T> head;
  private Node<T> tail;

  private static class Node<T> {

    private T value;
    private Node<T> next;
    private Node<T> prev;

    public Node(T t, Node<T> prev, Node<T> next) {
      this.value = t;
      this.next = next;
      this.prev = prev;
    }
  }

  /**
   * Appends the specified element to the end of this list (optional operation).
   *
   * @param t element to be appended to this list
   * @return <tt>true</tt>
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */
  @Override
  public boolean add(T t) {
    if (head == null || tail == null) {
      head = new Node<>(t, null, null);
      tail = head;
    } else {
      final Node<T> newTail = new Node<>(t, tail, null);
      tail.next = newTail;
      tail = newTail;
    }
    size++;
    return true;
  }

  /**
   * Returns an array containing all of the elements in this list in proper sequence (from first to
   * last element).
   *
   * <p>This method acts as bridge between array-based and collection-based
   * APIs.
   *
   * @return an array containing all of the elements in this list in proper sequence
   */
  @Override
  public Object[] toArray() {
    Object[] elements = new Object[size];
    Node<T> node = head;
    int index = 0;
    while (node != null) {
      elements[index++] = node.value;
      node = node.next;
    }
    return elements;
  }

  /**
   * The size of the ArrayList (the number of elements it contains).
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Returns <tt>true</tt> if this list contains no elements.
   *
   * @return <tt>true</tt> if this list contains no elements
   */
  @Override
  public boolean isEmpty() {
    return head == null && tail == null;
  }

  /**
   * Returns the index of the first occurrence of the specified element in this list, or -1 if this
   * list does not contain the element. More formally, returns the lowest index <tt>i</tt> such
   * that
   * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
   * or -1 if there is no such index.
   *
   * @param t
   */
  @Override
  public int indexOf(T t) {
    int index = 0;
    Node<T> node = head;
    while (node != null) {
      if (node.value.equals(t)) {
        return index;
      }
      index++;
      node = node.next;
    }
    return -1;
  }

  /**
   * Returns <tt>true</tt> if this list contains the specified element. More formally, returns
   * <tt>true</tt> if and only if this list contains at least one element <tt>e</tt> such that
   * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
   *
   * @param t element whose presence in this list is to be tested
   * @return <tt>true</tt> if this list contains the specified element
   * @throws NullPointerException if the specified element is null and this list does not permit
   *                              null elements
   */
  @Override
  public boolean contains(T t) {
    return indexOf(t) >= 0;
  }

  /**
   * Returns the element at the specified position in this list.
   *
   * @param index index of the element to return
   * @return the element at the specified position in this list
   * @throws IndexOutOfBoundsException if the index is out of range (<tt>index &lt; 0 || index &gt;=
   *                                   size()</tt>)
   */
  @Override
  public T get(int index) {
    if (index >= size || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    Node<T> node = head;
    for (int i = 0; i < index; i++) {
        node = node.next;
    }
    return node == null ? null : node.value;
  }

  /**
   * Removes the element at the specified position in this list. Shifts any subsequent elements to
   * the left (subtracts one from their indices).
   *
   * @param index the index of the element to be removed
   * @return the element that was removed from the list
   * @throws IndexOutOfBoundsException {@inheritDoc}
   */
  @Override
  public T remove(int index) {
    if (index >= size || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    Node<T> node = head;
    for (int i = 0; i < index; i++) {
      node = node.next;
    }
    size--;
    if (node != null) {
      Node<T> next = node.next;
      Node<T> prev = node.prev;
      if (prev == null) {
        head = next;
      } else {
        prev.next = next;
        node.next = null;
      }
      if (next == null) {
        tail = prev;
      } else {
        next.prev = prev;
        node.prev = null;
      }
      return node.value;
    }
    return null;
  }

  /**
   * Removes all of the elements from this list (optional operation). The list will be empty after
   * this call returns.
   */
  @Override
  public void clear() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * Removes duplicate values from this LinkedList, using a Set to track values encountered so far.
   */
  public void removeDuplicateValues() {
    Set<T> values = new HashSet<>();
    for (int i = 0; i < size; i++) {
      T t = get(i);
      if (!values.add(t)) {
        remove(i--);
      }
    }
  }
}
