package ca.jrvs.practice.dataStructure.tree;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class JBSTreeTest {

  JBSTree<Integer> testTree = new JBSTree<>(Integer::compareTo);

  @Test
  public void insert() {
    testTree.insert(10);
    testTree.insert(5);
    testTree.insert(15);
    testTree.insert(1);
    testTree.insert(7);
    testTree.insert(2);
    testTree.insert(12);
    testTree.insert(20);

    assertArrayEquals(new Integer[]{1, 2, 5, 7, 10, 12, 15, 20}, testTree.inOrder());
  }

  @Test
  public void search() {
    insert();
    assertEquals(7, testTree.search(7).intValue());
    assertNull(testTree.search(15475));
  }

  @Test
  public void remove() {
    insert();
    assertEquals(5, testTree.remove(5).intValue());
    assertArrayEquals(new Integer[]{1, 2, 7, 10, 12, 15, 20}, testTree.inOrder());
  }

  @Test
  public void preOrder() {
    testTree.insert(10);
    testTree.insert(5);
    testTree.insert(15);
    testTree.insert(3);
    testTree.insert(8);
    testTree.insert(11);
    testTree.insert(20);
    assertArrayEquals(new Integer[]{10, 5, 3, 8, 15, 11, 20}, testTree.preOrder());
  }

  @Test
  public void inOrder() {
    testTree.insert(10);
    testTree.insert(5);
    testTree.insert(15);
    testTree.insert(3);
    testTree.insert(8);
    testTree.insert(11);
    testTree.insert(20);
    assertArrayEquals(new Integer[]{3, 5, 8, 10, 11, 15, 20}, testTree.inOrder());
  }

  @Test
  public void postOrder() {
    testTree.insert(10);
    testTree.insert(5);
    testTree.insert(15);
    testTree.insert(3);
    testTree.insert(8);
    testTree.insert(11);
    testTree.insert(20);
    assertArrayEquals(new Integer[]{3, 8, 5, 11, 20, 15, 10}, testTree.postOrder());
  }

  @Test
  public void findHeight() {
    insert();
    assertEquals(4, testTree.findHeight());
  }
}