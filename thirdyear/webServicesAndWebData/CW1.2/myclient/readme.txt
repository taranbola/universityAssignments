Name of python domain: sc16tb.pythonanywhere.com

username: ammar
password: helloworld

The first thing the person must do is enable a virtual environment on the DEC10 machines that is running python3 
and a version of Django on it. 
You will need to pip install requests as this a library that is used in my client. 
Then run myclient.py within this virtual environment by doing the command python myclient.py. 
To use the client there are some examples below of commands that can be entered;

register
login sc16tb.pythonanywhere.com
logout
list
view
average JE1 CD1
rate JE1 CD1 2017 1 5

The client has a good amount of validation, so it can ensure that invalid arguments can’t be used. 
The commands logout, list, view, average and rate cannot be used without being logged in. 
You can continuously enter commands in the client and the way to exit the program is by CTRL + c, 
which will kill the program. 
