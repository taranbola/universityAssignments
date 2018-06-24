/**
 * PaymentScreenController.java
 */

package sample;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.text.DecimalFormat;
import java.util.logging.Logger;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.time.LocalDate ;
import java.util.Hashtable;

import java.io.IOException;
import java.io.*;
import java.awt.image.BufferedImage;

import java.io.FileOutputStream;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;

import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import javax.imageio.ImageIO;

/**
 * The payment screen simulates card and cash payment for the till operator.
 * The total amount payable is passed into the class to be used.
 *
 * @author Dan Aves, Matt Cutts and Taranvir Bola
 */
public class PaymentScreenController {

  /** Path to the resulting PDF file. */
  public static final String CARD = "/cardReciept.pdf";
  public static final String CASH = "/cashReciept.pdf";

  //FXML element importing
  @FXML
  private Label changeDue;
  @FXML
  public Text totalAmount;
  @FXML
  private Button five,ten,fithteen,twenty,thirty,forty,cash,card;
  @FXML
  private TextField tender;


  boolean cashBol = false;              //Used to determine if user selects "cash"
  List seatsPayment = new ArrayList();  //Local list to store seats passed to method
  String  cardReciept = "cardReciept.pdf";
  String  cashReciept = "cashReciept.pdf";

  //Variables for Pdf
  double grandTotal;    //Used to store transaction total
  String seatNum;       //Stores the seat numbers as a String
  int screenIDLocal;    //Stores passed screenID
  LocalDate inputDate;  //Date of film
  String inputTime;     //Time of film
  String inputFilmName; //Specific filmname
  String inputScreenNumber; //Selected screen number for film
  double fixedTotal;  //Unaltered total for recipt


  /**
  * This method sets the local variable grandTotal to the passed total from
  * seatingScreenController.
  * @param total.
  */
  public void setTotal(double total){
    grandTotal = total;
    fixedTotal = total;
    totalAmount.setText("Total £ " + String.format("%.2f", total));
  }

  /**
  * Sets the local variable inputDate to the passed date from
  * SeatingScreenController.
  * @param date.
  */
  public void setDate(LocalDate date){
    inputDate = date;
  }

  /**
  * This method updates the local list for the passed seats
  * @param seats.
  */
  public void setSeats(List seats)
  {
    seatsPayment = seats;
  }

  /**
  * This method updates the local screenID for the passed ID dependent on
  * previous selections.
  * @param screenID.
  */
  public void setScreenID(int screenID){
    if(screenID < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
      screenIDLocal = screenID; //Update local version for access in other functions
    }
  }

  /**
  * Fetches passed filmName used for back function and pdf
  * @param name
  */
  public void setFilmName(String name){
    inputFilmName = name;
  }

  /**
  * Sets the local variable inputTime to the passed time from
  * SeatingScreenController.
  * @param time.
  */
  public void setTime(String time){
    inputTime = time;
  }

  /**
  * Returns passed Screen number for the film selected
  * @param screen
  */
  public void setScreenNumber(String screen){
    inputScreenNumber = screen;
  }

  /**
   * Actions when the back button is pressed
   * @param  event User Click
   * @throws IOException If button is pressed incorrectly.
   */
  public void backButtonClicked(ActionEvent event) throws IOException {
    Parent secondaryroot = FXMLLoader.load(getClass().getResource("resources/homeScreen.fxml"));
    Scene filmScreen = new Scene(secondaryroot);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(filmScreen);
    window.show();

  }

  /**
   * Will log you out of the till system
   * @param  event User click
   * @throws IOException If incorrectly pressed
   */
  public void logoutButtonClicked(ActionEvent event) throws IOException{
    Parent secondaryroot = FXMLLoader.load(getClass().getResource("resources/homeScreen.fxml"));
    Scene filmScreen = new Scene(secondaryroot);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(filmScreen);
    window.show();

  }

