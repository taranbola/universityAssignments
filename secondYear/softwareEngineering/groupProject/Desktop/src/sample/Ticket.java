/**
 * Ticket.java
 */

package sample;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Class for providing all ticket information for access in other classes
 *
 * @author Mitchell Gladstone
 */
public class Ticket {
  /** ID number of the ticket. */
  private int ticket_id;
  /** ID number of the customer.  */
  private int customer_id;
  /** ID number of the screening. */
  private int screening_id;
  /** ID number of the seat. */
  private int seat_id;

	/**
	 * Default Ticket constructor.
	 */
	public Ticket() {
		super();
	}

	/**
	 * Ticket constructor.
	 * @param customer_id  ID number of customer.
	 * @param screening_id ID number of screening.
	 * @param seat_id      ID number of seat.
	 */
	public Ticket(int customer_id, int screening_id, int seat_id) {
		super();
		this.ticket_id = 0;
		this.customer_id = customer_id;
		this.screening_id = screening_id;
		this.seat_id = seat_id;
	}

	/**
	 * @return ID number of ticket.
	 */
  @JsonIgnore
	public int getTicket_id() {
		return ticket_id;
	}

  /**
   * Sets new value of this.ticket_id.
   * @param ticket_id ID number of ticket.
   */
  @JsonIgnore
	public void setTicket_id(int ticket_id) {
    if(ticket_id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
		  this.ticket_id = ticket_id;
    }
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
	 * @return ID number of screening.
	 */
	public int getScreening_id() {
		return screening_id;
	}

	/**
	 * Sets new value of this.screening_id.
	 * @param screening_id ID number of screening.
	 */
	public void setScreening_id(int screening_id) {
    if(screening_id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
		  this.screening_id = screening_id;
    }
	}

	/**
	 * @return ID number of seat.
	 */
	public int getSeat_id() {
		return seat_id;
	}

	/**
	 * Sets new value of this.seat_id.
	 * @param seat_id ID number of seat.
	 */
	public void setSeat_id(int seat_id) {
    if(seat_id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
		  this.seat_id = seat_id;
    }
	}

	/**
	 * @return String representation of Ticket for printing.
	 */
	@Override
	public String toString() {
		return "Ticket [ticket_id=" + ticket_id + ", customer_id=" + customer_id + ", screening_id=" + screening_id + ", seat_id=" + seat_id + "]";
	}
}
