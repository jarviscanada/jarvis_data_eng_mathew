package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListAPIs {

  public static void main(String[] args) {

    // Create from collection. Get first and last
    LinkedList<String> words = new LinkedList<>(Arrays.asList("Words", "Letters", "Numbers"));
    System.out.println(words.getFirst() + " " + words.getLast());

    // Stack and Queue style adds
    words.push("First");
    words.add("Last");
    System.out.println(words.getFirst() + " " + words.getLast());

    // Add collection at index
    words.addAll(1, Arrays.asList("One", "Two", "Three"));
    // Convert to array
    System.out.println(Arrays.toString(words.toArray()));

    // Sort
    words.sort(String.CASE_INSENSITIVE_ORDER);
    System.out.println(words);

    // Stack and Queue style removal. Lambda forEach
    System.out.println(words.pop());
    words.forEach(x -> System.out.print(x + " "));
    System.out.print('\n');
    System.out.println(words.remove());
    words.forEach(x -> System.out.print(x + " "));
    System.out.print('\n');

    // Foreach loop
    for (String s : words) {
      System.out.print(s.toUpperCase() + " ");
    }
    System.out.println();

    // Iterator looping
    LinkedList<Character> letters = new LinkedList<>();
    Iterator<String> wordIterator = words.iterator();
    // Use stack operations to print each word in the list backwards
    while (wordIterator.hasNext()) {
      for (char c : wordIterator.next().toCharArray()) {
        letters.push(c);
      }
      for (char c : letters) {
        System.out.print(c);
      }
      System.out.println();
      letters.clear();
    }
  }
}
