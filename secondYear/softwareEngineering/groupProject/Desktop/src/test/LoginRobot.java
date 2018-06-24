package sample;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.testfx.api.FxAssert.verifyThat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
*
* A robot checks the login screen for invalid login details and correct login details.
* Tests are passed if all ok.
*
* @author Dan Aves,Matthew Cutts
*/

public class LoginRobot extends ApplicationTest{

  @Override
  public void start (Stage stage) throws Exception {
    Parent mainNode = FXMLLoader.load(getClass().getResource("resources/loginScreen.fxml"));
    stage.setScene(new Scene(mainNode));
    stage.show();
    stage.toFront();
  }

  @Before
  public void setUp () throws Exception {
  }

  @After
  public void tearDown () throws Exception {
    FxToolkit.hideStage();
    release(new KeyCode[]{});
    release(new MouseButton[]{});
  }

  /**
  * Checks if message is displayed for invalid credentials
  */

  @Test
  public void Test1InvalidLogin () {
    clickOn("#username");
    write("This is a test!");
    clickOn("#passwordField");
    write("This is a test!");
    clickOn("#loginButton");
    verifyThat("#invalidDetails", hasText("Invalid details entered, try again"));
  }
  /**
  * Simply checks the text of button
  */
  @Test
  public void Test2LoginButton() {
    verifyThat("#loginButton", hasText("Login"));
  }

  /**
  * Tries to log into system
  */
  @Test
  public void Test3CorrectLogin() {
    clickOn("#username");
    write("user");
    clickOn("#passwordField");
    write("password");
    clickOn("#loginButton");

  }

}
