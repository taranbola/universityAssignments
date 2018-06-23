import java.util.*;
/**
 * A class to create a pokerhand
 *
 * <p>This will see what type of different pokers hands there are. It
 * will also add and discard in the pokerhand. </p>
 *
 * <p>Provided for use in COMP1721 Coursework 2.</p>
 *
 * @author Taranvir Bola
 */
public class PokerHand extends CardCollection{
  /**
   * PokerHand This is a constructor for pokerhand
   * It will create an empty hand.
   */
  public PokerHand(){
    super();
  }

  /**
   * PokerHand Creates a poker hand of cards using a parameter
   * @param cards Takes in a string called cards which the values are turned
   * into the cards in pokerhand.
   */
  public PokerHand(String cards){
     if(!cards.isEmpty()){
       String[] parts = cards.split("\\s+");
       if(parts.length < 6 ){
        for(String currentCard: parts){
          Card temp = new Card(currentCard);
          super.add(temp);
        }
       }
       else{
        throw new PokerException("Poker Hand has more than 5 cards.");
       }
     }
   }

  /**
   * add  will add a new card to the poker hand from the deck
   * @param card It will add card if the pokerhand isn't full
   * or it doesn't contain it
   */
  public void add(Card card){
    if(cards.size() < 5 && (cards.contains(card)) == false){
      super.add(card);
    }
    else if(cards.size() == 5){
      throw new PokerException("Poker Hand is full");
    }
    else{
      throw new PokerException("Hand already contains this Card");
    }
  }

  /**
   * discard This will add all the cards in pokerhand back to the deck. It will
   * then discard the pokerhand.
   * @param deck This is the deck of 52 cards, which will take in the
   * pokerhand's cards.
   */
  public void discard(Deck deck){
    if(cards.size() == 0){
      throw new PokerException("Poker hand is empty.");
    }
    else{
      for(Card currentCard: this.cards){
        deck.add(currentCard);
      }
      super.discard();
    }
  }

  /**
   * toString This will create a string for the pokerhand
   * @return It returns the string of the pokerhand
   */
  public String toString(){
    String pokerHandString = "";
    for(Card currentCard: this.cards){
      if(pokerHandString.length() != 0){
        pokerHandString += " ";
      }
      pokerHandString += (currentCard.toString());
    }
    return pokerHandString;
  }

  /**
   * toFancyString This will create a fancystring for the pokerhand
   * @return It returns the fancystring of the pokerhand
   */
  public String toFancyString(){
    String pokerHandFancyString = "";
    for(Card currentCard: this.cards){
      if(pokerHandFancyString.length() != 0){
        pokerHandFancyString += " ";
      }
      pokerHandFancyString += (currentCard.toFancyString());
    }
    return pokerHandFancyString;
  }

  /**
   * isFourOfAKind It will see if the pokerhand is a fourOfKind
   * @return It will return true if it is or false if it isn't.
   */
  public boolean isFourOfAKind(){
    if(cards.size() != 5){
      return false;
    }

    Map<Card.Rank,Integer>isFourKindMap = new HashMap<>();
    for(Card currentCard: this.cards){
      Card.Rank key = currentCard.getRank();
      if (isFourKindMap.containsKey(key)){
        isFourKindMap.put(key,isFourKindMap.get(key)+1);
      }
      else{
        isFourKindMap.put(key, 1);
      }
    }
    if(isFourKindMap.containsValue(4)){
      return true;
    }
      return false;
  }

  /**
   * isFlush It will see if the pokerhand is a flush
   * @return It will return true if it is or false if it isn't.
   */
  public boolean isFlush(){
    if(cards.size() != 5){
      return false;
    }

    Map<Card.Suit,Integer>isFlushMap = new HashMap<>();
    for(Card currentCard: this.cards){
      Card.Suit key = currentCard.getSuit();
      if (isFlushMap.containsKey(key)){
        isFlushMap.put(key,isFlushMap.get(key)+1);
      }
      else{
        isFlushMap.put(key, 1);
      }
    }
    if(isFlushMap.containsValue(5)){
      return true;
    }
      return false;
  }

