/**
 * A class to show a message if an error occurs.
 *
 * <p>This will allow to send an error with a
 * personalised message for that error. </p>
 *
 * <p>Provided for use in COMP1721 Coursework 2.</p>
 *
 * @author Taranvir Bola
 */
public class PokerException extends RuntimeException{
  /**
   * Creates a Poker Exception from RuntimeException
   *
   * @param message A string which is a message outputted with the Exception
   */
  public PokerException(String message){
     super(message);
   }
}
