package ca.jrvs.practice.dataStructure.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;
import java.util.Set;
import org.junit.Test;

public class HashJMapTest {

  @Test
  public void get() {
  }

  @Test
  public void put() {
    HashJMap<Integer, String> testMap1 = new HashJMap<>();
    HashJMap<String, Integer> testMap2 = new HashJMap<>(32, 0.66F);

    testMap1.put(1, "one");
    testMap1.put(2, "two");
    testMap1.put(3, "three");
    testMap1.put(-5, "negative five");

    assertEquals("three", testMap1.get(3));
    assertEquals("negative five", testMap1.get(-5));

    testMap1.put(1, "111");
    assertEquals("111", testMap1.get(1));

    testMap2.put("one", 1);
    testMap2.put("two", 2);
    testMap2.put("three", 3);
    testMap2.put("negative five", -5);

    assertEquals(2, testMap2.get("two").intValue());
    assertEquals(-5, testMap2.get("negative five").intValue());
  }

  @Test
  public void containsKey() {
    HashJMap<Integer, Integer> map = new HashJMap<>();
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    assertTrue(map.containsKey(2));
    assertFalse(map.containsKey(168));
  }

  @Test
  public void entrySet() {
    HashJMap<String, String> map = new HashJMap<>();
    map.put("1", "one");
    map.put("2", "two");
    map.put("3", "three");
    map.put("4", "four");
    Set<Entry<String, String>> entries = map.entrySet();
    System.out.println(entries);
  }

  @Test
  public void size() {
    HashJMap<Integer, Integer> map = new HashJMap<>();
    map.put(1, 100);
    map.put(2, 200);
    map.put(3, 300);
    map.put(4, 400);
    assertEquals(4, map.size());
  }
}