package ca.jrvs.practice.dataStructure.list;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ArrayJListTest {

  ArrayList<Integer> arrayList;
  ArrayJList<Integer> arrayJList;

  @Before
  public void setup(){
    arrayList = new ArrayList<>(Arrays.asList(1,2,3,4,5));
    arrayJList = new ArrayJList<>(arrayList);
  }

  @Test
  public void grow() {
    arrayJList = new ArrayJList<>(Arrays.asList(1,2,3,4,5,6));
    arrayJList.add(7);
    arrayJList.add(8);
    arrayJList.add(9);
    arrayJList.add(10);
    arrayJList.add(11);
  }

  @Test
  public void add() {
    arrayList.add(10);
    assertTrue(arrayJList.add(10));
    assertEquals(arrayList.get(5), arrayJList.get(5));
  }

  @Test
  public void toArray() {
    assertArrayEquals(arrayList.toArray(), arrayJList.toArray());
  }

  @Test
  public void size() {
    assertEquals(arrayList.size(), arrayJList.size());
  }

  @Test
  public void isEmpty() {
    assertFalse(arrayJList.isEmpty());
  }

  @Test
  public void indexOf() {
    assertEquals(arrayList.indexOf(3), arrayJList.indexOf(3));
    assertEquals(arrayList.indexOf(500), arrayJList.indexOf(500));
  }

  @Test
  public void contains() {
    assertTrue(arrayJList.contains(3));
    assertFalse(arrayJList.contains(999));
  }

  @Test
  public void get() {
    assertEquals(arrayList.get(2), arrayJList.get(2));
  }

  @Test
  public void remove() {
    assertEquals(arrayList.remove(1), arrayJList.remove(1));
    assertArrayEquals(arrayList.toArray(), arrayJList.toArray());
  }

  @Test
  public void clear() {
    arrayJList.clear();
    assertEquals(0, arrayJList.size());
  }
}