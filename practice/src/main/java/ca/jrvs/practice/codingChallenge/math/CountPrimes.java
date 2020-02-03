package ca.jrvs.practice.codingChallenge.math;

import java.util.HashSet;
import java.util.Set;

/**
 * Ticket: https://www.notion.so/Count-Primes-6528ffb84a5c4d7cb5fd98984c5ff52e
 */
public class CountPrimes {

  /**
   * Returns the number of primes less than the given value, exclusive. This function uses a "sieve"
   * technique to determine which numbers are primes by process of elimination. Since primes are
   * wholly divisible by only one and themselves, you can eliminate every multiple of a prime from
   * the list of possible primes. Additionally, you can assume that by the sqrt(n)-th iteration, all
   * non-prime numbers have been marked, saving significant time. Time complexity is roughly O(n
   * log(n)) since each prime encountered reduces the size of potential prime numbers by up to half.
   * Space complexity is O(n) due to our use of a set.
   *
   * @param n The upper bound of values to check for primes inside.
   * @return The number of primes between 1 and n exclusive (ie. starting from 2)
   */
  public int countPrimes(int n) {
    if (n < 3) {
      return 0;
    } else if (n == 3) {
      return 1;
    }
    Set<Integer> nonPrimes = new HashSet<>();
    for (int i = 2; i * i < n; i++) {
      if (!nonPrimes.contains(i)) {
        for (int j = i * i; j < n; j += i) {
          nonPrimes.add(j);
        }
      }
    }
    return n - 2 - nonPrimes.size();
  }
}
