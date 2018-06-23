import java.util.ArrayList;
import java.util.Collections;

public class Ex15{
    public static void main(String[] args) {
      ArrayList<Date> listOfDates = new ArrayList<>();

      listOfDates.add(new Date(22,2011,Month.DECEMBER));
      listOfDates.add(new Date(2,1997,Month.JANUARY));
      listOfDates.add(new Date(11,2017,Month.APRIL));
      listOfDates.add(new Date(2,1989,Month.MARCH));       //adds the values
      listOfDates.add(new Date(11,1918,Month.NOVEMBER));   //to the arraylist
      listOfDates.add(new Date(15,2032,Month.MAY));
      listOfDates.add(new Date(19,1992,Month.JANUARY));
      listOfDates.add(new Date(1,2000,Month.NOVEMBER));

      System.out.println("\nUnsorted List:");
      for(int i = 0; i < listOfDates.size(); i++) {     //prints the unsorted list
        System.out.print(listOfDates.get(i) + "\n");
      }

      Collections.sort(listOfDates);          //calls the compareTo in Month.java
                                              //puts it in the .sort function
      System.out.println("\nSorted List:");
      for(int i = 0; i < listOfDates.size(); i++) {
        System.out.print(listOfDates.get(i) + "\n");    //prints sorted list
      }

    }
}
