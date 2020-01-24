package ca.jrvs.practice.dataStructure.map;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

public class HashJMap<K, V> implements JMap<K, V> {

  private static final int DEFAULT_INITIAL_CAPACITY = 16;
  private static final float DEFAULT_LOAD_FACTOR = 0.75F;
  private float loadFactor;
  private int size = 0;
  private int threshold;
  private Node<K, V>[] table;
  private Set<Entry<K, V>> entrySet;

  public HashJMap() {
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  public HashJMap(int initialCapacity, float loadFactor) {
    if (loadFactor > 1 || loadFactor <= 0 || initialCapacity <= 0) {
      throw new IllegalArgumentException();
    }

    this.loadFactor = loadFactor;
    threshold = (int) (loadFactor * initialCapacity);
    entrySet = new HashSet<>();
  }

  @Override
  public V get(Object key) {
    if (containsKey(key)) {
      Node<K, V> current = table[getIndex(key, table.length)];
      if (current.key.equals(key)) {
        return current.value;
      }
      while (current.next != null) {
        if (current.key.equals(key)) {
          return current.value;
        }
        current = current.next;
      }
    }
    return null;
  }

  @Override
  public void put(K key, V value) {
    if (table == null) {
      table = resize();
    }
    int index = getIndex(key, table.length);
    Node<K, V> entry = new Node<>(index, key, value, null);
    if (table[index] != null) {
      Node<K, V> current = table[index];
      if (current.key.equals(entry.key)) {
        current.value = entry.value;
        return;
      }
      while (current.next != null) {
        if (current.equals(entry)) {
          current.value = entry.value;
          return;
        }
        current = current.next;

      }
      current.next = entry;
    } else {
      table[index] = entry;
    }
    entrySet.add(entry);
    size++;

    if (size >= threshold) {
      table = resize();
    }
  }

  @Override
  public boolean containsKey(Object key) {
    Node<K, V> current = table[getIndex(key, table.length)];
    if (current != null) {
      if (current.key.equals(key)) {
        return true;
      }
      while (current.next != null) {
        if (current.key.equals(key)) {
          return true;
        }
        current = current.next;
      }
    }
    return false;
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return entrySet;
  }

  @Override
  public int size() {
    return size;
  }

  private int getIndex(Object key, int tableLength) {
    return Math.abs(key.hashCode()) % (tableLength - 1);
  }

  private Node<K, V>[] resize() {
    int oldCap = table == null ? 0 : table.length;
    int oldThreshold = threshold;
    int newCap = 0;
    int newThreshold = 0;

    if (oldCap > 0) {
      newCap = oldCap << 1;
      newThreshold = (int) (newCap * loadFactor);
    } else if (oldThreshold > 0) {
      newCap = oldThreshold;
      newThreshold = (int) (newCap * loadFactor);
    } else {
      newCap = DEFAULT_INITIAL_CAPACITY;
      newThreshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    }
    threshold = newThreshold;
    @SuppressWarnings({"unchecked"})
    Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCap];
    if (entrySet != null) {
      for (Entry<K, V> e : entrySet) {
        rehash(e, newTable);
      }
    }
    return newTable;
  }

  private void rehash(Entry<K, V> entry, Node<K, V>[] table) {
    int index = getIndex(entry.getKey(), table.length);
    Node<K, V> entryNode;
    if (table[index] != null) {
      entryNode = table[index];
      while (entryNode.next != null) {
        entryNode = entryNode.next;
      }
      entryNode.next = new Node<>(index, entry.getKey(), entry.getValue(), null);
    }
  }

  private static class Node<K, V> implements Map.Entry<K, V> {

    int hash;
    K key;
    V value;
    Node<K, V> next;

    Node(int hash, K key, V value, Node<K, V> next) {
      this.hash = hash;
      this.key = key;
      this.value = value;
      this.next = next;
    }

    @Override
    public K getKey() {
      return key;
    }

    @Override
    public V getValue() {
      return value;
    }

    @Override
    public V setValue(V value) {
      V oldVal = this.value;
      this.value = value;
      return oldVal;
    }

    public String toString() {
      return key + " => " + value;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o instanceof Map.Entry) {
        Map.Entry<?, ?> node = (Map.Entry<?, ?>) o;
        return Objects.equals(key, node.getKey()) &&
            Objects.equals(value, node.getValue());
      } else {
        return false;
      }
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(key) ^ Objects.hashCode(value);
    }
  }

}

