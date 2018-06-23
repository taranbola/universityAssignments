import java.util.Scanner;						//gets the scanner library to input data
class Minutes {								//name of the class

    public static void main(String[] args) {				//declares the function with arguements
    	System.out.println("Enter the number of minutes below:");	//print statemtns that asks for no. of mins
	Scanner input = new Scanner(System.in);				//input variable to input data

	int minutes = input.nextInt();					//variable mins which is the value of the input
	int days = minutes /1440;					//uses input to calculate no. of days
	int years = days /365;						//uses input to calculate no. of years
	days %= 365;							//

	System.out.println("Years = "+ years + ", Days = " + days);	//prints no. of days and years
    }

}

