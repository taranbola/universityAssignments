# -*- coding: utf-8 -*-
from flask import render_template, flash, make_response, redirect, session, url_for, request
from app import app, db, admin
from sqlalchemy.sql import func
from flask_bcrypt import Bcrypt
from flask_admin.contrib.sqla import ModelView
from flask_mail import Mail, Message
from .forms import SessionForm, SignupForm, PasswordForm, CardForm, CheckoutForm, SearchForm, ChangeCustomerForm
from app.models import Customer,Card,Film,Screen,Screening,Login,Seat,Staff,Ticket
import datetime,pyqrcode,cairosvg
mail=Mail(app)
bcrypt = Bcrypt(app)
#specified imports are for qrcode, pdf creator, email and encryption

@app.route('/confirm')
def confirm():
    #will simply load the confirm page in html
    return render_template('confirm.html', title='Successful Transaction')

@app.route('/search',methods=['GET', 'POST'])
def search():
    #This will create empty film queries.
    films = Film.query.filter(Film.film_name.like("")).all()
    screenings = Screening.query.filter(Screening.screening_date.like("")).all()
    searchform= SearchForm()
    if searchform.validate_on_submit():
        #This will search the queries for searchform data as a substring
        films = Film.query.filter(Film.film_name.contains(searchform.search.data)).all()
        screenings = Screening.query.filter(Screening.screening_date.contains(searchform.search.data)).all()
    return render_template('search.html', title='Search',searchform=searchform,films=films,screenings=screenings)

@app.route('/customer',methods=['GET', 'POST'])
def customer():
    if 'variable' not in session:
        return redirect(url_for('login'))
    value = session['variable']
    #if someone is logged in they can't access this and will go to /login
    variableFind = Login.query.filter_by(login_email=value).first()
    customer = Customer.query.filter_by(customer_id=variableFind.customer_id).first()
    #it will find the customer id
    customerform= ChangeCustomerForm()
    message=""
    #if customer enters valid details it will change the values in db
    if customerform.validate_on_submit():
        findMobile = Customer.query.filter_by(customer_mobile=customerform.mobile.data).first()
        if findMobile:
            message = "Mobile number is already being used"
        else:
            variableFind = Login.query.filter_by(login_email=value).first()
            customer = Customer.query.filter_by(customer_id=variableFind.customer_id).first()
            customer.customer_f_name = customerform.firstname.data
            customer.customer_s_name= customerform.surname.data
            customer.customer_dob = customerform.dob.data
            customer.customer_mobile = customerform.mobile.data
            customer.customer_address = customerform.address.data
            customer.customer_postcode = customerform.postcode.data
            db.session.commit()
            return redirect(url_for('myaccount'))
    return render_template('customer.html', title='Change Customer Details',customerform=customerform,message=message)

@app.route('/changepassword',methods=['GET','POST'])
def changepassword():
    message = ""
    passwordform = PasswordForm()
    #if the username/old password are valid it will change the password
    #and will hash it as well. Else it will say it is an invalid username.
    if passwordform.validate_on_submit():
        variableFind = Login.query.filter_by(login_email=passwordform.changeusername.data).first()
        if variableFind:
            if (bcrypt.check_password_hash(variableFind.login_password, passwordform.changepassword.data) == True):						#validation for user input
                pw_hash = bcrypt.generate_password_hash(passwordform.newpassword.data)
                variableFind.login_password = pw_hash
                variableFind.login_hint = passwordform.hint.data
                db.session.commit()
                return redirect(url_for('logout'))
        message="Invalid Username or Password"
    return render_template('changepassword.html', title='Change Password',passwordform=passwordform,message=message)

@app.route('/')
def index():
    #This will render the homepage of the site.
    return render_template('index.html', title='Home')

@app.route('/whatson')
def whatson():
    #This will display all the films in the db.
    film = Film.query.all()
    return render_template('whatson.html', title='What is on?',film=film)

