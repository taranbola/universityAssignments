import java.util.*;
/**
 * A class to create and deal 52 playing cards
 *
 * <p>This will create a 52 deck of playing cards. It will
 * also deal them and shuffle them as well. </p>
 *
 * <p>Provided for use in COMP1721 Coursework 2.</p>
 *
 * @author Taranvir Bola
 */
public class Deck extends CardCollection{
  /**
   * Deck will create a 52 deck of playing cards.
   */
  public Deck(){
    super();
    Card cards = new Card(Card.Rank.values()[0], Card.Suit.values()[0]);
    for (Card.Suit s: Card.Suit.values() ){
      for (Card.Rank r: Card.Rank.values() ){
        cards = new Card(r,s);
        this.add(cards);
      }
    }
  }
  /**
   * This will deal a particular card by removing it from the Deck.
   * @return It will return the card that is removed to be put in the pokerhand.
   */
  public Card deal(){
    if (this.cards.isEmpty()){
      throw new PokerException("Deck is empty");
    }
    else{
      Card dealed = this.cards.get(0);
      this.cards.remove(dealed);
      return dealed;
    }
  }
  /**
   * This will shuffle deck of cards.
   */
  public void shuffle(){
    Collections.shuffle(this.cards);
  }
}
