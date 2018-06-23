import java.util.Scanner;						//gets the scanner library to input data
class MilesToKM {							//name of the class

    public static void main(String[] args) {				//declares the function with arguements
    	System.out.println("Enter the number of miles below:");		//print statement asking to no. of miles
	Scanner input = new Scanner(System.in);				//input variable to input data

	double miles = input.nextInt();					//miles value which is the value of the input 
	miles = miles * 1.6;						//calculation that works out km of miles input						
	System.out.println(miles);					//prints the calculation value 
    }

}