  /**
   * isPair It will see if the pokerhand is a pair.
   * @return It will return true if it is or false if it isn't.
   */
  public boolean isPair(){
    if((cards.size() != 5) || (isTwoPairs() == true)){
      return false;
    }

    Map<Card.Rank,Integer>isPairMap = new HashMap<>();
    for(Card currentCard: this.cards){
      Card.Rank key = currentCard.getRank();
      if (isPairMap.containsKey(key)){
        isPairMap.put(key,isPairMap.get(key)+1);
      }
      else{
        isPairMap.put(key, 1);
      }
    }
    if(isPairMap.containsValue(2)){
      return true;
    }
      return false;
  }

  /**
   * isTwoPairs It will see if the pokerhand is a two pair.
   * @return It will return true if it is or false if it isn't.
   */
  public boolean isTwoPairs(){
    if(cards.size() != 5){
      return false;
    }

    Map<Card.Rank,Integer>isTwoPairMap = new HashMap<>();
    for(Card currentCard: this.cards){
      Card.Rank key = currentCard.getRank();
      if (isTwoPairMap.containsKey(key)){
        isTwoPairMap.put(key,isTwoPairMap.get(key)+1);
      }
      else{
        isTwoPairMap.put(key, 1);
      }
    }
    if(isTwoPairMap.containsValue(2)){
      int counterPairs = 0;
      for(Integer check : isTwoPairMap.values()){
        if(check == 2){
          counterPairs++;
        }
      }
      if(counterPairs == 2){
      return true;
    }
    }
      return false;
  }

  /**
   * isThreeOfAKind It will see if the pokerhand is a three of a kind.
   * @return It will return true if it is or false if it isn't.
   */
  public boolean isThreeOfAKind(){
    if((cards.size() != 5) || (isFullHouse() == true)){
      return false;
    }

    Map<Card.Rank,Integer>isPairMap = new HashMap<>();
    for(Card currentCard: this.cards){
      Card.Rank key = currentCard.getRank();
      if (isPairMap.containsKey(key)){
        isPairMap.put(key,isPairMap.get(key)+1);
      }
      else{
        isPairMap.put(key, 1);
      }
    }
    if(isPairMap.containsValue(3)){
      return true;
    }
      return false;
  }

  /**
   * isFullHouse It will see if the pokerhand is a full house
   * @return It will return true if it is or false if it isn't.
   */
  public boolean isFullHouse(){
    if(cards.size() != 5){
      return false;
    }

    Map<Card.Rank,Integer>isFullHouseMap = new HashMap<>();
    for(Card currentCard: this.cards){
      Card.Rank key = currentCard.getRank();
      if (isFullHouseMap.containsKey(key)){
        isFullHouseMap.put(key,isFullHouseMap.get(key)+1);
      }
      else{
        isFullHouseMap.put(key, 1);
      }
    }
    if(isFullHouseMap.containsValue(2) && isFullHouseMap.containsValue(3)){
      return true;
    }
      return false;
  }

  /**
   * isStraight It will see if the pokerhand is a three of a kind.
   * @return It will return true if it is or false if it isn't.
   */
  public boolean isStraight(){
    if(cards.size() != 5){
      return false;
    }

    int ordered = this.cards.get(0).getRank().ordinal();

    for(int counter = 1; counter < this.cards.size(); counter++){
      if(this.cards.get(counter).getRank().ordinal() == ordered + 1){
        ordered++;
      }
      else if ((this.cards.get(this.cards.size() - 1 ).getRank() == Card.Rank.ACE) && (counter == this.cards.size() - 1 )){
        ordered++;
      }
      else{
        return false;
      }
    }
    return true;
  }

}