@app.route('/myaccount')
def myaccount():
    if 'variable' not in session:
        return redirect(url_for('login'))
    value = session['variable']
    #redirects to /login if not logged in.
    variableFind = Login.query.filter_by(login_email=value).first()
    if variableFind:
        #It will output this data on the myaccount page.
        customer = Customer.query.filter_by(customer_id=variableFind.customer_id).first()
        card = Card.query.filter_by(customer_id=variableFind.customer_id).first()
        tickets = Ticket.query.filter_by(customer_id=variableFind.customer_id).all()
    return render_template('myaccount.html', title='My Account',customer=customer,tickets=tickets,card=card)

@app.route('/unsetvariable')
def logout():
	session.pop('variable', None)					#gets rid of the session
	return redirect(url_for('index'))

@app.route('/movie/<movieID>')
def movie(movieID):
    current = datetime.date.today()
    #will send 7 days worth of data to the movie page
    film = Film.query.filter_by(film_id=movieID).first()
    screening = Screening.query.filter_by(film_id=movieID,screening_date=current).order_by(func.Time(Screening.screening_time)).all()
    screeningtomorrow = Screening.query.filter_by(film_id=movieID).filter_by(screening_date=(current + datetime.timedelta(days=1))).order_by(func.Time(Screening.screening_time)).all()
    screeningplus2 = Screening.query.filter_by(film_id=movieID).filter_by(screening_date=(current + datetime.timedelta(days=2))).order_by(func.Time(Screening.screening_time)).all()
    screeningplus3 = Screening.query.filter_by(film_id=movieID).filter_by(screening_date=(current + datetime.timedelta(days=3))).order_by(func.Time(Screening.screening_time)).all()
    screeningplus4 = Screening.query.filter_by(film_id=movieID).filter_by(screening_date=(current + datetime.timedelta(days=4))).order_by(func.Time(Screening.screening_time)).all()
    screeningplus5 = Screening.query.filter_by(film_id=movieID).filter_by(screening_date=(current + datetime.timedelta(days=5))).order_by(func.Time(Screening.screening_time)).all()
    screeningplus6 = Screening.query.filter_by(film_id=movieID).filter_by(screening_date=(current + datetime.timedelta(days=6))).order_by(func.Time(Screening.screening_time)).all()
    return render_template('movie.html', title='Movie', film=film, screening=screening, screeningtomorrow=screeningtomorrow,screeningplus2=screeningplus2,screeningplus3=screeningplus3,screeningplus4=screeningplus4,screeningplus5=screeningplus5,screeningplus6=screeningplus6)

@app.route('/seatchoice/<screeningID>')
def seatchoice(screeningID):
    #access by the url of screeningID.
    #uses that to display the screening ID information.
    #If it is an invalid screening it will redirect to whatson.
    screening = Screening.query.filter_by(screening_id=screeningID).first()
    if screening:
        film = Film.query.filter_by(film_id=screening.film_id).first()
        capacity = Screen.query.filter_by(screen_id=screening.screen_id).first()
        seats = Seat.query.all()
    else:
        return redirect(url_for('whatson'))
    return render_template('seatchoice.html', title='Choose Seat',screening=screening, film=film, seats=seats)

