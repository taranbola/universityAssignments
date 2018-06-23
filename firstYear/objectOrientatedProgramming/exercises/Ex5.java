import java.util.Scanner;							
class TempConversion {								
    public static void main(String[] args) {					
	Scanner input = new Scanner(System.in);
					
   	System.out.println("Celsius Fahrenheit ");												

	for(int Celsius=0; Celsius<101; Celsius+=2){
		double Fahrenheit = ((1.8*Celsius)+32);				//prints out a list of celcius to farenheit...
		System.out.printf("%d\t%.1f\n", Celsius,Fahrenheit); 		//from 0 - 100, incremented by 2
	}

   }
}
