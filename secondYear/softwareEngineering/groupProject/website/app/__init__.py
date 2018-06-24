from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_admin import Admin
from flask_mail import Mail

app = Flask(__name__)
mail = Mail(app)
#This is the config of the email for the
app.config['MAIL_SERVER']='smtp.gmail.com'
app.config['MAIL_PORT'] = 465
app.config['MAIL_USERNAME'] = 'teamosprey18@gmail.com'
app.config['MAIL_PASSWORD'] = 'osprey18'
app.config['MAIL_USE_TLS'] = False
app.config['MAIL_USE_SSL'] = True
mail = Mail(app)
app.config.from_object('config')

db = SQLAlchemy(app)

admin = Admin(app,template_mode='bootstrap3')

from app import views, models, rest_api
