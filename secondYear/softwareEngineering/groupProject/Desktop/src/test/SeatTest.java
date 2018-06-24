/**
 * SeatTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Test class for Seat.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby
 */
public class SeatTest {

  /** Seat test object. */
  private Seat testSeat;

  /**
   * Sets up the test fixture (called before every test case method).
   */
  @Before
  public void setUp() {
    testSeat = new Seat();
  }

  /**
   * Tears down the test fixture (called after every test case method).
   */
  @After
  public void tearDown() {
    testSeat = null;
  }

  /**
   * Test seat_id can be set.
   */
  @Test
  public void testPosSeatID() {
    int testID = 64;
    testSeat.setSeat_id(testID);
    assertThat(testSeat.getSeat_id(), is(testID));
  }

  /**
   * Test seat_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegSeatID() {
    int testID = -64;
    testSeat.setSeat_id(testID);
  }

  /**
   * Test Seat.java toString method.
   */
  @Test
  public void testToString() {
    int testID = 64;

    String expectedOut = "Seat [id=" + testID + "]";

    testSeat.setSeat_id(testID);

    assertThat(testSeat.toString(), is(expectedOut));
  }
}
