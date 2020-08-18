#!/usr/bin/env python
from __future__ import division

import cv2
import numpy as np
import sys
import roslib
import rospy
import tf
import math
import time
import actionlib

from ar_track_alvar_msgs.msg import AlvarMarkers
from move_base_msgs.msg import *
from actionlib_msgs.msg import *
from geometry_msgs.msg import Twist, Vector3, Pose, Point, Quaternion
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError
from kobuki_msgs.msg import BumperEvent
import contour

class Position():
    def __init__(self):
        # setting global variables
        self.bump = False
        self.counter = 0
        self.result = False

        self.sub = rospy.Subscriber('/ar_pose_marker', AlvarMarkers, self.Callback)
        rospy.Subscriber('/mobile_base/events/bumper', BumperEvent, self.Bumped)

    def Bumped(self, BumperEvent):
        # actual control of the bumper in taken care of navigation, not here
        # this is here just in case we bump while positioning, which is very unliekly
        self.bump = True
        rospy.loginfo("bumped object, reverse and stop!")

    def Callback(self, data, storedAr):
        self.move_base = actionlib.SimpleActionClient("move_base", MoveBaseAction)
        self.move_base.wait_for_server(rospy.Duration(5))
        try:
            if self.bump == True:
                desired_velocity.linear.x = -0.2 # Backward with 0.2 m/sec.
                for i in range(30):
                    pub.publish(desired_velocity)
                    rate.sleep()
            else:
                # Create a tf listener
                listener = tf.TransformListener()
                # Sleeping for 3 second to listen to tf messages.
                time.sleep(3.0)
                # Returns the transform from source_frame to target_frame at the time time.
                # time is a rospy.Time instance. The transform is returned as position (x, y, z)
                # and an orientation quaternion (x, y, z, w)
                (trans,rot) = listener.lookupTransform('/map', '/ar_marker_0', rospy.Time(0))
                matrix = listener.fromTranslationRotation(trans, rot)
                # we use the matrix to find what position we should move to in relation
                # to the ar image we have found.
                direction = matrix[:3 , 2]
                pose = trans + direction * 0.4
                theta_direction = direction * -1

                theta_direction1 = theta_direction / (theta_direction[0] ** 2 + theta_direction[1] ** 2) ** 0.5
                theta = math.atan2(theta_direction1[1], theta_direction1[0])
                position = {'x': float(str(pose[0])[:5]), 'y' : float(str(pose[1])[:5])}
                quat = {'r1' : 0.000, 'r2' : 0.000, 'r3' : np.sin(theta/2.0), 'r4' : np.cos(theta/2.0)}
                while self.result != True:
                    SendBotSuccess = self.goto(position, quat)
                if storedAr == False:
                    storedAr =
        except (tf.LookupException, tf.ConnectivityException, tf.ExtrapolationException):
            pass

    def goto(self, pos, quat):
            # Send a goal
        self.goal_sent = True
        goal = MoveBaseGoal()
        goal.target_pose.header.frame_id = 'map'
        goal.target_pose.header.stamp = rospy.Time.now()
        goal.target_pose.pose = Pose(Point(pos['x'], pos['y'], 0.000),Quaternion(quat['r1'], quat['r2'], quat['r3'], quat['r4']))

        # Start moving
        self.move_base.send_goal(goal)

        # Allow TurtleBot up to 60 seconds to complete task
        success = self.move_base.wait_for_result(rospy.Duration(60))
        state = self.move_base.get_state()
        while success and state == GoalStatus.SUCCEEDED:
            # We made it!
            self.result = True
            while not rospy.is_shutdown():
                contour.contourIdentifier()
                rospy.loginfo("waiting 3 seconds for image processing and then shutting down ar")
                rospy.sleep(3)
                shutdown()
        else:
            self.move_base.cancel_goal()
        self.goal_sent = False
        return self.result
def main(args):
	# Instantiating the class
	# rospy.init the entire node
	rospy.init_node('Position', anonymous=True)
	cI = Position()
	# Ensure that the node continues running with rospy.spin()
	rospy.spin()
# a shutdown to ensure that the nodes are only run once, we do not need it to carry
# on going towards an ar marker once we have found and positioned to it.
def shutdown():
    rospy.loginfo("Positioned and image taken")
    rospy.signal_shutdown("AR job complete")

if __name__ == '__main__':
    	main(sys.argv)
