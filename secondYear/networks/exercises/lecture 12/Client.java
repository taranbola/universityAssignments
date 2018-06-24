// Minimal client to test the non-blocking server.
// Note that this client is of the normal, blocking variety, as performance is not
// an issue for the client (although non-blocking clients are possible).
// MW 16/11/15; DAH 7/11/17.

import java.net.*;
import java.io.*;

public class Client {

	public static void main( String[] args )
	{

		// Need a single command line argument: The message to send (which is then echo'd back).
		if( args.length != 1 )
		{
			System.err.println( "Need a single command line argument." );
			return;
		}

		try 
		{ 
			// Open up a connection to port 8989 on the server's host (here assumed to be localhost,
			// i.e. the same machine on which you are running the client).
			Socket connection = new Socket( "localhost", 8989 );

			// Get a print writer to output a message, and a buffered input stream for the response.
			PrintWriter    p = new PrintWriter( connection.getOutputStream(), true );
			BufferedReader r = new BufferedReader(
								new InputStreamReader( connection.getInputStream() ) );

			// Read a string from the command line and send to the server. Also echo to screen. 
			System.out.println(" Sending: " + args[0] );
			p.println( args[0] );

			// Read back the welcome and echo strings, then finish.
			System.out.println(" Received: " + r.readLine()) ;
			System.out.println(" Received: " + r.readLine()) ;

			connection.close();
		}
		catch( IOException e )
		{
			e.printStackTrace();
		}
	}
}

