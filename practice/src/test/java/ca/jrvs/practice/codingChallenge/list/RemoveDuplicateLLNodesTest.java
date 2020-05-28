package ca.jrvs.practice.codingChallenge.list;

import ca.jrvs.practice.dataStructure.list.LinkedJList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class RemoveDuplicateLLNodesTest {

  LinkedJList<Integer> list;

  @Before
  public void setup() {
    list = new LinkedJList<>();
  }

  @Test
  public void removeDuplicateLLNodes() {
    list.add(1);
    list.add(2);
    list.add(1);
    list.add(3);
    System.out.println("Before: " + Arrays.toString(list.toArray()));
    RemoveDuplicateLLNodes.removeDuplicateLLNodes(list);
    System.out.println("After: " + Arrays.toString(list.toArray()));
  }

  @Test
  public void removeDuplicateLLNodes_AllDupes() {
    list.add(1);
    list.add(1);
    list.add(1);
    System.out.println("Before: " + Arrays.toString(list.toArray()));
    RemoveDuplicateLLNodes.removeDuplicateLLNodes(list);
    System.out.println("After: " + Arrays.toString(list.toArray()));
  }

  @Test
  public void removeDuplicateLLNodes_NoDupes() {
    list.add(1);
    list.add(2);
    list.add(3);
    System.out.println("Before: " + Arrays.toString(list.toArray()));
    RemoveDuplicateLLNodes.removeDuplicateLLNodes(list);
    System.out.println("After: " + Arrays.toString(list.toArray()));
  }

  @Test
  public void removeDuplicateLLNodes_Empty() {
    System.out.println("Before: " + Arrays.toString(list.toArray()));
    RemoveDuplicateLLNodes.removeDuplicateLLNodes(list);
    System.out.println("After: " + Arrays.toString(list.toArray()));
  }
}