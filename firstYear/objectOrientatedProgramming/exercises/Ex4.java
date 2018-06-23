import java.util.Scanner;							
class Quadratic {								
    public static void main(String[] args) {					
	Scanner input = new Scanner(System.in);			

    	System.out.println("Enter the a, b, and c values:");			
	int a = input.nextInt();						
    	int b = input.nextInt();						 
    	int c = input.nextInt();						
		
	double discriminant = ((b*b)-4*2*c);					//calculates the discriminant to see no. of roots

	if(discriminant >0) {
		System.out.println("The equation has 2 roots:");

		double PositiveRoot =(-b+(Math.sqrt(discriminant))/(2*a));	//if discriminant is positive it outputs...
		System.out.println("The positive root is " + PositiveRoot);	//2 roots

		double NegativeRoot =(-b-(Math.sqrt(discriminant))/(2*a)); 
		System.out.println("The Negative root is " + NegativeRoot);		
	}
	else if(discriminant ==0) {
		System.out.println("The equation has only one root:");		//if it is 0 it outputs 1 root
		
		double PositiveRoot =(-b+(Math.sqrt(discriminant))/(2*a));
		System.out.println("The root is " + PositiveRoot);	
	}
	else
		System.out.println("The equation has no roots");		//anything else there is no roots 			
	}
}
