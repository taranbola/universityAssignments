/**
 * Customer.java
 */

package sample;
import java.time.LocalDate;

/**
 * Class for Customer, populates customer information and provides methods
 * to retrieve and set customer info.
 *
 * @author Mitchell Gladstone
 */
public class Customer {
  /** ID number of the customer. */
  private int customer_id;
  /** First name of the customer. */
  private String customer_f_name;
  /** Surname of the customer. */
  private String  customer_s_name;
  /** DOB of the customer. */
  private LocalDate customer_dob;
  /** Mobile number of customer (string as '+44' may be necessary). */
  private String customer_mobile;
  /** Address of the customer. */
  private String customer_address;
  /** Postcode of the customer. */
  private String customer_postcode;

	/**
	 * Default Customer constructor.
	 */
	public Customer() {
		super();
	}

	/**
	 * Customer constructor.
	 * @param customer_id       ID number of customer.
	 * @param customer_f_name   First name of customer.
	 * @param customer_s_name   Surname of customer.
	 * @param customer_dob      DOB of customer.
	 * @param customer_mobile   Mobile number of customer.
	 * @param customer_address  Address of customer.
	 * @param customer_postcode Postcode of customer.
	 */
	public Customer(int customer_id, String customer_f_name, String customer_s_name, LocalDate customer_dob, String customer_mobile, String customer_address, String customer_postcode) {
		super();
		this.customer_id = customer_id;
		this.customer_f_name = customer_f_name;
		this.customer_s_name = customer_s_name;
		this.customer_dob = customer_dob;
		this.customer_mobile = customer_mobile;
		this.customer_address = customer_address;
		this.customer_postcode = customer_postcode;
	}

  /**
   * Till Customer constructor.
   * @param customer_id ID number of customer.
   */
  public Customer(int customer_id) {
    super();
    this.customer_id = 5;
    this.customer_f_name = "till";
    this.customer_s_name = "till";
    LocalDate inputDate = LocalDate.of(2017,10,10);
    this.customer_dob = inputDate;
    this.customer_mobile = "till";
    this.customer_address = "till";
    this.customer_postcode = "till";
  }

	/**
	 * @return ID number of customer.
	 */
	public int getCustomer_id() {
		return customer_id;
	}

  /**
   * Sets new value of this.customer_id.
   * @param customer_id ID number of customer.
   */
	public void setCustomer_id(int customer_id) {
    if(customer_id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
		  this.customer_id = customer_id;
    }
	}

  /**
   * @return First name of customer.
   */
	public String getCustomer_f_name() {
		return customer_f_name;
	}

  /**
   * Sets new value of this.customer_f_name.
   * @param customer_f_name First name of customer.
   */
	public void setCustomer_f_name(String customer_f_name) {
    if(customer_f_name == null || customer_f_name == "") {
      throw new IllegalArgumentException("Customer must have a first name.");
    } else {
		  this.customer_f_name = customer_f_name;
    }
	}

	/**
	 * @return Surname of customer.
	 */
	public String getCustomer_s_name() {
		return customer_s_name;
	}

  /**
   * Sets new value of this.customer_s_name.
   * @param customer_s_name Surname of customer.
   */
	public void setCustomer_s_name(String customer_s_name) {
    if(customer_s_name == null || customer_s_name == "") {
      throw new IllegalArgumentException("Customer must have a surname.");
    } else {
		  this.customer_s_name = customer_s_name;
    }
	}

	/**
	 * @return DOB of customer.
	 */
	public LocalDate getCustomer_dob() {
		return customer_dob;
	}

	/**
	 * Sets new value of this.customer_dob.
	 * @param customer_dob DOB of customer.
	 */
	public void setCustomer_dob(LocalDate customer_dob) {
    LocalDate current = LocalDate.now();
    if(current.isBefore(customer_dob)) {
      throw new IllegalArgumentException("Date must be in the past.");
    } else {
		  this.customer_dob = customer_dob;
    }
	}

	/**
	 * @return Mobile number of customer.
	 */
	public String getCustomer_mobile() {
		return customer_mobile;
	}

	/**
	 * Sets new value of this.customer_mobile.
	 * @param customer_mobile Mobile number of customer.
	 */
	public void setCustomer_mobile(String customer_mobile) {
    if(customer_mobile == null || customer_mobile == "") {
      throw new IllegalArgumentException("Customer must have a mobile number.");
    } else {
		  this.customer_mobile = customer_mobile;
    }
	}

	/**
	 * @return Address of customer.
	 */
	public String getCustomer_address() {
		return customer_address;
	}

	/**
	 * Sets new value of this.customer_address.
	 * @param customer_address Address of customer.
	 */
	public void setCustomer_address(String customer_address) {
    if(customer_address == null || customer_address == "") {
      throw new IllegalArgumentException("Customer must have an address.");
    } else {
		  this.customer_address = customer_address;
    }
	}

  /**
   * @return Postcode of customer.
   */
	public String getCustomer_postcode() {
		return customer_postcode;
	}

	/**
	 * Sets new value of this.customer_postcode.
	 * @param customer_postcode Postcode of customer.
	 */
	public void setCustomer_postcode(String customer_postcode) {
    if(customer_postcode == null || customer_postcode == "") {
      throw new IllegalArgumentException("Customer must have a postcode.");
    } else {
		  this.customer_postcode = customer_postcode;
    }
	}

	/**
	 * @return String representation of Customer for printing.
	 */
	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", customer_f_name=" + customer_f_name + ", customer_s_name=" + customer_s_name + ", customer_dob=" + customer_dob + ", customer_mobile=" + customer_mobile + ", customer_address=" + customer_address + ", customer_postcode=" + customer_postcode + "]";
	}
}
