/**
 * ScreeningTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

/**
 * Test class for Screening.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby and Qasim Hussain
 */
public class ScreeningTest {

  /** Screening test object. */
  private Screening testScreening;

  /**
   * Sets up the test fixture (called before every test case method).
   */
  @Before
  public void setUp() {
    testScreening = new Screening();
  }

  /**
   * Tears down the test fixture (called after every test case method).
   */
  @After
  public void tearDown() {
    testScreening = null;
  }

  /**
   * Test screening_id can be set.
   */
  @Test
  public void testPosScreeningID() {
    int testScreeningID = 1;
    testScreening.setScreening_id(testScreeningID);
    assertThat(testScreening.getScreening_id(), is(testScreeningID));
  }

  /**
   * Test screening_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegScreeningID() {
    int testScreeningID = -1;
    testScreening.setScreening_id(testScreeningID);
  }

  /**
   * Test film_id can be set.
   */
  @Test
  public void testPosFilmID() {
    int testFilmID = 2;
    testScreening.setFilm_id(testFilmID);
    assertThat(testScreening.getFilm_id(), is(testFilmID));
  }

  /**
   * Test film_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegFilmID() {
    int testFilmID = -2;
    testScreening.setFilm_id(testFilmID);
  }

  /**
   * Test screening_time can be set.
   */
  @Test
  public void testScreeningTime() {
    LocalTime testScreeningTime = LocalTime.of(12,30,45,50);
    testScreening.setScreening_time(testScreeningTime);
    assertThat(testScreening.getScreening_time(), is(testScreeningTime));
  }

  /**
   * Test screening_date can be set.
   */
  @Test
  public void testScreeningDate() {
    LocalDate testScreeningDate = LocalDate.of(2018, Month.JANUARY, 1);
    testScreening.setScreening_date(testScreeningDate);
    assertThat(testScreening.getScreening_date(), is(testScreeningDate));
  }

  /**
   * Test screen_id can be set.
   */
  @Test
  public void testPosScreenID() {
    int testScreenID = 3;
    testScreening.setScreen_id(testScreenID);
    assertThat(testScreening.getScreen_id(), is(testScreenID));
  }

  /**
   * Test screen_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegScreenID() {
    int testScreenID = -3;
    testScreening.setScreen_id(testScreenID);
  }

  /**
   * Test Screening.java toString method.
   */
  @Test
  public void testToString() {
    int testScreeningID = 1;
    int testFilmID = 2;
    LocalTime testTime = LocalTime.of(12,30,45,50);
    LocalDate testDate = LocalDate.of(2018, Month.JANUARY, 1);
    int testScreenID = 3;

    String expectedOut = "Screening [screening_id=" + testScreeningID + ", film_id=" + testFilmID + ", screening_time=" + testTime + ", screening_date=" + testDate + ", screen_id=" + testScreenID + "]";

    testScreening.setScreening_id(testScreeningID);
    testScreening.setFilm_id(testFilmID);
    testScreening.setScreening_time(testTime);
    testScreening.setScreening_date(testDate);
    testScreening.setScreen_id(testScreenID);

    assertThat(testScreening.toString(), is(expectedOut));
  }
}
