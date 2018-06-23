// Correctness testing for COMP1721 Coursework 2 (Full Solution)

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class FullTests {

  private PokerHand empty, full, pair, twoPairs;
  private PokerHand threeOfAKind, fourOfAKind, fullHouse;

  @Before
  public void setUp() {
    empty = new PokerHand();
    full = new PokerHand("AC 3D 5H 7S 9C");
    pair = new PokerHand("AC AD 3H 5S 7C");
    twoPairs = new PokerHand("AC AD 3H 3S 5C");
    threeOfAKind = new PokerHand("AC AD AH 3S 5C");
    fourOfAKind = new PokerHand("AC AD AH AS 3C");
    fullHouse = new PokerHand("AC AD AH 3S 3C");
  }

  @Test
  public void isPair() {
    PokerHand fourCards = new PokerHand("AC AD 3H 5S");
    assertThat(empty.isPair(), is(false));
    assertThat(fourCards.isPair(), is(false));
    assertThat(full.isPair(), is(false));
    assertThat(pair.isPair(), is(true));
    assertThat(twoPairs.isPair(), is(false));
  }

  @Test
  public void isTwoPairs() {
    PokerHand fourCards = new PokerHand("AC AD 3H 3S");
    assertThat(empty.isPair(), is(false));
    assertThat(fourCards.isTwoPairs(), is(false));
    assertThat(full.isTwoPairs(), is(false));
    assertThat(pair.isTwoPairs(), is(false));
    assertThat(twoPairs.isTwoPairs(), is(true));
  }

  @Test
  public void isThreeOfAKind() {
    PokerHand fourCards = new PokerHand("AC AD AH 3S");
    assertThat(empty.isThreeOfAKind(), is(false));
    assertThat(fourCards.isThreeOfAKind(), is(false));
    assertThat(pair.isThreeOfAKind(), is(false));
    assertThat(threeOfAKind.isThreeOfAKind(), is(true));
    assertThat(fourOfAKind.isThreeOfAKind(), is(false));
    assertThat(fullHouse.isThreeOfAKind(), is(false));
  }

  @Test
  public void isFullHouse() {
    assertThat(empty.isFullHouse(), is(false));
    assertThat(pair.isFullHouse(), is(false));
    assertThat(threeOfAKind.isFullHouse(), is(false));
    assertThat(fourOfAKind.isFullHouse(), is(false));
    assertThat(fullHouse.isFullHouse(), is(true));
  }

  @Test
  public void isStraight() {
    PokerHand fourCards = new PokerHand("2C 3D 4H 5S");
    PokerHand straight = new PokerHand("2C 3D 4H 5S 6C");
    PokerHand lowAceStraight = new PokerHand("AC 2D 3H 4S 5C");
    PokerHand notStraight = new PokerHand("2C 3D 4H 5S 7C");
    assertThat(empty.isStraight(), is(false));
    assertThat(fourCards.isStraight(), is(false));
    assertThat(straight.isStraight(), is(true));
    assertThat(lowAceStraight.isStraight(), is(true));
    assertThat(notStraight.isStraight(), is(false));
  }

  @Test
  public void isStraightHighAce() {
    PokerHand highAceStraight = new PokerHand("TC JD QH KS AC");
    assertThat(highAceStraight.isStraight(), is(true));
  }

}
