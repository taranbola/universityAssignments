/**
 * ScreenTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Test class for Screen.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby
 */
public class ScreenTest {

  /** Screen test object. */
  private Screen testScreen;

  /**
   * Sets up the test fixture (called before every test case method).
   */
  @Before
  public void setUp() {
    testScreen = new Screen();
  }

  /**
   * Tears down the test fixture (called after every test case method).
   */
  @After
  public void tearDown() {
    testScreen = null;
  }

  /**
   * Test screen_id can be set.
   */
  @Test
  public void testPosScreenID() {
    int testID = 64;
    testScreen.setScreen_id(testID);
    assertThat(testScreen.getScreen_id(), is(testID));
  }

  /**
   * Test screen_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegScreenID() {
    int testID = -64;
    testScreen.setScreen_id(testID);
  }

  /**
   * Test screen_capacity can be set.
   */
  @Test
  public void testPosScreenCapacity() {
    int testScreenCapacity = 100;
    testScreen.setScreen_capacity(testScreenCapacity);
    assertThat(testScreen.getScreen_capacity(), is(testScreenCapacity));
  }

  /**
   * Test screen_capacity cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegScreenCapacity() {
    int testScreenCapacity = -100;
    testScreen.setScreen_capacity(testScreenCapacity);
  }

  /**
   * Test Screen.java toString method.
   */
  @Test
  public void testToString() {
    int testID = 64;
    int testScreenCapacity = 100;

    String expectedOut = "Screen [screen_id=" + testID + ", screen_capacity=" + testScreenCapacity + "]";

    testScreen.setScreen_id(testID);
    testScreen.setScreen_capacity(testScreenCapacity);

    assertThat(testScreen.toString(), is(expectedOut));
  }
 }
