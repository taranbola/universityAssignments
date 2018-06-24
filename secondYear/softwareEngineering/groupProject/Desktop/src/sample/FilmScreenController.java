/**
 * FilmScreenController.java
 */

package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.List;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Fetches film names for a selected date and
 * outputs the names to the UI for user selection.
 *
 * @author Dan Aves
 */
public class FilmScreenController{

  //Declaring FXML elements
  @FXML
  public Button film1,film2,film3,film4,film5,film6,film7,film8,film9,film10;
  public Label filmDateText;

  LocalDate inputDate;

  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  /**
  * Sets the local variable inputDate to the passed date from
  * HomeScreenController, dependent on user selection. Also sets text
  * on the UI.
  * @param date.
  */
  public void setDate(LocalDate date){
    inputDate = date;
    filmDateText.setText("Showing films for " + dtf.format(inputDate));
  }

  /**
  * This method fetches the correct film names dependent on the previously selected
  * date.
  * @param date Used to find all film names
  */
  public void setScreen(LocalDate date) throws Exception{
    RestClient client = new RestClient("localhost", 5000);

    List filmList = new ArrayList();
    List<Screening> screeningsList = new ArrayList<Screening>();
    List<Integer> filmIdList = new ArrayList<Integer>();

    //Fetch all screenings for particular date
    screeningsList = client.getScreeningsByDate(inputDate);

    //Get the film ids and add to a new list
    for (int i = 0 ; i <screeningsList.size(); i++){
      int fid= screeningsList.get(i).getFilm_id();
      if (filmIdList.contains(fid)){
        //Do nothing - prvents duplicates
      }
      else{
        filmIdList.add(fid);
      }
    }

    //Gets the filmname for a particular filmID and displays to ui.
    for(int i =0; i<filmIdList.size() ;i++){
      int fid = Integer.parseInt(filmIdList.get(i).toString());
      Film f = client.getFilm(fid);
      String filmName = f.getFilm_name();
      int filmId = f.getFilm_id();
      switch (filmId) {
        case 1: film1.setText(filmName);
        break;
        case 2: film2.setText(filmName);
        break;
        case 3: film3.setText(filmName);
        break;
        case 4: film4.setText(filmName);
        break;
        case 5: film5.setText(filmName);
        break;
        case 6: film6.setText(filmName);
        break;
        case 7: film7.setText(filmName);
        break;
        case 8: film8.setText(filmName);
        break;
        case 9: film9.setText(filmName);
        break;
        case 10: film10.setText(filmName);
        break;
      }
    }
  }


  /**
  * Takes user back a page to homescreen
  * @param event clicking back button
  */
  public void backButtonClicked(ActionEvent event) throws IOException{
    Parent secondaryroot = FXMLLoader.load(getClass().getResource("resources/homeScreen.fxml"));
    Scene filmScreen = new Scene(secondaryroot);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(filmScreen);
    window.show();
  }

  /**
  * When a film is selected the timetableScreen is loaded showing times
  * for that particular film.
  *
  * @param event clicking a filmName
  */
  public void selectFilm(ActionEvent event) throws Exception{

    FXMLLoader Loader = new FXMLLoader();
    Loader.setLocation(getClass().getResource("resources/timetableScreen.fxml"));
    try{
      Loader.load();
    }catch (IOException ex){
      Logger.getLogger(FilmScreenController.class.getName());
    }

    TimeTableController display = Loader.getController();
    //Determine what button is clicked
    String filmName = (((Button)event.getSource()).getText());
    String buttonID = (((Button)event.getSource()).getId());

    int filmID =0;
    //Enables correct ID to be passed to next screen dependent on film selected
    if (buttonID.equals("film1")){
      filmID = 1;
    }
    if (buttonID.equals("film2")){
      filmID = 2;
    }
    if (buttonID.equals("film3")){
      filmID = 3;
    }
    if (buttonID.equals("film4")){
      filmID = 4;
    }
    if (buttonID.equals("film5")){
      filmID = 5;
    }
    if (buttonID.equals("film6")){
      filmID = 6;
    }
    if (buttonID.equals("film7")){
      filmID = 7;
    }
    if (buttonID.equals("film8")){
      filmID = 8;
    }
    if (buttonID.equals("film9")){
      filmID = 9;
    }
    if (buttonID.equals("film10")){
      filmID = 10;
    }

    //Pass relevant information to next screen
    display.setDate(inputDate); //pass date to next controller
    display.calculateShowingTimes(filmID); //pass film ID
    display.setFilmName(filmName);

    Parent p = Loader.getRoot();
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(new Scene(p));
    window.show();
  }
}
