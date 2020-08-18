from hashlib import sha256
import json
import time
import marshal
import binascii
from flask import Flask, request
import requests

#This represents a single block on the greater blockchain.
class Block:
    def __init__(self, index, contracts, timestamp, previous_hash, nonce=0):
        self.index = index
        self.contracts = contracts
        self.timestamp = timestamp
        self.previous_hash = previous_hash
        self.nonce = nonce

    def compute_hash(self):
        """
        A function that return the hash of the block contents.
        """
        block_string = json.dumps(self.__dict__, sort_keys=True)
        return sha256(block_string.encode()).hexdigest()

#this will be our blockchain will object which we will interact
#with and make changes to.
class Blockchain:
    # difficulty of our PoW algorithm
    difficulty = 2

    #Initialises the blockchain wiht a few variables
    def __init__(self):
        self.unconfirmed_contracts = []
        self.chain = []

    #creates the first block on the blockchain
    def create_genesis_block(self):
        """
        A function to generate genesis block and appends it to
        the chain. The block has index 0, previous_hash as 0, and
        a valid hash.
        """
        genesis_block = Block(0, [], 0, "0")
        genesis_block.hash = genesis_block.compute_hash()
        self.chain.append(genesis_block)

    #gets the last block on the blockchain
    @property
    def last_block(self):
        return self.chain[-1]

    #Adds a block on the blockchain
    def add_block(self, block, proof):
        """
        A function that adds the block to the chain after verification.
        Verification includes:
        * Checking if the proof is valid.
        * The previous_hash referred in the block and the hash of latest block
          in the chain match.
        """
        previous_hash = self.last_block.hash

        if previous_hash != block.previous_hash:
            return False

        if not Blockchain.is_valid_proof(block, proof):
            return False

        block.hash = proof
        self.chain.append(block)
        return True

    #tries to get a few different values of nonce that satisfies our
    #difficulty criteria
    @staticmethod
    def proof_of_work(block):
        """
        Function that tries different values of nonce to get a hash
        that satisfies our difficulty criteria.
        """
        block.nonce = 0

        computed_hash = block.compute_hash()
        while not computed_hash.startswith('0' * Blockchain.difficulty):
            block.nonce += 1
            computed_hash = block.compute_hash()

        return computed_hash

    #will add a new contract onto the unconfirmed_contracts variable
    #adds an index for each contract
    #these are waiting to be mined
    def add_new_contract(self, contract):
        if(self.unconfirmed_contracts == []):
            if(self.last_block.contracts == []):
                contract['index'] = 0
            else:
                lastcontract = self.last_block.contracts[-1]
                contract['index'] = lastcontract['index'] + 1
        else:
            last = self.unconfirmed_contracts[-1]
            contract['index'] = last['index'] + 1

        self.unconfirmed_contracts.append(contract)

    #checks if the hash is a valid block
    @classmethod
    def is_valid_proof(cls, block, block_hash):
        """
        Check if block_hash is valid hash of block and satisfies
        the difficulty criteria.
        """
        return (block_hash.startswith('0' * Blockchain.difficulty) and
                block_hash == block.compute_hash())

    #checks when given a chain if it is valid with it's own
    @classmethod
    def check_chain_validity(cls, chain):
        result = True
        previous_hash = "0"

        for block in chain:
            block_hash = block.hash
            # remove the hash field to recompute the hash again
            # using `compute_hash` method.
            delattr(block, "hash")

            if not cls.is_valid_proof(block, block_hash) or \
                    previous_hash != block.previous_hash:
                result = False
                break

            block.hash, previous_hash = block_hash, block_hash

        return result

    #this will add the unconfirmed_contracts to a block, create the block,
    #checks the block is valid to go on and then adds it to the
    #blockchain then removes empties unconfirmed_contracts.
    def mine(self):
        """
        This function serves as an interface to add the pending
        contracts to the blockchain by adding them to the block
        and figuring out Proof Of Work.
        """
        if not self.unconfirmed_contracts:
            return False

        last_block = self.last_block

        new_block = Block(index=last_block.index + 1,
                          contracts=self.unconfirmed_contracts,
                          timestamp=time.time(),
                          previous_hash=last_block.hash)

        proof = self.proof_of_work(new_block)
        self.add_block(new_block, proof)

        self.unconfirmed_contracts = []

        return True

