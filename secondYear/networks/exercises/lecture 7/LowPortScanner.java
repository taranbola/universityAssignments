//
// Simple example of using the Java Socket class.
// Scans all ports on the given machine (or the local machine, if none was provided)
// from 1 to 1023 inclusive, and reports those that have a server (i.e. for which a
// connection was successfully made).
//
// Recommended to only run this on a School machine, as it can be very slow if
// attempted over the WiFi network.
//

import java.net.*;					// For Socket, UnknownHostException
import java.io.*;					// For IOException

public class LowPortScanner {

  public static void main( String[] args ) {

	// The default hostname    
    String host = "localhost";

	// If called with a hostname as the first command line argument, use that instead.
    if( args.length > 0 ) {
      host = args[0];
    }

	// Loop over all of the reserved ports, try to open a socket to each in turn.
    for (int i = 1; i < 1024; i++) {
      try {
        Socket s = new Socket(host, i);
        System.out.println("There is a server on port " + i + " of " + host);
      }
      catch (UnknownHostException ex) {
        System.err.println(ex);
        break;
      }
      catch (IOException ex) {
        // Must not be a server on this port; don't report anything.
      }
    } // End of the port loop.
  
  }  // End of main()
  
}  // End of PortScanner class.

