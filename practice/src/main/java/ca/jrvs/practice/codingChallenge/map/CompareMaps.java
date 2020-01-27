package ca.jrvs.practice.codingChallenge.map;

import ca.jrvs.practice.dataStructure.map.HashJMap;
import java.util.Map;

public class CompareMaps {

  /**
   * Check if 2 maps are equal using the Collections API's implementation. This is for the sake of
   * having a baseline to work against. Time complexity is O(n) since we have to iterate through the
   * KVPs of one map. Space complexity is O(1) since we only allocate a few pointers
   *
   * @param map1 The first map to compare
   * @param map2 The second map to compare
   * @return True if maps are equal, else false
   */
  public boolean compareMapsCollections(Map<?, ?> map1, Map<?, ?> map2) {
    return map1.equals(map2);
  }

  /**
   * Check if 2 maps are equal using HashJMap's implementation. This custom implementation should be
   * nearly identical to the Collections implementation. Time complexity is O(n) since we iterate
   * through every entry in one map. Space complexity is O(1) since we only allocate a few
   * pointers.
   *
   * @param map1 The first map to compare
   * @param map2 The second map to compare
   * @return True if maps are equal, else false
   */
  public boolean compareMapsHashJMap(HashJMap<?, ?> map1, HashJMap<?, ?> map2) {
    return map1.equals(map2);
  }

}
