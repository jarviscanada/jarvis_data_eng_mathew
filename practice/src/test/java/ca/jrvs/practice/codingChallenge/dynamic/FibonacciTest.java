package ca.jrvs.practice.codingChallenge.dynamic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FibonacciTest {

  @Test
  public void fibonacciRecursive() {
    assertEquals(55, Fibonacci.fibonacciRecursive(10));
    assertEquals(75025, Fibonacci.fibonacciRecursive(25));
    assertEquals(3524578, Fibonacci.fibonacciRecursive(33));
  }

  // This test is just to illustrate the time difference between dynamic and pure recursive
  // This call takes 6 orders of magnitude more calculations than fibonacciRecursive(33)
  @Test
  public void bigFibRecursive() {
    assertEquals(12586269025L, Fibonacci.fibonacciRecursive(50));
  }

  @Test
  public void fibonacciDynamic() {
    assertEquals(55, Fibonacci.fibonacciDynamic(10));
    assertEquals(75025, Fibonacci.fibonacciDynamic(25));
    assertEquals(3524578, Fibonacci.fibonacciDynamic(33));
  }

  // This test is just to illustrate the time difference between Dynamic and pure recursive solution
  // This call completes in ~4ms while the recursive version took ~1 minute
  @Test
  public void bigFibDynamic() {
    assertEquals(12586269025L, Fibonacci.fibonacciDynamic(50));
  }
}