package ca.jrvs.practice.codingChallenge.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CountPrimesTest {

  CountPrimes countPrimes = new CountPrimes();

  @Test
  public void countPrimes() {
    assertEquals(4, countPrimes.countPrimes(10));
    assertEquals(10, countPrimes.countPrimes(30));
    assertEquals(0, countPrimes.countPrimes(2));
    assertEquals(1, countPrimes.countPrimes(3));
  }

  @Test
  public void countPrimes_Large() {
    assertEquals(100, countPrimes.countPrimes(542));
  }

  @Test
  public void countPrimes_VeryLarge() {
    assertTrue(100 < countPrimes.countPrimes(90000));
  }
}