// Correctness testing for COMP1721 Coursework 2 (Basic Solution)

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class BasicTests {

  private Deck deck;
  private PokerHand empty, oneCard, twoCards, full;

  @Before
  public void setUp() {
    deck = new Deck();
    empty = new PokerHand();
    oneCard = new PokerHand("AC");
    twoCards = new PokerHand("AC 3D");
    full = new PokerHand("AC 3D 5H 7S 9C");
  }

  // Deck class

  @Test
  public void deckSuperclass() {
    Class c = deck.getClass().getSuperclass();
    assertThat(c.getName(), is("CardCollection"));
  }

  @Test
  public void fullDeck() {
    assertThat(deck.size(), is(52));
  }

  @Test
  public void dealFullDeck() {
    Card c = deck.deal();
    assertThat(deck.size(), is(51));
    assertThat(c.getRank(), is(Card.Rank.ACE));
    assertThat(c.getSuit(), is(Card.Suit.CLUBS));
    c = deck.deal();
    assertThat(deck.size(), is(50));
    assertThat(c.getRank(), is(Card.Rank.TWO));
    assertThat(c.getSuit(), is(Card.Suit.CLUBS));
  }

  @Test(expected=PokerException.class)
  public void dealEmptyDeck() {
    deck.discard();
    deck.deal();
  }

  // PokerHand class

  @Test
  public void handSuperclass() {
    Class c = empty.getClass().getSuperclass();
    assertThat(c.getName(), is("CardCollection"));
  }

  @Test
  public void handCreation() {
    assertThat(empty.size(), is(0));
    assertThat(oneCard.size(), is(1));
    assertThat(twoCards.size(), is(2));
  }

  @Test(expected=PokerException.class)
  public void tooManyCards() {
    new PokerHand("AC 3D 5H 7S 9C JD");
  }

  @Test
  public void addCard() {
    PokerHand hand = new PokerHand();
    hand.add(new Card("AC"));
    assertThat(hand.size(), is(1));
    hand.add(new Card("3D"));
    assertThat(hand.size(), is(2));
  }

  @Test(expected=PokerException.class)
  public void addToFullHand() {
    full.add(new Card("JD"));
  }

  @Test(expected=PokerException.class)
  public void addCardAlreadyPresent() {
    oneCard.add(new Card("AC"));
  }

  @Test
  public void discardToDeck() {
    PokerHand hand = new PokerHand();
    hand.add(deck.deal());
    hand.add(deck.deal());
    assertThat(hand.size(), is(2));
    assertThat(deck.size(), is(50));
    hand.discard(deck);
    assertThat(hand.size(), is(0));
    assertThat(deck.size(), is(52));
  }

  @Test(expected=PokerException.class)
  public void discardEmptyHand() {
    empty.discard(deck);
  }

  @Test
  public void handAsString() {
    assertThat(empty.toString(), is(""));
    assertThat(oneCard.toString(), is("AC"));
    assertThat(twoCards.toString(), is("AC 3D"));
    assertThat(full.toString(), is("AC 3D 5H 7S 9C"));
  }

  @Test
  public void handAsFancyString() {
    assertThat(empty.toFancyString(), is(""));
    assertThat(oneCard.toFancyString(), is("A\u2663"));
    assertThat(twoCards.toFancyString(), is("A\u2663 3\u2666"));
    assertThat(full.toFancyString(), is("A\u2663 3\u2666 5\u2665 7\u2660 9\u2663"));
  }

  @Test
  public void isFourOfAKind() {
    PokerHand fourCards = new PokerHand("AC AD AH AS");
    PokerHand threeOfAKind = new PokerHand("AC AD AH 3S 5C");
    PokerHand fourOfAKind = new PokerHand("AC AD AH AS 3C");
    assertThat(empty.isFourOfAKind(), is(false));
    assertThat(fourCards.isFourOfAKind(), is(false));
    assertThat(threeOfAKind.isFourOfAKind(), is(false));
    assertThat(fourOfAKind.isFourOfAKind(), is(true));
  }

  @Test
  public void isFlush() {
    PokerHand fourCards = new PokerHand("AC 3C 5C 7C");
    PokerHand flushClubs = new PokerHand("AC 3C 5C 7C 9C");
    PokerHand flushSpades = new PokerHand("AS 3S 5S 7S 9S");
    PokerHand notFlush = new PokerHand("AC 3C 5C 7C 9D");
    assertThat(empty.isFlush(), is(false));
    assertThat(fourCards.isFlush(), is(false));
    assertThat(flushClubs.isFlush(), is(true));
    assertThat(flushSpades.isFlush(), is(true));
    assertThat(notFlush.isFlush(), is(false));
  }

}
