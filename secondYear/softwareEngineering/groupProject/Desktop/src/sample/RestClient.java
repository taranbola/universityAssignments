/**
 * RestClient.java
 */

package sample;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import java.text.SimpleDateFormat;
import java.time.LocalDate ;

/**
 * Class for creating a client for the http server.
 * Also has methods that implements CinemaApi interface.
 * @author Mitchell Gladstone
 */
public class RestClient implements CinemaApi {

  /**  */
  private HttpClient client;
  /**  */
  private ObjectMapper mapper;

      /**
       * Initiates an instance of rest client when passed host info
       * and port number a connection to server is created.
       * @param host String that is host info.
       * @param port Port number on which to connect.
       */
      public RestClient(String host, int port){
        super();
        this.client = new HttpClient(host, port);
        this.mapper = new ObjectMapper();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        mapper.setDateFormat(df);
      }

      /**
      * method that when called posts a new customer to database through the server
      * not implemented as not used
      */
      public Customer createCustomer(){
        return null;
      }

      /**
      * method that when called deletes a customer from the database through the server
      * not implemented as not used
      */
      public boolean deleteCustomer(Customer customer){
        return false;
      }

      /**
      * method that when called updates  a  customer that is stored in database through the server
      * not implemented as not used
      */
      public boolean updateCustomer(Customer customer){
        return false;
      }

      /**
      * method that when called returns a list of all customers that are stored in database
      * not implemented as not used
      */
      public List<Customer> getCustomers(){
        return null;
      }


      /**
      * Film methods
      */

      /**
      * method that when called posts a new film to database through the server
      * not implemented as not used
      */
      public Film createFilm(){
        return null;
      }

      /**
      * method that when called deletes a film from the database through the server
      * not implemented as not used
      */
      public boolean deleteFilm(Film film){
        return false;
      }



      /**
      * method that when called updates  a film that is stored in database through the server
      * not implemented as not used
      */
      public boolean updateFilm(Film film){
        return false;
      }


      /**
      * method that when called gets a film record from database through server based on filmID value
      *@param id represents filmID value which is primary key for film record wanted from database
      *@return Film film - film object that hold all data on film record that was returned from database
      */
      public Film getFilm(int id) throws Exception {
        String json = this.client.get("film/"+id);
        Film film = mapper.readValue(json, Film.class);
        return film;


      }

      /**
      * method that when called returns a list of all films that are stored in database
      * @return list of film objects where each element represents a film record stored in database
      */

      public List<Film> getFilms() throws Exception{
        String json = this.client.get("film");
        JsonNode jsonNode = mapper.readTree(json).get("objects");
        String foo = jsonNode.toString();

        TypeReference<List<Film>> mapType = new TypeReference<List<Film>>() {};
        List<Film> jsonToList = mapper.readValue(foo, mapType);


        return jsonToList;
      }

      /**
      * Screen methods
      */


      /**
      * method that when called posts a new screen to database through the server
      * not implemented as not used
      */
      public Screen createScreen(){
        return null;
      }

      /**
      * method that when called deletes a screen from the database through the server
      * not implemented as not used
      */
      public boolean deleteScreen(Screen screen){
        return false;
      }

      /**
      * method that when called updates a screen that is stored in database through the server
      * not implemented as not used
      */
      public boolean updateScreen(Screen screen){
        return false;
      }

      /**
      * method that when called gets a screen record from database through server based on screenID value
      *@param id represents ScreenID value which is primary key for film record wanted from database
      *@return Screen screen - film object that hold all data on film record that was returned from database
      */
      public Screen getScreen(int id) throws Exception {
        String json = this.client.get("screen/"+id);
        Screen screen = mapper.readValue(json, Screen.class);
        return screen;
      }

      /**
      * method that when called returns a list of all screens that are stored in database
      * @return list of screen objects where each element represents a screen record stored in database
      */
      public List<Screen> getScreens() throws Exception{
        String json = this.client.get("screen");
        JsonNode jsonNode = mapper.readTree(json).get("objects");
        String foo = jsonNode.toString();

        TypeReference<List<Screen>> mapType = new TypeReference<List<Screen>>() {};
        List<Screen> jsonToList = mapper.readValue(foo, mapType);


        return jsonToList;
      }


      /**
      * Screenings methods
      */

      /**
      * method that when called posts a new screening to database through the server
      * not implemented as not used
      */
      public Screening createScreening(){
        return null;
      }

      /**
    * method that when called deletes a screening from the database through the server
    * not implemented as not used
    */

      public boolean deleteScreening(Screening screening){
        return false;
      }

      /**
    * method that when called updates a screen that is stored in database through the server
    * not implemented as not used
    */
      public boolean updateScreening(Screening screening){
        return false;
      }

      /**
    * method that when called gets a screening record from database through server based on screeningID value
    *@param id represents ScreeningID value which is primary key for film record wanted from database
    *@return Screening screening - screening object that hold all data on film record that was returned from database
    */
      public Screening getScreening(int id) throws Exception {
        String json = this.client.get("screening/"+id);
        Screening screening = mapper.readValue(json, Screening.class);
        return screening;
      }


