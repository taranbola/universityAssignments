/**
 * BookingScreenController.java
 */

package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.text.DecimalFormat;
import java.util.logging.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class for selection of particular ticket types. A running total price
 * is displayed dependent on selection
 *
 * @author Dan Aves
 */
public class BookingScreenController {
  //Declaring FXML elements
  @FXML
  private ComboBox child,student,adult,vip,vipPensioner,teen,pensioner;
  @FXML
  private Label childTotal,studentTotal,adultTotal,vipTotal,grandTotal,teenTotal,pensionerTotal,vipPensionerTotal;

  //Variables are used for calculating total prices
  Double childTotal2,studentTotal2,adultTotal2,vipTotal2,teenTotal2,pensionerTotal2,vipPensionerTotal2;
  Double grandTotal2 = 0.0;
  int selectedChild,selectedStudent,selectedAdult,selectedVip,selectedTeen,selectedPensioner,selectedVipPensioner =0;

  //Variables are passed from previous controller, dependent on user selection
  LocalDate inputDate;
  String inputTime;
  int screeningID;
  int inputFilmID;
  String inputFilmName;
  String inputScreenNumber;
  int ticketQuantity;


  /**
  * Sets the local variable inputDate to the passed date from
  * TimeTableController.
  * @param date.
  */
  public void setDate(LocalDate date){
    LocalDate current = LocalDate.now();
    inputDate = date;
  }

  /**
  * Returns passed inputDate
  * @return inputDate.
  */
  public LocalDate getDate(){
    return inputDate;
  }

  /**
  * Sets passed filmID used for back function
  * @param id selected filmID
  */
  public void setFilmID(int id){

      inputFilmID = id;
  }

  /**
  * Sets passed filmName and is stored locally
  * @param name filmName
  */
  public void setFilmName(String name){
    inputFilmName = name;
  }
  /**
  * Sets the local variable inputTime to the passed time from
  * TimeTableController.
  * @param time selected filmTime
  */
  public void setTime(String time){
    if(time == null || time == "") {
      throw new IllegalArgumentException("Time must have a value.");
    } else {
      inputTime = time;
    }
  }

  /**
  * Returns passed time
  * @return inputTime.
  */
  public String getTime(){
    return inputTime;
  }

  /**
  * Sets the local variable screeningID to the passed screening id from
  * TimeTableController.
  * @param id.
  */
  public void setScreeningID(int id){
    if(id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
      screeningID = id;
    }
  }

  /**
  * gets passed screeningID
  * @return screeningID.
  */
  public int getScreeningID(){
    return screeningID;
  }

  /**
  * sets passed Screen number for the film selected
  * @param screen
  */
  public void setScreenNumber(String screen){
    inputScreenNumber = screen;
  }

  /**
  * Takes user back to previous screen
  * @param  event       User clicks.
  * @throws Exception
  */
  public void backButtonClicked(ActionEvent event) throws Exception {
    FXMLLoader Loader = new FXMLLoader();
    Loader.setLocation(getClass().getResource("resources/timetableScreen.fxml"));
    try{
      Loader.load();
    }catch (IOException ex){
      Logger.getLogger(TimeTableController.class.getName());
    }

    TimeTableController display = Loader.getController();
    display.setDate(inputDate); //pass date to next controller
    display.calculateShowingTimes(inputFilmID); //pass film ID
    display.setFilmName(inputFilmName);


    Parent p = Loader.getRoot();
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(new Scene(p));
    window.show();

  }

  /**
  * Calculates the childTotal and displays on screen and appends grandTotal
  * @param event.
  * @throws IOException
  */
  public void comboBoxChild(ActionEvent event) throws IOException{
    if (selectedChild > 0){
      grandTotal2 += -childTotal2;
    }
    String amount = child.getValue().toString();
    ticketQuantity += Double.parseDouble(amount);
    childTotal2 = Double.parseDouble(amount) *5.00;
    childTotal.setText("Total   £ "+ String.format("%.2f",childTotal2));
    grandTotal2 +=childTotal2;
    grandTotal();
    selectedChild++;
  }

  /**
  * Calculates the teenTotal and displays on screen and appends grandTotal
  * @param event.
  * @throws IOException
  */
  public void comboBoxTeen(ActionEvent event) throws IOException{
    if (selectedTeen > 0){
      grandTotal2 += -teenTotal2;
    }
    String amount = teen.getValue().toString();
    ticketQuantity += Double.parseDouble(amount);
    teenTotal2 = Double.parseDouble(amount) *5.00;
    teenTotal.setText("Total   £ "+ String.format("%.2f",teenTotal2));
    grandTotal2 +=teenTotal2;
    grandTotal();
    selectedTeen++;
  }

  /**
  * Calculates the Student and displays on screen and appends grandTotal
  * @param event.
  * @throws IOException
  */
  public void comboBoxStudent(ActionEvent event) throws IOException {
    if (selectedStudent > 0){
      grandTotal2 += -studentTotal2;
    }
    String amount = student.getValue().toString();
    ticketQuantity += Double.parseDouble(amount);
    studentTotal2 = Double.parseDouble(amount) *5.00;
    studentTotal.setText("Total   £ "+ String.format("%.2f",studentTotal2));
    grandTotal2 +=studentTotal2;
    grandTotal();
    selectedStudent++;
  }

