import java.net.*;								// For InetAddress and IOException.
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

/**
 * Created by Taranvir Bola
 * This function will allow a user to enter multiple addresses on one line via
 * the command line or the keyboard scanner. It will then check if it's real and
 * output relevant information about the address. If there are 2 or more ipv4
 * addresses it will compare them and see the Hierarchy of similarity between
 * them.
 */
public class cwk1{

	private static final String IPv4 = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	private static final String IPv6 = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

	private InetAddress inet = null;															//global variables and pattern for ipv4 and ipv6
  private Scanner readInput = null;
	private static List<String[]> listOfLists = new ArrayList<String[]>();

	public cwk1(){
		readInput = new Scanner( System.in );				//constructor to initialuse readInput
	}

	public void output(String host) throws IOException{
		Pattern ipv4 = Pattern.compile(IPv4);									//This will print out all the address info
		inet = InetAddress.getByName( host );							    //It will call relevant functions with an instance
																													//of InetAddress. It adds to a list of addressses if ipv4.

		System.out.println( "IP Address: " + inet.getHostAddress() );
		System.out.println( "Host name : " + inet.getHostName() );
		System.out.println( "Canonical Hostname: " + inet.getCanonicalHostName() );

		if(inet.isReachable(1000)){
			System.out.printf("Given IP address: Could be Reached%n");
			}
    else{
			System.out.printf("Given IP address: Could not be Reached%n");
		}

	  if(ipv4.matcher(inet.getHostAddress()).matches()) {
	   System.out.println("Given IP Address: Is IPv4");
		 String[] splitAddress = inet.getHostAddress().split("\\.");
		 listOfLists.add(splitAddress);
	  }
		else if(isIPv6(host) == true){
	   System.out.println("Given IP Address: Is IPv6");
		}

	}

	public void findHierarchy(){
		String[] firstValue = listOfLists.get(0);
		String[] secondAfterValue;
		for (int index = 0; index < 4; index++ ){
			for (int listValue = 1; listValue < listOfLists.size(); listValue++ ){
				secondAfterValue = listOfLists.get(listValue);
				if( !( firstValue[index].equals(secondAfterValue[index]) ) ){
					if(index == 0){
						System.out.println("*.*.*.*  - No Hierarchy");
						return;
					}
					printHierarchy(index);					//This will find the Hierarchy of the addresses.
					return;													//It uses first value compared against all
				}																	//the other values. If it has a Hierarchy it runs
			}																		//printHierarchy.
		}
		printHierarchy(4);
	}

	public void printHierarchy(int branchIndex){
		String[] array  = listOfLists.get(0);
		for (int index = 0; index < 4; index++) {				//This will take in the branchindex
			if(index < branchIndex){											//,this is where the value changes
				System.out.print(array[index]);							// on the 2 addresses. It then prints
			}																							//the Hierarchy.
			else{
				System.out.print("*");
			}
			if(index < 3){
				System.out.print(".");
			}
		}
		System.out.println("\n");
	}

  public void continuousReadInput() {
		System.out.println( "\nIn continuous scan mode: Start typing...." );
		while( true ) {
			System.out.print( "*****************************\n" );
			int counter = 0;
			String userInput = readInput.nextLine();								//This will allow user to continuously type
			if(userInput.length() == 0){}														//addresses and will catch exceptions. For
			else{																									  //each of the inputs it will call the output
				String[] inputList = userInput.split(" ");						//function and will run findHierarchy at
				for( String eachInput : inputList ){									//the end.
					try{
						System.out.print("\n");;
						output(eachInput);
						if(isIPv6(eachInput) == false){
							counter++;
						}
					}
					catch(IOException e){
					  System.out.println(eachInput + ": Unknown IP or Domain Name Address");
					}
	      }
				System.out.println();
				if (counter > 1){
						findHierarchy();
				}
				listOfLists.clear();
		 }
		}
	}

	public boolean isIPv6(String host)throws IOException{
		Pattern ipv6 = Pattern.compile(IPv6);												//this function will see
		inet = InetAddress.getByName( host );												//if it is an ipv6 and
		if (ipv6.matcher(inet.getHostAddress()).matches()) {				//output a true value if so.
	   return true;
	  }
		return false;
	}

	public static void main( String[] args ) {
		cwk1 instance = new cwk1();
		int counter = 0;
		for( String arguement : args){					//This will go through the args
			try{																	//it will then run the output function
				System.out.println();								//on it and catch exceptions.
				instance.output(arguement);
				if(instance.isIPv6(arguement) == false){
					counter++;
				}
			}
			catch(IOException e){
				System.out.println(arguement + ": Unknown IP or Domain Name Address");
			}
		}
		if(counter > 1){
			System.out.println();							//This will run the findHierarchy
			instance.findHierarchy();					//and clear the lists at the end.
			listOfLists.clear();
		}
    instance.continuousReadInput();
	}

}
