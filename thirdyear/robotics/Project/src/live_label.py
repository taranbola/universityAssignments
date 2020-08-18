

import numpy as np
import tensorflow as tf
from __future__ import division
import cv2
import rospy
import sys

from geometry_msgs.msg import Twist, Vector3
from sensor_msgs.msg import Image
from cv_bridge import CvBridge, CvBridgeError

camera = cv2.VideoCapture(0)

def load_graph(model_file):
  graph = tf.Graph()
  graph_def = tf.GraphDef()

  with open(model_file, "rb") as f:
    graph_def.ParseFromString(f.read())
  with graph.as_default():
    tf.import_graph_def(graph_def)

  return graph

def load_labels(label_file):
  label = []
  proto_as_ascii_lines = tf.gfile.GFile(label_file).readlines()
  for l in proto_as_ascii_lines:
    label.append(l.rstrip())
  return label

def grabVideoFeed():
    grabbed, frame = camera.read()
    return frame if grabbed else None


if __name__ == "__main__":

    model_file = "final_graph.pb"
    label_file = "output_labels.txt"
    input_height = 224
    input_width = 224
    input_mean = 0
    input_std = 255
    input_layer = "Placeholder"
    output_layer = "final_result"

    #load tensorflow graph and labels file
    graph = load_graph(model_file)
    labels = load_labels(label_file)

    #set the inputs and outputs for the graph
    input_name = "import/" + input_layer
    output_name = "import/" + output_layer
    input_operation = graph.get_operation_by_name(input_name)
    output_operation = graph.get_operation_by_name(output_name)

    with tf.Session(graph=graph) as sess:
        while True:

            live_frame = grabVideoFeed()
            if live_frame is None:
                raise SystemError('Issue grabbing the frame')

            resized_frame = cv2.resize(live_frame, (input_height,input_width), interpolation=cv2.INTER_CUBIC)
            cv2.imshow("Image", resized_frame)

            numpy_frame = np.float32(resized_frame)
            #ensure frame have same mean and range
            normalised = cv2.normalize(numpy_frame, None, alpha=0, beta=1, norm_type=cv2.NORM_MINMAX, dtype=cv2.CV_32F)
            t = np.expand_dims(normalised, axis=0)

            #run forward pass of framne through net
            results = sess.run(output_operation.outputs[0], {
                               input_operation.outputs[0]: t})
            #find the highest output
            results = np.squeeze(results)
            top_k = results.argsort()[-7:][::-1]
            print(labels[top_k[0]], results[top_k[0]])

            if cv2.waitKey(1) & 0xFF == ord('q'):
                sess.close()
                break

    camera.release()
    cv2.destroyAllWindows()
