package ca.jrvs.apps.practice;

import static java.util.stream.Stream.of;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImp implements LambdaStreamExc {

  /**
   * Create a String stream from array
   * <p>
   * note: arbitrary number of value will be stored in an array
   *
   * @param strings String values to put into an array
   * @return A Stream object containing the Strings passed to the function
   */
  @Override
  public Stream<String> createStrStream(String... strings) {
    return Stream.of(strings);
  }

  /**
   * Convert all strings to uppercase please use createStrStream
   *
   * @param strings Strings to be made upper case then converted to a stream
   * @return a Stream of upper case strings
   */
  @Override
  public Stream<String> toUpperCase(String... strings) {
    return createStrStream(strings).map(String::toUpperCase);
  }

  /**
   * filter strings that contains the pattern e.g. filter(stringStream, "a") will return another
   * stream which no element contains a
   *
   * @param stringStream A Stream of Strings which we want to add a filter to
   * @param pattern      A pattern which we want to filter out of the Stream
   * @return a new Stream of Strings which has a filter applied
   */
  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    // Normally Stream.filter() is used to define what is included, so we can negate the pattern
    // to achieve the desired exclusion effect
    return stringStream.filter(ss -> !ss.contains(pattern));
  }

  /**
   * Create a intStream from a arr[]
   *
   * @param arr an array of primitive ints
   * @return an IntStream made from the primitive int array
   */
  @Override
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  /**
   * Create a IntStream range from start to end inclusive
   *
   * @param start primitive int indicating the start of a number range
   * @param end   primitive int indicating the end of a number range
   * @return an IntStream whose source is all ints from (start, end)
   */
  @Override
  public IntStream createIntStream(int start, int end) {
    return IntStream.rangeClosed(start, end);
  }

  /**
   * Convert a stream to list
   *
   * @param stream A generic Stream to convert to a List of the same type
   * @return A List of the same type as the Stream passed in
   */
  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  /**
   * Convert a intStream to list
   *
   * @param intStream An IntStream to convert
   * @return A list of Integers
   */
  @Override
  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  /**
   * Convert a intStream to a doubleStream and compute square root of each element
   *
   * @param intStream A Stream of primitive ints to convert
   * @return A DoubleStream of square roots of the IntStream's contents
   */
  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {
    return intStream.mapToDouble(Math::sqrt);
  }

  /**
   * filter all even number and return odd numbers from a intStream
   *
   * @param intStream a stream of primitive ints to filter
   * @return an IntStream with a filter applied to only get odd numbers
   */
  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(num -> num % 2 == 1);
  }

  /**
   * Return a lambda function that print a message with a prefix and suffix This lambda can be
   * useful to format logs
   * <p>
   * You will learn: - functional interface http://bit.ly/2pTXRwM & http://bit.ly/33onFig - lambda
   * syntax
   * <p>
   * e.g. LambdaStreamExc lse = new LambdaStreamImp(); Consumer<String> printer =
   * lse.getLambdaPrinter("start>", "<end"); printer.accept("Message body");
   * <p>
   * sout: start>Message body<end
   *
   * @param prefix prefix str
   * @param suffix suffix str
   * @return Returns a Lambda print statement
   */
  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return (x -> System.out.print(prefix + x + suffix));
  }

  /**
   * Print each message with a given printer Please use `getLambdaPrinter` method
   * <p>
   * e.g. String[] messages = {"a","b", "c"}; lse.printMessages(messages,
   * lse.getLambdaPrinter("msg:", "!") );
   * <p>
   * sout: msg:a! msg:b! msg:c!
   *
   * @param messages An array of messages to be printed
   * @param printer  A Lambda-based Printer
   */
  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {
    createStrStream(messages).forEach(printer);
  }

  /**
   * Print all odd number from a intStream. Please use `createIntStream` and `getLambdaPrinter`
   * methods
   * <p>
   * e.g. lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
   * <p>
   * sout: odd number:1! odd number:3! odd number:5!
   *
   * @param intStream A stream of primitive ints
   * @param printer   A lambda that prints the values given to it with some decoration
   */
  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {
    getOdd(intStream).mapToObj(String::valueOf).forEach(printer);
  }

  /**
   * Square each number from the input. Please write two solutions and compare difference - using
   * map and flat - using flatMap
   *
   * @param ints A stream of Lists of Integers
   * @return A Stream of Integer objects
   */
  @Override
  public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints) {
    return ints.flatMap(List::stream).map(x -> (int) Math.pow(x, 2));
  }

  public static void main(String[] args) {
    LambdaStreamExcImp lsei = new LambdaStreamExcImp();
    String[] strings = new String[]{"one", "two", "thRee", "four", "FIVE"};
    int[] numbers = new int[]{1,2,3,4,5,6,7,8,9};
    // String test cases
    lsei.printMessages(strings, lsei.getLambdaPrinter("prefix ", " suffix\n"));
    lsei.printMessages(lsei.filter(lsei.createStrStream(strings), "t")
            .toArray(String[]::new), lsei.getLambdaPrinter("Filtered out 't' ", "\n"));
    lsei.printMessages(lsei.toUpperCase(strings).toArray(String[]::new),
        lsei.getLambdaPrinter("Upper ","\n"));
    // Int test cases
    lsei.printOdd(lsei.createIntStream(numbers),
        lsei.getLambdaPrinter("odd nums ", "\n"));
    lsei.printMessages(lsei.squareRootIntStream(lsei.createIntStream(1,9)).mapToObj(String::valueOf)
        .toArray(String[]::new), lsei.getLambdaPrinter("Square Root Ints ", "\n"));
    lsei.printMessages(lsei.flatNestedInt(
        Stream.of(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5, 6),
            Arrays.asList(7, 8, 9)
        )
        ).map(String::valueOf).toArray(String[]::new),
        lsei.getLambdaPrinter("Squared ", "\n"));
  }
}
