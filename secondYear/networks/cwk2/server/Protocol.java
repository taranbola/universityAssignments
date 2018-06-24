import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

/**
* This will process all the user inputs that are done in the client. It will
* send any relevant files and messages to the user. This will take in the 4
* commands list, bye, get and put.
* Reuses code from Sun Microsystems and KKProtocol
* @author Taranvir Bola
*/
public class Protocol{
  private static final int WAITING = 0;
  private static final int COMMANDS = 1;
  private int state = WAITING;

  private String wd = null;

  private String clientDirectory = "/clientFiles/";
  private String serverDirectory = "serverFiles/";

  /**
  *The function will take in a string input of what the user input. It
  * will then split the input into firstCommand and filename. Before it
  * accepts on input from the user it will send a message to the client.
  * It will then take in any of the 4 commands else send a unknown command
  * message.
  */
  public String processInput(String theInput) throws IOException{

    String theOutput = null;
    String firstCommand = null;
    String filename = null;

    if(theInput != null){
      String[] splitInput= theInput.split(" ");
      firstCommand = splitInput[0];
      if(splitInput.length > 1){
        filename = splitInput[1];
      }
    }

    if (state == WAITING){
      wd = theInput;
      clientDirectory = wd + clientDirectory;
      theOutput = "Enter commands:";
      state = COMMANDS;
    }

    else if (state == COMMANDS){

      if (firstCommand.equalsIgnoreCase("list") && filename == null) {
        String fileLine= "";
        File folder = new File(serverDirectory);
        String[] files = folder.list();
        for (String file : files) {
          fileLine = fileLine + file + " ";
        }
        theOutput = fileLine;
      }

      else if (firstCommand.equalsIgnoreCase("bye") && filename == null) {
        theOutput = "Bye";
      }

      else if (firstCommand.equalsIgnoreCase("get") && filename != null) {
        /** Validation to ensure file is in correct folder */
        if(fileCheck(filename,serverDirectory) == true){
          if(fileCheck( filename , clientDirectory ) == false){
            obtainFile( filename, serverDirectory , clientDirectory );
            theOutput = filename + " successfully sent to client Directory";
          }
          else{
            theOutput = filename + " already in Client Directory";
          }
        }
        else{
          theOutput = filename + " not in Server Directory";
        }
      }

      else if (firstCommand.equalsIgnoreCase("put") && filename != null) {
        /** Validation to ensure file is in correct folder */
        if(fileCheck( filename , clientDirectory ) == true){
          if(fileCheck( filename , serverDirectory ) == false){
            obtainFile(filename , clientDirectory , serverDirectory);
            theOutput = filename + " successfully sent to server Directory";
          }
          else{
            theOutput = filename + " already in Server Directory";
          }
        }
        else{
          theOutput = filename + " not in Client Directory";
        }
      }

      else {
        theOutput = "Unknown Command";
      }
    }

    return theOutput;
  }

  /**
  *This will take in a filename, location of where the file is and
  *a location of where it is going. It will then send a file from
  *one location to another. It uses a buffer to read in the file
  *contents and write it's content into the new folder. If there is
  *an invalid file it will send an exception.
  */
  private void obtainFile(String filename, String inputLocation,
  String outputLocation) throws IOException{

    InputStream streamIn = null;
    OutputStream streamOut = null;
    try{
      streamIn = new FileInputStream(inputLocation + filename);
      streamOut = new FileOutputStream(outputLocation + filename);
      int filesize = streamIn.available();
      byte[] buffer = new byte[filesize];
      int readSize;
      while ((readSize = streamIn.read(buffer)) > 0 ) {
        streamOut.write(buffer, 0 , readSize);
      }
      streamIn.close();
      streamOut.close();
    }
    catch (IOException e){
      System.out.println("Unknown File Location");
    }
  }

  /**
  This will check if a filename exists in a given folder.
  It will return true if there is a filename in the folder
  else it will return false if it isn't in their.
  */
  private boolean fileCheck(String filename, String foldername){
    File folder = new File(foldername);
    String[] files = folder.list();
    for (String file : files){
      if(file.equals(filename) == true){
        return true;
      }
    }
    return false;
  }

}
