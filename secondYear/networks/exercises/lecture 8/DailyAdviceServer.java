//
// The server for the 'daily advice' application, where the client requests a
// single line of advice from the server and then closes the connection.
//

import java.io.*;
import java.net.*;
import java.util.*;

public class DailyAdviceServer
{
	// Feel free to add any 'hilarious' one-line advice strings here.
    private String[] adviceList = {
        "Take smaller bites",
        "Go for the tight jeans. No they do NOT make you look fat",
        "One word: inappropriate",
        "Just for today, be honest. Tell your boss what you *really* think",
        "You might want to rethink that haircut"
    };

    // The advice sent to the client is just randomly selected from the above list.
    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }

    public void runServer() {
        try
        {

        	// For the example in lectures, we use port 4242.
            ServerSocket serverSock = new ServerSocket(4242);
            while( true )
            {
            	// Accept; blocking; will not return until a client has made contact.
                Socket sock = serverSock.accept();

				// Get the address of the client. Also get the date. This information could be
				// added to a log file, for instance.
                InetAddress inet = sock.getInetAddress();
                Date date = new Date();
                System.out.println("\nDate " + date.toString() );
                System.out.println("Connection made from " + inet.getHostName() );

				// Send a single line of text to the client.
                PrintWriter writer = new PrintWriter(sock.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);    		 // Write to client
                writer.close();
                System.out.println(advice);		 // Local server echo
                sock.close();
            }
        }
        catch (IOException ex) {
            System.out.println( ex );
        }
    }

    public static void main(String[] args) {
        DailyAdviceServer server = new DailyAdviceServer();
        server.runServer();
    }

}
