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
		# Initialise the value you wish to use for sensitivity in the colour detection (10 should be enough)
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
		cv2.namedWindow('Camera_Feed')
		cv2.imshow('Camera_Feed', cv_image)
		cv2.waitKey(3)

		hsv_image = cv2.cvtColor(cv_image, cv2.COLOR_BGR2HSV)
		hsv_green_lower = np.array([60, 100, 100])
		hsv_green_upper = np.array([60, 255, 255])

		hsv_lower_red = np.array([0,100,100])
		hsv_upper_red = np.array([10,255,255])

		hsv_lower_blue = np.array([110,100,100])
		hsv_upper_blue = np.array([130,255,255])

		mask = cv2.inRange(hsv_image, hsv_green_lower, hsv_green_upper)
		mask2 = cv2.inRange(hsv_image, hsv_lower_red, hsv_upper_red)
		mask3 = cv2.inRange(hsv_image, hsv_lower_blue, hsv_upper_blue)

		res_green = cv2.bitwise_and(cv_image,cv_image, mask= mask)
		res_red = cv2.bitwise_and(cv_image,cv_image, mask= mask2)
		res_blue = cv2.bitwise_and(cv_image,cv_image, mask= mask3)
		imageFlag = cv2.bitwise_or(res_green, res_red)
		imageFlag2 =cv2.bitwise_or(imageFlag,res_blue)

		cv2.namedWindow('Camera_Feed')
		cv2.imshow('Camera_Feed', cv_image)
		cv2.imshow('Res Green',res_green)
		cv2.imshow('Res Red',res_red)#
		cv2.imshow('Res Blue',res_blue)
		cv2.imshow('imageFlag',imageFlag2)
		cv2.waitKey(3)
		#Show the resultant images you have created. You can show all of them or just the end result if you wish to.


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