app = Flask(__name__)

# the node's copy of blockchain
blockchain = Blockchain()
blockchain.create_genesis_block()

# the address to other participating members of the network
peers = dict()
previously_ran_contracts =[]

# endpoint to submit a new contract. This will be used by
# our application to add new data (contracts) to the blockchain
@app.route('/new_contract', methods=['POST'])
def new_contract():
    contract_data = request.get_json()
    required_fields = ["type","message", "data", "runner"]

    for field in required_fields:
        if not contract_data.get(field):
            return "Invalid contract data", 404

    removed_slash = request.host_url.rstrip('/')
    contract_data["author"] = removed_slash
    contract_data["timestamp"] = time.time()

    blockchain.add_new_contract(contract_data)

    return "Success", 201

# endpoint to submit a new response to a contract. This will be used by
# our application to add new data (responses) to the blockchain
@app.route('/new_response', methods=['POST'])
def new_response():
    response_data = request.get_json()
    required_fields = ["type", "message", "completed", "oci"]

    for field in required_fields:
        if not str(response_data.get(field)):
            return "Invalid contract data", 404

    removed_slash = request.host_url.rstrip('/')
    response_data["author"] = removed_slash
    response_data["timestamp"] = time.time()

    blockchain.add_new_contract(response_data)

    return "Success", 201


# endpoint to return the node's copy of the chain.
# Our application will be using this endpoint to query
# all the posts to display.
@app.route('/chain', methods=['GET'])
def get_chain():
    chain_data = []
    for block in blockchain.chain:
        chain_data.append(block.__dict__)
    return json.dumps({"length": len(chain_data),
                       "chain": chain_data,
                       "peers": dict(peers)})


# endpoint to request the node to mine the unconfirmed
# contracts (if any). We'll be using it to initiate
# a command to mine from our application itself.
@app.route('/mine', methods=['GET'])
def mine_unconfirmed_contracts():
    result = blockchain.mine()
    if not result:
        return "No contracts to mine"
    else:
        # Making sure we have the longest chain before announcing to the network
        chain_length = len(blockchain.chain)
        consensus()
        if chain_length == len(blockchain.chain):
            # announce the recently mined block to the network
            announce_new_block(blockchain.last_block)
        return "Block #{} is mined.".format(blockchain.last_block.index)


# endpoint to add new peers to the network.
@app.route('/register_node', methods=['POST'])
def register_new_peers():
    node_address = request.get_json()["node_address"]
    if not node_address:
        return "Invalid data", 400

    # Add the node to the peer list

    dict1 = {node_address : 10}
    peers.update(dict1)

    # Return the consensus blockchain to the newly registered node
    # so that he can sync
    return get_chain()

#will call the register_node and will then
#sync the data of the chain, peers etc
@app.route('/register_with', methods=['POST'])
def register_with_existing_node():
    """
    Internally calls the `register_node` endpoint to
    register current node with the node specified in the
    request, and sync the blockchain as well as peer data.
    """
    node_address = request.get_json()["node_address"]
    if not node_address:
        return "Invalid data", 400

    removed_slash = request.host_url.rstrip('/')

    data = {"node_address": removed_slash}
    headers = {'Content-Type': "application/json"}

    # Make a request to register with remote node and obtain information
    response = requests.post(node_address + "/register_node",
                             data=json.dumps(data), headers=headers)

    if response.status_code == 200:
        global blockchain
        global peers
        # update chain and the peers
        chain_dump = response.json()['chain']
        blockchain = create_chain_from_dump(chain_dump)
        peers.update(response.json()['peers'])
        return "Registration successful", 200
    else:
        # if something goes wrong, pass it on to the API response
        return response.content, response.status_code

