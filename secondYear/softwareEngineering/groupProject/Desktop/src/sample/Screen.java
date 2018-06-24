/**
 * Screen.java
 */

package sample;
import java.util.Map;

/**
 * Class for providing all information for screen information.
 *
 * @author
 */
public class Screen extends Resource {

  /** ID number of the screen. */
  private int screen_id;
  /** Capacity of the screen. */
  private int screen_capacity;

  /**
   * @return ID number of screen.
   */
	public int getScreen_id() {
		return screen_id;
	}

	/**
	 * Sets new value of this.screen_id.
	 * @param screen_id ID number of screen.
	 */
	public void setScreen_id(int screen_id) {
    if(screen_id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
		  this.screen_id = screen_id;
    }
	}

	/**
	 * @return Capacity of screen.
	 */
	public int getScreen_capacity() {
		return screen_capacity;
	}

	/**
	 * Sets new value of this.screen_capacity.
	 * @param screen_capacity Capacity of screen.
	 */
	public void setScreen_capacity(int screen_capacity) {
    if(screen_capacity < 0) {
      throw new IllegalArgumentException("Capacity must NOT be negative.");
    } else {
		  this.screen_capacity = screen_capacity;
    }
	}

	/**
	 * @return String representation of Screen for printing.
	 */
	@Override
	public String toString() {
		return "Screen [screen_id=" + screen_id + ", screen_capacity=" + screen_capacity + "]";
	}
}
