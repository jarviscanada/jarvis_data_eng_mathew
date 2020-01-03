package ca.jrvs.practice.codingChallenge.arrays;

import org.junit.Test;

public class PrintLetterNumberTest {

  String test1 = "test";
  String test2 = "abcdefghijklmnopqrstuvwxyz";
  String test3 = "aAaAaAbBbBbB";
  String test4 = "";
  String test5 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  PrintLetterNumber printLetterNumber = new PrintLetterNumber();

  @Test
  public void printLetterNumber() {
    printLetterNumber.printLetterNumber(test1);
    printLetterNumber.printLetterNumber(test2);
    printLetterNumber.printLetterNumber(test3);
    printLetterNumber.printLetterNumber(test4);
    printLetterNumber.printLetterNumber(test5);
  }
}