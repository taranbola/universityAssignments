from flask import render_template, flash, redirect, session, url_for, request, g
from app import app, db, admin
from flask.ext.admin.contrib.sqla import ModelView
from .forms import CreateForm                                   #all the imports needed for the functions
from app.models import Task
import datetime

admin.add_view(ModelView(Task, db.session))                     #creates the db

@app.route('/')
def index():                                                    #creates the index page
    return render_template('task.html',title='Todo List Application')

@app.route('/create_task', methods=['GET', 'POST'])
def create_task():
    form = CreateForm()                                      #creates a form to input task data
    if form.validate_on_submit():
       form_thing = Task(title=form.number1.data,description=form.number2.data, completed=False, date=datetime.datetime.utcnow())
       db.session.add(form_thing)
       db.session.commit()
       flash('Succesfully')
       return redirect(url_for('all_tasks'))
    return render_template('create_task.html', title='Todo List Application', form=form)

@app.route('/all_tasks')
def all_tasks():
    not_complete = Task.query.filter_by(completed=False).all()                  #creates the not_complete page
    return render_template('all_tasks.html',title='Todo List Application', not_complete=not_complete)

@app.route('/all_tasks/<id>')
def changeBool(id):
    task = Task.query.filter_by(id=int(id)).first()
    task.completed = True                                       #will send unclompeted task to completed
    db.session.commit()
    return redirect(url_for('all_tasks'))

@app.route('/all_completed_tasks')
def all_completed_tasks():
    complete = Task.query.filter_by(completed=True).all()                   #creates the completed page
    return render_template('all_completed_tasks.html',title='Todo List Application', complete=complete)

@app.route('/all_completed_tasks/<id>')
def remove(id):
    task = Task.query.filter_by(id=int(id)).first()                 #will delete the completed tasks
    db.session.delete(task)
    db.session.commit()
    return redirect(url_for('all_completed_tasks'))
