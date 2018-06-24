/**
 * GenericWrapperTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * Test class for GenericWrapper.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby and Qasim Hussain
 */
public class GenericWrapperTest {

  /** GenericWrapper test object. */
  private GenericWrapper testGenericWrapper;

  /**
   * Sets up the test fixture (Called before every test case method).
   */
  @Before
  public void setUp() {
    testGenericWrapper = new GenericWrapper();
  }

  /**
   * Tears down the test fixture (Called after every test case method).
   */
  @After
  public void tearDown() {
    testGenericWrapper = null;
  }

  /**
   * Test Objects can be set.
   */
  @Test
  public void testObjects() {
    List<String> testObjects = new ArrayList<String>();
    testObjects.add("obj1");
    testObjects.add("obj2");
    testObjects.add("obj3");

    testGenericWrapper.setObjects(testObjects);

    assertThat(testGenericWrapper.getObjects(), is(testObjects));
    assertThat(testGenericWrapper.getObjects().size(), is(3));
    assertThat(testGenericWrapper.getObjects().get(1), is("obj2"));
  }

  /**
   * Test Num_results can be set.
   */
  @Test
  public void testPosNumResults() {
    int testNumResults = 99;
    testGenericWrapper.setNum_results(testNumResults);
    assertThat(testGenericWrapper.getNum_results(), is(testNumResults));
  }

  /**
   * Test Num_results cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegNumResults() {
    int testNumResults = -99;
    testGenericWrapper.setNum_results(testNumResults);
  }

  /**
   * Test Total_pages can be set.
   */
  @Test
  public void testPosTotalPages() {
    int testTotalPages = 100;
    testGenericWrapper.setTotal_pages(testTotalPages);
    assertThat(testGenericWrapper.getTotal_pages(), is(testTotalPages));
  }

  /**
   * Test Total_pages cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegTotalPages() {
    int testTotalPages = -100;
    testGenericWrapper.setTotal_pages(testTotalPages);
  }

  /**
   * Test Page can be set.
   */
  @Test
  public void testPosPage() {
    int testPage = 5;
    testGenericWrapper.setPage(testPage);
    assertThat(testGenericWrapper.getPage(), is(testPage));
  }

  /**
   * Test Page cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegPage() {
    int testPage = -5;
    testGenericWrapper.setPage(testPage);
  }
}
