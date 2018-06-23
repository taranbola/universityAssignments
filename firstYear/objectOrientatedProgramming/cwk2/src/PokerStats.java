import java.util.*;
/**
 * A class to create a number of decks of pokerhand
 *
 * <p>This will see the different type of pokerhands there are and their
 * probability. It will see how the probability from the number of deals the
 * user enters on the command line. </p>
 *
 * <p>Provided for use in COMP1721 Coursework 2.</p>
 *
 * @author Taranvir Bola
 */
public class PokerStats extends CardCollection{
  /**
   * main User specifies number of decks, creates this many with pokerhands and
   * then calculates the different hands it get's.
   * @param args User specifies in this parameter the number of decks they want.
   */
  public static void main(String[] args) {

    int noOfDecks = Integer.parseInt(args[0]);
    int noOfFlushes = 0;
    int fourOfKind = 0;
    int noOfPairs = 0;
    int noOfTwoPairs = 0;
    int threeOfKind = 0;
    int noOffullHouse = 0;
    int noOfStraights = 0;

    for(int noOfDecksCounter = 0; noOfDecksCounter < noOfDecks; noOfDecksCounter++){
      Deck deck = new Deck();
      deck.shuffle();
      for(int counter = 0; counter < 10; counter++){
        PokerHand pokerhand = new PokerHand();
        for(int pokerhandcounter = 0; pokerhandcounter < 5; pokerhandcounter++){
          pokerhand.add(deck.deal());
          if(pokerhand.isFlush() == true){
            noOfFlushes++;
          }

          if(pokerhand.isFourOfAKind() == true){
            fourOfKind++;
          }

          if(pokerhand.isPair() == true){
            noOfPairs++;
          }

          if(pokerhand.isTwoPairs() == true){
            noOfTwoPairs++;
          }

          if(pokerhand.isThreeOfAKind() == true){
            threeOfKind++;
          }

          if(pokerhand.isFullHouse() == true){
            noOffullHouse++;
          }

          if(pokerhand.isStraight() == true){
            noOfStraights++;
          }

        }
        System.out.println(pokerhand.toFancyString());
      }
    System.out.println("");
    }

    System.out.println(noOfDecks*10 + " deals" + "\n" );
    System.out.printf("P(Pair)\t\t\t= %.3f%%\n", noOfPairs / (float)noOfDecks*10);
    System.out.printf("P(Two Pairs)\t\t= %.3f%%\n", noOfTwoPairs / (float)noOfDecks*10);
    System.out.printf("P(Three Of A Kind)\t= %.3f%%\n", threeOfKind / (float)noOfDecks*10);
    System.out.printf("P(Four Of A Kind)\t= %.3f%%\n", fourOfKind / (float)noOfDecks*10);
    System.out.printf("P(Full House)\t\t= %.3f%%\n", noOffullHouse / (float)noOfDecks*10);
    System.out.printf("P(Flush)\t\t= %.3f%%\n", noOfFlushes / (float)noOfDecks*10);
    System.out.printf("P(Straight)\t\t= %.3f%%\n", noOfStraights / (float)noOfDecks*10);

  }
}
