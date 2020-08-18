#!/usr/bin/env python

from __future__ import division
import cv2
import numpy as np
import rospy
import sys

from geometry_msgs.msg import Twist, Vector3
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError

class colourIdentifier():

	def __init__(self):
		# Initialise a publisher to publish messages to the robot base
		# We covered which topic receives messages that move the robot in the 2nd Lab Session


		# Initialise any flags that signal a colour has been detected in view


		# Initialise the value you wish to use for sensitivity in the colour detection (10 should be enough)
		sensitivity = 10

		# Initialise some standard movement messages such as a simple move forward and a message with all zeroes (stop)

		# Remember to initialise a CvBridge() and set up a subscriber to the image topic you wish to use
		self.bridge = CvBridge()
		self.sub = rospy.Subscriber('camera/rgb/image_raw', Image, self.callback)
		# We covered which topic to subscribe to should you wish to receive image data


	def callback(self, data):
		# Convert the received image into a opencv image
		# But remember that you should always wrap a call to this conversion method in an exception handler
		try:
			cv_image = self.bridge.imgmsg_to_cv2(data,"bgr8")
		except CvBridgeError as e:
			print(e)
		cv2.waitKey(3)
		# Set the upper and lower bounds for the two colours you wish to identify
		hsv_green_lower = np.array([60, 100, 100])
		hsv_green_upper = np.array([60, 255, 255])

		hsv_lower_red = np.array([0,100,100])
		hsv_upper_red = np.array([10,255,255])

		hsv_lower_blue = np.array([110,100,100])
		hsv_upper_blue = np.array([130,255,255])

		# Convert the rgb image into a hsv image
		hsv_image = cv2.cvtColor(cv_image, cv2.COLOR_BGR2HSV)

		# Filter out everything but particular colours using the cv2.inRange() method
		mask = cv2.inRange(hsv_image, hsv_green_lower, hsv_green_upper)
		mask2 = cv2.inRange(hsv_image, hsv_lower_red, hsv_upper_red)
		mask3 = cv2.inRange(hsv_image, hsv_lower_blue, hsv_upper_blue)

		# res_green = cv2.bitwise_and(cv_image,cv_image, mask= mask)
		# res_red = cv2.bitwise_and(cv_image,cv_image, mask= mask2)
		# res_blue = cv2.bitwise_and(cv_image,cv_image, mask= mask3)
		imageFlag = cv2.bitwise_or(mask,mask2)
		imageFlag2 =cv2.bitwise_or(imageFlag,mask3)

		res = cv2.bitwise_and(cv_image,cv_image, mask= imageFlag2)

		#this creates the contour for greens
		cnts, hierarchy = cv2.findContours(mask,cv2.RETR_LIST,cv2.CHAIN_APPROX_SIMPLE)
		if len(cnts) > 0:
			c = max(cnts, key=cv2.contourArea)
			((x, y), radius) = cv2.minEnclosingCircle(c)
			M = cv2.moments(c)
			center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
			if radius > 10:
				cv2.circle(res, (int(x), int(y)), int(radius),(0, 255, 255), 2)
				cv2.circle(res, center, 5, (0, 0, 255), -1)

		#this creates the contour for reds
		cnts2, hierarchy2 = cv2.findContours(mask2,cv2.RETR_LIST,cv2.CHAIN_APPROX_SIMPLE)
		if len(cnts2) > 0:
			c = max(cnts2, key=cv2.contourArea)
			((x, y), radius) = cv2.minEnclosingCircle(c)
			M = cv2.moments(c)
			center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
			if radius > 10:
				cv2.circle(res, (int(x), int(y)), int(radius),(0, 255, 255), 2)
				cv2.circle(res, center, 5, (0, 0, 255), -1)

		#this creates the contour for blues
		cnts3, hierarchy3 = cv2.findContours(mask3,cv2.RETR_LIST,cv2.CHAIN_APPROX_SIMPLE)
		if len(cnts3) > 0:
			c = max(cnts3, key=cv2.contourArea)
			((x, y), radius) = cv2.minEnclosingCircle(c)
			M = cv2.moments(c)
			center = (int(M["m10"] / M["m00"]), int(M["m01"] / M["m00"]))
			if radius > 10:
				cv2.circle(res, (int(x), int(y)), int(radius),(0, 255, 255), 2)
				cv2.circle(res, center, 5, (0, 0, 255), -1)

		cv2.imshow('Camera_Feed', res)
		cv2.waitKey(3)

# Create a node of your class in the main and ensure it stays up and running
# handling exceptions and such
def main(args):
	# Instantiate your class
	# And rospy.init the entire node
	rospy.init_node('listener', anonymous=True)
	cI = colourIdentifier()
	rospy.spin()
	# Ensure that the node continues running with rospy.spin()
	# You may need to wrap rospy.spin() in an exception handler in case of KeyboardInterrupts

	# Remember to destroy all image windows before closing node


# Check if the node is executing in the main path
if __name__ == '__main__':
	main(sys.argv)
