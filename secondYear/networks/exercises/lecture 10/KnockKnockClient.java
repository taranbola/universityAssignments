/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.*;
import java.net.*;

public class KnockKnockClient {

    private Socket kkSocket = null;
    private PrintWriter socketOutput = null;
    private BufferedReader socketInput = null;

    public void playKnockKnock() {

        try {

            // Try and create the socket. If cslin152 is unavailable, run the server on
            // the same machine as the client, and use the hostname "localhost".
            kkSocket = new Socket( "localhost", 2323 );

            // Chain a writing stream.
            socketOutput = new PrintWriter( kkSocket.getOutputStream(), true );

            // Chain a reading stream.
            socketInput = new BufferedReader(
            				new InputStreamReader(kkSocket.getInputStream()) );

        }
        catch( UnknownHostException e ) {
            System.err.println( "Don't know about host.\n" );
            System.exit(1);
        }
        catch( IOException e ) {
            System.err.println( "Couldn't get I/O for the connection to host.\n" );
            System.exit(1);
        }

        // Chain a reader from the keyboard.
        BufferedReader stdIn = new BufferedReader(
        						new InputStreamReader(System.in) );
        String fromServer;
        String fromUser;

        // Read from server.
        try
        {
          while( (fromServer=socketInput.readLine())!=null )
          {
              // Echo server string.
              System.out.println( "Server: " + fromServer );
              if( fromServer.equals("Bye.") ) break;

              // Client types in response
              fromUser = stdIn.readLine();
    	      if( fromUser!=null )
    	      {
                  // Echo client string.
                  System.out.println( "Client: " + fromUser );

                  // Write to server.
                  socketOutput.println(fromUser);
              }
          }
          socketOutput.close();
          socketInput.close();
          stdIn.close();
          kkSocket.close();
        }
        catch (IOException e) {
            System.err.println("I/O exception during execution\n");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
      KnockKnockClient kkc = new KnockKnockClient();
      kkc.playKnockKnock();
    }

}
