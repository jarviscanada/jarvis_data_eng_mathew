package ca.jrvs.practice.dataStructure.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JTreeSetTest {

  JTreeSet<Integer> testTreeSet = new JTreeSet<>(Integer::compareTo);

  @Test
  public void size() {
    assertEquals(0, testTreeSet.size());
    add();
    assertEquals(7, testTreeSet.size());
    testTreeSet.clear();
    assertEquals(0, testTreeSet.size());
  }

  @Test
  public void contains() {
    add();
    assertTrue(testTreeSet.contains(10));
    assertTrue(testTreeSet.contains(15));
    assertFalse(testTreeSet.contains(1111));
  }

  @Test
  public void add() {
    assertTrue(testTreeSet.add(10));
    assertTrue(testTreeSet.add(5));
    assertTrue(testTreeSet.add(15));
    assertTrue(testTreeSet.add(2));
    assertTrue(testTreeSet.add(7));
    assertTrue(testTreeSet.add(12));
    assertTrue(testTreeSet.add(20));

    assertFalse(testTreeSet.add(15));
    assertFalse(testTreeSet.add(2));
  }

  @Test
  public void remove() {
    add();
    assertTrue(testTreeSet.remove(5));
    assertTrue(testTreeSet.remove(10));
    assertTrue(testTreeSet.remove(2));
    assertFalse(testTreeSet.remove(-159));
  }

  @Test
  public void clear() {
    add();
    assertTrue(testTreeSet.contains(10));
    assertTrue(testTreeSet.size() > 0);
    testTreeSet.clear();
    assertEquals(0, testTreeSet.size());
    assertTrue(testTreeSet.add(10));
  }
}