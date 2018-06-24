import java.net.*;
import java.io.*;
import java.util.concurrent.*;

/**
* The class Server will create a server and allow up 10 clients to
* connect to it. It uses socket 8888 to connect to the clients and creates
* a ClientHandler instance class for each.
* Reuses code from Sun Microsystems and KKExecuterServer
* @author Taranvir Bola
*/
public class Server{
	/**
	*This will functions will initalise and create the Server socket.
	*It will do this on socket 8888. It will allow a thread pool of 10, which
	*will allow 10 clients to connect the server at once. It will
	*continously keep running the server.
	*/
	public static void main( String[] args )throws IOException {
		ServerSocket server = null;
		ExecutorService service = null;

		// Try to open up the listening port
		try{
			server = new ServerSocket(8888);
		}
		catch (IOException e) {
			System.err.println("Could not listen on port: 8888.");
			System.exit(-1);
		}

		// Initialise the executor.
		service = Executors.newFixedThreadPool(10); //multi-threadedpool

		// For each new client, submit a new handler to the thread pool.
		while( true ){
			Socket client = server.accept();
			service.submit( new ClientHandler(client) );
		}

	}
}
