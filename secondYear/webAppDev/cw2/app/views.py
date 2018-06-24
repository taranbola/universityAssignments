from flask import render_template, flash, redirect, session, url_for, request, make_response
from app import app, db, admin
from flask_admin.contrib.sqla import ModelView
from .forms import CreateForm, SessionForm, SignupForm, PasswordForm         #all the imports needed for the functions
from app.models import Task,User
import datetime

@app.route('/', methods = ['GET', 'POST'])
def login():
	message=""
	form = SessionForm()
	if form.validate_on_submit():										#when form is submitted...
		variableFind = User.query.filter_by(username=form.value.data).first()
		if variableFind:
			if variableFind.password == form.pw.data:					#validation to check user data
				session['variable'] = form.value.data				#creates the session
				return redirect(url_for('create_task'))
			else:
				f = open("log.txt","a") 					#writes to log.txt attemped login
				f.write(str(datetime.datetime.utcnow()) + "\tAttempted Login with Username:" + form.value.data)
				f.write(" and Password:" + form.pw.data + "\n")
				f.close()
		message="Invalid Username or Password"
	return render_template('session.html',title='Kanban Software Planning Board',form=form, message = message)

@app.route('/unsetvariable')
def logout():
	session.pop('variable', None)					#gets rid of the session
	return redirect(url_for('login'))

@app.route('/signup', methods=['GET', 'POST'])
def signup():
	message=""
	user = User.query.all()
	signform = SignupForm()										#use validation for forms
	if signform.validate_on_submit():
		variableFind = User.query.filter_by(username=signform.username.data).first()
		if variableFind:
			message="Username Already Taken"
		else:
			username_form = User(username=signform.username.data,password=signform.password.data)
			db.session.add(username_form)									#checks user data and adds to db
			db.session.commit()
			return redirect(url_for('create_task'))
	return render_template('signUp.html', title='Kanban Software Planning Board',signform=signform,user=user,message=message)

@app.route('/password', methods=['GET', 'POST'])
def password():
	message=""
	passwordchange = PasswordForm()
	if passwordchange.validate_on_submit():
		variableFind = User.query.filter_by(username=passwordchange.changeusername.data).first()
		if variableFind:
			if variableFind.password == passwordchange.changepassword.data:						#validation for user input
				variableFind.password = passwordchange.newpassword.data
				db.session.commit()
				return redirect(url_for('login'))
			else:
				flash("Unable to change Password")
				f = open("log.txt","a") #opens file with name of "log.txt"
				f.write(str(datetime.datetime.utcnow()) + "\tAttempted Password change with Username:" + passwordchange.changeusername.data)
				f.write(" and Password:" + passwordchange.changepassword.data)							#adds to log.txt
				f.write(" and New Password:" + passwordchange.newpassword.data + "\n")
				f.close()
		message="Invalid Username or Password"
	return render_template('password.html', title='Kanban Software Planning Board',passwordchange=passwordchange,message = message)

@app.route('/create_task', methods=['GET', 'POST'])
def create_task():
	if 'variable' not in session:
		return redirect(url_for('login'))
	else:
	    form = CreateForm()                                      #creates a form to input task data
	    if form.validate_on_submit():
	       form_thing = Task(description=form.number2.data, kanban=1, date=datetime.datetime.utcnow())
	       db.session.add(form_thing)
	       db.session.commit()
	       return redirect(url_for('input_queue'))
	    return render_template('create_task.html', title='Kanban Software Planning Board', form=form)

@app.route('/input_queue')
def input_queue():
	if 'variable' not in session:
		return redirect(url_for('login'))
	else:
		kanban1 = Task.query.filter_by(kanban=1).all()                  #creates the input_queue page
		return render_template('inputQueue.html',title='Kanban Software Planning Board', kanban1=kanban1)

@app.route('/input_queue/<id>')
def changeto2(id):
    task = Task.query.filter_by(taskID=int(id)).first()
    task.kanban = 2
    db.session.commit()
    return redirect(url_for('input_queue'))							#creates the button for the changing kanban value to 2

@app.route('/analysis')
def analysis():
	if 'variable' not in session:
		return redirect(url_for('login'))
	else:
		kanban2 = Task.query.filter_by(kanban=2).all()                  #creates the analysis page
		return render_template('analysis.html',title='Kanban Software Planning Board', kanban2=kanban2)

@app.route('/analysis/<id>')
def changeto3(id):
    task = Task.query.filter_by(taskID=int(id)).first()
    task.kanban = 3
    db.session.commit()
    return redirect(url_for('analysis'))		#creates the button for the changing kanban value to 3

@app.route('/development')
def development():
	if 'variable' not in session:
		return redirect(url_for('login'))
	else:
		kanban3 = Task.query.filter_by(kanban=3).all()                  #creates the development page
		return render_template('development.html',title='Kanban Software Planning Board',kanban3=kanban3)

@app.route('/development/<id>')
def changeto4(id):
    task = Task.query.filter_by(taskID=int(id)).first()
    task.kanban = 4
    db.session.commit()
    return redirect(url_for('development'))				#creates the button for the changing kanban value to 4

@app.route('/build_ready')
def build_ready():
	if 'variable' not in session:
		return redirect(url_for('login'))
	else:
		kanban4 = Task.query.filter_by(kanban=4).all()                  #creates the buildReady page
		return render_template('buildReady.html',title='Kanban Software Planning Board', kanban4=kanban4)

@app.route('/buildReady/<id>')
def changeto5(id):
    task = Task.query.filter_by(taskID=int(id)).first()
    task.kanban = 5
    db.session.commit()
    return redirect(url_for('build_ready'))							#creates the button for the changing kanban value to 5

@app.route('/test')
def test():
	if 'variable' not in session:
		return redirect(url_for('login'))
	else:
		kanban5 = Task.query.filter_by(kanban=5).all()                  #creates the test page
		return render_template('test.html',title='Kanban Software Planning Board', kanban5=kanban5)

@app.route('/test/<id>')
def changeto6(id):
    task = Task.query.filter_by(taskID=int(id)).first()
    task.kanban = 6
    db.session.commit()
    return redirect(url_for('test'))										#creates the button for the changing kanban value to 6

@app.route('/release_ready')
def release_ready():
	if 'variable' not in session:
		return redirect(url_for('login'))
	else:
		kanban6 = Task.query.filter_by(kanban=6).all()                  #creates the release ready page
		return render_template('releaseReady.html',title='Kanban Software Planning Board', kanban6=kanban6)

@app.route('/release_ready/<id>')
def remove(id):
    task = Task.query.filter_by(taskID=int(id)).first()                 #will delete the completed tasks
    db.session.delete(task)
    db.session.commit()
    return redirect(url_for('release_ready'))
