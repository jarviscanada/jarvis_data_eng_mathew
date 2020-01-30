package ca.jrvs.practice.codingChallenge.dynamic;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

  /**
   * Calculate then Nth number in a fibonacci sequence via recursion. Time complexity is O(2^n)
   * because we must calculate every result in a recursion tree with a height of n. Space complexity
   * is O(n) because we must hold onto at most n frames in the call stack for recursion. Java does
   * not optimize tail recursion.
   *
   * @param n The target fibonacci number to calculate
   * @return The Nth fibonacci number
   */
  public static long fibonacciRecursive(int n) {
    if (n == 1 || n == 2) {
      return 1;
    } else {
      return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }
  }

  /**
   * Calculate the Nth number in the fibonacci sequence via recursion with dynamic programming. Past
   * fibonacci numbers (including the base cases) are cached in a HashMap, thereby eliminating any
   * branches where n has been previously calculated. This solution is still suceptible to Stack
   * overflow, and may be moreso than the traditional recursive method.
   * <p>
   * Time complexity is O(n) since we only recurse down one branch of the original recursion tree.
   * Space complexity is O(n) because we still need a stack frame for every level of the recursion
   * tree (which is n), and we also need space for the map storing up to n results.
   *
   * @param n The fibonacci number to calculate
   * @return The Nth fibonacci number
   */
  public static long fibonacciDynamic(int n) {
    Map<Integer, Long> pastResults = new HashMap<>();
    pastResults.put(1, 1L);
    pastResults.put(2, 1L);
    return fibonacciDynamic(n, pastResults);
  }

  private static long fibonacciDynamic(int n, Map<Integer, Long> pastResults) {
    if (!pastResults.containsKey(n)) {
      pastResults.put(n,
          fibonacciDynamic(n - 1, pastResults) + fibonacciDynamic(n - 2, pastResults));
    }
    return pastResults.get(n);
  }
}
