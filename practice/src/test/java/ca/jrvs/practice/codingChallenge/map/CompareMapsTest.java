package ca.jrvs.practice.codingChallenge.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import ca.jrvs.practice.dataStructure.map.HashJMap;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CompareMapsTest {

  @Test
  public void compareMapsCollections() {
    Map<Integer, Integer> map1 = new HashMap<>();
    Map<Integer, Integer> map2 = new HashMap<>();

    assertEquals(map1, map2);
    assertEquals(map2, map1);

    map1.put(5, 5);
    assertNotEquals(map1, map2);
    map2.put(5, 5);
    assertEquals(map1, map2);

    map1.put(30, null);
    map2.put(30, null);
    assertEquals(map1, map2);

    map1.put(null, 15);
    map2.put(null, 15);
    assertEquals(map1, map2);
  }

  @Test
  public void compareMapsHashJMap() {
    HashJMap<Integer, Integer> jMap1 = new HashJMap<>();
    HashJMap<Integer, Integer> jMap2 = new HashJMap<>();
    CompareMaps cmp = new CompareMaps();

    assertTrue(cmp.compareMapsHashJMap(jMap1, jMap2));
    assertTrue(cmp.compareMapsHashJMap(jMap2, jMap1));

    jMap1.put(5, 5);
    assertFalse(cmp.compareMapsHashJMap(jMap1, jMap2));
    jMap2.put(5, 5);
    assertTrue(cmp.compareMapsHashJMap(jMap1, jMap2));

    jMap1.put(7, null);
    jMap2.put(7, null);
    assertTrue(cmp.compareMapsHashJMap(jMap1, jMap2));

    jMap1.put(null, 12);
    jMap2.put(null, 12);
    assertTrue(cmp.compareMapsHashJMap(jMap1, jMap2));

  }
}