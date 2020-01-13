package ca.jrvs.practice.codingChallenge.list;

import static org.junit.Assert.*;

import ca.jrvs.practice.codingChallenge.list.LinkedListCycle.ListNode;
import org.junit.Before;
import org.junit.Test;

public class MiddleOfLinkedListTest {

    ListNode one;
    ListNode two;
    ListNode three;
    ListNode four;
    ListNode five;
    MiddleOfLinkedList middleTest;

    @Before
    public void setup() {
      middleTest = new MiddleOfLinkedList();
      one = new ListNode(1);
      two = new ListNode(2);
      three = new ListNode(3);
      four = new ListNode(4);
      five = new ListNode(5);
    }

  @Test
  public void middleNode_oddLength() {
    one.next = two;
    two.next = three;
    three.next = four;
    four.next = five;
    five.next = null;
    assertEquals(three, middleTest.middleNode(one));
  }

  @Test
  public void middleNode_EvenLength() {
      one.next = two;
      two.next = three;
      three.next = four;
      four.next = null;
    assertEquals(three, middleTest.middleNode(one));
  }

  @Test
  public void middleNode_zeroLength() {
    assertNull(middleTest.middleNode(null));
  }

  @Test
  public void middleNode_oneLength() {
      assertEquals(one, middleTest.middleNode(one));
  }

  @Test
  public void middleNode_twoLength() {
      one.next = two;
      two.next = null;
      assertEquals(two, middleTest.middleNode(two));
  }
}