/**
 * Main.java
 */

package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is the starting point
 * of for the desktop application.
 *
 * @author Dan Aves
 */
public class Main extends Application {

  Stage window;
  Scene loginScreen;

  //Loads first screen
  @Override
  public void start(Stage primaryStage) throws Exception{
    window = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("resources/loginScreen.fxml"));
    window.setTitle("Desktop Interface");
    loginScreen = new Scene(root, 600, 500);
    window.setScene(loginScreen);
    window.show();

  }

  /**
  * This is the main method for the desktop app.
  *
  * @param args Standard input arguments.
  */

  public static void main(String[] args) throws Exception {
    launch(args);
  }
}
