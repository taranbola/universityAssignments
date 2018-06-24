// Class for COMP2931 Coursework 1

package comp2931.cwk1;
import java.util.Calendar;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Simple representation of a date.
 */
public class Date {

  private int year;
  private int month;
  private int day;
  private int dayNumber;
  private boolean leapYear;

  /**
   * Creates a date using the given values for year, month and day.
   * It validates if a date is allowed in a leap year.
   * Doesn't allow invalid feb29 non-leap year date.
   *
   * @param y Year
   * @param m Month
   * @param d Day
   */
  public Date(int y, int m, int d) {
    year = y;
    month = m;
    day = d;
    if(year < 0){
      throw new IllegalArgumentException("Invalid Year");
    }

    if(month < 1 || month > 12){
      throw new IllegalArgumentException("Invalid Month");
    }

    if(day < 1 || day > 31 || month == 2 && day > 29){
      throw new IllegalArgumentException("Invalid Day");
    }

    if ((year % 4 == 0) && (year % 100 != 0) ||
      (year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0)) {
        leapYear = true;
    }
    else{
      leapYear = false;
    }

    if(month == 2 && day == 29 && !leapYear){
      throw new IllegalArgumentException("Invalid Day");
    }
  }
  /**
   * Creates an instance of todays date
   * It returns the current date
   */
  public Date(){
    Calendar cal = Calendar.getInstance();
    year = cal.get(Calendar.YEAR);
    month = cal.get(Calendar.MONTH)+1;
    day = cal.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * Returns the year component of this date.
   *
   * @return Year
   */
  public int getYear() {
    return year;
  }

  /**
   * Returns the month component of this date.
   *
   * @return Month
   */
  public int getMonth() {
    return month;
  }

  /**
   * Returns the day component of this date.
   *
   * @return Day
   */
  public int getDay() {
    return day;
  }
  /**
   * Provides a the dayNumber of the year(day 333).
   *
   * @return dayNumber
   */
  public int getDayOfYear() {
    int dayNumber = 0;
    int[] myList = {31,28,31,30,31,30,31,31,30,31,30,31};
    for (int i=0; i<(month-1); i++) {
      dayNumber += myList[i];
    }
    dayNumber += day;
    if(leapYear){
      dayNumber++;
    }
    return dayNumber;
  }

  /**
   * Provides a string representation of this date.
   *
   * ISO 8601 format is used (YYYY-MM-DD).
   *
   * @return Date as a string
   */
  @Override
  public String toString() {
    return String.format("%04d-%02d-%02d", year, month, day);
  }
  /**
   * Checks equality of 2 objects
   * Object other is one of the objects
   * @return   true/false if the objects are equal.
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      // 'other' is same object as this one!
      return true;
    }
    else if (! (other instanceof Date)) {
      // 'other' is not a Date object
      return false;
    }
    else {
      // Compare fields
      Date otherDate = (Date) other;
      return getYear() == otherDate.getYear()
          && getMonth() == otherDate.getMonth()
          && getDay() == otherDate.getDay();
    }
  }

}
