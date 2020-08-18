import os
import unittest
import tempfile
from app import *

class TestCase(unittest.TestCase):
    def setUp(self):
        app.config['TESTING'] = True
        app.config['WTF_CSRF_ENABLED'] = False
        app.config['DEBUG'] = False         #initial set up of the testing
        self.app = app.test_client()

    def test_index(self):
        response = self.app.get('/',follow_redirects=True, content_type='html/text')     #tests the / page loads
        self.assertTrue(b'Decentralized Smart Contract Network' in response.data)

    def test_index_code(self):
        response = self.app.get('/',follow_redirects=True, content_type='html/text')     #tests the / page loads
        self.assertEqual(response.status_code, 200)

    def test_rep(self):
        response = self.app.get('/rep',follow_redirects=True, content_type='html/text')     #tests the /rep page loads
        self.assertTrue(b'Server' in response.data)

    def test_rep_code(self):
        response = self.app.get('/rep',follow_redirects=True, content_type='html/text')     #tests the /rep page code is 200
        self.assertEqual(response.status_code, 200)

    def test_host_code(self):
        response = self.app.get('/changehost',follow_redirects=True, content_type='html/text')     #tests the /host page doesn't load
        self.assertEqual(response.status_code, 405)

    def test_host_server(self):
        response = self.app.post('/changehost', data=dict(server="http://127.0.0.1:8001"), follow_redirects=True) #tests if I can change the server to 8000
        self.assertIn(b'Decentralized Smart Contract Network', response.data)

    def test_host_server2(self):
        response = self.app.post('/changehost', data=dict(server="http://127.0.0.1:8002"), follow_redirects=True) #tests if I can change the server to 8002
        self.assertIn(b'Decentralized Smart Contract Network', response.data)

    def test_host_server3(self):
        response = self.app.post('/changehost', data=dict(server="http://127.0.0.1:8000"), follow_redirects=True) #tests if I can change the server to 8000
        self.assertIn(b'Decentralized Smart Contract Network', response.data)

    def test_sourcecode(self):
        response = self.app.get('/sourcecode/0',follow_redirects=True, content_type='html/text')     #tests the source page loads (becomes false if blockchain used)
        self.assertTrue(b'That contract does not exist on the blockchain' in response.data)

    def test_sourcecode_code(self):
        response = self.app.get('/sourcecode/0',follow_redirects=True, content_type='html/text')     #tests the /sourcecode/0 page loads
        self.assertEqual(response.status_code, 200)

if __name__ == '__main__':
    unittest.main()
