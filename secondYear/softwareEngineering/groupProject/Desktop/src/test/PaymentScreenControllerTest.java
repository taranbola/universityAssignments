/**
 * PaymentScreenControllerTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * Test class for PaymentScreenController.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby and Qasim Hussain
 */
public class PaymentScreenControllerTest {

  /** PaymentScreenController test object. */
  private PaymentScreenController testPSC;

    /**
     * Sets up the test fixture (Called before every test case method).
     */
    @Before
    public void setUp() {
      testPSC = new PaymentScreenController();
    }

    /**
     * Tears down the test fixture (Called after every test case method).
     */
    @After
    public void tearDown() {
      testPSC = null;
    }

    /**
     * Test Seats can be set.
     */
    @Test
    public void testSeats() {
      List<String> testSeats = new ArrayList<String>();
      testSeats.add("seat1");
      testSeats.add("seat2");
      testSeats.add("seat3");

      testPSC.setSeats(testSeats);

      assertThat(testPSC.getSeats(), is(testSeats));
      assertThat(testPSC.getSeats().size(), is(3));
      assertThat(testPSC.getSeats().get(1), is("seat2"));
    }

    /**
     * Test ScreenID can be set.
     */
    @Test
    public void testPosScreenID() {
      int testScreenID = 123;
      testPSC.setScreenID(testScreenID);
      assertThat(testPSC.getScreenID(), is(testScreenID));
    }

    /**
     * Test ScreenID cannot be set to a negative integer.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegScreenID() {
      int testScreenID = -123;
      testPSC.setScreenID(testScreenID);
    }

}
