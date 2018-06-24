from flask_wtf import Form
from wtforms import TextAreaField, StringField
from wtforms.validators import DataRequired             #imports needed for form
from .models import Task

class CreateForm(Form):
    number1 = StringField('number1', validators=[DataRequired()])   #the title field of form
    number2 = TextAreaField('number2', validators=[DataRequired()]) #the description field
