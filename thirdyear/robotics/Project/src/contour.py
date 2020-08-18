#!/usr/bin/env python

from __future__ import division
import cv2
import numpy as np
import rospy
import sys

from geometry_msgs.msg import Twist, Vector3
from sensor_msgs.msg import Image as Imagemsg
from cv_bridge import CvBridge, CvBridgeError
from featurematchReal import imageCalc
from PIL import Image
import time

class contourIdentifier():

	def __init__(self):
		self.bridge = CvBridge()
		self.image_data = None
		self.sub = rospy.Subscriber('camera/rgb/image_raw', Imagemsg, self.image_stream)
		# We covered which topic to subscribe to should you wish to receive image data
		time.sleep(5)
		self.callback(self.grab_current_image())

	def image_stream(self, data):
		print('saving new image stream')
		self.image_data = data

	def grab_current_image(self):
		print('pulling latest image')
		return self.image_data

	def callback(self, data):
		# Convert the received image into a opencv image
		try:
			cv_image = self.bridge.imgmsg_to_cv2(data,"bgr8")
		except CvBridgeError as e:
			print(e)

		# Set the upper and lower bounds for the two colours you wish to identify
		hsv_green_lower = np.array([0, 18, 18])
		hsv_green_upper = np.array([255, 255, 255])

		# Convert the rgb image into a hsv image
		hsv_image = cv2.cvtColor(cv_image, cv2.COLOR_BGR2HSV)

		# Filter out everything but particular colours using the cv2.inRange() method
		mask = cv2.inRange(hsv_image, hsv_green_lower, hsv_green_upper)

		res = cv2.bitwise_and(cv_image,cv_image, mask=mask)

		#this creates the contour for the given mask
		cnts, hierarchy = cv2.findContours(mask,cv2.RETR_LIST,cv2.CHAIN_APPROX_SIMPLE)

		try: hierarchy = hierarchy[0]
		except: hierarchy = []

		#gets the height and width of the mask
		height, width = mask.shape
		min_x, min_y = width, height
		max_x = max_y = 0

		#for each contour it finds it will determine the largest/smallest
		#rectangle. It will then draw the smallest one.
		for contour, hier in zip(cnts, hierarchy):
			(x,y,w,h) = cv2.boundingRect(contour)
			min_x, max_x = min(x, min_x), max(x+w, max_x)
			min_y, max_y = min(y, min_y), max(y+h, max_y)
			if w > 80 and h > 80:
				if((y-16)<0):
					cropbottom = 0
				else:
					cropbottom=y-16
				if ((y+h+16)>=height):
					croptop=height
				else:
					croptop=y+h+16
				crop = np.copy(cv_image[cropbottom:croptop, x:x+w])
				cv2.rectangle(cv_image, (x,y), (x+w,y+h), (255, 0, 0), 2)

		print('saving image')
		cv2.imwrite('detections/temp.png', crop)
		imageQuery = Image.open("detections/temp.png")
		output = "UnknownCharacter"
		output = imageCalc(imageQuery)
		locations = open("detections/locations.txt", "a")
		locations.write("Detection: " + output + " | ")
		locations.close()
		print(output)
		cv2.imwrite('detections/' + output + '.png', cv_image)
		cv2.waitKey(3)

		rospy.signal_shutdown('pic taken')


# Create a node of your class in the main and ensure it stays up and running
# handling exceptions and such
def main(args):
	# Instantiate your class
	# And rospy.init the entire node
	rospy.init_node('listener', anonymous=True)
	cI = contourIdentifier()
	rospy.spin()


# Check if the node is executing in the main path
if __name__ == '__main__':
	main(sys.argv)
