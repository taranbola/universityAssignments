/**
* SeatingScreenController.java
*/

package sample;

import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import java.time.LocalDate ;

/**
* Class for selecting seats within the Cinema
*
* @author Dan Aves
*/
public class SeatingScreenController {

  /** Total price of tickets. */
  double totalNew;

  @FXML
  public Button seat1, seat2, seat3, seat4, seat5, seat6 ,seat7, seat8, seat9, seat10;
  public Button seat11, seat12, seat13, seat14, seat15, seat16, seat17, seat18, seat19, seat20;
  public Button seat21, seat22, seat23, seat24, seat25, seat26, seat27, seat28, seat29, seat30;
  public Button seat31, seat32, seat33, seat34, seat35, seat36, seat37, seat38, seat39, seat40;
  public Button seat41, seat42, seat43, seat44, seat45, seat46, seat47, seat48, seat49, seat50;
  public Button seat51, seat52, seat53, seat54, seat55, seat56, seat57, seat58, seat59, seat60;
  public Button seat61, seat62, seat63, seat64, seat65, seat66, seat67, seat68, seat69, seat70;
  public Button seat71, seat72, seat73, seat74, seat75, seat76, seat77, seat78, seat79, seat80;
  public Button seat81, seat82, seat83, seat84, seat85, seat86, seat87, seat88, seat89, seat90;
  public Button seat91, seat92, seat93, seat94, seat95, seat96 ,seat97, seat98, seat99, seat100;

  /** ID number of screen. */
  int screenID;
  /** Arrary of seats. */
  int inputScreeningID;
  List seats = new ArrayList();
  /** Date of the screening. */
  LocalDate inputDate;
  /** Time of the screening. */
  String inputTime;
  /** ID number of the film. */
  int inputFilmID;
  /** Name of the film. */
  String inputFilmName;
  /** ID number of the screening. */
  int screeningID;
  /** Number of screen which film shows on. */
  String inputScreenNumber;
  /** Total amount of tickets selected. */
  int ticketQuantity;
  /** Tracks amount of tickets selected */
  int seatSelectionCount;


  /**
  * Sets new value of totalNew.
  * @param grandTotal2 Total price of tickets.
  */
  public void setTotal(double grandTotal2){
    totalNew = grandTotal2;
  }

  /**
  * Sets the local variable inputDate to the passed
  * date from BookingScreenController.
  * @param date Date of screening.
  */
  public void setDate(LocalDate date){
    inputDate = date;
  }

  /**
  * Sets the local variable screeningID to the passed
  * screening id from TimeTableController.
  * @param id ID number of screening.
  */
  public void setScreeningID(int id){
    screeningID = id;
  }

  /**
  * Sets passed filmID used for back function.
  * @param id ID number of film.
  */
  public void setFilmID(int id){
    inputFilmID = id;
  }

  /**
  * Sets passed filmName used for back function
  * @param name Name of film.
  */
  public void setFilmName(String name){
    inputFilmName = name;
  }

  /**
  * Sets the local variable inputTime to the passed
  * time from BookingScreenController.
  * @param time Time of screening.
  */
  public void setTime(String time){
    inputTime = time;
  }

  /**
  * Sets passed Screen number for the film selected
  * @param screen
  */
  public void setScreenNumber(String screen){
    inputScreenNumber = screen;
  }

