import java.io.*;
import java.net.*;
import java.util.*;

/**
* This class will allow a client to be created and enter input messages to
* the server. It will create a socket on port 8888.
* Reuses code from Sun Microsystems and KnockKnockClient
* @author Taranvir Bola
*/
public class Client{
	private Socket socket = null;
	private PrintWriter socketOutput = null;
	private BufferedReader socketInput = null;
	private Scanner serverReader = null;


	/**
	* This constuctor will create the variable to allow the scanner to be used.
	*/
	public Client(){
		serverReader = new Scanner( System.in );
	}

	/**
	* This function will create a socket for the client to connect to. It will
	* use localhost on port 8888.It will output whatever the server sends as a
	* message. If the client sends the message bye, the client will close along
	* with input stream, output stream and the socket.
	*/
	public void fileTransfer() {

		try {

			// try and create the socket
			socket = new Socket( "localhost", 8888 );
			// chain a writing stream
			socketOutput = new PrintWriter(socket.getOutputStream(), true);

			// chain a reading stream
			socketInput = new BufferedReader(
										new InputStreamReader(socket.getInputStream()));
		}
		catch (UnknownHostException e) {
			System.err.println("Don't know about host.\n");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host.\n");
			System.exit(1);
		}

		// chain a reader from the keyboard
		String fromServer;
		socketOutput.println(System.getProperty("user.dir"));

		// communication loop
		try {
			while( true ) {
				// read from server
				if((fromServer = socketInput.readLine()) != null) {
					System.out.println("Server: " + fromServer);
					if (fromServer.equals("Bye"))
					break;

					System.out.print("Client: " );
					String inputReader = serverReader.nextLine();

					if (inputReader != null) {
						// echo client string
						socketOutput.println(inputReader);
					}
				}
			}
			socketOutput.close();
			socketInput.close();
			socket.close();
		}
		catch (IOException e) {
			System.err.println("I/O exception during execution\n");
			System.exit(1);
		}
	}

	/**
	* This function will create an instance of userInput which uses the
	* fileTransfer function to transfer files and messages.
	*
	*/
	public static void main(String[] args) {
		Client userInput = new Client();
		userInput.fileTransfer();
	}

}
