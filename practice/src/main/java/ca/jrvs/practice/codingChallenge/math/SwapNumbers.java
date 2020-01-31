package ca.jrvs.practice.codingChallenge.math;

/**
 * Ticket: https://www.notion.so/Swap-two-numbers-d8b93b96aad54bd59bc492986647b0f7
 */
public class SwapNumbers {

  /*
   * This challenge requires swapping two numbers in an array WITHOUT using the traditional
   * "triangle swap" method.
   */

  /**
   * Swap two numbers in an array using bit manipulation. A ^ B ^ A = B and B ^ A ^ B = A 1000 ^
   * 0111 = 1111, 1111 ^ 1000 = 0111; 0111 ^ 1000 = 1111, 1111 ^ 0111 = 1000;
   * <p>
   * Time complexity is O(1) since we perform a total of 3 operations. Space complexity is O(0)
   * since we use no extra space.
   *
   * @param arr    The array to swap values in
   * @param first  The index of the first value
   * @param second The index of the second value
   */
  public void swapNumbersBitManip(int[] arr, int first, int second) {
    arr[first] ^= arr[second];
    arr[second] ^= arr[first];
    arr[first] ^= arr[second];
  }

  /**
   * Swap two numbers using simple math. C = A + B, therefore C - B = A and C - A = B. Overflow is
   * not a concern since the subsequent subtraction will cause an underflow, in Java at least.
   * <p>
   * Time complexity is O(1) since we perform 3 operations. Space complexity is O(0) since we use no
   * extra space.
   *
   * @param arr    The array to swap values in.
   * @param first  The index of the first value
   * @param second The index of the second value
   */
  public void swapNumbersMath(int[] arr, int first, int second) {
    arr[first] += arr[second];
    arr[second] = arr[first] - arr[second];
    arr[first] = arr[first] - arr[second];
  }

}
