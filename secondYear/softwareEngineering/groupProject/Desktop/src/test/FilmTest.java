/**
 * FilmTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Test class for Film.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby and Qasim Hussain
 */
public class FilmTest {

  /** Film test object. */
  private Film testFilm;

  /**
   * Sets up the test fixture (called before every test case method).
   */
  @Before
  public void setUp() {
    testFilm = new Film();
  }

  /**
   * Tears down the test fixture (called after every test case method).
   */
  @After
  public void tearDown() {
    testFilm = null;
  }

  /**
   * Test film_id can be set.
   */
  @Test
  public void testPosFilmID() {
    int testID = 64;
    testFilm.setFilm_id(testID);
    assertThat(testFilm.getFilm_id(), is(testID));
  }

  /**
   * Test film_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegFilmID() {
    int testID = -64;
    testFilm.setFilm_id(testID);
  }

  /**
   * Test film_name can be set.
   */
  @Test
  public void testFilmName() {
    String testFilmName = "Alpha";
    testFilm.setFilm_name(testFilmName);
    assertThat(testFilm.getFilm_name(), is(testFilmName));
  }

  /**
   * Test film_name cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoFilmName() {
    String testFilmName = "";
    testFilm.setFilm_name(testFilmName);
  }

  /**
   * Test film_name cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullFilmName() {
    String testFilmName = null;
    testFilm.setFilm_name(testFilmName);
  }

  /**
   * Test film_description can be set.
   */
  @Test
  public void testFilmDescription() {
    String testFilmDesc = "Lorem Ipsum";
    testFilm.setFilm_description(testFilmDesc);
    assertThat(testFilm.getFilm_description(), is(testFilmDesc));
  }

  /**
   * Test film_description cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoFilmDescription() {
    String testFilmDesc = "";
    testFilm.setFilm_description(testFilmDesc);
  }

  /**
   * Test film_description cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullFilmDescription() {
    String testFilmDesc = null;
    testFilm.setFilm_description(testFilmDesc);
  }

  /**
   * Test film_runtime can be set.
   */
  @Test
  public void testPosFilmRuntime() {
    int testFilmRuntime = 100;
    testFilm.setFilm_runtime(testFilmRuntime);
    assertThat(testFilm.getFilm_runtime(), is(testFilmRuntime));
  }

  /**
   * Test film_runtime cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegFilmRuntime() {
    int testFilmRuntime = -100;
    testFilm.setFilm_runtime(testFilmRuntime);
  }

  /**
   * Test film_director can be set.
   */
  @Test
  public void testFilmDirector() {
    String testFilmDirector = "Spielberg";
    testFilm.setFilm_director(testFilmDirector);
    assertThat(testFilm.getFilm_director(), is(testFilmDirector));
  }

  /**
   * Test film_director cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoFilmDirector() {
    String testFilmDirector = "";
    testFilm.setFilm_director(testFilmDirector);
  }

  /**
   * Test film_director cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullFilmDirector() {
    String testFilmDirector = null;
    testFilm.setFilm_director(testFilmDirector);
  }

  /**
   * Test film_age_rating can be set.
   */
  @Test
  public void testFilmAgeRating() {
    String testFilmAgeRating = "18";
    testFilm.setFilm_age_rating(testFilmAgeRating);
    assertThat(testFilm.getFilm_age_rating(), is(testFilmAgeRating));
  }

  /**
   * Test film_age_rating cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoFilmAgeRating() {
    String testFilmAgeRating = "";
    testFilm.setFilm_age_rating(testFilmAgeRating);
  }

  /**
   * Test film_age_rating cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullFilmAgeRating() {
    String testFilmAgeRating = null;
    testFilm.setFilm_age_rating(testFilmAgeRating);
  }

  /**
   * Test Film.java toString method.
   */
  @Test
  public void testToString() {
    int testID = 64;
    String testFilmName = "Alpha";
    String testFilmDesc = "Lorem Ipsum";
    int testFilmRuntime = 100;
    String testFilmDirector = "Spielberg";
    String testFilmAgeRating = "18";

    String expectedOut = "Film [film_id=" + testID + ", film_name=" + testFilmName + ", film_description=" + testFilmDesc + ", film_runtime=" + testFilmRuntime + ", film_director=" + testFilmDirector + ", film_age_rating=" + testFilmAgeRating + "]";

    testFilm.setFilm_id(testID);
    testFilm.setFilm_name(testFilmName);
    testFilm.setFilm_description(testFilmDesc);
    testFilm.setFilm_runtime(testFilmRuntime);
    testFilm.setFilm_director(testFilmDirector);
    testFilm.setFilm_age_rating(testFilmAgeRating);

    assertThat(testFilm.toString(), is(expectedOut));
  }
}
