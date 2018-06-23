  /**
  * @author Taranvir Bola
  */
import java.util.*;
/**
 * <p>This will take in a line of a file and then use this to calculate
 * variables.It will use this information and check this to make sure that
 * it is in the correct format using compareTo, equals and hashcode.</p>
 */
public class WeatherRecord implements Comparable<WeatherRecord>{
  private int year;
  private int month;
  private double maxTemp;
  private double minTemp;
  private int frostDays;
  private double rainfall;
  private double sunHours;

  /**
  * WeatherRecord will trap details of a record of the file
  * @param  String line will take a line of the file in as a parameter
  * @return    It will not return anything but will change the global
  *            variables to values that are in the line of the file.
  */
  public WeatherRecord(String line){
    Scanner numberOfLines = new Scanner(line).useDelimiter("\\s+");

    int count = 0;
    while (numberOfLines.hasNext()) {
      count++;
      numberOfLines.next();
    }
    if(count == 7){
      Scanner splitted = new Scanner(line).useDelimiter("\\s+");

      year = (splitted.nextInt());
      month = (splitted.nextInt());
      maxTemp = (splitted.nextDouble());
      minTemp = (splitted.nextDouble());
      frostDays = (splitted.nextInt());
      rainfall = (splitted.nextDouble());
      sunHours = (splitted.nextDouble());
    }
    else{
      throw new InputMismatchException("Line of file doesn't contain enough values");
    }
  };

  /**
  * getMonthName will work out the String equivelent of the number of the month
  * @return It will return a string of the month, from January til December
  */
  public String getMonthName(){
        String monthString;
            switch (month) {
                case 1:  monthString = "January";
                         break;
                case 2:  monthString = "February";
                         break;
                case 3:  monthString = "March";
                         break;
                case 4:  monthString = "April";
                         break;
                case 5:  monthString = "May";
                         break;
                case 6:  monthString = "June";
                         break;
                case 7:  monthString = "July";
                         break;
                case 8:  monthString = "August";
                         break;
                case 9:  monthString = "September";
                         break;
                case 10: monthString = "October";
                         break;
                case 11: monthString = "November";
                         break;
                case 12: monthString = "December";
                         break;
                default: monthString = "Invalid month";
                         break;
            }
            return monthString;
  };

  /**
   * equals Equals will check that the variables
   * @param  Object other is being compared to each global variable
   * @return     will return true if values are equal, else false
   */
  @Override
  public boolean equals(Object other){
    if(other == this){
      return true;
    }
    else if(!(other instanceof WeatherRecord )){
      return false; //other object isn't an instance of **** so it cannot be equal
    }
    else{
      WeatherRecord otherWeatherRecord = (WeatherRecord) other;
      return year == otherWeatherRecord.year &&
             month == otherWeatherRecord.month &&
             maxTemp == otherWeatherRecord.maxTemp &&
             minTemp == otherWeatherRecord.minTemp &&
             frostDays == otherWeatherRecord.frostDays &&
             rainfall == otherWeatherRecord.rainfall &&
             sunHours == otherWeatherRecord.sunHours;
    }
  }

  /**
   * hashCode will create a value for hashcode and make it for the value equivelent
   * @return It returns the value of that hashCode, unique key only for this value
   */
  @Override
  public int hashCode(){
    int result = 14;
    result = 31 * result + year;
    result = 31 * result + month;
    result = 31 * result + (int)maxTemp;
    result = 31 * result + (int)minTemp;
    result = 31 * result + frostDays;
    result = 31 * result + (int)rainfall;
    result = 31 * result + (int)sunHours;
    return result;
  }

  /**
   * compareTo will compare an instance of year/month and see's if it's the same
   * @param  WeatherRecord other will be an instance of year/month
   * @return  will return true/false whether the comparison is the same
   */
  public int compareTo(WeatherRecord other){
    int comp = Integer.compare(year, other.year);
    if (comp == 0){
      comp = Integer.compare(month, other.month);
    }
    return comp;
  }

  /**
   * getYear
   * @return will return the integer of year
   */
  public int getYear(){
     return year;
  }

  /**
   * getMonth
   * @return will return the integer of the month
   */
  public int getMonth(){
    return month;
  }

  /**
   * getMaxTemp
   * @return will return the double/float of the maximum temperature
   */
  public double getMaxTemp(){
     return maxTemp;
  }

  /**
   * getMinTemp
   * @return  will return the double/float of the minimum temperature
   */
  public double getMinTemp(){
     return minTemp;
  }

  /**
   * getFrostDays
   * @return will return the no. of days where there was frost on the ground
   */
  public int getFrostDays(){
    return frostDays;
  }

  /**
   * getRainfall
   * @return will return the rainfall for that month
   */
  public double getRainfall(){
    return rainfall;
  }

  /**
   * getSunHours
   * @return will return the number of sun hours for a given month
   */
  public double getSunHours(){
    return sunHours;
  }

}
