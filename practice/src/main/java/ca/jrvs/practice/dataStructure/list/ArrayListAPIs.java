package ca.jrvs.practice.dataStructure.list;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayListAPIs {

  public static void main(String[] args) {

    // Create ArrayList from collection
    ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
    System.out.println(numbers.get(3));

    // Add and remove numbers by index
    numbers.add(3,100);
    System.out.println("" + numbers.get(3) + " " + numbers.get(4));
    numbers.remove(3);
    System.out.println(numbers.get(3));

    numbers.add(1);
    numbers.add(2);
    numbers.add(1);
    // Index vs lastIndex
    System.out.println(numbers.indexOf(1));
    System.out.println(numbers.lastIndexOf(1));

    // Contains and ContainsAll
    System.out.println(numbers.contains(55));
    System.out.println(numbers.containsAll(Arrays.asList(1,3,5,7)));

    // Sort with a method reference
    numbers.sort(Integer::compare);
    numbers.forEach(System.out::println);

    // Get ArrayList size
    System.out.println(numbers.size());

    // Convert to array
    System.out.println(Arrays.toString(numbers.toArray()));

  }
}
