import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

/**
* For each client an instance of clientHandler will be created. It will create
* a new version of protocol. It will run up to 10 clients and acts as a
* middle man between the protocol and the client. Allows messages to be passed
* between each.
* Reuses code from Sun Microsystems and kkClientHandler
* @author Taranvir Bola
*/
public class ClientHandler extends Thread {
  private Socket socket = null;

  public ClientHandler(Socket socket) {
    super("clientHandler");
    this.socket = socket;
  }

  /**
  * This will take in the message from the client and will then pass that to
  * the protocol. It will take in messages from the protocol and pass them to
  * the client. It will log all the requests made by the user in log.txt. It
  * will do this for each client.
  */
  public void run() {

    try {

      /* This will create an reader and writer streams, to and from the client*/
      String inputLine, outputLine;
      PrintWriter streamOut = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader streamIn = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));

      InetAddress inet = socket.getInetAddress();
      Date date = new Date();
      System.out.println("\nDate " + date.toString() );
      System.out.println("Connection made from " + inet.getHostName() );

      /* Creates protocol instance to send message to client*/
      Protocol clientProtocol = new Protocol();
      inputLine = streamIn.readLine();
      outputLine = clientProtocol.processInput(inputLine);
      streamOut.println(outputLine);

      while ((inputLine = streamIn.readLine()) != null) {

        /* Logs any request made by the user whilst they are
        * sending a message or command.
        */
        DateFormat df = new SimpleDateFormat("dd/MM/yy:HH:mm:ss : ");
        Date dateobj = new Date();
        BufferedWriter writer = new BufferedWriter(
        new FileWriter("log.txt", true));
        String str = df.format(dateobj) + inet.getHostAddress()
        + " : " + inputLine + "\n";
        writer.append(str);
        writer.close();

        outputLine = clientProtocol.processInput(inputLine);
        streamOut.println(outputLine);
      }
      streamOut.close();
      streamIn.close();
      socket.close();

    }

    catch (IOException e) {
      System.out.println("Unknown File Location");
    }

  }
}
