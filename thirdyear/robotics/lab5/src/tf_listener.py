#!/usr/bin/env python  
import roslib
import rospy
import tf
import time

if __name__ == '__main__':
    rospy.init_node('tf_example',anonymous=True)

    listener = tf.TransformListener() # Create a tf listener
    time.sleep(3.0) # Sleeping for 3 second to listen to tf messages.

    try:
        # Asking tf to find the transform of child_frame in parent_frame.
        (trans,rot) = listener.lookupTransform('/parent_frame', '/child_frame', rospy.Time(0))
    except (tf.LookupException, tf.ConnectivityException, tf.ExtrapolationException):
        pass

    print trans
    print rot

