//
// "Hello world" the threads using the inheritance method.
//

import java.lang.Thread;

public class HelloT extends Thread {

	// Override run() [which isn't declared abstract in java.lang.Thread, but does nothing]
	public void run() {
		System.out.println( "Hello from a thread." );
	}
	
	// Create a single thread and start it. Note we can only schedule the execution,
	// when run() is actually called depends on the OS scheduler.
	public static void main( String args[] ) {
		HelloT t = new HelloT();
		t.start();
	}

}