  /**
  * Calculates the AdultTotal and displays on screen and appends grandTotal
  * @param event.
  * @throws IOException
  */
  public void comboBoxAdult(ActionEvent event) throws IOException{
    if (selectedAdult > 0){
      grandTotal2 += -adultTotal2;
    }
    String amount = adult.getValue().toString();
    ticketQuantity += Double.parseDouble(amount);
    adultTotal2 = Double.parseDouble(amount) *5.00;
    adultTotal.setText("Total   £ "+ String.format("%.2f",adultTotal2));
    grandTotal2 += adultTotal2;
    grandTotal();
    selectedChild++;
  }

  /**
  * Calculates the pensionerTotal and displays on screen and appends grandTotal
  * @param event.
  * @throws IOException
  */
  public void comboBoxPensioner(ActionEvent event) throws IOException{
    if (selectedPensioner > 0){
      grandTotal2 += -pensionerTotal2;
    }
    String amount = pensioner.getValue().toString();
    ticketQuantity += Double.parseDouble(amount);
    pensionerTotal2 = Double.parseDouble(amount) *3.50;
    pensionerTotal.setText("Total   £ "+ String.format("%.2f",pensionerTotal2));
    grandTotal2 +=pensionerTotal2;
    grandTotal();
    selectedPensioner++;
  }

  /**
  * Calculates the PensionerVipTotal and displays on screen and appends grandTotal
  * @param event.
  * @throws IOException
  */
  public void comboBoxPensionerVip(ActionEvent event) throws IOException{
    if (selectedVipPensioner > 0){
      grandTotal2 += -vipPensionerTotal2;
    }
    String amount = vipPensioner.getValue().toString();
    ticketQuantity += Double.parseDouble(amount);
    vipPensionerTotal2 = Double.parseDouble(amount) *6.00;
    vipPensionerTotal.setText("Total   £ "+ String.format("%.2f",vipPensionerTotal2));
    grandTotal2 +=vipPensionerTotal2;
    grandTotal();
    selectedVipPensioner++;
  }

  /**
  * Calculates the vipTotal and displays on screen and appends grandTotal
  * @param event.
  * @throws IOException
  */
  public void comboBoxVip(ActionEvent event) throws IOException{
    if (selectedVip > 0){
      grandTotal2 += -vipTotal2;
    }
    String amount = vip.getValue().toString();
    ticketQuantity += Double.parseDouble(amount);
    vipTotal2 = Double.parseDouble(amount) *7.50;
    vipTotal.setText("Total   £ "+ String.format("%.2f",vipTotal2));
    grandTotal2 += vipTotal2;
    grandTotal();
    selectedVip++;

  }

  /**
  * Sets the grandTotal text based on the updated 'grandTotal2'
  */
  public void grandTotal(){
    grandTotal.setText("Grand total   £ " + String.format("%.2f", grandTotal2));
  }

  /**
  * Returns grandTotal2
  * @return grandTotal2.
  */
  public Double getGrandTotal(){
    return grandTotal2;
  }

  /**
  * Once 'selectSeats' is clicked, seatingScreen is loaded.
  *
  * @param event clicking 'selectSeats' button
  * @throws Exception
  */
  public void selectSeats(ActionEvent event) throws Exception{
    FXMLLoader Loader = new FXMLLoader();
    Loader.setLocation(getClass().getResource("resources/seatingScreen.fxml"));
    try{
      Loader.load();
    }catch (IOException ex){
      Logger.getLogger(SeatingScreenController.class.getName());
    }

    SeatingScreenController display = Loader.getController();
    //Variables are passed through for use in other screens
    display.setTotal(grandTotal2); //Total price of tickets
    display.populateSeats(screeningID); //ScreenID
    display.setTime(inputTime); //Time of film
    display.setDate(inputDate); //Date of film
    display.setScreeningID(screeningID); //ScreenID
    display.setFilmID(inputFilmID); //FilmID
    display.setFilmName(inputFilmName); //FilmName
    display.setScreenNumber(inputScreenNumber);
    display.setTicketQuantity(ticketQuantity);

    Parent p = Loader.getRoot();
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(new Scene(p));
    window.show();
  }

  /**
  * Sets up the comboBoxes with values.
  */
  public void initialize() {
    child.getItems().addAll(0,1,2,3,4);
    child.setEditable(true);
    student.getItems().addAll(0,1,2,3,4);
    student.setEditable(true);
    adult.getItems().addAll(0,1,2,3,4);
    adult.setEditable(true);
    vip.getItems().addAll(0,1,2,3,4);
    vip.setEditable(true);
    vipPensioner.getItems().addAll(0,1,2,3,4);
    vipPensioner.setEditable(true);
    teen.getItems().addAll(0,1,2,3,4);
    teen.setEditable(true);
    pensioner.getItems().addAll(0,1,2,3,4);
    pensioner.setEditable(true);
  }
}
