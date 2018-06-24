/**
 * BookingScreenControllerTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.time.LocalDate;
import java.time.Month;

/**
 * Test class for BookingScreenController.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby and Qasim Hussain
 */
public class BookingScreenControllerTest {

  /** BSC test object. */
  private BookingScreenController testBSC;

  /**
   * Sets up the test fixture (called before every test case method).
   */
  @Before
  public void setUp() {
    testBSC = new BookingScreenController();
  }

  /**
   * Tears down the test fixture (called after every test case method).
   */
  @After
  public void tearDown() {
    testBSC = null;
  }

  // /**
  //  * Test date can be set.
  //  */
  // @Test
  // public void testDate() {
  //   LocalDate testDate = LocalDate.of(2018, Month.JANUARY, 1);
  //   testBSC.setDate(testDate);
  //   assertThat(testBSC.getDate(), is(testDate));
  // }
  //
  // /**
  //  * Test date cannot be in the future.
  //  */
  // @Test(expected = IllegalArgumentException.class)
  // public void testFutureDate() {
  //   LocalDate testDate = LocalDate.of(3018, Month.JANUARY, 1);
  //   testBSC.setDate(testDate);
  // }

  /**
   * Test time can be set.
   */
  @Test
  public void testTime() {
    String testTime = "12:30";
    testBSC.setTime(testTime);
    assertThat(testBSC.getTime(), is(testTime));
  }

  /**
   * Test time cannot be an empty string.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNoTime() {
    String testTime = "";
    testBSC.setTime(testTime);
  }

  /**
   * Test time cannot be null.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNullTime() {
    String testTime = null;
    testBSC.setTime(testTime);
  }

  /**
   * Test ScreeningID can be set.
   */
  @Test
  public void testPosScreeningID() {
    int testScreeningID = 123;
    testBSC.setScreeningID(testScreeningID);
    assertThat(testBSC.getScreeningID(), is(testScreeningID));
  }

  /**
   * Test ScreeningID cannot be set to a negative integer.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testNegScreeningID() {
    int testScreeningID = -123;
    testBSC.setScreeningID(testScreeningID);
  }
}
