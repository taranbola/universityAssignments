public class Date implements Comparable<Date>{
  private int day;
  private int year;
  Month monthTest;

  public Date(int day1, int year1, Month monthTest1){
    day = day1;
    year = year1;                         //takes in a set of parameters
    monthTest = monthTest1;
  };

  public int getDay(){
    return day;
  }

  public int getYear(){                     //getters for each variable
    return year;
  }

  public Month getMonth(){
    return monthTest;
  }

  public int compareTo(Date other){
    int comp = Integer.compare(year, other.year);
    if (comp == 0){                                     //compares the values..
      comp = Integer.compare(day, other.day);           //outputs 0 if equal, -1
       if (comp == 0){                                  //if less and 1 if more
         comp = monthTest.compareTo(other.monthTest);
       }
    }
    return comp;
  }

  @Override
  public String toString(){           //prints out the date in a string format
    return day + " " + monthTest + " " + year;
  }

}
