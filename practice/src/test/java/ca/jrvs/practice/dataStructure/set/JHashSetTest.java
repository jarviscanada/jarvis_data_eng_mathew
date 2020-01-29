package ca.jrvs.practice.dataStructure.set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JHashSetTest {

  JHashSet<String> testSet = new JHashSet<>();

  @Test
  public void size() {
    add();
    assertEquals(100, testSet.size());
    testSet.remove("val" + 55);
    assertEquals(99, testSet.size());
    testSet.clear();
    assertEquals(0, testSet.size());
  }

  @Test
  public void contains() {
    add();
    assertTrue(testSet.contains("val" + 1));
    assertFalse(testSet.contains("BadValue"));
  }

  @Test
  public void add() {
    for (int i = 0; i < 100; i++) {
      assertTrue(testSet.add("val" + i));
    }
    assertFalse(testSet.add("val" + 1));
  }

  @Test
  public void remove() {
    add();
    assertTrue(testSet.remove("val20"));
    assertTrue(testSet.remove("val0"));
    assertTrue(testSet.remove("val99"));
    assertFalse(testSet.remove("BadValue"));
  }

  @Test
  public void clear() {
    add();
    assertEquals(100, testSet.size());
    testSet.clear();
    assertEquals(0, testSet.size());
  }
}