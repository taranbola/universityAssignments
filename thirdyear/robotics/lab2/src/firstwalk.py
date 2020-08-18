#!/usr/bin/env python

import rospy
from geometry_msgs.msg import Twist
from kobuki_msgs.msg import BumperEvent
bump = False

def callback(BumperEvent):
    global bump
    bump = True
    rospy.loginfo("bumped object, reverse and stop!")


def publisher():
    global bump
    pub = rospy.Publisher('mobile_base/commands/velocity', Twist, queue_size = 10)
    rospy.Subscriber('/mobile_base/events/bumper', BumperEvent, callback)
    rospy.init_node('Walker', anonymous=True)
    rate = rospy.Rate(10) #10hz
    desired_velocity = Twist()
    desired_velocity.linear.x = 0.2 # Forward with 0.2 m/sec.
    while not rospy.is_shutdown():
        pub.publish(desired_velocity)
        if bump == True:
            desired_velocity.linear.x = -0.2 # Backward with 0.2 m/sec.
            for i in range(30):
                pub.publish(desired_velocity)
                rate.sleep()
            break;

if __name__ == "__main__":
	try:
		publisher()
	except rospy.ROSInterruptException:
		pass
