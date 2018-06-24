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
* A robot checks the film screen for the correct button action.
*
* @author Matthew Cutts, Dan Aves 
* @version 1.1 (2018-04-24)
*/

public class ScreeningRobot extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getResource("resources/homeScreen.fxml"));
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
    * Test to see if we can click on the time we want to view the film.
    */
    @Test
    public void Test1film1 () {
        clickOn("#viewToday");
        clickOn("#film1");
        clickOn("#screen1");
        clickOn("#backButton");
    }
}
