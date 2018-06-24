/**
 * TicketTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Test class for Ticket.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby
 */
public class TicketTest {

  /** Ticket test object. */
  private Ticket testTicket;

  /**
   * Sets up the test fixture (called before every test case method).
   */
  @Before
  public void setUp() {
    testTicket = new Ticket();
  }

  /**
   * Tears down the test fixture (called after every test case method).
   */
  @After
  public void tearDown() {
    testTicket = null;
  }

  /**
   * Test ticket_id can be set.
   */
  @Test
  public void testPosTicketID() {
    int testID = 1;
    testTicket.setTicket_id(testID);
    assertThat(testTicket.getTicket_id(), is(testID));
  }

  /**
   * Test ticket_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegTicketID() {
    int testID = -1;
    testTicket.setTicket_id(testID);
  }

  /**
   * Test customer_id can be set.
   */
  @Test
  public void testPosCustomerID() {
    int testID = 2;
    testTicket.setCustomer_id(testID);
    assertThat(testTicket.getCustomer_id(), is(testID));
  }

  /**
   * Test customer_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegCustomerID() {
    int testID = -2;
    testTicket.setCustomer_id(testID);
  }

  /**
   * Test screening_id can be set.
   */
  @Test
  public void testPosScreeningID() {
    int testID = 3;
    testTicket.setScreening_id(testID);
    assertThat(testTicket.getScreening_id(), is(testID));
  }

  /**
   * Test screening_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegScreeningID() {
    int testID = -3;
    testTicket.setScreening_id(testID);
  }

  /**
   * Test seat_id can be set.
   */
  @Test
  public void testPosSeatID() {
    int testID = 4;
    testTicket.setSeat_id(testID);
    assertThat(testTicket.getSeat_id(), is(testID));
  }

  /**
   * Test seat_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegSeatID() {
    int testID = -4;
    testTicket.setSeat_id(testID);
  }

  /**
   * Test Ticket.java toString method.
   */
  @Test
  public void testToString() {
    int testTicketID = 1;
    int testCustomerID = 2;
    int testScreeningID = 3;
    int testSeatID = 4;

    String expectedOut = "Ticket [ticket_id=" + testTicketID + ", customer_id=" + testCustomerID + ", screening_id=" + testScreeningID + ", seat_id=" + testSeatID + "]";

    testTicket.setTicket_id(testTicketID);
    testTicket.setCustomer_id(testCustomerID);
    testTicket.setScreening_id(testScreeningID);
    testTicket.setSeat_id(testSeatID);

    assertThat(testTicket.toString(), is(expectedOut));
  }
}
