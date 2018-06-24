module add anaconda3/5.0.1
source activate flask
cd website
[ -e app.db ] && rm app.db
python db_create.py
python db_populate.py
#Shell script will create a temp file of flask in anaconda removes a db
#if it exists. It will then create a blank db.
