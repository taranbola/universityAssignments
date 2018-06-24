/**
 * HomeScreenController.java
 */

package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Logger;

/**
 *
 * The HomeScreenController is the start of the film booking process.
 * A user is able to select films for todays date or a future date.
 *
 * @author Dan Aves
 */
public class HomeScreenController {

  @FXML
  private DatePicker filmDate;

  /**
  * When 'showToday' button is clicked the film screen is loaded showing films
  * for todays date
  * @param event clicking 'showToday' button
  */
  public void showToday(javafx.event.ActionEvent event) throws Exception{

    FXMLLoader Loader = new FXMLLoader();
    Loader.setLocation(getClass().getResource("resources/filmScreen.fxml"));
    try{
      Loader.load();
    }catch (IOException ex){
      Logger.getLogger(FilmScreenController.class.getName());
    }
    //Get todays date
    LocalDate localDate = LocalDate.now();
    //Pass date to next film screen
    FilmScreenController display = Loader.getController();
    display.setDate(localDate);
    display.setScreen(localDate);

    Parent p = Loader.getRoot();
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(new Scene(p));
    window.show();
  }

  /**
  * When 'showFutureDate' button is clicked the film screen is loaded showing films
  * for the selected date,
  * @param event clicking 'showFutureDate' button
  */
  public void showFutureDate(javafx.event.ActionEvent event) throws Exception{
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate date = filmDate.getValue();

    FXMLLoader Loader = new FXMLLoader();
    Loader.setLocation(getClass().getResource("resources/filmScreen.fxml"));
    try{
      Loader.load();
    }catch (IOException ex){
      Logger.getLogger(FilmScreenController.class.getName());
    }

    FilmScreenController display = Loader.getController();
    display.setDate(date);
    display.setScreen(date);

    Parent p = Loader.getRoot();
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(new Scene(p));
    window.show();
  }

  public void logoutButtonClicked(javafx.event.ActionEvent event) throws Exception{
      Parent secondaryroot = FXMLLoader.load(getClass().getResource("resources/loginScreen.fxml"));
      Scene filmScreen = new Scene(secondaryroot);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(filmScreen);
      window.show();
  }
}
