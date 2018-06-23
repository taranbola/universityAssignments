import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javafx.scene.image.Image;


/**
 * Simple representation of a playing card.
 *
 * <p>Provided for use in COMP1721 Coursework 2.</p>
 *
 * @author Nick Efford
 */
public class Card implements Comparable<Card> {

  /*--------------------------- Enumerated types ---------------------------*/

  public enum Rank {
    ACE('A'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'),
    SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'),
    JACK('J'), QUEEN('Q'), KING('K');

    private char symbol;

    Rank(char c) { symbol = c; }

    public char getChar() { return symbol; }

    @Override
    public String toString()
    {
      String rankName = name();
      return rankName.substring(0, 1) + rankName.substring(1).toLowerCase();
    }
  }

  public enum Suit {
    CLUBS('\u2663'), DIAMONDS('\u2666'),
    HEARTS('\u2665'), SPADES('\u2660');

    private char symbol;

    Suit(char c) { symbol = c; }

    public char getSymbol() { return symbol; }
    public char getChar() { return name().charAt(0); }

    @Override
    public String toString()
    {
      String suitName = name();
      return suitName.substring(0, 1) + suitName.substring(1).toLowerCase();
    }
  }

  /*--------------------------- Class-level code ---------------------------*/

  private static Map<String,Image> images = new HashMap<>();

  static {
    try (JarFile jar = new JarFile("images.jar")) {
      for (Suit suit : Suit.values()) {
        for (Rank rank : Rank.values()) {
          String card = String.format("%c%c", rank.getChar(), suit.getChar());
          String filename = card + ".png";
          JarEntry entry = jar.getJarEntry(filename);
          Image image = new Image(jar.getInputStream(entry));
          images.put(card, image);
        }
      }
    }
    catch (IOException error) {
      // Do nothing if images cannot be loaded
    }
  }

  /*------------------------- Instance-level code --------------------------*/

  private Rank rank;
  private Suit suit;

  /**
   * Creates a Card object.
   *
   * @param rank Rank of the card
   * @param suit Suit of the card
   */
  public Card(Rank rank, Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  /**
   * Creates a Card object, given its name as a string.
   *
   * <p>Name can either be given in full - e.g., "Ace of Clubs" - or
   * be abbreviated to a two-character code - e.g., "AC".</p>
   *
   * @param name Name of card
   * @throws IllegalArgumentException if string is invalid
   */
  public Card(String name) {
    if (name.length() > 2) {
      // Assume a long name format

      String[] parts = name.split("\\s+");
      if (parts.length == 3 && parts[1].toLowerCase().equals("of")) {
        rank = Rank.valueOf(parts[0].toUpperCase());
        suit = Suit.valueOf(parts[2].toUpperCase());
      }
      else {
        throw new IllegalArgumentException("Invalid card name format");
      }
    }
    else {
      // Assume a two-character name

      for (Rank r: Rank.values()) {
        if (r.getChar() == name.charAt(0)) {
          rank = r;
          break;
        }
      }

      if (rank == null) {
        throw new IllegalArgumentException("Unrecognised rank");
      }

      for (Suit s: Suit.values()) {
        if (s.getChar() == name.charAt(1)) {
          suit = s;
          break;
        }
      }

      if (suit == null) {
        throw new IllegalArgumentException("Unrecognised suit");
      }
    }
  }

  /**
   * @return This card's rank
   */
  public Rank getRank() {
    return rank;
  }

  /**
   * @return This card's suit
   */
  public Suit getSuit() {
    return suit;
  }

  /**
   * @return Image associated with this card
   */
  public Image getImage() {
    return images.get(this.toString());
  }

  /**
   * Computes the hash code for this card.
   *
   * @return Hash code
   */
  @Override public int hashCode() {
    // Algorithm comes from Josh Bloch's "Effective Java"

    final int prime = 37;
    int result = 17;
    result = prime*result + rank.ordinal();
    result = prime*result + suit.ordinal();

    return result;
  }

  /**
   * Tests whether this card is equal to another object.
   *
   * @param thing Object with which this card is being compared
   * @return true if thing is equal to this card, false otherwise
   */
  @Override
  public boolean equals(Object thing) {
    boolean same = false;

    if (thing == this) {
      same = true;
    }
    else if (thing != null && thing instanceof Card) {
      final Card card = (Card) thing;
      if (rank == card.rank && suit == card.suit) {
        same = true;
      }
    }

    return same;
  }

  /**
   * Creates a two-character string representation of this card.
   *
   * <p>The first character represents rank, the second represents suit.</p>
   *
   * @return String representation of this card
   */
  @Override
  public String toString() {
    return String.format("%c%c", rank.getChar(), suit.getChar());
  }

  /**
   * Creates a fancy two-character string representation of this card.
   *
   * <p>The first character represents rank, the second is a Unicode
   * symbol representing the suit.</p>
   *
   * @return String representation of this card
   */
  public String toFancyString() {
    return String.format("%c%c", rank.getChar(), suit.getSymbol());
  }

  /**
   * Generates this card's full name - e.g., "Ace of Spades".
   *
   * @return Full name of this card, as a string
   */
  public String fullName() {
    return String.format("%s of %s", rank, suit);
  }

  /**
   * Compares this card to another, using their natural ordering
   * (by suit, then by rank).
   *
   * @return A negative integer if this card comes before the other, 0 if
   *   they are the same, a positive integer if this card comes after
   */
  @Override
  public int compareTo(Card other) {
    int difference = suit.compareTo(other.suit);

    if (difference == 0) {
      difference = rank.compareTo(other.rank);
    }

    return difference;
  }

  /**
   * Computes the value of this card.
   *
   * <p>Value is based on rank and disregards suit. Aces score 1
   * and picture cards all score 10.</p>
   *
   * @return Card value
   */
  public int value() {
    return Math.min(rank.ordinal() + 1, 10);
  }
}