@app.route('/checkout/<screeningID>/<seatID>',methods=['GET', 'POST'])
def checkout(screeningID,seatID):
    price = ""
    if 'variable' not in session:
        return redirect(url_for('login'))
    value = session['variable']

    #This will get the customer information and will then redirect if
    #the seat has alread been taken.
    variableFind = Login.query.filter_by(login_email=value).first()
    if variableFind:
        customer = Customer.query.filter_by(customer_id=variableFind.customer_id).first()
        cardFind = Card.query.filter_by(customer_id=variableFind.customer_id).first()
        screening = Screening.query.filter_by(screening_id=screeningID).first()
        film = Film.query.filter_by(film_id=screening.film_id).first()
        seatID = int(seatID)
        seat = Seat.query.filter_by(seat_id=seatID).first()
        ticketTaken = Ticket.query.filter_by(seat_id=seat.seat_id).first()
        if ticketTaken:
            if ticketTaken.seat_id == seat.seat_id:
                if ticketTaken.screening_id == screening.screening_id:
                    return redirect(url_for('seatchoice', screeningID=screening.screening_id))


        #checks if user allowed to watch this film and will set the price
        #structure if over 65.
        age = ""
        ratings = {'U': 4, 'PG': 8, '12': 12,'15': 15,'18': 18}
        if str(film.film_age_rating) in ratings:
            if (agefinder(customer.customer_dob) < (ratings[str(film.film_age_rating)]) ):
                age = "You are underage to watch this film"
        if seatID < 10 and agefinder(customer.customer_dob) <= 64:
            price = "7.50"
        elif seatID < 10 and agefinder(customer.customer_dob) > 64:
            price = "6.00 (Over 65 Discount)"
        elif seatID > 10 and agefinder(customer.customer_dob) > 64:
            price = "3.50 (Over 65 Discount)"
        else:
            price = "5.00"

        checkoutform = CheckoutForm()
        cardform = CardForm()
        #if either of the forms are valid it will create the ticket
        #it will then create the email with some data and a specific qr code.
        #It converts the svg ticket to a pdf, which is sent with the email.
        if checkoutform.validate_on_submit() or cardform.validate_on_submit():
            another_form = Ticket(customer_id=customer.customer_id,screening_id=screening.screening_id,seat_id=seatID)
            db.session.add(another_form)
            db.session.commit()

            info = [film.film_name,str(screening.screening_date),str(screening.screening_time)
                    ,str(seat.seat_id), str(screening.screen_id),
                    str(customer.customer_f_name + " " + customer.customer_s_name)]
            subject = 'Osprey Cinema: Ticket for ' + info[0] + ' on ' + info[1]
            content = ("<h1>Ticket Information<h1>" + "<br> <b>Customer: </b>" + info[5]
                 + "<br> <b>Film: </b>" + info[0]  + "<br> <b>Date: </b>" + info[1]
                 + "<br> <b>Time: </b>" + info[2] + "<br> <b>Seat: </b>" + info[3]
                 + "<br> <b>Screen: </b>" + info[4] + "<br> <b>Price:  </b>" + price)

            ticketinfo = ("<Osprey Cinema Valid Ticket> \nCustomer Name: " + info[5]
                       + "\nScreening Date: " + info[1] + "\nScreening Time: " + info[2]
                       + "\nScreen Number: " + info[4] + "\nSeat Number: " + info[3]
                       + "\nPrice: " + price)
            ticketcode = pyqrcode.create(ticketinfo, error='L', version=8, mode='binary')
            ticketcode.svg('app/static/img/ticket.svg', scale=8)
            cairosvg.svg2pdf(url='app/static/img/ticket.svg', write_to='app/static/img/ticket.pdf')

            msg = Message(subject, sender = 'Osprey Cinema', recipients = [value])
            msg.html = content
            with app.open_resource("static/img/ticket.pdf") as fp:
                msg.attach("ticket.pdf", "application/pdf", fp.read())
            mail.send(msg)

            return redirect(url_for('confirm'))
    return render_template('checkout.html', title='Checkout', price=price,customer=customer,film=film, seat=seat, screening=screening, checkoutform=checkoutform, cardform=cardform, age=age, cardFind=cardFind)

@app.route('/login', methods=['GET', 'POST'])
def login():
    if 'variable' in session:
        return redirect(url_for('myaccount'))
    #redirects to the myaccount page if logged in
    message=""
    sessionform = SessionForm()
    #checks the username and the password to allow you to login.
    #It will decrypt the hash of the password to see.
    if sessionform.validate_on_submit():
        variableFind = Login.query.filter_by(login_email=sessionform.login.data).first()
        if variableFind:
            if (bcrypt.check_password_hash(variableFind.login_password, sessionform.password.data) == True):
                session['variable'] = sessionform.login.data				#creates the session
                return redirect(url_for('myaccount'))
        message="Invalid Username or Password"
    return render_template('login.html', title='Login', sessionform=sessionform,message=message)

