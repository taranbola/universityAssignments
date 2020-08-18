#!/usr/bin/env python
from __future__ import division
import rospy, time
from kobuki_msgs.msg import BumperEvent
from std_msgs.msg import Header
from sensor_msgs.msg import LaserScan
from nav_msgs.msg import Odometry
from ar_track_alvar_msgs.msg import AlvarMarkers
import cv2
import numpy as np
import sys
import roslib
import tf
import math
import time
import actionlib
from move_base_msgs.msg import *
from actionlib_msgs.msg import *
from geometry_msgs.msg import Twist, Vector3, Pose, Point, Quaternion
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError
import contour
import subprocess as sp

class Searcher():

    def __init__(self):

        # initialise variable to store LaserScan data
        self.scan_stream_data = None
        self.scan_odometry_data = None
        self.rate = rospy.Rate(10)
        # subscribe to BumperEvent, LaserScan & Odometry
        self.bumper = rospy.Subscriber('/mobile_base/events/bumper', BumperEvent, self.bumper_event)
        self.laser_scan = rospy.Subscriber('/scan', LaserScan, self.scan_stream)
        self.odom = rospy.Subscriber('/odom', Odometry, self.scan_odometry)
        # create movement publisher
        self.move = rospy.Publisher('mobile_base/commands/velocity', Twist, queue_size = 10)
        self.wall_location = 'no wall location'
        self.result = False
        # laser scan distance thresholds
        self.thresh = 1
        self.adjust_thresh = 0.65
        # counter for number of ranges within threshold for each section
        self.left = 0
        self.right = 0
        self.mid = 0
        # movement command
        self.move_now = Twist()
        # bump checker
        self.bump = False
        # bump counter
        self.bump_counter = 0
        # obstacle checkers
        self.stuck = False
        # number of ranges per section
        self.right_section = 213
        self.middle_section = 426
        self.left_section = 639
        # AR markers found/not found
        self.StoredAr1 = False
        self.StoredAr2 = False
        # transform listener
        self.listener = tf.TransformListener()

        self.x = ''
        self.y = ''

        # AR x,y positions
        self.ArPositionStore = []
        self.working=False

        # create fresh file if it doesnt exist
        locations = open("detections/locations.txt", "w")
        locations.close()

        self.go()
        # subscribe to AR marker detection
        self.sub = rospy.Subscriber('/ar_pose_marker', AlvarMarkers, self.Callback)


    # function to do a 360 degree(ish) degree spin
    def spin_360(self):
        run = True
        spinTime = time.time() + 6
        while time.time() < spinTime:
            self.move_now.angular.z = 1.0
            self.move_now.linear.x = 0
            self.move.publish(self.move_now)
            self.rate.sleep()

    # function to check for Bumper Events
    def bumper_event(self, BumperEvent):
        self.bump = True
        rospy.loginfo("bumped object, reverse and stop!")
        print('bump event')

    # function to update scan_stream_data variable with latest Laser Scan data
    def scan_stream(self, scan):
        self.scan_stream_data = scan

    # function to return the latest Laser Scan data ranges at the point of request
    def latest_scan(self):
        return self.scan_stream_data.ranges

    # function to update scan_odometry_data variable with latest Odometry data (unused)
    def scan_odometry(self, scan):
        self.scan_odometry_data = scan

    # function to return latest Odometry data at the point of request (unused)
    def latest_odometry(self):
        return self.scan_odometry_data

    # function to reset variables storing number of ranges recorded
    def reset_counters(self):
        self.left = 0
        self.right = 0
        self.mid = 0

    # function to do minor obstacle correction (adjusts course slightly)
    def obstacle_correction(self):
        print('ob')
        # if more ranges are detected on the right than left turn left
        if self.right > self.left:
            self.bump_counter += 1
            spinTime = time.time() + 0.5
            while time.time() < spinTime:
                self.move_now.angular.z = 0.4
                self.move_now.linear.x = 0
                self.move.publish(self.move_now)
                self.rate.sleep()
            self.navigate(self.latest_scan())
        # else turn right
        else:
            self.bump_counter += 1
            spinTime = time.time() + 0.5
            while time.time() < spinTime:
                self.move_now.angular.z = -0.4
                self.move_now.linear.x = 0
                self.move.publish(self.move_now)
                self.rate.sleep()
            self.navigate(self.latest_scan())

    # function to recover
    def recover(self):
        print('recovering')
    # function to increase section counters based on number of Laser Scan ranges within threshold
    def get_follow_points(self, latest_scan, thresh):
        for x in range(len(latest_scan)):
            if latest_scan[x] < thresh:
                if x < self.right_section:
                    self.right += 1
                elif x > self.right_section and x < self.middle_section:
                    self.mid += 1
                elif x > self.middle_section and x < self.left_section:
                    self.left += 1

    # starts the movement portion of the program
    def go(self):
        # request x, y coordinates
        self.x = float(input("Enter x value "))
        self.y = float(input("Enter y value "))
        theta = 0
        position = {'x': self.x, 'y' : self.y}
        quat = {'r1' : 0.000, 'r2' : 0.000, 'r3' : np.sin(theta/2.0), 'r4' : np.cos(theta/2.0)}

        # call goto with provided coordinates
        self.goto(position, quat)
        time.sleep(3)
        # call spin_360() once arrived
        self.spin_360()
        # after spinning, navigate using latest scan data
        self.navigate(self.latest_scan())

    # function to save coordinates to file
    def save_coordinates(self):
        print('nothing')

    def Callback(self, data):
        if(self.working==False):
            self.working=True
            try:
                # Create a tf listener
                # Sleeping for 3 second to listen to tf messages.
                # Returns the transform from source_frame to target_frame at the time time.
                # time is a rospy.Time instance. The transform is returned as position (x, y, z)
                # and an orientation quaternion (x, y, z, w)
                # listener.waitForTransform('/map', '/ar_marker_0',rospy.Time(),rospy.Duration(.1))
                (trans,rot) = self.listener.lookupTransform('/map', '/ar_marker_0', rospy.Time(0))
                matrix = self.listener.fromTranslationRotation(trans, rot)
                # we use the matrix to find what position we should move to in relation
                # to the ar image we have found.
                direction = matrix[:3 , 2]
                pose = trans + direction * 0.4
                theta_direction = direction * -1
                theta_direction1 = theta_direction / (theta_direction[0] ** 2 + theta_direction[1] ** 2) ** 0.5
                theta = math.atan2(theta_direction1[1], theta_direction1[0])
                position = {'x': float(str(pose[0])[:5]), 'y' : float(str(pose[1])[:5])}
                quat = {'r1' : 0.000, 'r2' : 0.000, 'r3' : np.sin(theta/2.0), 'r4' : np.cos(theta/2.0)}
                print(self.StoredAr1)
                print(trans)

                # when marker is detected, if a first marker is not currently stored then run
                if (self.StoredAr1 == False): # if no first marker is found
                    # send turtlebot to AR marker
                    SendBotSuccess = self.goto(position, quat)
                    time.sleep(10)
                    print('going to: ' + str(position) + ' ' + str(quat))
                    # pull follow points based on close-range scan
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    # if turtlebot is not close enough to image, move closer until it detects the wall
                    while self.mid == 0:
                        self.move_now.linear.x = 0.1
                        self.move_now.angular.z = 0
                        self.move.publish(self.move_now)
                        self.reset_counters()
                        self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    # adjust and turn to the right if more detections on the right than left
                    if self.left < self.right:
                        while self.left < self.right:
                            print('adjusting to ar, turning right ' + str(self.mid))
                            self.move_now.linear.x = 0
                            self.move_now.angular.z = -0.1
                            self.move.publish(self.move_now)
                            self.reset_counters()
                            self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    # adjust and turn to the left if more detections on the left than right
                    elif self.right < self.left:
                        while self.right < self.left:
                            print('adjusting to ar, turning left ' + str(self.mid))
                            self.move_now.linear.x = 0
                            self.move_now.angular.z = 0.1
                            self.move.publish(self.move_now)
                            self.reset_counters()
                            self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    time.sleep(10)
                    # create subprocess to open run contour
                    extProc = sp.Popen(['python', 'contour.py'])
                    # terminate when the program has finished
                    if sp.Popen.poll(extProc) == 'None':
                        sp.Popen.terminate(extProc)
                    status = sp.Popen.poll(extProc)
                    time.sleep(10)
                    print(status)
                    # store the location x,y of the AR marker
                    self.ArPositionStore.append([trans[0], trans[1]])
                    print(self.ArPositionStore)
                    print('took picture of first marker')
                    # open file, store the location x, y of the first marker to file
                    locations = open("detections/locations.txt", "a")
                    locations.write("x: " + str(trans[0]) + ", y: " + str(trans[1]) + "\n")
                    # close file
                    locations.close()
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    while self.mid != 0:
                        self.move_now.linear.x = -0.1
                        self.move_now.angular.z = 0
                        self.move.publish(self.move_now)
                        self.reset_counters()
                        self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    if self.wall_location == 'right':
                        while self.left > 0:
                            print('turning left')
                            self.move_now.angular.z = 0.2
                            self.move_now.linear.x = 0
                            self.move.publish(self.move_now)
                            self.rate.sleep()
                            self.reset_counters()
                            self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    elif self.wall_location == 'left':
                        while self.right > 0:
                            print('turning right')
                            self.move_now.angular.z = -0.2
                            self.move_now.linear.x = 0
                            self.move.publish(self.move_now)
                            self.rate.sleep()
                            self.reset_counters()
                            self.get_follow_points(self.latest_scan(), self.adjustthresh)
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)
                    # declare that the first marker has been found
                    self.StoredAr1 = True

                # call Check_scanned_ar function to check if the current scanned AR maker is the same as the first stored
                elif(self.Check_scanned_ar(trans) == False): # if current marker found is not same as last found
                    # send turtlebot to AR marker
                    SendBotSuccess = self.goto(position, quat)
                    # pull follow points based on close-range scan
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)
                    # if turtlebot is not close enough to image, move closer until it detects the wall
                    while self.mid == 0:
                        self.move_now.linear.x = 0.1
                        self.move_now.angular.z = 0
                        self.move.publish(self.move_now)
                        self.reset_counters()
                        self.get_follow_points(self.latest_scan(), self.thresh)
                    # adjust and turn to the right if more detections on the right than left
                    if self.left < self.right:
                        while self.left < self.right:
                            print('adjusting to ar, turning right ' + str(self.mid))
                            self.move_now.linear.x = 0
                            self.move_now.angular.z = -0.1
                            self.move.publish(self.move_now)
                            self.reset_counters()
                            self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    # adjust and turn to the left if more detections on the left than right
                    elif self.right < self.left:
                        while self.right < self.left:
                            print('adjusting to ar, turning left ' + str(self.mid))
                            self.move_now.linear.x = 0
                            self.move_now.angular.z = 0.1
                            self.move.publish(self.move_now)
                            self.reset_counters()
                            self.get_follow_points(self.latest_scan(), self.adjust_thresh)
                    time.sleep(10)
                    # create subprocess to open run contour
                    extProc = sp.Popen(['python', 'contour.py'])
                    # terminate when the program has finished
                    if sp.Popen.poll(extProc) == 'None':
                        sp.Popen.terminate(extProc)
                    status = sp.Popen.poll(extProc)
                    time.sleep(10)
                    print(status)
                    # declare that the second marker has been found
                    self.StoredAr2 = True
                    # store the location x,y of the AR marker
                    self.ArPositionStore.append([trans[0], trans[1]])
                    print(self.ArPositionStore)
                    print('took picture of second marker')
                    # open file, store the location x, y of the first marker to file
                    locations = open("detections/locations.txt", "a")
                    locations.write("x: " + str(trans[0]) + ", y: " + str(trans[1]) + "\n")
                    # close files
                    locations.close()
                    # shutdown program
                    rospy.signal_shutdown("all jobs completed!")
                # else if marker detected is the same as previous recorded, continue navigation
                else:
                    self.navigate(self.latest_scan())
                    print('markers are same, continue nav')

            except (tf.LookupException, tf.ConnectivityException, tf.ExtrapolationException):
                pass
            # navigate
            print('no makers in range')
            self.navigate(self.latest_scan())
            self.working=False

    # function to compare two AR marker positions to see if they are the same
    def Check_scanned_ar(self, trans):
        for x in range(len(self.ArPositionStore)):
            #print("trans:"+ trans[0] + "trans1 "+ trans[1] + "arstored1" +self.ArPositionStore[x][0] + "arstored2" +self.ArPositionStore[x][1])
            print(self.StoredAr1)
            print(self.ArPositionStore)
            if (math.fabs(trans[0] - self.ArPositionStore[x][0]) < 0.3) and (math.fabs(trans[1] - self.ArPositionStore[x][1])< 0.3):
                return True
        return False

    # function to send the turtlebot to a user or function provided location
    def goto(self, pos, quat):
        self.move_base = actionlib.SimpleActionClient("move_base", MoveBaseAction)
        self.move_base.wait_for_server(rospy.Duration(5))
        print('moving to position')
            # Send a goal
        self.goal_sent = True
        goal = MoveBaseGoal()
        goal.target_pose.header.frame_id = 'map'
        goal.target_pose.header.stamp = rospy.Time.now()
        goal.target_pose.pose = Pose(Point(pos['x'], pos['y'], 0.000),Quaternion(quat['r1'], quat['r2'], quat['r3'], quat['r4']))

        # Start moving
        print('sending goal')
        self.move_base.send_goal(goal)
        -3
        print('trying to go to ' + str(pos['x']) + ' ' + str(pos['y']))
        print('goal sent')

        # Allow TurtleBot up to 60 seconds to complete task
        success = self.move_base.wait_for_result(rospy.Duration(60))
        state = self.move_base.get_state()
        if success and state == GoalStatus.SUCCEEDED:
            self.result = True
            self.get_follow_points(self.latest_scan(), self.adjust_thresh)
            print(self.scan_stream_data)
            print(self.left)
            print(self.mid)
            print(self.right)
            rospy.loginfo("waiting 5 seconds for image processing and then shutting down ar")
            print(self.result)
            return self.result
            self.result = False
            time.sleep(5)
        else:
            print ('cancelling goal')
            self.move_base.cancel_goal()
        print ('goal was sent')
        self.goal_sent = False

    def navigate(self, latest_scan):
        # test print latest scan data
        #print(latest_scan)
        # test print number of points in scan data
        #print(len(latest_scan))
        #
        print('navigating! wall is: ' + self.wall_location + ' ' + str(self.left) + ' ' +  str(self.mid) + ' ' + str(self.right))


        self.get_follow_points(latest_scan, self.thresh)

        # test print range direction count
        # print('right section ' + str(self.right) + ' | middle section ' + str(self.mid) + ' | left section ' + str(self.left))

        if self.bump_counter >= 5:
            self.stuck = True
        elif self.stuck == True:
            self.recover()
            self.bump_counter = 0
            self.stuck == False
        else:

            if self.bump == True:
                self.move_now.linear.x = -0.2
                self.move_now.angular.z = 0
                for i in range(12000):
                    self.move.publish(self.move_now)
                self.bump = False
                self.reset_counters()
                self.get_follow_points(self.latest_scan(), self.thresh)
                # print('Obstacle check: right section ' + str(self.right) + ' | middle section ' + str(self.mid) + ' | left section ' + str(self.left))
                rospy.sleep(3)
                print(self.bump_counter)
                self.obstacle_correction()
                self.reset_counters()
            else:
                self.reset_counters()
                self.get_follow_points(self.latest_scan(), self.thresh)
                if self.wall_location == 'no wall location' and self.mid == 0 and self.left == 0 and self.right == 0:
                    self.move_now.linear.x = 0.1
                    self.move_now.angular.z = 0
                    self.move.publish(self.move_now)
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)
                elif self.wall_location == 'left' and self.mid == 0 and self.left == 0 and self.right == 0:
                    spinTime = time.time() + 4
                    while time.time() < spinTime:
                        print('no wall, moving a bit' + str(self.left) + str(self.mid) + str(self.right))
                        self.move_now.linear.x = 0.15
                        self.move_now.angular.z = 0
                        self.move.publish(self.move_now)
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)
                    while self.left < 10:
                        print('turning left')
                        self.move_now.angular.z = 0.2
                        self.move_now.linear.x = 0
                        self.move.publish(self.move_now)
                        self.rate.sleep()
                        self.reset_counters()
                        self.get_follow_points(self.latest_scan(), self.thresh)

                elif self.wall_location == 'right' and self.mid == 0 and self.left == 0 and self.right == 0:
                    spinTime = time.time() + 4
                    while time.time() < spinTime:
                        print('no wall, moving a bit' + str(self.left) + str(self.mid) + str(self.right))
                        self.move_now.linear.x = 0.15
                        self.move_now.angular.z = 0
                        self.move.publish(self.move_now)
                    while self.right < 10:
                        print('turning right')
                        self.move_now.angular.z = -0.2
                        self.move_now.linear.x = 0
                        self.move.publish(self.move_now)
                        self.rate.sleep()
                        self.reset_counters()
                        self.get_follow_points(self.latest_scan(), self.thresh)

                elif (self.mid == 0 and (self.right > 0 or self.left > 0)):
                    print('move forward')
                    self.move_now.linear.x = 0.15
                    self.move_now.angular.z = 0
                    self.move.publish(self.move_now)
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)

                    #elif (self.mid > 100 and self.right > 100 and self.left > 100):
                    #	print ('obstacle detected')

                elif self.mid > 0 and (self.right > self.left):
                    # print('turn left ')
                    self.move_now.angular.z = 0.8
                    self.move_now.linear.x = 0.15
                    self.move.publish(self.move_now)
                    self.wall_location = 'right'
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)

                elif self.mid == 0 and self.right > 40 and (self.right > self.left):
                    # print('stay course wall to right ')
                    self.move_now.angular.z = 0.6
                    self.move_now.linear.x = 0.15
                    self.move.publish(self.move_now)
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)
                    self.wall_location = 'right'

                elif self.mid > 0 and (self.left > self.right):
                    print('turn right ')
                    self.move_now.angular.z = -0.8
                    self.move_now.linear.x = 0.15
                    self.move.publish(self.move_now)
                    self.wall_location = 'left'
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)

                elif self.mid == 0 and self.left > 40 and (self.left > self.right):
                    # print('stay course wall to left ')
                    self.move_now.angular.z = -0.6
                    self.move_now.linear.x = 0.15
                    self.move.publish(self.move_now)
                    self.reset_counters()
                    self.get_follow_points(self.latest_scan(), self.thresh)
                    self.wall_location = 'left'

    def shutdown(self):
        sleep(3)

if __name__ == "__main__":
    try:
        rospy.init_node('searcher', anonymous=True)
        cI = Searcher()
        rospy.spin()
    except rospy.ROSInterruptException:
        pass
