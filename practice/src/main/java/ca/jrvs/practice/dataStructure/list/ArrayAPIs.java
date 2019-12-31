package ca.jrvs.practice.dataStructure.list;

import java.util.Arrays;
import java.util.List;

// Class for practicing Array operations
public class ArrayAPIs {

  public static void main(String[] args) {
    int[] inline = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    int[] explicit = new int[5];

    for (int i = 0; i < explicit.length; i++) {
      explicit[i] = i + 1;
    }

    String[][] inline2d = {
        {"Mr.", "Ms.", "Mrs.", "Dr."},
        {"Stark", "Strange", "Parker", "Kent", "Wayne"}
    };

    System.out.println(Arrays.toString(inline2d));
    System.out.println(Arrays.toString(inline2d[0]) + Arrays.toString(inline2d[1]));

    Character[] copyFrom = {'a', 'b', 'c', 'd', 'e'};
    Character[] copyTo = Arrays.copyOf(copyFrom, 8);
    printArray(copyTo);

    String[] sortMe = {"Bear", "Shark", "Antelope", "Zebra", "Cat", "Dog", "Lizard"};
    Arrays.sort(sortMe);
    printArray(sortMe);

    System.out.println(Arrays.binarySearch(sortMe, "Shark"));

    List<String> colors = Arrays.asList("Red", "Blue", "Green", "Yellow", "Orange", "Purple");
    colors = Arrays.asList(new String[]{"Black", "White", "Grey", "Brown", "Teal"});
  }

  public static <T> void printArray(T[] arr) {
    for (T t : arr) {
      System.out.print(t + " ");
    }
    System.out.print('\n');
  }
}
