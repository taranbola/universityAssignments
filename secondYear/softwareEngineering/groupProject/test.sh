module add anaconda3/5.0.1
source activate flask
./dbcreate.sh
cd website
python test.py
#this will run the test script for the website.
