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
* A robot checks the ticket screen for the correct button action.
*
* @author Matthew Cutts, Dan Aves
* @version 1.1 (2018-04-24)
*/

public class PaymentRobot extends ApplicationTest {

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
    * Test to see if we can pay using cash, by pressing the vlaue £40.
    */
    @Test
    public void Test1Cash () {
        clickOn("#viewToday");
        clickOn("#film1");
        clickOn("#film1");
        clickOn("#adult");
        write("2");
        clickOn("#selectSeats");
        clickOn("#seat50");
        clickOn("#seat51");
        clickOn("#payButton");
        clickOn("#cash");
        clickOn("#forty");
    }

    /**
    * Test to see if we can pay using Card.
    */
    @Test
    public void Test1Card() {
        clickOn("#viewToday");
        clickOn("#film1");
        clickOn("#film1");
        clickOn("#adult");
        write("2");
        clickOn("#selectSeats");
        clickOn("#seat99");
        clickOn("#seat98");
        clickOn("#payButton");
        clickOn("#card");
    }

    /**
    * Test to see if we can pay by enetering the amount of cash the customer
    * has given the staff.
    */
    @Test
    public void Test1EnterTender() {
        clickOn("#viewToday");
        clickOn("#film1");
        clickOn("#film1");
        clickOn("#adult");
        write("2");
        clickOn("#selectSeats");
        clickOn("#seat68");
        clickOn("#seat67");
        clickOn("#payButton");
        clickOn("#tender");
        write("50");

    }
}