  /**
   * This method of payment is being used.
   * @param  event User click
   * @throws Exception  If pressed incorrectly
   */
  public void cardClicked(ActionEvent event) throws Exception{
    AlertBox.display("Please wait", "Processing payment", "card");
    card.setStyle("-fx-background-color: #4286f4");
    totalAmount.setText("Total £ " + ("0.00"));
    bookSeats();
    createCardPDF(cardReciept);
  }

  /**
   * This will book seats on a website for each seat
   * @throws Exception If no seats booked.
   */
  public void bookSeats() throws Exception{
    RestClient client = new RestClient("localhost", 5000);
    // create screening obj for appropriate screening
    int seatsPaymentSize = seatsPayment.size();
    //Loop through seat array
    for(int i =0; i<seatsPaymentSize; i++)
    {
      Screening screening = client.getScreening(screenIDLocal);
      //create seat obj for appropraite seat
      Seat seat = client.getSeat(Integer.parseInt(seatsPayment.get(i).toString()));
      //till is customer 5
      Customer c = new Customer(5);
      //Create a ticket for each seat
      client.createTicket(c,screening,seat);
    }

  }

  /**
   * Will get a list of seats
   * @return the list of seats
   */
  public List getSeats(){
    return seatsPayment;
  }

  /**
   * Will give you the screen number
   * @return Screen number
   */
  public int getScreenID(){
    return screenIDLocal;
  }

  /**
   * It will tell you when the cash button is clicked.
   * @param  event User Click
   * @throws Exception If incorrectly pressed
   */
  public void cashClicked(ActionEvent event) throws Exception{
    cashBol = true;
    cash.setStyle("-fx-background-color: #4286f4");
  }

  /**
   * It will take in a user cash value
   * @param  event Staff text input
   * @throws Exception Enters invalid data
   */
  public void tenderEntered(ActionEvent event) throws Exception{
    String input = tender.getText();
    if (cashBol == true){
      grandTotal -= Double.parseDouble(input);
      change();
    }
  }

  /**
   * Will say that customer gave £5, remove from total
   * @param  event User click
   * @throws Exception Button incorrectly pressed
   */
  public void fiveClicked(ActionEvent event) throws Exception{
    if (cashBol == true){
      grandTotal -=5;
      change();
    }
  }

  /**
  * Will say that customer gave £10, remove from total
  * @param  event User click
  * @throws Exception Button incorrectly pressed
  */
  public void tenClicked(ActionEvent event) throws Exception{
    if (cashBol == true){
      grandTotal -=10;
      change();
    }
  }

  /**
  * Will say that customer gave £15, remove from total
  * @param  event User click
  * @throws Exception Button incorrectly pressed
  */
  public void fithteenClicked(ActionEvent event) throws Exception{
    if (cashBol == true){
      grandTotal -=15;
      change();
    }
  }

  /**
  * Will say that customer gave £20, remove from total
  * @param  event User click
  * @throws Exception Button incorrectly pressed
  */
  public void twentyClicked(ActionEvent event) throws Exception{
    if (cashBol == true){
      grandTotal -=20;
      change();
    }
  }

  /**
  * Will say that customer gave £30, remove from total
  * @param  event User click
  * @throws Exception Button incorrectly pressed
  */
  public void thirtyClicked(ActionEvent event) throws Exception{
    if (cashBol == true){
      grandTotal -=30;
      change();
    }
  }

  /**
  * Will say that customer gave £40, remove from total
  * @param  event User click
  * @throws Exception Button incorrectly pressed
  */
  public void fortyClicked(ActionEvent event) throws Exception{
    if (cashBol == true){
      grandTotal -=40;
      change();
    }
  }

  /**
   * It will book the seats, create a cash pdf and output the change value
   * @throws Exception If invalid value
   */
  public void change() throws Exception{
    if (grandTotal < 0){
      grandTotal = Math.abs(grandTotal);
      totalAmount.setText("Total: £" + ("0.00"));
      changeDue.setText("Change: £" + String.format("%.2f", grandTotal));
      bookSeats();
      createCashPDF(cashReciept);
    }
    else if (grandTotal == 0.0){
        totalAmount.setText("Total £ " + ("0.00"));
        changeDue.setText("");
        bookSeats();
    }
    else{
      totalAmount.setText("Total £ " + String.format("%.2f", grandTotal));
    }
  }

