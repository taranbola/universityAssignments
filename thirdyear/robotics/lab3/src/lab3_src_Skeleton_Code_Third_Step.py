#!/usr/bin/env python

from __future__ import division
import cv2
import numpy as np
import rospy
import sys
import math

from geometry_msgs.msg import Twist, Vector3
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError
from std_msgs.msg import String
# Complete the skeleton code to send messages based on what colours are
# present in the image view..
class colourIdentifier():

    def __init__(self):
        # Initialise a publisher to publish messages to the robot base
        # We covered which topic receives messages that move the robot in the 2nd Lab Session
        self.pub = rospy.Publisher('mobile_base/commands/velocity', Twist)

        # Initialise any flags that signal a colour has been detected in view   green red blue
        self.SAW_GREEN="Green"
        self.SAW_RED="Red"
        self.SAW_BLUE="Blue"

        # Initialise the value you wish to use for sensitivity in the colour detection (10 should be enough)
        self.sensitivity=10

        # Initialise some standard movement messages such as a simple move forward and a message with all zeroes (stop)
        self.STOP=0
        self.FORWARD=10

        # Remember to initialise a CvBridge() and set up a subscriber to the image topic you wish to use
        self.bridge=CvBridge()

        # We covered which topic to subscribe to should you wish to receive image data
        rospy.Subscriber('/camera/rgb/image_raw', Image, self.callback)

    def callback(self, data):
        # Convert the received image into a opencv image
        # But remember that you should always wrap a call to this conversion method in an exception handler
        try:
            cv_image = self.bridge.imgmsg_to_cv2(data, "bgr8")
        except CvBridgeError as e:
            print(e)

        # Set the upper and lower bounds for the two colours you wish to identify
        hsv_green_lower = np.array([60 - self.sensitivity, 100, 100])
        hsv_green_upper = np.array([60 + self.sensitivity, 255, 255])

        hsv_red_lower = np.array([0 - self.sensitivity, 100, 100])
        hsv_red_upper = np.array([0 + self.sensitivity, 255, 255])

        hsv_blue_lower = np.array([120-self.sensitivity, 100, 100])
        hsv_blue_upper = np.array([120 + self.sensitivity, 255, 255])
        # Convert the rgb image into a hsv image
        hsv = cv2.cvtColor(cv_image, cv2.COLOR_BGR2HSV)

        # Filter out everything but particular colours using the cv2.inRange() method
        mask1 = cv2.inRange(hsv, hsv_green_lower, hsv_green_upper)
        mask2 = cv2.inRange(hsv, hsv_red_lower, hsv_red_upper)
        mask3 = cv2.inRange(hsv, hsv_blue_lower, hsv_blue_upper)

        # To combine the masks you should use the cv2.bitwise_or() method
        # You can only bitwise_or two image at once, so multiple calls are necessary for more than two colours
        out1=cv2.bitwise_or(mask1, mask2)
        out2=cv2.bitwise_or(out1, mask3)

        # Apply the mask to the original image using the cv2.bitwise_and() method
        # As mentioned on the worksheet the best way to do this is to bitwise and an image with itself and pass the mask to the mask parameter
        # As opposed to performing a bitwise_and on the mask and the image.
        output = cv2.bitwise_and(cv_image, cv_image, mask=out2)

        # Find the contours that appear within the certain colours mask using the cv2.findContours() method
        # For <mode> use cv2.RETR_LIST for <method> use cv2.CHAIN_APPROX_SIMPLE
        contours1, hier1=cv2.findContours(mask1, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
        contours2, hier2=cv2.findContours(mask2, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
        contours3, hier3=cv2.findContours(mask3, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

        # Loop over the contours
        # There are a few different methods for identifying which contour is the biggest
        # Loop throguht the list and keep track of whioch contour is biggest or
        # Use the max() method to find the largest contour

        #green
        if len(contours1) > 0:
            c = max(contours1, key=cv2.contourArea)
            ((x, y), radius) = cv2.minEnclosingCircle(c)
            M = cv2.moments(c)
            center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
            self.SAW_GREEN="Green"
            # only proceed if the radius meets a minimum size
            if radius > 10:
                # draw the circle and centroid on the frame,
                cv2.circle(output, (int(x), int(y)), int(radius),(0,255,255), 2)
        else:
            self.SAW_GREEN=""

        #red
        if len(contours2) > 0:
            c = max(contours2, key=cv2.contourArea)
            ((x, y), radius) = cv2.minEnclosingCircle(c)
            M = cv2.moments(c)
            center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
            self.SAW_RED="Red"
            # only proceed if the radius meets a minimum size
            if radius > 10:
                # draw the circle and centroid on the frame,
                cv2.circle(output, (int(x), int(y)), int(radius),(0,255,255), 2)
        else:
            self.SAW_RED=""

        #blue
        if len(contours3) > 0:
            c = max(contours3, key=cv2.contourArea)
            ((x, y), radius) = cv2.minEnclosingCircle(c)
            M = cv2.moments(c)
            center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
            self.SAW_BLUE="Blue"
            # only proceed if the radius meets a minimum size
            if radius > 10:
                # draw the circle and centroid on the frame,
                cv2.circle(output, (int(x), int(y)), int(radius),(0,255,255), 2)
        else:
            self.SAW_BLUE=""

        print("%s  %s  %s"%(self.SAW_GREEN, self.SAW_RED, self.SAW_BLUE))
        #Show the resultant images you have created. You can show all of them or just the end result if you wish to.
        cv2.namedWindow('Camera_Feed')
        cv2.imshow('Camera_Feed', output)
        cv2.waitKey(3)


# Create a node of your class in the main and ensure it stays up and running
# handling exceptions and such
def main(args):
    # Instantiate your class
    # And rospy.init the entire node
    cI = colourIdentifier()
    rospy.init_node('Skeleton_Code_Third_Step', anonymous=True)

    # Ensure that the node continues running with rospy.spin()
    # You may need to wrap it in an exception handler in case of KeyboardInterrupts
    rospy.spin()

    # Remember to destroy all image windows before closing node


# Check if the node is executing in the main path
if __name__ == '__main__':
    main(sys.argv)
