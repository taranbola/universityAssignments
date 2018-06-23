import java.util.Scanner;							//gets the scanner library to input data
class DistanceCalc {								//name of the class
    public static void main(String[] args) {					//declares the function with arguements
	Scanner input = new Scanner(System.in);					//input variable to input data

    	System.out.println("Enter the x1 and y1 values:");			//print statement asking to enter x1,y1
	int x1 = input.nextInt();						//x1 value which is the value of the input 
    	int y1 = input.nextInt();						//y1 value which is the value of the next input 

	System.out.println("Enter the x2 and y2 values:");			//print statement asking to enter x2,y2
	int x2 = input.nextInt();						//x1 value which is the value of the input
    	int y2 = input.nextInt();						//y2 value which is the value of the next input
	
	double distance = Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));	//calculates the distance then square roots it
	System.out.println("Distance = "+ distance);				//prints the value of the distance
    }
}
