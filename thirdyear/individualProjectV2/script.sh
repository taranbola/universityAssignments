export FLASK_APP=node_server.py
flask run --port 8000 &
flask run --port 8001 &
flask run --port 8002 &
cd samplecontracts
python compilecontracts.py
cd ..
sleep 2
python addnode.py
python run_app.py
