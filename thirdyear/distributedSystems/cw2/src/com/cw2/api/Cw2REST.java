/*
 * Created on 10 Sep 2013
 * Revised 17 Oct 2018
 *
 * Modified Karim Djemame code for my owm API's and
 * changed my own server
 */
package com.cw2.api;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.DocFlavor;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This will be ran when a GET request is called on port 9999 in the form of localhost:9999/api/location/<string>
 */
@Path("/api")
public class Cw2REST {
   long startTime = System.currentTimeMillis();
   TreeMap<String, String> map = new TreeMap<String, String>();

   /**
    * This function will call both API functions and call the APIs. It will utilise the String a as the parameter for
    * these functions. It will run my own API and then return a String for the function value. This will be returned in
    * a HTML format to the webpage. The runtime will be measured for my own API.
    * @param a This parameter will be whatever location the user enters into the URL for the browser. This will be
    *          then be used as an input for the first API.
    * @return It will return a string which will then be outputted onto the webpage for the user.
    */
   @GET
   @Path("/location/{a}")
   @Produces(MediaType.TEXT_HTML)
   public String addPlainText(@PathParam("a") String a) {
      invokeTimezone(invokeWeather(a));
      System.out.print("Runtime for my own: ");
      System.out.print(System.currentTimeMillis()-startTime + " milliseconds\n\n");
      return (value(a)) + "";
   }

   /**
    *This function will build the string which will then be outputted onto the webpage. It will read this from
    * a global treemap, which will have relevant data added to it in other functions.
    * @param city This will be a string, the location name which will be added to a treemap of data.
    * @return It will return a completed string which will have all the relevant data attached to it.
    */
   public String value(String city) {
      map.put("Location", "Location: " + city);
      StringBuilder f = new StringBuilder();
      for (String keys : map.values()) {
         f.append("<b>");
         f.append(keys);
         f.append("<b>");
         f.append("<br>");
      }
      return f.toString();
   }

   /**
    * This will take in a location name and utilise the openweathermap API to found all the relevant data about that
    * location, by parsing the JSON data. It will then add this to the treemap and print the runtime of the function.
    * @param city It will take the location and query the API with that location, then get the data for it.
    * @return It will return the longitude/latitude from the JSON data, This will go in the second API as an input.
    */
   public double[] invokeWeather(String city) {
      long startTime = System.currentTimeMillis();
      URL url;
      HttpURLConnection connection = null;
      InputStream is = null;
      double lat = 0.0;
      double lon = 0.0;

      try {
         System.out.println("API 1 Input \nLocation: " + city);
         String urlString = "http://api.openweathermap.org/data/2.5/weather?q="+ city +"&units=metric&appid=522807511d2b338ce7145c8142679743";
         System.out.println(urlString);

         //Gets the data from the API and then appends it to a string to be used to parse
         url = new URL(urlString);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");
         connection.connect();
         is = connection.getInputStream();
         BufferedReader theReader =
                 new BufferedReader(new InputStreamReader(is, "UTF-8"));

         StringBuilder responseStrBuilder = new StringBuilder();
         String reply;
         while ((reply = theReader.readLine()) != null) {
            responseStrBuilder.append(reply);
         }

         //Parses the JSON data and then adds it to the global treemap
         JSONObject obj = new JSONObject(responseStrBuilder.toString());
         JSONObject coord = obj.getJSONObject("coord");
         lat = coord.getDouble("lat");
         lon = coord.getDouble("lon");
         map.put("latitude","Latitude: " + lat);
         map.put("longitude","Longitude: " + lon);
         JSONObject main =obj.getJSONObject("main");
         map.put("temp","Temperature: " + main.getDouble("temp") + " degrees");
         map.put("tempmin","Minimum Temperature: " + main.getDouble("temp_min") + " degrees");
         map.put("tempmax","Maximum Temperature: " + main.getDouble("temp_max") + " degrees");
         map.put("pressure","Pressure: " + main.getDouble("pressure") + " hPa");
         map.put("humidity","Humidity: " + main.getDouble("humidity") + "%");
         JSONObject wind =obj.getJSONObject("wind");
         map.put("speed","Wind Speed: " + wind.getDouble("speed") + " metres/sec");
         map.put("speed","Wind Direction: " + wind.getInt("deg") + " deg");
         JSONObject sys =obj.getJSONObject("sys");
         Date sunrise = new Date(sys.getInt("sunrise") * 1000L);
         Date sunset = new Date(sys.getInt("sunset") * 1000L);
         SimpleDateFormat jdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss z");
         map.put("sunrise", "Sunrise: " + jdf.format(sunrise));
         map.put("sunset", "Sunset: " + jdf.format(sunset));
         JSONArray weather = obj.getJSONArray("weather");
         map.put("weathercondition","Weather Condition: " + weather.getJSONObject(0).getString("main"));

      } catch (Exception e) {
         e.printStackTrace();
      }
      //Prints out the runtime and returns the longitude and latitude
      System.out.print("Runtime for API 1: ");
      System.out.print(System.currentTimeMillis()-startTime + " milliseconds\n\n");
      return (new double[]{lat, lon});
   }

   /**
    *This function will take in the latitude and longitude and then utilise the timezonedb API to get relevant timezone
    * information regarding that latitude and longitude. It will then add this data to the Treemap and print the runtime
    * of that function.
    * @param latlong This is the latitude and longitude, taken as an input from invokeWeather function.
    */
   public void invokeTimezone(double[] latlong) {
      long startTime = System.currentTimeMillis();
      URL url;
      HttpURLConnection connection = null;
      InputStream is = null;

      try {
         System.out.println("API 2 Input \nLatitude: " + latlong[0] + " & Longitude: " + latlong[1]);
         String urlString2 = "http://api.timezonedb.com/v2.1/get-time-zone?key=SU2V4VPGYYB8&format=json&by=position&lng=" + latlong[1] + "&lat=" + latlong[0];
         System.out.println(urlString2);

         //Reads in the data and then adds it to one string to be parsed.
         url = new URL(urlString2);
         connection = (HttpURLConnection) url.openConnection();
         connection.setRequestMethod("GET");
         connection.connect();
         is = connection.getInputStream();
         BufferedReader theReader =
                 new BufferedReader(new InputStreamReader(is, "UTF-8"));

         String reply;
         StringBuilder responseStrBuilder = new StringBuilder();
         while ((reply = theReader.readLine()) != null) {
            responseStrBuilder.append(reply);
         }

         //Parses the JSON data and then puts it in the treemap.
         JSONObject obj = new JSONObject(responseStrBuilder.toString());
         map.put("country","Country: " + obj.getString("countryName"));
         map.put("zone","Timezone: " + obj.getString("zoneName"));
         map.put("abbreviation","Timezone Abbreviation: " + obj.getString("abbreviation"));
         map.put("nextabb","Next Timezone Abbreviation: " + obj.getString("nextAbbreviation"));
         Date date = new Date(obj.getInt("zoneStart") * 1000L);
         Date date2 = new Date(obj.getInt("zoneEnd") * 1000L);
         SimpleDateFormat jdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss z");
         map.put("datestart", "Timezone Start: " + jdf.format(date));
         map.put("dateend","Timezone End: " +  jdf.format(date2));

      } catch (Exception e) {
         e.printStackTrace();
      }
      //Prints the runtime
      System.out.print("Runtime for API 2: ");
      System.out.print(System.currentTimeMillis()-startTime + " milliseconds\n\n");
   }

}
