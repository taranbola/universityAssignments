import os
import unittest
import tempfile
from app import *


class TestCase(unittest.TestCase):
    def setUp(self):
        self.db_fd, app.config['DATABASE'] = tempfile.mkstemp()
        app.testing = True                                              #initial set up of the testing
        self.app = app.test_client()

    def tearDown(self):
        os.close(self.db_fd)                                #what it gets rid of at the end
        os.unlink(app.config['DATABASE'])

    def test_indexLoads(self):
        response = self.app.get('/',follow_redirects=True, content_type='html/text')        #test the / page
        self.assertEqual(response.status_code, 200)

    def test_password(self):
        response = self.app.get('/password',follow_redirects=True, content_type='html/text')     #tests the /passsword page loads
        self.assertEqual(response.status_code, 200)

    def test_signup(self):
        response = self.app.get('/signup',follow_redirects=True, content_type='html/text')     #tests the /signup page loads
        self.assertEqual(response.status_code, 200)

    def test_createtask(self):
        response = self.app.get('/create_task',follow_redirects=True, content_type='html/text')     #tests the /create_task page loads
        self.assertEqual(response.status_code, 200)

    def test_logout(self):
        response = self.app.get('/unsetvariable',follow_redirects=True, content_type='html/text')     #tests the /logout page loads
        self.assertEqual(response.status_code, 200)

    def test_inputqueue(self):
        response = self.app.get('/input_queue',follow_redirects=True, content_type='html/text')     #tests the /input_queue page loads
        self.assertEqual(response.status_code, 200)

    def test_analysis(self):
        response = self.app.get('/analysis',follow_redirects=True, content_type='html/text')     #tests the /analysis page loads
        self.assertEqual(response.status_code, 200)

    def test_development(self):
        response = self.app.get('/development',follow_redirects=True, content_type='html/text')     #tests the /development page loads
        self.assertEqual(response.status_code, 200)

    def test_buildready(self):
        response = self.app.get('/build_ready',follow_redirects=True, content_type='html/text')     #tests the /buildReady page loads
        self.assertEqual(response.status_code, 200)

    def test_test(self):
        response = self.app.get('/test',follow_redirects=True, content_type='html/text')     #tests the /test page loads
        self.assertEqual(response.status_code, 200)

    def test_release(self):
        response = self.app.get('/release_ready',follow_redirects=True, content_type='html/text')     #tests the /release_ready page loads
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
