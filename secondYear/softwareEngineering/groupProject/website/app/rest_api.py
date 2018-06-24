from flask import render_template, flash, make_response, redirect, session, url_for, request
from app import app, db, admin
import  flask_restless
from .models import Card, Customer, Film, Screen, Screening, Seat, Staff, Ticket, Login


manager = flask_restless.APIManager(app, flask_sqlalchemy_db=db)

#Defining get put and post for all models and ignoring back referencing.
manager.create_api(Card, methods=['GET', 'POST','PUT', 'DELETE'])
manager.create_api(Customer, methods=['GET', 'POST','PUT', 'DELETE'])
manager.create_api(Film, methods=['GET', 'POST','PUT', 'DELETE'],exclude_columns=['screening', 'film_poster'])
manager.create_api(Screen, methods=['GET', 'POST','PUT', 'DELETE'],exclude_columns=['screening'])
manager.create_api(Screening, methods=['GET', 'POST','PUT', 'DELETE'],exclude_columns=['film','ticket','screen'],results_per_page=-1)
manager.create_api(Seat, methods=['GET', 'POST','PUT', 'DELETE'],exclude_columns=['ticket'])
manager.create_api(Staff, methods=['GET', 'POST','PUT', 'DELETE'])
manager.create_api(Ticket, primary_key='ticket_id' ,methods=['GET', 'POST','PUT', 'DELETE'], exclude_columns=['screening','customer','seat'],results_per_page=-1)
manager.create_api(Login, methods=['GET', 'POST', 'PUT', 'DELETE'])
