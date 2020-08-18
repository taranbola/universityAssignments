===== GROUP 16 ROBOTICS PROJECT =====

MEMBERS:
	JOSEPH HANCOCK
	DANIEL FRYER
	EBEN BENTLEY
	TARAN BOLA

*** Turtlebot room searcher using wall following algorithm ***

RUN INSTRUCTIONS:

	- Open terminal window
	- Enter: singularity shell --nv /vol/scratch/ros/comp3631.img
	- Enter: xterm &
	- In xterm window enter: export TURTLEBOT_GAZEBO_WORLD_FILE="WORLD-FILE-LOCATION"
	- In same xterm window enter: roslaunch turtlebot_gazebo turtlebot_world.launch
	- Open new xterm window
	- In new xterm window enter: cd "LAUNCH-FOLDER-LOCATION"
	- In same xterm window enter: roslaunch simulated_localisation.launch map_file:="YAML-FILE-LOCATION"
	- Open new xterm window
	- In new xterm window enter: cd "LAUNCH-FOLDER-LOCATION"
	- In same xterm window enter: roslaunch ar_tracking.launch
	- Open new xterm window
	- In new xterm window enter: cd "LAUNCH-FOLDER-LOCATION"
	- In same xterm window enter: roslaunch turtlebot_rviz_launchers view_navigation.launch
	- Open new xterm window
	- In new xterm window enter: cd "PROJECT-SOURCE-FOLDER-LOCATION"

	- In Rviz window, localise the Turtlebot to position shown in world file
	- In Project source folder xterm window enter: chmod +x search.py
	- In same xterm window enter: ./search.py
	- Give program x + y coordinates

	Once program has completed, check detections folder for saved images and AR marker locations

DISCLAIMER:

	- Program will freeze in rare situations where the AR markers are too close to each other
	- If program fails to reach the end goal, please run again
	- If program will not run again, please close all processes and repeat run instructions