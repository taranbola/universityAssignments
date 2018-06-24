//
// Simple client that reads from a server running the DailyAdviceServer application,
// which opens up port 4242 and sends single lines of advice. This client reads that
// advice, displays it, and then closes the connection.
//
// Note that the server is not running at all times, just around the time of the lectures
// in which this material was covered!
//

import java.io.*;
import java.net.*;

public class DailyAdviceClient
{
    public void connect() {
        try {
        	// Try to open up a connection with cslin152, port number 4242. If cslin152 is unavailable,
        	// run the server on the same machine as the client, and use the hostname "localhost".
            Socket s = new Socket( "cslin152", 4242 );

            // Buffer the input stream for performance.
            BufferedReader reader = new BufferedReader(
                                       new InputStreamReader(
                                          s.getInputStream() ) );
            
            // Get a line from the server and display.
            String advice = reader.readLine();
            System.out.println("Thought for the day: " + advice);
            
            // Close the reader and the connection.
            reader.close();
            s.close();
        }
        catch( IOException e )
        {
            System.out.println( e );
        }
    }
    
    public static void main(String[] args)
    {
        DailyAdviceClient client = new DailyAdviceClient();
        client.connect();
    }
}
