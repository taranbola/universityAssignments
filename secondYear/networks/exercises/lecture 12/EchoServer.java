// Simple java.nio (i.e. non-blocking) based Echo server, that resends the message sent
// by clients straight back to the same client.
// Based on the example at http://www.xinotes.net/notes/note/694/
// Minor modifications DAH/17/2017.

// Imports
import java.io.IOException;
import java.net.*;				// For: InetAddress, InetSocketADdtess, Socket and SocketAddress
import java.nio.ByteBuffer;
import java.nio.channels.*;		// For: SelectionKey; Selector; ServerSocketChannel; SocketChannel.
import java.util.*;


public class EchoServer
{
    private InetAddress addr;
    private int port;
    private Selector selector;
    private Map<SocketChannel,List<byte[]>> dataMap;

	// Constructor. Store arguments and initialise the map.
    public EchoServer( InetAddress address, int portnum ) throws IOException
    {
        addr = address;
        port = portnum;
        dataMap = new HashMap<SocketChannel,List<byte[]>>();
    }

    public void startServer() throws IOException
    {
        // Create selector and channel.
        selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        
        // Configure the channel to be non-blocking, and register it with the selector.
        serverChannel.configureBlocking( false );
        serverChannel.register( selector, SelectionKey.OP_ACCEPT );

        // Bind the channel to the port and address specified on construction.
        serverChannel.socket().bind( new InetSocketAddress(addr,port) );

		// Start the infinite server loop.
        System.out.println( "Echo server running; Ctrl-C to stop." );
        while( true )
        {
            // Wait for events.
            selector.select();

            // Wakeup to work on selected keys (returned as a java.util.Iterator).
            Iterator keys = selector.selectedKeys().iterator();
            while( keys.hasNext() )
            {
                SelectionKey key = (SelectionKey) keys.next();

				// Remove the key from the set so that we don't act on it twice.
                keys.remove();

				// Check the key is valid; skip if not.
				if( !key.isValid() ) continue;

				// Call private instance methods depending on nature of key.
                if( key.isAcceptable() )
                {
                    accept(key);
                }
                else if( key.isReadable() )
                {
                    read(key);
                }
                else if( key.isWritable() )
                {
                    write(key);
                }
            }
        }
    }

	//
	// Called when a registered channel (i.e. a key) is ready to accept (a client connection).
	// Creates a new channel ready for reading from the client, and registers with the selector.
	//
    private void accept( SelectionKey key ) throws IOException
    {
    	// Recover the server channel from key.
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        
        // Create a non-blocking channel for this client.
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);

        // Write a welcome message to the channel.
        channel.write( ByteBuffer.wrap("Welcome, this is a non-blocking EchoServer\r\n".getBytes()) );

		// Extract the socket for this client so we can output its address.
		// In a real server this would be logged; for this demo just print.
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println( "Connected to: " + remoteAddr );

        // Add this channel to the map, and register channel with selector for further IO; this time, reading.
        dataMap.put( channel, new ArrayList<byte[]>() );
        channel.register( selector, SelectionKey.OP_READ );
    }
    
    //
    // Called when a channel is ready to read. 
    //
    private void read( SelectionKey key ) throws IOException
    {
    	// Get the channel from the key.
        SocketChannel channel = (SocketChannel) key.channel();

		// Read in whatever the client sent (assumed to be less than 8192 bytes).
        ByteBuffer buffer = ByteBuffer.allocate( 8192 );
        int numRead = -1;
        try
        {
            numRead = channel.read(buffer);
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }

		// If nothing was read, assume the connection has been closed.
        if( numRead==-1 )
        {
        	// Remove this channel from the persistent store.
            dataMap.remove(channel);
            
            // Message to screen. Would normally be logged.
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            System.out.println( "Connection closed by client: " + remoteAddr );
            
            // Close the channel and remove it's registration from the selector.
            channel.close();
            key.cancel();

            return;
        }

		// Otherwise send message back to the client. Also for this demo, echo to screen.
		byte[] data = new byte[numRead];
        System.arraycopy( buffer.array(), 0, data, 0, numRead );

        System.out.println( "Got: " + new String(data) );

        doEcho( key, data );
    }

	//
	// Performs the echo of the required message.
	//
    private void doEcho( SelectionKey key, byte[] data ) 
    {
    	// Get the channel, and add to the data associated with this channel in the map.
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> pendingData = dataMap.get(channel);
        pendingData.add(data);

		// Say that we are now interested in the 'write' operation.
        key.interestOps( SelectionKey.OP_WRITE );
    }

	//
	// Writes whatever text is stored in the map for this channel 
	//
    private void write( SelectionKey key ) throws IOException
    {
    	// Get the data associated with this channel from the map; since this is a list of possibly
    	// more than one item, iterate over all of them (not necessary if the client only sends one
    	// message, as in our case, but necessary if clients send multiple messages and they all need
    	// to be echoed).
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> pendingData = dataMap.get(channel);
        Iterator<byte[]> items = pendingData.iterator();
        while( items.hasNext() )
        {
            byte[] item = items.next();
            items.remove();
            channel.write( ByteBuffer.wrap(item) );
        }
        
        // Go back to reading for this channel (i.e. more messages from the client to echo).
        key.interestOps( SelectionKey.OP_READ );
    }

	//
	// The 'null' here means localhost, and the port number must match that in the client.
	//
    public static void main( String[] args ) throws Exception
    {
        EchoServer echo = new EchoServer( null, 8989 );
        echo.startServer();
    }
}
