/**
* @author Taranvir Bola
*/
import java.util.*;
import java.io.*;

/**
 *<p> This class will read a file, use the content to calculate a
 *number of different things. It will have getters to output the global variable
 *characteristics. </p> 
 */
public class WeatherStation {
  private String name;
  private Location location;
  private ArrayList<WeatherRecord> linesOfFile = new ArrayList<>();

  /**
   * WeatherStation Reads a file and uses the content in the code.
   * @param The parameter will take in a filename it will read
   * @return It won't return anything, but will use much of the files content to
   *            put in a global ArrayList
   * @throws It will throw a file not found Exception if there is no file inputted
   */
  public WeatherStation (String filename)throws FileNotFoundException{
    Scanner input = new Scanner(new File(filename));
    name = input.nextLine();
    location = new Location(input.nextLine());

    input.nextLine();
    input.nextLine();

    while (input.hasNextLine()) {
      WeatherRecord r = new WeatherRecord(input.nextLine());
      linesOfFile.add(r);
    }
  };

  /**
   * getName
   * @return Returns the name of the file
   */
  public String getName(){ return name; };

  /**
   * getLocation
   * @return Returns the location of the given records.
   */
  public Location getLocation(){ return location; };

  /**
   * getRecordCount
   * @return Returns the size of all the records in the ArrayList
   */
  public int getRecordCount(){ return linesOfFile.size(); };

  /**
   * getRecord
   * @param  An integer that will be an index of a record
   * @return It will return the record of the given index
   */
  public WeatherRecord getRecord(int index){
    return linesOfFile.get(index);
  };

  /**
   * findSunniestMonth It will found the sunniest month in all of the records
   *                    in the ArrayList.
   * @return It will return the record of the sunniestMonth in the record
   */
  public WeatherRecord findSunniestMonth(){
    WeatherRecord sunniestMonth = linesOfFile.get(0);
    double currentSunniestMonth = 0;

    for(int value = 0; value < linesOfFile.size(); value++){
      WeatherRecord weatherRecord1 = linesOfFile.get(value);
      double currentMonthValue = weatherRecord1.getSunHours();
      if(currentMonthValue > currentSunniestMonth ){
        WeatherRecord temp = linesOfFile.get(value);
        sunniestMonth = temp;
        currentSunniestMonth = currentMonthValue;
      }
    }
    return sunniestMonth;
  };

  /**
   * findWettestYear It'll found the record which will had the highest rainfall
   * @return It will return the record with the highest rainfall
   */
  public int findWettestYear(){
    int maxYear = 0;
    double tempYearValue = 0;
    double yearValue = 0;
    for(int value =0; value < linesOfFile.size(); value++){
      WeatherRecord weatherRecord4 = linesOfFile.get(value);
      tempYearValue += weatherRecord4.getRainfall();
      if(weatherRecord4.getMonth() == 12 ){
         if(yearValue < tempYearValue){
           yearValue = tempYearValue;
           maxYear = weatherRecord4.getYear();
         }
      tempYearValue = 0;
      }
    }
      return maxYear;
  };

  /**
   * findDriestYear It'll calculate the record with the lowest rainfall
   * @return  It'll return the record with the lowest rainfall
   */
  public int findDriestYear(){
      int maxYear = 0;
      double tempYearValue = 0;
      double yearValue = 10000;
      for(int value =0; value < linesOfFile.size(); value++){
        WeatherRecord weatherRecord5 = linesOfFile.get(value);
        tempYearValue += weatherRecord5.getRainfall();
        if(weatherRecord5.getMonth() == 12 ){
           if(yearValue > tempYearValue){
                        yearValue = tempYearValue;
             maxYear = weatherRecord5.getYear();
           }
        tempYearValue = 0;
        }
      }
      return maxYear;
  };

  /**
   * [meanMaxTemp It will return the average of the maxTemp in the records for a month
   * @param  int month This is a month to work out the average for.
   * @return  It will return the average for a given month
   */
  public double meanMaxTemp(int month){
    double averageMonth = 0.0;
    double monthCounter = 0.0;

    for(int value =0; value < linesOfFile.size(); value++){
      WeatherRecord weatherRecord2 = linesOfFile.get(value);
      if(weatherRecord2.getMonth() == month ){
         averageMonth += weatherRecord2.getMaxTemp();
         monthCounter++;
      }
    }
    averageMonth = averageMonth/monthCounter;
    return averageMonth;
  };

  /**
   * totalRainfall It works out the rainfall for a given year.
   * @param  int year  The year that we will calculate it for.
   * @return  It will return the total rainfall for that year.
   */
  public double totalRainfall(int year){
    double totalRainfallValue = 0;
    for(int value = 0; value < linesOfFile.size(); value++){
        WeatherRecord weatherRecord6 = linesOfFile.get(value);
        if(weatherRecord6.getYear() == year ){
          totalRainfallValue += weatherRecord6.getRainfall();
        }
    }
    return totalRainfallValue;
  };

}
