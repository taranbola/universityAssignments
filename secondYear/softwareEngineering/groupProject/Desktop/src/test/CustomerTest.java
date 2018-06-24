/**
 * CustomerTest.java
 */

package sample;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.time.LocalDate;
import java.time.Month;

/**
 * Test class for Customer.java. Used to ensure class is working as
 * intended and appropriate validation has been used.
 *
 * @author Ben Ashby and Qasim Hussain
 */
public class CustomerTest {

  /** Customer test object. */
  private Customer testCustomer;

  /**
   * Sets up the test fixture (called before every test case method).
   */
  @Before
  public void setUp() {
    testCustomer = new Customer();
  }

  /**
   * Tears down the test fixture (called after every test case method).
   */
  @After
  public void tearDown() {
    testCustomer = null;
  }

  /**
   * Test customer_id can be set.
   */
  @Test
  public void testPosCustomerID() {
    int testID = 64;
    testCustomer.setCustomer_id(testID);
    assertThat(testCustomer.getCustomer_id(), is(testID));
  }

  /**
   * Test customer_id cannot be set to a negative integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegCustomerID() {
    int testID = -64;
    testCustomer.setCustomer_id(testID);
  }

  /**
   * Test customer_f_name can be set.
   */
  @Test
  public void testCustomerFName() {
    String testFName = "Steve";
    testCustomer.setCustomer_f_name(testFName);
    assertThat(testCustomer.getCustomer_f_name(), is(testFName));
  }

  /**
   * Test customer_f_name cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoCustomerFName() {
    String testFName = "";
    testCustomer.setCustomer_f_name(testFName);
  }

  /**
   * Test customer_f_name cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullCustomerFName() {
    String testFName = null;
    testCustomer.setCustomer_f_name(testFName);
  }

  /**
   * Test customer_s_name can be set.
   */
  @Test
  public void testCustomerSName() {
    String testSName = "Smith";
    testCustomer.setCustomer_s_name(testSName);
    assertThat(testCustomer.getCustomer_s_name(), is(testSName));
  }

  /**
   * Test customer_s_name cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoCustomerSName() {
    String testSName = "";
    testCustomer.setCustomer_s_name(testSName);
  }

  /**
   * Test customer_s_name cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullCustomerSName() {
    String testSName = null;
    testCustomer.setCustomer_s_name(testSName);
  }

  /**
   * Test customer_dob can be set.
   */
  @Test
  public void testCustomerDob() {
    LocalDate testDob = LocalDate.of(2018, Month.JANUARY, 1);
    testCustomer.setCustomer_dob(testDob);
    assertThat(testCustomer.getCustomer_dob(), is(testDob));
  }

  /**
   * Test customer_dob cannot be in the future.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFutureCustomerDob() {
    LocalDate testDob = LocalDate.of(3018, Month.JANUARY, 1);
    testCustomer.setCustomer_dob(testDob);
  }

  /**
   * Test customer_mobile can be set.
   */
  @Test
  public void testCustomerMobile() {
    String testMobile = "+447123456789";
    testCustomer.setCustomer_mobile(testMobile);
    assertThat(testCustomer.getCustomer_mobile(), is(testMobile));
  }

  /**
   * Test customer_mobile cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoCustomerMobile() {
    String testMobile = "";
    testCustomer.setCustomer_mobile(testMobile);
  }

  /**
   * Test customer_mobile cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullCustomerMobile() {
    String testMobile = null;
    testCustomer.setCustomer_mobile(testMobile);
  }

  /**
   * Test customer_address can be set.
   */
  @Test
  public void testCustomerAddress() {
    String testAddress = "10 Downing Street";
    testCustomer.setCustomer_address(testAddress);
    assertThat(testCustomer.getCustomer_address(), is(testAddress));
  }

  /**
   * Test customer_address cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoCustomerAddress() {
    String testAddress = "";
    testCustomer.setCustomer_address(testAddress);
  }

  /**
   * Test customer_address cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullCustomerAddress() {
    String testAddress = null;
    testCustomer.setCustomer_address(testAddress);
  }

  /**
   * Test customer_postcode can be set.
   */
  @Test
  public void testCustomerPostcodeSet() {
    String testPostcode = "LS11AA";
    testCustomer.setCustomer_postcode(testPostcode);
    assertThat(testCustomer.getCustomer_postcode(), is(testPostcode));
  }

  /**
   * Test customer_postcode cannot be an empty string.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNoCustomerPostcode() {
    String testPostcode = "";
    testCustomer.setCustomer_postcode(testPostcode);
  }

  /**
   * Test customer_postcode cannot be null.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNullCustomerPostcode() {
    String testPostcode = null;
    testCustomer.setCustomer_postcode(testPostcode);
  }

  /**
   * Test Customer.java toString method.
   */
  @Test
  public void testToString() {
    int testID = 64;
    String testFName = "Steve";
    String testSName = "Smith";
    LocalDate testDob = LocalDate.of(2018, Month.JANUARY, 1);
    String testMobile = "+447123456789";
    String testAddress = "10 Downing Street";
    String testPostcode = "LS11AA";

    String expectedOut = "Customer [customer_id=" + testID + ", customer_f_name=" + testFName + ", customer_s_name=" + testSName + ", customer_dob=" + testDob + ", customer_mobile=" + testMobile + ", customer_address=" + testAddress + ", customer_postcode=" + testPostcode + "]";

    testCustomer.setCustomer_id(testID);
    testCustomer.setCustomer_f_name(testFName);
    testCustomer.setCustomer_s_name(testSName);
    testCustomer.setCustomer_dob(testDob);
    testCustomer.setCustomer_mobile(testMobile);
    testCustomer.setCustomer_address(testAddress);
    testCustomer.setCustomer_postcode(testPostcode);

    assertThat(testCustomer.toString(), is(expectedOut));
  }
}
