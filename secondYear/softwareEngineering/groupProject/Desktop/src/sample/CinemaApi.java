/**
* CinemaApi.java
*/

package sample;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;

/**
* A file that creates an intrerface.
* Declarees all possible api functions.
*
* @author Mitchell Gladstone
*/
public interface CinemaApi {

  // Customer Methods

  public Customer createCustomer();

  public boolean deleteCustomer(Customer customer);

  public boolean updateCustomer(Customer customer);

  public List<Customer> getCustomers();

  // Film Methods

  public Film createFilm();

  public boolean deleteFilm(Film film);

  public boolean updateFilm(Film film);

  public Film getFilm(int id) throws Exception ;

  public List<Film> getFilms() throws Exception;

  // Screen Methods

  public Screen createScreen();

  public boolean deleteScreen(Screen screen);


  public boolean updateScreen(Screen screen);

  public Screen getScreen(int id) throws Exception ;


  public List<Screen> getScreens() throws Exception;



  /**
  * Screenings methods
  */
  public Screening createScreening();

  public boolean deleteScreening(Screening screening);


  public boolean updateScreening(Screening screening);

  public Screening getScreening(int id) throws Exception ;

  public List<Screening> getScreenings() throws Exception;

  public List<Screening> getScreeningsByDate(LocalDate date) throws Exception;



  /**
  * Seat methods
  */
  public Seat createSeat();


  public boolean deleteSeat(Seat seat);

  public boolean updateSeat(Seat seat);

  public Seat getSeat(int id) throws Exception;

  public List<Seat> getSeats() throws Exception;

  // Ticket methods

  public Ticket createTicket(Customer customer, Screening screening, Seat seat)  throws Exception;

  public boolean deleteTicket(Ticket ticket);

  public boolean updateTicket(Ticket ticket);

  public Ticket getTicket(int id) throws Exception;


  public List<Ticket> getTickets() throws Exception;
}
