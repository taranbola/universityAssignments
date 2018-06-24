import java.net.*;
import java.io.*;

public class FileServer {

  public static void main (String[] args ) throws IOException {

    int bytesRead;
    int current = 0;

    ServerSocket serverSocket = null;
    serverSocket = new ServerSocket(8888);

    while(true) {
        Socket clientSocket = null;
        clientSocket = serverSocket.accept();

        InputStream in = clientSocket.getInputStream();

        // Writing the file to disk
        // Instantiating a new output stream object
        OutputStream output = new FileOutputStream("hello.txt");

        byte[] buffer = new byte[1024];
        while ((bytesRead = in.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        // Closing the FileOutputStream handle
        output.close();
    }
  }
}
