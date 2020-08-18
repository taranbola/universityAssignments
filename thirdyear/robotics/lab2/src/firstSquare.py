#!/usr/bin/env python

import rospy
from geometry_msgs.msg import Twist

def publisher():
    pub = rospy.Publisher('mobile_base/commands/velocity', Twist)
    rospy.init_node('Walker', anonymous=True)
    rate = rospy.Rate(10) #10hz
    desired_velocity = Twist()
    while not rospy.is_shutdown():
        desired_velocity.angular.z = 0 # turn with 0 m/sec.
        desired_velocity.linear.x = 0.2 # Forward with 0.2 m/sec.
        for i in range (52):
            pub.publish(desired_velocity)
            rate.sleep()
        desired_velocity.linear.x = 0 # Forward with 0 m/sec.
        desired_velocity.angular.z = 0.2 # turn with 0.2 m/sec.
        for i in range(80):
            pub.publish(desired_velocity)
            rate.sleep()

if __name__ == "__main__":
	try:
		publisher()
	except rospy.ROSInterruptException:
		pass
