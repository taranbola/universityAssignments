import datetime
import json
import binascii
import requests
import os
import uncompyle6
import random
from flask import render_template, redirect, request, flash
from requests import Request, Session
from requests.exceptions import ConnectionError
from werkzeug.utils import secure_filename
from app import app

CONNECTED_NODE_ADDRESS = "http://127.0.0.1:8000"
ALLOWED_EXTENSIONS = set(['pyc'])
posts = []

#This Function will check if a filetype is allowed tobe submitted
def allowed_file(filename):
	return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

#generates the hexidecimel data of the .pyc file which will be
#sent to the server
def runfile(filename):
    f = open("temp/" + filename,"rb")
    if f.mode == "rb":
        contents = f.read()
        newcontents = binascii.hexlify(contents)
        decoded = newcontents.decode('utf-8')
        return decoded

#will fetch all the posts (contracts) from the servers /chain
#endpoint. Will then return them when called
def fetch_posts():
    """
    Function to fetch the chain from a blockchain node, parse the
    data and store it locally.
    """
    get_chain_address = "{}/chain".format(CONNECTED_NODE_ADDRESS)
    response = requests.get(get_chain_address)
    if response.status_code == 200:
        content = []
        chain = json.loads(response.content)
        for block in chain["chain"]:
            for contract in block["contracts"]:
                contract["hash"] = block["previous_hash"]
                content.append(contract)

        global posts
        posts = sorted(content, key=lambda k: k['timestamp'],
                       reverse=True)

#takes in a timestamp and puts it in a better format
def timestamp_to_string(epoch_time):
    return datetime.datetime.fromtimestamp(epoch_time).strftime('%d-%m-%Y , %H:%M:%S')

#will randomly allocate a node to be specified to run the contract
def randomallocation():
    get_chain_address = "{}/chain".format(CONNECTED_NODE_ADDRESS)
    response = requests.get(get_chain_address)
    allocation_pool = []
    if response.status_code == 200:
        chain = json.loads(response.content)
        for each in chain['peers']:
            if(each == CONNECTED_NODE_ADDRESS):
                continue
            else:
                allocation_pool.append(each)
    return random.choice(allocation_pool)

#will fetch the drop down list of possible contracts, for a response
#to be given for. They have to be valid for that server.
def fetch_valid_response_index():
	fetch_posts()
	global posts
	listofvalids = []
	listofresponses = []
	unresponded = []
	for each in posts:
		if(each['author'] == CONNECTED_NODE_ADDRESS):
			if(each['type'] == "contract"):
				listofvalids.append(each)
			elif(each['type'] == "response"):
				listofresponses.append(each)

	for valid in listofvalids:
		found = False
		for response in listofresponses:
			if (str(valid['index']) == str(response['oci'])):
				found = True
				break
		if found == False:
			unresponded.append(valid['index'])

	return unresponded

#the index page which will load all the contracts and the interface
@app.route('/')
def index():
	fetch_posts()
	contract_index = fetch_valid_response_index()
	return render_template('index.html',
                           title='Decentralized Smart Contract Network',
                           posts=posts,
                           node_address=CONNECTED_NODE_ADDRESS,
                           readable_time=timestamp_to_string,
						   contract_index=contract_index)

#This is the endpoint which will process the contract form, it will
#then post it to the servers endpoint with the correct data in the
#correct format for the request
@app.route('/submit', methods=['POST'])
def submit_textarea():
    """
    Endpoint to create a new contract via our application.
    """

    uploadedfile = request.files["myfile"]
    if uploadedfile and allowed_file(uploadedfile.filename):
        filename = secure_filename(uploadedfile.filename)
        uploadedfile.save(os.path.join("temp/", filename))
        flash('Contract successfully created')
    else:
        flash('File Type has to be .pyc (Compiled .py files)')
        return redirect('/')


    hexcompiledfile = runfile(filename)
    mes = request.form['message']
    runner_address = randomallocation()
    post_object = {
        'type': "contract",
        'runner': runner_address,
        'data': hexcompiledfile,
        'message': mes,
    }

    new_contract_address = "{}/new_contract".format(CONNECTED_NODE_ADDRESS)

    requests.post(new_contract_address,
                  json=post_object,
                  headers={'Content-type': 'application/json'})

    return redirect('/')

#This is the endpoint which will process the response form, it will
#then post it to the servers endpoint with the correct data in the
#correct format for the request
@app.route('/response', methods=['POST'])
def submit_response():
	checkvalue = bool(request.form.get('check'))
	post_object = {'type': "response",'oci': request.form['oci'],'completed': checkvalue ,'message': request.form['message']}
	new_contract_address = "{}/new_response".format(CONNECTED_NODE_ADDRESS)
	requests.post(new_contract_address,json=post_object,headers={'Content-type': 'application/json'})
	flash('Response successfully created')
	return redirect('/')

#This endpoint is used to change the CONNECTED_NODE_ADDRESS and
#therefore change which server is currently being used.
@app.route('/changehost', methods=['POST'])
def changehost():
    """
    Endpoint to change current server node address.
    """
    global CONNECTED_NODE_ADDRESS
    CONNECTED_NODE_ADDRESS = request.form['server']
    return redirect('/')

#This endpoint will show the .py code for a specific contract data,
#it will decompile the .pyc file and show it on the site.
@app.route('/sourcecode/<int:index>')
def sourcecode(index):
    fetch_posts()
    global posts
    for each in posts:
        if (each['index'] == index):
            data = each['data']
            unhexed_data = binascii.unhexlify(data)
            f = open('temp/temp.pyc', 'wb+')
            f.write(unhexed_data)
            f.close()

            with open("temp/temp.py", "wb") as fileobj:
                uncompyle6.decompile_file("temp/temp.pyc", fileobj)
                fileobj.close()

            with open('temp/temp.py', 'r') as file:
                data2 = file.read()
            return "<xmp>" + data2 + "</xmp>"
        else:
            continue
    return "That contract does not exist on the blockchain"

#This endpoint will show the POS table, showing which servers
#are best using only data on the blockchain
@app.route('/rep')
def rep():
	fetch_posts()
	global posts
	string = """<table style="text-align:center"><thead><tr><th>Server&ensp;</th>
	<th>Contracts uploaded&ensp;</th><th>Assigned Contracts&ensp;</th>
	<th>Completed Contracts&ensp;</th><th>Uncompleted Contracts&ensp;
	</th><th>Reputation Score&ensp;</th></tr>"""
	response = requests.get("{}/chain".format(CONNECTED_NODE_ADDRESS))
	peers = response.json()['peers']
	for each in peers:
		string += ("<tr><td>" + each + "</td>")
		author = 0
		runner = 0
		contractsgiven= []
		for post in posts:
			if (post['type'] == "contract"):
				if (post['runner'] == each):
					runner += 1
					contractsgiven.append(post['index'])
				if (post['author'] == each):
					author += 1
		string += ("<td>" + str(author) + "</td>")
		string += ("<td>" + str(runner) + "</td>")

		completed = 0
		uncompleted = 0
		for post in posts:
			if (post['type'] == "response"):
				if (int(post['oci']) in contractsgiven):
					if(post['completed'] == True):
						completed += 1
					if(post['completed'] == False):
						uncompleted += 1

		string += ("<td>" + str(completed) + "</td>")
		string += ("<td>" + str(uncompleted) + "</td>")
		repscore = peers[each]
		repscore += completed
		repscore -= (uncompleted + author)
		string += ("<td>" + str(repscore) + "</td>")
	string += "</tbody></table>"
	return string
