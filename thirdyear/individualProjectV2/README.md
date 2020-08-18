# Decentralized Smart Contract Network

A Decentralized Smart Contract Network is a blockchain that enables smart 
contracts to be ran and stored on there. This is different to other networks as
it only has one smart contract node running the contract. This is more of a
PoS (Proof of Stake) model instead of the traditional PoW (Proof of Work).

## Installation

The installation will need to be on Python version 2.7.17 with Flask version 
1.1. The version for the requests package has to be 2.22 or above. The uncompyle
package has to be uncompyle 6 and no earlier versions.

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install the
following;

```bash
pip install Flask
pip install uncompyle6
pip install requests
```
Note: The program works best on Linux Ubuntu but may vary on different OS's

## Running

First you must make sure that nothing is running on the following ports;

```bash
127.0.0.1:8000
127.0.0.1:8001
127.0.0.1:8002
127.0.0.1:5000
```

Note: For whatever reason you can't change this, port numbers can be changed
manually in the shell script and the Flask servers.

First you must run the script.sh on a terminal. On linux you can use the following 
commands, firstly to make it executable and then run it;

```bash
chmod +x script.sh
./script.sh
```

This should run the 3 servers (`8000,8001 and 8002`) and the Flask interface we
will interact with (`5000`). You should be able to interact with the Flask 
interface by simply typing in [http://127.0.0.1:5000](http://127.0.0.1:5000).

You can exit this interface by simply typing in `CTRL + C`. This will not stop the
servers though so `killall flask` should be used instead. This can vary from OS to
OS and there may be other commands.

## Node/Server Endpoints

There are many different accessible endpoints on each node/server's side. In 
terms of server this can be for `8000`, `8001` and `8002`. The following endpoints can 
be accessed through a simple browser (Using a GET request).

###### [/chain](http://127.0.0.1:8000/chain) - Access the raw JSON data of the blockchain<br>
###### [/mine](http://127.0.0.1:8000/mine) -  Mine a new block on that node/server<br>
###### [/pending_contract](http://127.0.0.1:8000/pending_contract) - See all the Pending Contracts waiting to be mined <br>
###### [/run_contract](http://127.0.0.1:8000/run_contract) - Will run any outstanding contracts on that node/server <br>

## Flask Endpoints

These are the main accessible endpoints that can be found on the Flask Interface
side. There are others which are found in the code which are POST requests
whilst these are all GET requests.

###### [/](http://127.0.0.1:5000/) - The homepage for the Flask interface<br>
###### [/rep](http://127.0.0.1:5000/rep) - To see the rep of all the node/servers using the blockchain<br>
###### [/sourcecode/(insert contract index)](http://127.0.0.1:5000/sourcecode/0) - Get the raw .py code for a specific contract <br>

## Testing

There are some basic unit tests that are done on the program. First you will need to
do the running of the shell script `script.sh`. Before any contracts are added
you can then run the tests to see if that everything is correct. You can do it 
by running the command;

```bash
python test.py
```

There are 10 tests and they should all pass. If you create a contract afterwards
and then rerun the commmand, 9 will pass and 1 will fail. This is not a problem 
at all.

## Licence

Taranvir Bola<br>
University of Leeds
