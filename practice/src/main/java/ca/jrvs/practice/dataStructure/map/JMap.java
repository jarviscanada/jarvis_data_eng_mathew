package ca.jrvs.practice.dataStructure.map;

import java.util.Map.Entry;
import java.util.Set;

public interface JMap<K, V> {

  V get(Object key);

  void put(K key, V value);

  boolean containsKey(Object key);

  Set<Entry<K, V>> entrySet();

  int size();

}
