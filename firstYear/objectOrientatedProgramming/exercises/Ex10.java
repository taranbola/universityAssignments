import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class fileReading{
    public static void main(String[] args)throws FileNotFoundException{
      Scanner input = new Scanner(new File("intValues.txt"));
      int numberOfLines = 10;
      int[] numbers = new int[numberOfLines];
      int i = 0;

      System.out.println("\nNumbers in Empty Array:");
      for(i =0; i < numberOfLines; i++){        //dsiplays the current empty
        System.out.println(numbers[i]);         //..array
      }

      System.out.println("\nNumbers in Array from File:");
      i = 0;
      while (input.hasNextInt()){
        numbers[i] = input.nextInt();
        System.out.println(numbers[i]);     //prints the array from the file
        i++;
      }
      input.close();

      Arrays.sort(numbers);
      System.out.println("\nNumbers Sorted:");    //sorts the values of the
      for(i =0; i < numberOfLines; i++){          //.. the array in
        System.out.println(numbers[i]);           //ascending order and prints.
      }
    }
}
