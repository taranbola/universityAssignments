#!/usr/bin/env python
import numpy as np
import cv2
import glob

# methods reads in an image, converts to cv image, loops and compares image to supplied 'real images'
# returns the name of the most likely image as a string to terminal
def imageCalc(image):

    # Initiate SIFT detector
    orb = cv2.ORB()

    real_img_list = []
    real_names = []

    for filename in glob.glob('../cluedo/real_images/*.png'):
        im = cv2.imread(filename, 0)
        name = filename.split("real_images/")[1].split('.')[0]
        real_names.append(name)
        real_img_list.append(im)

    distances = []
    amounts = []
    averages = []

    image.convert('RGB')
    open_cv_image = np.array(image)
    open_cv_image = open_cv_image[:, :, ::-1].copy()

    for img in real_img_list:

        # find the keypoints and descriptors with SIFT
        kp, des = orb.detectAndCompute(img, None)
        kp1, des1 = orb.detectAndCompute(open_cv_image, None)

        # create BFMatcher object
        bf = cv2.BFMatcher(cv2.NORM_HAMMING, crossCheck=True)

        # Match descriptors.
        matches = bf.match(des, des1)

        # calculates likelyhood of image based on number of matches and value of the match
        distance = 0
        count = 0
        for dist in matches:
            if 0 <= dist.distance < 70:
                distance += dist.distance
                count += 1

        average = distance / count
        averages.append(average)
        amounts.append(count)
        distances.append(distance)

    index = averages.index(min(averages))

    return real_names[index]
