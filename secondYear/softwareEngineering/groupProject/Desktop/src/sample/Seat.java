/**
 * Seat.java
 */

package sample;

/**
 * Class for representing a seat.
 *
 * @author Mitchell Gladstone
 */
public class Seat{
  /** ID number of the seat. */
  private int seat_id;

  /**
   * @return ID number of seat.
   */
  public int getSeat_id(){
    return this.seat_id;
  }

  /**
   * Sets new value of this.seat_id.
   * @param id ID number of seat.
   */
  public void setSeat_id(int id){
    if(id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
      this.seat_id = id;
    }
  }

  /**
   * @return String representation of Seat for printing.
   */
  public String toString(){
      return "Seat [id="+this.seat_id+"]";
  }

}