      /**
      * method that when called returns a list of all screenings that are stored in database
      * @return list of screening objects where each element represents a screening record stored in database
      */
      public List<Screening> getScreenings() throws Exception {
        String json = this.client.get("screening");
        JsonNode jsonNode = mapper.readTree(json).get("objects");
        String foo = jsonNode.toString();

        TypeReference<List<Screening>> mapType = new TypeReference<List<Screening>>() {};
        List<Screening> jsonToList = mapper.readValue(foo, mapType);


        return jsonToList;
      }

      /**
      * method that when called returns a list of all screenings that are stored in database that date value matches that of the date specified
      *@param date - date value that we will search for screenings on
      * @return list of screen objects where each element represents a screening record stored in database
      */
      public List<Screening> getScreeningsByDate(LocalDate date) throws Exception {
        String filter =  "{%22filters%22:[{%22name%22:%22screening_date%22,%22op%22:%22eq%22,%22val%22:%22" +date.toString()+"%22}]}" ;
        String json = this.client.get("screening?q="+filter);

        JsonNode jsonNode = mapper.readTree(json).get("objects");
        String foo = jsonNode.toString();

        TypeReference<List<Screening>> mapType = new TypeReference<List<Screening>>() {};
        List<Screening> jsonToList = mapper.readValue(foo, mapType);


        return jsonToList;
      }
      /**
      * Seat methods
      */

      /**
      * method that when called posts a new seat to database through the server
      * not implemented as not used
      */
      public Seat createSeat(){
        return null;
      }

      /**
    * method that when called deletes a seat from the database through the server
    * not implemented as not used
    */

      public boolean deleteSeat(Seat seat){
        return false;
      }

      /**
    * method that when called updates a seat that is stored in database through the server
    * not implemented as not used
    */
      public boolean updateSeat(Seat seat){
        return false;
      }

      /**
          * method that when called gets a seat record from database through server based on seatID value
          *@param id represents SeatID value which is primary key for film record wanted from database
          *@return Seat seat - seat object that hold all data on film record that was returned from database
          */

      public Seat getSeat(int id) throws Exception{
        String json = this.client.get("seat/"+id);
        Seat seat = mapper.readValue(json, Seat.class);
        return seat;
      }

      /**
    * method that when called returns a list of all seats that are stored in database
    * @return list of seat objects where each element represents a screen record stored in database
    */

      public List<Seat> getSeats() throws Exception{
        String json = this.client.get("seat");
        JsonNode jsonNode = mapper.readTree(json).get("objects");
        String foo = jsonNode.toString();

        TypeReference<List<Seat>> mapType = new TypeReference<List<Seat>>() {};
        List<Seat> jsonToList = mapper.readValue(foo, mapType);


        return jsonToList;
      }

      public Ticket createTicket(Customer customer, Screening screening, Seat seat) throws Exception{
        Ticket t = new Ticket(customer.getCustomer_id(), screening.getScreening_id(),seat.getSeat_id());
        String json = mapper.writeValueAsString(t);
        this.client.post("ticket", json);

        return null;
      }

      /**
    * method that when called deletes a ticket from the database through the server
    * not implemented as not used
    */
      public boolean deleteTicket(Ticket ticket){
        return false;
      }

      /**
    * method that when called updates a ticket that is stored in database through the server
    * not implemented as not used
    */
      public boolean updateTicket(Ticket ticket){
        return false;
      }

      /**
      * method that when called gets a ticket record from database through server based on ticketID value
      *@param id represents ticketID value which is primary key for ticket record wanted from database
      *@return Ticket ticket - ticket object that hold all data on film record that was returned from database
      */
      public Ticket getTicket(int id) throws Exception{
        String json = this.client.get("ticket/"+id);
        Ticket ticket = mapper.readValue(json, Ticket.class);
        return ticket;
      }

      /**
    * method that when called returns a list of all tickets that are stored in database
    * @return list of ticket objects where each element represents a ticket record stored in database
    */
      public List<Ticket> getTickets() throws Exception{
        String json = this.client.get("ticket");
        JsonNode jsonNode = mapper.readTree(json).get("objects");
        String foo = jsonNode.toString();

        TypeReference<List<Ticket>> mapType = new TypeReference<List<Ticket>>() {};
        List<Ticket> jsonToList = mapper.readValue(foo, mapType);


        return jsonToList;
      }


      /**
      * method that when called returns a list of all tickets that are stored in database that screemingID value matches that of the screening specified
      *@param screeningID - screeningID value that we will search for tickets on
      * @return list of ticket objects where each element represents a ticket record stored in database
      */
      public List<Ticket> getTicketsByScreening(int screeningID) throws Exception {
        String filter =  "{%22filters%22:[{%22name%22:%22screening_id%22,%22op%22:%22eq%22,%22val%22:%22" +screeningID+"%22}]}" ;
        String json = this.client.get("ticket?q="+filter);

        JsonNode jsonNode = mapper.readTree(json).get("objects");
        String foo = jsonNode.toString();

        TypeReference<List<Ticket>> mapType = new TypeReference<List<Ticket>>() {};
        List<Ticket> jsonToList = mapper.readValue(foo, mapType);


        return jsonToList;
      }


}
