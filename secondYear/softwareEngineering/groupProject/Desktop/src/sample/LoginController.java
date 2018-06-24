/**
 * LoginController.java
 */

package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;


/**
 * The loginController is the first screen the user sees.
 * The user logs in with their credentials here.
 *
 * @author Dan Aves
 */
public class LoginController{

  @FXML
  private TextField username,password;
  private  Button loginButton;
  @FXML
  private Label invalidDetails;

  String username1 = "user";
  String password1 = "password";

  /**
  * Checks if credentials are valid and loads homescreen or displays error message
  * @param event clicking loginButton
  */

  public void loginButtonClicked(ActionEvent event) throws IOException {
    String usernameEntered = username.getText();
    String passwordEntered = password.getText();

    if (usernameEntered.equals(username1) && passwordEntered.equals(password1)) {
      Parent secondaryroot = FXMLLoader.load(getClass().getResource("resources/homeScreen.fxml"));
      Scene filmScreen = new Scene(secondaryroot);
      Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
      window.setScene(filmScreen);
      window.show();

    } else {
      invalidDetails.setText("Invalid details entered, try again");

    }
  }
}