  /**
   * Creates the PDF for the card with the QR code
   * @param  cardReciept is the name of the pdf
   * @throws DocumentException If the document can't be created.
   * @throws IOException If it can't write the file.
   */
  private void createCardPDF(String cardReciept) throws DocumentException, IOException {

    File file = new File(CARD);
    file.getParentFile().mkdirs();
    //create a document
    Document document = new Document();
    //create an instance for it to be generated
    PdfWriter.getInstance(document, new FileOutputStream(cardReciept));
    //opeing the document to write to it.
    document.open();
    //from line 297 - 329 information is being added to the pdf document.
    document.add(new Paragraph("Osprey Cinema"));
    document.add(new Paragraph("University Of Leeds Union"));
    document.add(new Paragraph("Leeds\n\n"));
    document.add(new Paragraph("\n-----------------------------------------------"));
    document.add(new Paragraph("\nYour Viewing\n"));
    document.add(new Paragraph(inputFilmName));
    document.add(new Paragraph("\nDate: " + inputDate));
    document.add(new Paragraph("\nTime: " + inputTime));
    document.add(new Paragraph("\nScreen: " + inputScreenNumber));
    int seatsPaymentSize = seatsPayment.size();
    //Loop through seat array
    String stringname = "";
    for(int i =0; i<seatsPaymentSize; i++)
    {
      stringname += seatsPayment.get(i).toString() + " | ";

    }
    document.add(new Paragraph("\nSeat(s): " + stringname));
    document.add(new Paragraph("\n----------------------------------------------- "));
    document.add(new Paragraph("\n\n**** **** **** 6190"));
    document.add(new Paragraph("Visa Debit "));
    document.add(new Paragraph("Merchant ID: **12345 \n Terminal ID: ****1234  \n\n"));
    document.add(new Paragraph("SALE \n\n"));
    document.add(new Paragraph("Your account will be debited with the total amount shown: \n Total: " + grandTotal + "\n\n"));
    // document.add(new Paragraph("Number of items: " + items + "\n\n"));
    document.add(new Paragraph("SOURCE:     CONTACTLESS \n\n"));
    document.add(new Paragraph("Authorisation Code: 12387 \n\n"));
    document.add(new Paragraph("Please keep this reciept for your records. \n\n"));
    document.add(new Paragraph("CUSTOMER COPY \n\n"));
    document.add(new Paragraph("Total: £" + grandTotal + "\n\n"));
    document.add(new Paragraph("Thank you for visiting Britains Best Cinema Experience"));
    document.add(new Paragraph("#OspreyCinemaWhereExcitingHappens\n\n"));
    document.add(new Paragraph("Tell us how we did by sending us an email\n with the chance to win £100. \n Eamil: ukoc@OSPREYCinema.com \n"));

    //This populates the Qr code
    String qrinfo = inputFilmName + " | " + inputDate + " | " + inputTime + " | " + inputScreenNumber + " | " + stringname;
    generateQr(qrinfo);

    Image QR = Image.getInstance("ticket.png");
    QR.setAlignment(Image.MIDDLE);
    //adding the 'QR' to the pdf document
    document.add(QR);
    //this is closing the file so it can no longer be written to.
    document.close();
  }