  /**
  * Sets ticket quantity based on selection on previous screen
  * Used for validation.
  * @param amount
  */
  public void setTicketQuantity(int amount){
    ticketQuantity = amount;

  }
  /**
  * Takes user to the payment screen.
  * @param  event       User clicks.
  * @throws IOException
  */
  public void payClicked(ActionEvent event)throws IOException{
    if(seatSelectionCount != ticketQuantity)
    {
      AlertBox.display("Error", "Not enough seats selected!  ", "ticket");
    }
    else if(ticketQuantity == 0){
      AlertBox.display("Error", "You need to select a ticket  ", "ticket");
    }
    else{ //If validation passes proceed to next screen

      FXMLLoader Loader = new FXMLLoader();
      Loader.setLocation(getClass().getResource("resources/PaymentScreen.fxml"));
      try{
        Loader.load();
      }catch (IOException ex){
        Logger.getLogger(SeatingScreenController.class.getName());
      }

      PaymentScreenController display = Loader.getController();
      display.setTotal(totalNew);
      display.setSeats(seats);
      display.setScreenID(inputScreeningID);
      display.setTime(inputTime); //Time of film
      display.setDate(inputDate); //Date of film
      display.setFilmName(inputFilmName); //FilmName
      display.setScreenNumber(inputScreenNumber);

      Parent p = Loader.getRoot();
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(new Scene(p));
      window.show();
    }
  }

  /**
  * Run when screen is open to display what screens are taken based on ticket data
  * @param  screeningID Used to find tickets
  * @throws Exception
  */
  public void populateSeats(int screeningID) throws Exception{
    RestClient client = new RestClient("localhost", 5000);
    inputScreeningID = screeningID;
    List tickets = new ArrayList();
    List<Ticket> ticketsSold = new ArrayList<Ticket>();
    ticketsSold = client.getTicketsByScreening(inputScreeningID) ;
    //Tickets sold represent the tickets for a specific film
    if (ticketsSold.size()== 0 ){
      //Do nothing, prevents errors.
    }
    else{ //Loop through tickets and populate screen
      for(Ticket t : ticketsSold){
        int seat = t.getSeat_id();
        takenSeats(seat);
      }
    }
  }

  /**
  * Allows a user to select a seat. Once selected the seat text is updated,
  * error handling is carried out to check if too many seats are selected
  * @param  event     user click
  * @throws Exception
  */
  public void seatSelection(ActionEvent event) throws Exception {
    seatSelectionCount++;
    if (seatSelectionCount > ticketQuantity){
      AlertBox.display("Error", "Too many seats selected!", "seat");
      seatSelectionCount--;
    }
    else{
      //Pass ticket total to here and define array based on the amount of seats to be selected based on tickets
      String seatText = (((Button)event.getSource()).getText());

      if(seatText.equals("Taken")){
        AlertBox.display("Error", "Seat is already taken, choose another!", "seat");
        seatSelectionCount--;
      }
      else{
        seats.add(seatText);
        ((Button)event.getSource()).setText("X");
      }
    }
  }

  /**
  * Takes user back to previous page
  * @param  event user click
  * @throws Exception
  */
  public void backButtonClicked(ActionEvent event) throws Exception {
    FXMLLoader Loader = new FXMLLoader();
    Loader.setLocation(getClass().getResource("resources/bookingScreen.fxml"));
    try{
      Loader.load();
    }catch (IOException ex){
      Logger.getLogger(BookingScreenController.class.getName());
    }

    BookingScreenController display = Loader.getController();
    //Get information from clicked button
    String time = (((Button)event.getSource()).getText());
    String buttonID = (((Button)event.getSource()).getId());

    display.setDate(inputDate); //pass date to next controller
    display.setTime(inputTime); //pass time
    display.setScreeningID(screeningID);
    display.setFilmID(inputFilmID);
    display.setFilmName(inputFilmName);
    display.setScreenNumber(inputScreenNumber);

    Parent p = Loader.getRoot();
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(new Scene(p));
    window.show();
  }

  /**
  * Reloads the page and clears the selection of any seats
  * @param  event user click
  * @throws Exception
  */
  public void refreshScreen(ActionEvent event) throws Exception{
    FXMLLoader Loader = new FXMLLoader();
    Loader.setLocation(getClass().getResource("resources/seatingScreen.fxml"));
    try{
      Loader.load();
    }catch (IOException ex){
      Logger.getLogger(SeatingScreenController.class.getName());
    }
    //Variables are passed back to class to refresh
    SeatingScreenController display = Loader.getController();
    display.setTotal(totalNew);
    display.populateSeats(screeningID);
    display.setTime(inputTime);
    display.setDate(inputDate);
    display.setScreeningID(screeningID);
    display.setFilmID(inputFilmID);
    display.setFilmName(inputFilmName);
    display.setScreenNumber(inputScreenNumber);
    display.setTicketQuantity(ticketQuantity);


    Parent p = Loader.getRoot();
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(new Scene(p));
    window.show();
  }

