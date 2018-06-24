//
// "Hello world" the threads using the interface method.
//

import java.lang.*;			// For Thread and Runnable

public class HelloI implements Runnable {

	// Define the interface method run()
	public void run() {
		System.out.println( "Hello from a thread." );
	}
	
	// Create a single thread with our Runnable object and start it.
	public static void main( String args[] ) {
		HelloI h = new HelloI();
		Thread t = new Thread(h);
		t.start();
	}

}
