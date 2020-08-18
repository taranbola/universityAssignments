#!/usr/bin/env python

import rospy
from geometry_msgs.msg import Twist

def publisher():
    	pub = rospy.Publisher('mobile_base/commands/velocity', Twist)
    	rospy.init_node('Walker', anonymous=True)
    	rate = rospy.Rate(10) #10hz
    	desired_velocity = Twist()
    	desired_velocity.linear.x = 0.2 # Forward with 0.2 m/sec.
    	# for i in range (30):
    	# 	pub.publish(desired_velocity)
    	# 	rate.sleep()
    	desired_velocity.angular.z = 0.4 # Backward with 0.2 m/sec.
    	# for i in range(30):
    	while not rospy.is_shutdown():
    		pub.publish(desired_velocity)

if __name__ == "__main__":
	try:
		publisher()
	except rospy.ROSInterruptException:
		pass