  /**
   * Creates the PDF for the cash with the QR code
   * @param  cardReciept is the name of the pdf
   * @throws DocumentException If the document can't be created.
   * @throws IOException If it can't write the file.
   */
  private void createCashPDF(String cashReciept) throws DocumentException, IOException {

    File file = new File(CASH);
    file.getParentFile().mkdirs();
    //create a document
    Document document = new Document();
    //create an instance for it to be generated
    PdfWriter.getInstance(document, new FileOutputStream(cashReciept));
    //opeing the document to write to it.
    document.open();
    //from line 346 - 378 information is being added to the pdf document.
    document.add(new Paragraph("Osprey Cinema"));
    document.add(new Paragraph("University Of Leeds Union"));
    document.add(new Paragraph("Leeds\n\n"));
    document.add(new Paragraph("\n-----------------------------------------------"));
    document.add(new Paragraph("\nYour Viewing\n"));
    document.add(new Paragraph(inputFilmName));
    document.add(new Paragraph("\nDate: " + inputDate));
    document.add(new Paragraph("\nTime: " + inputTime));
    document.add(new Paragraph("\nScreen: " + inputScreenNumber));
    int seatsPaymentSize = seatsPayment.size();
    //Loop through seat array
    String stringname = "";
    for(int i =0; i<seatsPaymentSize; i++)
    {
      stringname += seatsPayment.get(i).toString() + " | ";

    }
    document.add(new Paragraph("\nSeat(s): " + stringname));
    document.add(new Paragraph("\n----------------------------------------------- "));
    document.add(new Paragraph("\n\nCash Payment "));
    document.add(new Paragraph("Merchant ID: **12345 \n Terminal ID: ****1234  \n\n"));
    document.add(new Paragraph("SALE \n\n"));
    document.add(new Paragraph("You have been charged: \n Total: " + fixedTotal + "\n"));
    // document.add(new Paragraph("Number of items: " + items + "\n\n"));
    document.add(new Paragraph("\nSOURCE:     CASH\n\n"));
    document.add(new Paragraph("Authorisation Code: 12387 \n\n"));
    document.add(new Paragraph("Please keep this reciept for your records. \n\n"));
    document.add(new Paragraph("CUSTOMER COPY \n\n"));
    document.add(new Paragraph("Total: £" + fixedTotal + "\n\n"));
    document.add(new Paragraph(changeDue.getText() + "\n\n"));
    document.add(new Paragraph("Thank you for visiting Britains Best Cinema Experience"));
    document.add(new Paragraph("#OspreyCinemaWhereExcitingHappens\n\n"));
    document.add(new Paragraph("Tell us how we did by sending us an email\n with the chance to win £100. \n Eamil: ukoc@OSPREYCinema.com \n"));

    //This populates the Qr code
    String qrinfo = inputFilmName + " | " + inputDate + " | " + inputTime + " | " + inputScreenNumber + " | " + stringname;
    generateQr(qrinfo);
    Image QR = Image.getInstance("ticket.png");
    QR.setAlignment(Image.MIDDLE);
    //adding the 'QR' to the pdf document
    document.add(QR);
    //this is closing the file so it can no longer be written to.
    document.close();
  }

  /**
   * Will create a QR code from a piece of text, is called ticket.png
   * @param String Will be the contents of the readable QR code.
   */
  private static void generateQr(String text){
      File file = new File("ticket.png");
      Hashtable hash = new Hashtable();
      //works out size of string and creates the error correct level
      hash.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
      QRCodeWriter codewriter = new QRCodeWriter();
      //creates the QR matrix
      try {
          BitMatrix matrix = codewriter.encode(text, BarcodeFormat.QR_CODE,500,500,hash);
          //creates the specific image file
          BufferedImage code = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
          code.createGraphics();
          //Creates the white background
          Graphics2D colours = (Graphics2D)code.getGraphics();
          colours.setColor(Color.white);
          colours.fillRect(0,0,500,500);
          colours.setColor(Color.BLACK);
          //creates each pixel of the QR code
          for(int i = 0 ; i<500;i++){
              for(int j = 0; j < 500;j++){
                  if (matrix.get(i,j)){
                      colours.fillRect(i,j,1,1);
                  }
              }
          }
          //creates an image with a png file type
          ImageIO.write(code,"png",file);

      } catch (WriterException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
  }
}
