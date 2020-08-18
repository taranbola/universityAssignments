The commands needed are:
  kotlinc -include-runtime -d quake.jar args.kt process.kt quake.kt
  java -jar quake.jar ....

  You must first put the possible options for the data. It will be -d ( Sort by depth ascending) or -m (Sort by magnitude descending).
  There also may be -n for northern hemisphere or -s for southern hemisphere.
  You then put the severity argument. These can be all, 1.0, 2.5, 4.5 and significant.
  You then put the time argument. These can be hour, day, week and month.
  An example would be -n -m significant day.
  This would give you the northern hemisphere significant earthquakes for today sorted by magnitude descending.