  /**
  * Update seats which are taken with appropraite text and colour
  * @param seat seat ID
  */
  public void takenSeats(int seat){
    switch (seat) {
      case 1: seat1.setText("Taken");
      seat1.setStyle("-fx-background-color: #4286f4");
      break;
      case 2: seat2.setText("Taken");
      seat2.setStyle("-fx-background-color: #4286f4");
      break;
      case 3: seat3.setText("Taken");
      seat3.setStyle("-fx-background-color: #4286f4");
      break;
      case 4: seat4.setText("Taken");
      seat4.setStyle("-fx-background-color: #4286f4");
      break;
      case 5: seat5.setText("Taken");
      seat5.setStyle("-fx-background-color: #4286f4");
      break;
      case 6: seat6.setText("Taken");
      seat6.setStyle("-fx-background-color: #4286f4");
      break;
      case 7: seat7.setText("Taken");
      seat7.setStyle("-fx-background-color: #4286f4");
      break;
      case 8: seat8.setText("Taken");
      seat8.setStyle("-fx-background-color: #4286f4");
      break;
      case 9: seat9.setText("Taken");
      seat9.setStyle("-fx-background-color: #4286f4");
      break;
      case 10: seat10.setText("Taken");
      seat10.setStyle("-fx-background-color: #4286f4");
      break;
      case 11: seat11.setText("Taken");
      seat11.setStyle("-fx-background-color: #4286f4");
      break;
      case 12: seat12.setText("Taken");
      seat12.setStyle("-fx-background-color: #4286f4");
      break;
      case 13: seat13.setText("Taken");
      seat13.setStyle("-fx-background-color: #4286f4");
      break;
      case 14: seat14.setText("Taken");
      seat14.setStyle("-fx-background-color: #4286f4");
      break;
      case 15: seat15.setText("Taken");
      seat15.setStyle("-fx-background-color: #4286f4");
      break;
      case 16: seat16.setText("Taken");
      seat16.setStyle("-fx-background-color: #4286f4");
      break;
      case 17: seat17.setText("Taken");
      seat17.setStyle("-fx-background-color: #4286f4");
      break;
      case 18: seat18.setText("Taken");
      seat18.setStyle("-fx-background-color: #4286f4");
      break;
      case 19: seat19.setText("Taken");
      seat19.setStyle("-fx-background-color: #4286f4");
      break;
      case 20: seat20.setText("Taken");
      seat20.setStyle("-fx-background-color: #4286f4");
      break;
      case 21: seat21.setText("Taken");
      seat21.setStyle("-fx-background-color: #4286f4");
      break;
      case 22: seat22.setText("Taken");
      seat22.setStyle("-fx-background-color: #4286f4");
      break;
      case 23: seat23.setText("Taken");
      seat23.setStyle("-fx-background-color: #4286f4");
      break;
      case 24: seat24.setText("Taken");
      seat24.setStyle("-fx-background-color: #4286f4");
      break;
      case 25: seat25.setText("Taken");
      seat25.setStyle("-fx-background-color: #4286f4");
      break;
      case 26: seat26.setText("Taken");
      seat26.setStyle("-fx-background-color: #4286f4");
      break;
      case 27: seat27.setText("Taken");
      seat27.setStyle("-fx-background-color: #4286f4");
      break;
      case 28: seat28.setText("Taken");
      seat28.setStyle("-fx-background-color: #4286f4");
      break;
      case 29: seat29.setText("Taken");
      seat29.setStyle("-fx-background-color: #4286f4");
      break;
      case 30: seat30.setText("Taken");
      seat30.setStyle("-fx-background-color: #4286f4");
      break;
      case 31: seat31.setText("Taken");
      seat31.setStyle("-fx-background-color: #4286f4");
      break;
      case 32: seat32.setText("Taken");
      seat32.setStyle("-fx-background-color: #4286f4");
      break;
      case 33: seat33.setText("Taken");
      seat33.setStyle("-fx-background-color: #4286f4");
      break;
      case 34: seat34.setText("Taken");
      seat34.setStyle("-fx-background-color: #4286f4");
      break;
      case 35: seat35.setText("Taken");
      seat35.setStyle("-fx-background-color: #4286f4");
      break;
      case 36: seat36.setText("Taken");
      seat36.setStyle("-fx-background-color: #4286f4");
      break;
      case 37: seat37.setText("Taken");
      seat37.setStyle("-fx-background-color: #4286f4");
      break;
      case 38: seat38.setText("Taken");
      seat38.setStyle("-fx-background-color: #4286f4");
      break;
      case 39: seat39.setText("Taken");
      seat39.setStyle("-fx-background-color: #4286f4");
      break;
      case 40: seat40.setText("Taken");
      seat40.setStyle("-fx-background-color: #4286f4");
      break;
      case 41: seat41.setText("Taken");
      seat41.setStyle("-fx-background-color: #4286f4");
      break;
      case 42: seat42.setText("Taken");
      seat42.setStyle("-fx-background-color: #4286f4");
      break;
      case 43: seat43.setText("Taken");
      seat43.setStyle("-fx-background-color: #4286f4");
      break;
      case 44: seat44.setText("Taken");
      seat44.setStyle("-fx-background-color: #4286f4");
      break;
      case 45: seat45.setText("Taken");
      seat45.setStyle("-fx-background-color: #4286f4");
      break;
      case 46: seat46.setText("Taken");
      seat46.setStyle("-fx-background-color: #4286f4");
      break;
      case 47: seat47.setText("Taken");
      seat47.setStyle("-fx-background-color: #4286f4");
      break;
      case 48: seat48.setText("Taken");
      seat48.setStyle("-fx-background-color: #4286f4");
      break;
      case 49: seat49.setText("Taken");
      seat49.setStyle("-fx-background-color: #4286f4");
      break;
      case 50: seat50.setText("Taken");
      seat50.setStyle("-fx-background-color: #4286f4");
      break;
      case 51: seat51.setText("Taken");
      seat51.setStyle("-fx-background-color: #4286f4");
      break;
      case 52: seat52.setText("Taken");
      seat52.setStyle("-fx-background-color: #4286f4");
      break;
      case 53: seat53.setText("Taken");
      seat53.setStyle("-fx-background-color: #4286f4");
      break;
      case 54: seat54.setText("Taken");
      seat54.setStyle("-fx-background-color: #4286f4");
      break;
      case 55: seat55.setText("Taken");
      seat55.setStyle("-fx-background-color: #4286f4");
      break;
      case 56: seat56.setText("Taken");
      seat56.setStyle("-fx-background-color: #4286f4");
      break;
      case 57: seat57.setText("Taken");
      seat57.setStyle("-fx-background-color: #4286f4");
      break;
      case 58: seat58.setText("Taken");
      seat58.setStyle("-fx-background-color: #4286f4");
      break;
      case 59: seat59.setText("Taken");
      seat59.setStyle("-fx-background-color: #4286f4");
      break;
      case 60: seat60.setText("Taken");
      seat60.setStyle("-fx-background-color: #4286f4");
      break;
      case 61: seat61.setText("Taken");
      seat61.setStyle("-fx-background-color: #4286f4");
      break;
      case 62: seat62.setText("Taken");
      seat62.setStyle("-fx-background-color: #4286f4");
      break;
      case 63: seat63.setText("Taken");
      seat63.setStyle("-fx-background-color: #4286f4");
      break;
      case 64: seat64.setText("Taken");
      seat64.setStyle("-fx-background-color: #4286f4");
      break;
      case 65: seat65.setText("Taken");
      seat65.setStyle("-fx-background-color: #4286f4");
      break;
      case 66: seat66.setText("Taken");
      seat66.setStyle("-fx-background-color: #4286f4");
      break;
      case 67: seat67.setText("Taken");
      seat67.setStyle("-fx-background-color: #4286f4");
      break;
      case 68: seat68.setText("Taken");
      seat68.setStyle("-fx-background-color: #4286f4");
      break;
      case 69: seat69.setText("Taken");
      seat69.setStyle("-fx-background-color: #4286f4");
      break;
      case 70: seat70.setText("Taken");
      seat70.setStyle("-fx-background-color: #4286f4");
      break;
      case 71: seat71.setText("Taken");
      seat71.setStyle("-fx-background-color: #4286f4");
      break;
      case 72: seat72.setText("Taken");
      seat72.setStyle("-fx-background-color: #4286f4");
      break;
      case 73: seat73.setText("Taken");
      seat73.setStyle("-fx-background-color: #4286f4");
      break;
      case 74: seat74.setText("Taken");
      seat74.setStyle("-fx-background-color: #4286f4");
      break;
      case 75: seat75.setText("Taken");
      seat75.setStyle("-fx-background-color: #4286f4");
      break;
      case 76: seat76.setText("Taken");
      seat76.setStyle("-fx-background-color: #4286f4");
      break;
      case 77: seat77.setText("Taken");
      seat77.setStyle("-fx-background-color: #4286f4");
      break;
      case 78: seat78.setText("Taken");
      seat78.setStyle("-fx-background-color: #4286f4");
      break;
      case 79: seat79.setText("Taken");
      seat79.setStyle("-fx-background-color: #4286f4");
      break;
      case 80: seat80.setText("Taken");
      seat80.setStyle("-fx-background-color: #4286f4");
      break;
      case 81: seat81.setText("Taken");
      seat81.setStyle("-fx-background-color: #4286f4");
      break;
      case 82: seat82.setText("Taken");
      seat82.setStyle("-fx-background-color: #4286f4");
      break;
      case 83: seat83.setText("Taken");
      seat83.setStyle("-fx-background-color: #4286f4");
      break;
      case 84: seat84.setText("Taken");
      seat84.setStyle("-fx-background-color: #4286f4");
      break;
      case 85: seat85.setText("Taken");
      seat85.setStyle("-fx-background-color: #4286f4");
      break;
      case 86: seat86.setText("Taken");
      seat86.setStyle("-fx-background-color: #4286f4");
      break;
      case 87: seat87.setText("Taken");
      seat87.setStyle("-fx-background-color: #4286f4");
      break;
      case 88: seat88.setText("Taken");
      seat88.setStyle("-fx-background-color: #4286f4");
      break;
      case 89: seat89.setText("Taken");
      seat89.setStyle("-fx-background-color: #4286f4");
      break;
      case 90: seat90.setText("Taken");
      seat90.setStyle("-fx-background-color: #4286f4");
      break;
      case 91: seat91.setText("Taken");
      seat91.setStyle("-fx-background-color: #4286f4");
      break;
      case 92: seat92.setText("Taken");
      seat92.setStyle("-fx-background-color: #4286f4");
      break;
      case 93: seat93.setText("Taken");
      seat93.setStyle("-fx-background-color: #4286f4");
      break;
      case 94: seat94.setText("Taken");
      seat94.setStyle("-fx-background-color: #4286f4");
      break;
      case 95: seat95.setText("Taken");
      seat95.setStyle("-fx-background-color: #4286f4");
      break;
      case 96: seat96.setText("Taken");
      seat96.setStyle("-fx-background-color: #4286f4");
      break;
      case 97: seat97.setText("Taken");
      seat97.setStyle("-fx-background-color: #4286f4");
      break;
      case 98: seat98.setText("Taken");
      seat98.setStyle("-fx-background-color: #4286f4");
      break;
      case 99: seat99.setText("Taken");
      seat99.setStyle("-fx-background-color: #4286f4");
      break;
      case 100: seat100.setText("Taken");
      seat100.setStyle("-fx-background-color: #4286f4");
    }
  }
}
