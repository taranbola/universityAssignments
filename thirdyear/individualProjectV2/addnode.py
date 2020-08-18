import requests
from requests import Request, Session
import json
from requests.exceptions import ConnectionError

#program to simply add the nodes to the peers, to make sure
#all the nodes know about each other.

headers = {'Content-Type': 'application/json',}
datasending = '{"node_address": "http://127.0.0.1:8000"}'
datasending1 = '{"node_address": "http://127.0.0.1:8001"}'

r = requests.post('http://127.0.0.1:8000/register_with', data = datasending,headers=headers)
print(str(r) + " " + str(r.status_code) + " " + str(r.text))
r = requests.post('http://127.0.0.1:8001/register_with', data = datasending,headers=headers)
print(str(r) + " " + str(r.status_code) + " " + str(r.text))
r = requests.post('http://127.0.0.1:8002/register_with', data = datasending,headers=headers)
print(str(r) + " " + str(r.status_code) + " " + str(r.text))
r = requests.post('http://127.0.0.1:8002/register_with', data = datasending1,headers=headers)
print(str(r) + " " + str(r.status_code) + " " + str(r.text))
