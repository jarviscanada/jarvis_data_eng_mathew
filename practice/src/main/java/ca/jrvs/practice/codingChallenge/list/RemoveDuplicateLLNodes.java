package ca.jrvs.practice.codingChallenge.list;

import ca.jrvs.practice.dataStructure.list.LinkedJList;
import java.util.HashSet;
import java.util.Set;

/**
 * Ticket: https://www.notion.so/Duplicate-LinkedList-Node-a0b4e094d665449fb5ace33c0595f6eb
 */
public class RemoveDuplicateLLNodes {

  /**
   * Remove duplicate values from a LinkedJList. This implementation uses a Set to track values
   * encountered so far. Runtime Complexity is O(n) since iterating through a list is n and HashSet
   * lookup is constant-ish. Memory usage is O(n) for the use of a Set
   *
   * @param list The list to remove duplicates from
   * @param <T>  The inner type of the List
   * @return The list, with the duplicates removed.
   */
  public static <T> LinkedJList<T> removeDuplicateLLNodes(LinkedJList<T> list) {
    Set<T> values = new HashSet<>();
    for (int i = 0; i < list.size(); i++) {
      T t = list.get(i);
      if (!values.add(t)) {
        list.remove(i--);
      }
    }
    return list;
  }
}
