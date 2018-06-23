import java.util.*;
import java.io.*;

public class WeatherInfo{
  public static void main(String[] args) throws FileNotFoundException{

    try{
      WeatherStation weatherStation1 = new WeatherStation(args[0]);

      //Outputs the name of the Station
      System.out.println("Name: " + weatherStation1.getName());

      //Outputs the location
      Location location2 = weatherStation1.getLocation();
      System.out.println("Location: " + location2.toString());

      //Outputs the number of records
      System.out.println(weatherStation1.getRecordCount()
      + " months of data available");

      WeatherRecord weatherRecord3 = weatherStation1.findSunniestMonth();

      //This will output the sunniest month record
      System.out.println("Sunniest month: " + weatherRecord3.getMonthName() + " "
       + weatherRecord3.getYear() +
       " (" + weatherRecord3.getSunHours() + " hours)" );

      //This will output the mean max temp
      System.out.printf("Mean max temp in August: %.1f deg C\n",
      weatherStation1.meanMaxTemp(8));

      //This will output the wettest year and it's value
      System.out.printf( "Wettest year: %d (%.1f mm)\n",weatherStation1.
      findWettestYear(),weatherStation1.totalRainfall
      (weatherStation1.findWettestYear()) );

      //This will output the driest year and it's value.
      System.out.printf( "Driest year: %d (%.1f mm)\n",
      weatherStation1.findDriestYear(),weatherStation1.totalRainfall
      (weatherStation1.findDriestYear()) );
    }

    //If there is no file inputted prints this...
    catch (FileNotFoundException e){
      System.out.println("Error: cannot access input file");
    }

    //Any other exception and this will be printed. 
    catch (Exception e){
      System.out.println("Usage: java WeatherInfo <filename>");
    }

  }

}
