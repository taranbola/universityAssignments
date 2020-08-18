import java.net.*
import java.util.*
import java.io.*

fun processURL(args: Array<String>) {
  val reader = Scanner(URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/" + args[0] + args[1]).openStream())
  var records : MutableList<Quake> = ArrayList()
  //this will read in from the url via the arguments given
  reader.nextLine().split(",")
  //the first line is saved for my headers of the table
  while (reader.hasNext()){
    val splitted = reader.nextLine().split(",")
    if(splitted[4] == "") continue
    else{
      if (args[2] != ""){
        //if the value is null, then it will only add southern/northern values to the table
        if(hemcheck(splitted[1].toDouble(),args[2]) == true){
          records.add(Quake(splitted[2].toDouble(),splitted[1].toDouble(),splitted[3].toDouble(),splitted[4].toDouble()))
          //adds to the MutableList of quakes
        }
        else{
          continue
        }
      }

      else{
        records.add(Quake(splitted[2].toDouble(),splitted[1].toDouble(),splitted[3].toDouble(),splitted[4].toDouble()))
      }
    }
  }
  if(args[3] != ""){
    if (args[3] == "d") records.sortBy{it.dep}
    if (args[3] == "m") records.sortByDescending{it.mag}
    //this will sort via the commands given, if a command is given
  }
  //calls to print the data
  printTable(records)
}

fun printTable(records : MutableList<Quake> = ArrayList()){
  //this functions purpose is to print out the table data
  println(("Longitude").padEnd(22) + ("Latitude").padEnd(22) + ("Depth").padEnd(12) + "Mag")
  for (i in 0..records.size -1){
    val q = records.get(i)
    println(q.lon.toString().padEnd(22) + q.lat.toString().padEnd(22) + q.dep.toString().padEnd(12) + q.mag)
    //this will print out each record in an easy to read format, at the top is the headers
  }
  println("\n${records.size} quakes")

  var depav = records.map { it->it.dep }.average()
  if (depav != depav){ depav = 0.0}
  println("Mean Depth = ${depav} km")
  //this will print the mean of the depth and will do 0.0 if it is NaN

  var magav = records.map { it->it.mag }.average()
  if (magav != magav){ magav = 0.0}
  println("Mean Magnitude = ${magav}")
  //this will print the mean of the Magnitude and will do 0.0 if it is NaN
}

fun hemcheck(n : Double , hem : String): Boolean{
  //this will check if a latitude is in the southern/hemisphere and returns
  //true or false if it is.
  if (hem.equals("n") && n >= 0.0){
    return true
  }
  else if(hem.equals("s") && n < 0.0){
    return true
  }
  else{
    return false
  }
}
