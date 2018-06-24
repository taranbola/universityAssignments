from flask import Flask
from flask_sqlalchemy import SQLAlchemy     #import admin, flask and sql
from flask_admin import Admin

app = Flask(__name__)
app.config.from_object('config')            #launches config
db = SQLAlchemy(app)

admin = Admin(app,template_mode='bootstrap3')       #runs app with bootstrap3

from app import views, models
