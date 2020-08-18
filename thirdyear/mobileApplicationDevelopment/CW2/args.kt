import java.net.*
import java.util.*
import java.io.*

fun main(args: Array<String>) {
  //calls the argument handler function
  val argsent = argHandle(args)
  //checks if the values sent back are not empty, if they arent it will start
  //processing the arguments given
  if (argsent[0] != "" && argsent[1] != "") {
    processURL(argsent)
  }
  else{
    errormessage()
  }
}

fun argHandle(args: Array<String>): Array<String> {
  try{
    //args sent is an array of determined values that are sent back to the main
    val argsent = Array<String>(4) {""}
    if(args.size < 2) throw IllegalArgumentException("Not enough Parameters")
    if(args.size > 4) throw IllegalArgumentException("Too many Parameters")
    if (args.size == 2){
      //checks all the arguements are valid if there are 2
      argsent[0] = checkmag(args[0]);
      argsent[1] = checktime(args[1]);
    }

    if(args.size == 3){
      //checks all the arguements are valid if there are 3
      var c = 0
      try{hemisphere(args[0])}catch(e : IllegalArgumentException){c--}
      try{sort(args[0])}catch(e : IllegalArgumentException){c++}

      if (c == 1) argsent[2] = hemisphere(args[0])
      else if(c == -1) argsent[3] = sort(args[0])
      else throw IllegalArgumentException("First Aguement Invalid")

      argsent[0] = checkmag(args[1])
      argsent[1] = checktime(args[2])
    }

    if(args.size == 4){
      //checks all the arguements are valid if there are 4
      var c = 0
      try{hemisphere(args[0])}catch(e : IllegalArgumentException){c--}
      try{sort(args[0])}catch(e : IllegalArgumentException){c++}
      if (c == 1){
        argsent[3] = sort(args[1])
        argsent[2] = hemisphere(args[0])
      }
      else if(c == -1){
        argsent[2] = hemisphere(args[1])
        argsent[3] = sort(args[0])
      }
      else throw IllegalArgumentException("First and second invalid invalid")

      argsent[0] = checkmag(args[2])
      argsent[1] = checktime(args[3])
    }
    //if it reaches this it will send the correct array to the main
    return argsent
  }catch (e: IllegalArgumentException){
    val someStrings = Array<String>(2) {""}
    //returns an empty array if an error is caught
    return someStrings
  }
}

fun checkmag(mag: String): String = when (mag) {
  //this function checks all the values for magnitude or throws an exception
  in "all" -> "all_"
  in "1.0" -> "1.0_"
  in "2.5"  -> "2.5_"
  in "4.5"   -> "4.5_"
  in "significant"   -> "significant_"
  else       -> throw IllegalArgumentException("Invalid Magnitude Given")
}

fun checktime(time: String): String = when (time) {
  //this function checks all the values for time or throws an exception
  in "hour" -> "hour.csv"
  in "day"  -> "day.csv"
  in "week"   -> "week.csv"
  in "month"   -> "month.csv"
  else       -> throw IllegalArgumentException("Invalid Time Given")
}

fun hemisphere(hem: String): String = when (hem) {
  //this function checks all the values for hemisphere or throws an exception
  in "-n" -> "n"
  in "-s"  -> "s"
  else       -> throw IllegalArgumentException("Invalid Hemisphere Given")
}

fun sort(order: String): String = when (order) {
  //this function checks all the values for sort or throws an exception
  in "-d" -> "d"
  in "-m"  -> "m"
  else       -> throw IllegalArgumentException("Invalid Sorting Format Given")
}

fun errormessage(){
  //error message is thrown if any of the arguments are invalid
  println(
  """
  One of your arguments is incorrect.
  You must first put the possible options for the data. It will be -d ( Sort by depth ascending) or -m (Sort by magnitude descending).
  There also may be -n for northern hemisphere or -s for southern hemisphere.
  You then put the severity argument. These can be all, 1.0, 2.5, 4.5 and significant.
  You then put the time argument. These can be hour, day, week and month.
  An example would be -n -m significant day.
  This would give you the northern hemisphere significant earthquakes for today sorted by magnitude descending.
  """)
}
