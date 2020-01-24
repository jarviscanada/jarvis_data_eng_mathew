package ca.jrvs.practice.codingChallenge.stackQueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ValidParentheses {

  /**
   * Checks a string to see if the parentheses in it are valid. In other words, check that the next
   * closing bracket corresponds to the last open bracket encountered, and no bracket is left
   * hanging at the end of the string. Time complexity is O(n) since we need to check every char of
   * the string once Space complexity is O(n) since we're storing up to n/2 elements in the stack
   *
   * @param toCheck The string to check for valid parentheses
   * @return True if the string's parentheses match up
   */
  public boolean validParentheses(String toCheck) {
    Map<Character, Character> parenMap = new HashMap<>();
    Stack<Character> nextClosingParen = new Stack<>();
    parenMap.put('(', ')');
    parenMap.put('[', ']');
    parenMap.put('{', '}');

    for (char c : toCheck.toCharArray()) {
      if (c == '(' || c == '[' || c == '{') {
        nextClosingParen.push(parenMap.get(c));
      } else if (c == ')' || c == ']' || c == '}') {
        if (nextClosingParen.isEmpty() || c != nextClosingParen.pop()) {
          return false;
        }
      }
    }
    return nextClosingParen.isEmpty();
  }

}
