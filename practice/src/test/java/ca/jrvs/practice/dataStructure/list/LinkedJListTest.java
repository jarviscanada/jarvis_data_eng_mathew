package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;

import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;

public class LinkedJListTest {

  LinkedList<Integer> linkedList;
  LinkedJList<Integer> linkedJList;

  @Before
  public void setup() {
    linkedList = new LinkedList<>();
    linkedList.add(1);
    linkedList.add(2);
    linkedList.add(3);
    linkedJList = new LinkedJList<>();
    linkedJList.add(1);
    linkedJList.add(2);
    linkedJList.add(3);
  }

  @Test
  public void add() {
    linkedList.add(50);
    assertTrue(linkedJList.add(50));
    assertEquals(linkedList.get(3), linkedJList.get(3));
  }

  @Test
  public void toArray() {
    assertArrayEquals(linkedList.toArray(), linkedJList.toArray());
  }

  @Test
  public void size() {
    assertEquals(linkedList.size(), linkedJList.size());
  }

  @Test
  public void isEmpty() {
    assertFalse(linkedJList.isEmpty());
    assertTrue(new LinkedJList<Integer>().isEmpty());
    linkedJList.remove(2);
    linkedJList.remove(1);
    linkedJList.remove(0);
    assertTrue(linkedJList.isEmpty());
  }

  @Test
  public void indexOf() {
    assertEquals(linkedList.indexOf(2), linkedJList.indexOf(2));
  }

  @Test
  public void contains() {
    assertTrue(linkedJList.contains(2));
  }

  @Test
  public void get() {
    assertEquals(linkedList.get(0), linkedJList.get(0));
  }

  @Test
  public void remove() {
    assertEquals(linkedList.remove(0), linkedJList.remove(0));
  }

  @Test
  public void clear() {
    linkedJList.clear();
    assertTrue(linkedJList.isEmpty());
  }
}