#is able to create a chain from a given data dump,
#this will be able to create all the data and be the same as
#the node which they got the data from.
def create_chain_from_dump(chain_dump):
    generated_blockchain = Blockchain()
    generated_blockchain.create_genesis_block()
    for idx, block_data in enumerate(chain_dump):
        if idx == 0:
            continue  # skip genesis block
        block = Block(block_data["index"],
                      block_data["contracts"],
                      block_data["timestamp"],
                      block_data["previous_hash"],
                      block_data["nonce"])
        proof = block_data['hash']
        added = generated_blockchain.add_block(block, proof)
        if not added:
            raise Exception("The chain dump is tampered!!")
    return generated_blockchain


# endpoint to add a block mined by someone else to
# the node's chain. The block is first verified by the node
# and then added to the chain.
@app.route('/add_block', methods=['POST'])
def verify_and_add_block():
    block_data = request.get_json()
    block = Block(block_data["index"],
                  block_data["contracts"],
                  block_data["timestamp"],
                  block_data["previous_hash"],
                  block_data["nonce"])

    proof = block_data['hash']
    added = blockchain.add_block(block, proof)

    if not added:
        return "The block was discarded by the node", 400

    return "Block added to the chain", 201


# endpoint to query unconfirmed contracts
@app.route('/pending_contract')
def get_pending_contract():
    return json.dumps(blockchain.unconfirmed_contracts)

#endpoint to run a contract which will then be added to the list of
#contracts already ran. Prints contract output onto terminal.
@app.route('/run_contract')
def run():
    contractlist = []
    removed_slash = request.host_url.rstrip('/')
    resp = requests.get('{}/chain'.format(removed_slash))
    chainjson = resp.json()
    length = resp.json()['length']
    chain = resp.json()['chain']
    counter = 0
    while counter != length:
        firstatt = chain[counter]
        counter += 1
        if ("contracts" in firstatt):
            listtrans = firstatt["contracts"]
            #done each because there can be multiple transact in 1 block
            for each in listtrans:
                if (each['type'] != "contract"):
                    continue
                if (each['index'] in previously_ran_contracts):
                    continue
                if(each['runner'] == removed_slash):
                    contractlist.append(each)

    response = ""

    for contract in contractlist:
        data = contract['data']
        unhexed_data = binascii.unhexlify(data)
        f = open('temp/temp.pyc', 'wb+')
        f.write(unhexed_data)
        f.close()

        pyc_file = r"temp/temp.pyc"
        with open(pyc_file, "rb") as pyc:
            pyc.seek(8)  # skip the header
            code = marshal.load(pyc)  # unmarshal the pyc into code object(s)
            # exec(pyc.read())
            print("------------------------------")
            print("Running Contract Number " + str(contract['index']) + ':' + "\n")
            exec(code)
            print("\n------------------------------")

        response += ("Ran Contract Number " + str(contract['index']) + "<br>")

    if(response == ""):
        response = "No Contracts were ran<br>"

    response += "Contracts Previously ran: " + str(previously_ran_contracts)

    for each in contractlist:
        previously_ran_contracts.append(each['index'])

    return response

#if a longer is found then will simply replace ours with the new
#one. This will create a consensus and will constantly update the
#chain
def consensus():
    """
    Our naive consnsus algorithm. If a longer valid chain is
    found, our chain is replaced with it.
    """
    global blockchain

    longest_chain = None
    current_len = len(blockchain.chain)

    for node in peers:
        response = requests.get('{}/chain'.format(node))
        length = response.json()['length']
        chain = response.json()['chain']
        if length > current_len and blockchain.check_chain_validity(chain):
            current_len = length
            longest_chain = chain

    if longest_chain:
        blockchain = longest_chain
        return True

    return False

#will simply tell the whole network of peers when they have a mined
#a block and therefore added it. They will then verifiy it.
def announce_new_block(block):
    """
    A function to announce to the network once a block has been mined.
    Other blocks can simply verify the proof of work and add it to their
    respective chains.
    """
    for peer in peers:
        url = "{}/add_block".format(peer)
        headers = {'Content-Type': "application/json"}
        requests.post(url,
                      data=json.dumps(block.__dict__, sort_keys=True),
                      headers=headers)

# Uncomment this line if you want to specify the port number in the code
#app.run(debug=True, port=8000)
