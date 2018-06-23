import java.util.ArrayList;
import java.io.File;                            //libraries needed in the code
import java.util.Scanner;
import java.io.FileNotFoundException;

class meanCalc{
    public static void main(String[] args)throws FileNotFoundException{
      Scanner input = new Scanner(new File(args[0]));   //takes in filename as a parameter
      ArrayList<Double> textNumbers = new ArrayList<>();  //float list
      double mean = 0.0;

      while (input.hasNextDouble()){
        Double currentValue = input.nextDouble();     //adds each value to the
        textNumbers.add(currentValue);                //...array from the file
      }

      for(double value : textNumbers){
        mean += value;
      }                                       //calculates the mean of the
      mean = mean/textNumbers.size();         //..values from the array
      System.out.printf("Mean of all numbers in the file is %.3f",mean);
    }
}