@app.route('/signup', methods=['GET', 'POST'])
def signup():
    i = 0
    message1 = ""
    message2 = ""
    signform = SignupForm()
    #some extra validation to check email and mobile number aren't already
    #being used if you attempt to signup.
    if signform.validate_on_submit():
        variableFind = Login.query.filter_by(login_email=signform.email.data).first()
        findMobile = Customer.query.filter_by(customer_mobile=signform.mobile.data).first()
        if variableFind:
            message1 = "Email is already being used"
            i = 1

        if findMobile:
            message2 = "Mobile number is already being used"
            i = 2

        if i == 0:
            form_thing = Customer(customer_f_name=signform.firstname.data,customer_s_name=signform.surname.data,customer_dob=signform.dob.data,customer_mobile=signform.mobile.data,customer_address=signform.address.data,customer_postcode=signform.postcode.data)
            db.session.add(form_thing)
            db.session.commit()
            #this will hash the password in the db.
            pw_hash = bcrypt.generate_password_hash(signform.confirm.data)
            another_form = Login(customer_id=form_thing.customer_id,login_email=signform.email.data,login_password=pw_hash,login_hint=signform.hint.data)
            db.session.add(another_form)
            db.session.commit()
            return redirect(url_for('login'))
    return render_template('signup.html', title='Sign up',signform=signform,message1=message1,message2=message2)

@app.route('/card', methods=['GET', 'POST'])
def card():
    message = ""
    if 'variable' not in session:
        return redirect(url_for('login'))
    value = session['variable']
    #have to be logged in to access page.
    variableFind = Login.query.filter_by(login_email=value).first()
    cardform = CardForm()
    if cardform.validate_on_submit():
        #this will ensure the card number isn't already being used.
        numberFind = Card.query.filter_by(card_number=decode(cardform.number.data)).first()
        if numberFind:
            message = "Card Number is already being used"
        else:
            #if it isn't it will add the card to a customer.
            cardFind = Card.query.filter_by(customer_id=variableFind.customer_id).first()
            expirydate = int(str(cardform.expirymonth.data) + str(cardform.expiryyear.data))
            if cardFind:
                #it will replace a previous card.
                cardFind.card_number=encode(cardform.number.data)
                cardFind.card_expiry=encode(expirydate)
                cardFind.card_cvv=encode(cardform.cvv.data)
                db.session.commit()
                return redirect(url_for('myaccount'))
            else:
                #or it will add a brand new card.
                form_thing = Card(customer_id=variableFind.customer_id,card_number=encode(cardform.number.data),
                card_expiry=encode(expirydate),card_cvv=encode(cardform.cvv.data))
                db.session.add(form_thing)
                db.session.commit()

                return redirect(url_for('myaccount'))

    return render_template('card.html', title='Card', cardform=cardform,message=message)

def agefinder(dob):
    #will find an age from a dob
    today = datetime.date.today()
    return today.year - dob.year - ((today.month, today.day) < (dob.month, dob.day))

def seatfinder(seat,screening):
    #will find if a seat has been taken.
    ticket = Ticket.query.filter_by(screening_id=screening.screening_id).all()
    if ticket:
        for each in ticket:
            if each.seat_id == seat.seat_id:
                return True
    else:
        return False

def timepassed(time):
    #will see if the screening time has passed.s
    now = datetime.datetime.now().time()
    if now < time:
        return False
    else:
        return True

def encode(number):
    #encodes card information
	encoded = number * 2
	encoded = encoded + 40
	encoded = encoded / 4
	return encoded

def decode(number):
    #decodes card information.
    decoded = number * 4
    decoded = decoded - 40
    decoded = decoded / 2
    return decoded

app.jinja_env.globals.update(seatfinder=seatfinder)
app.jinja_env.globals.update(timepassed=timepassed)
app.jinja_env.globals.update(decode=decode)
#these functions are used in jinja2
