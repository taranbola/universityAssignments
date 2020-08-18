Name of python domain: sc16tb.pythonanywhere.com

username: ammar
password:webservice

Open a virtual environment, that is running python3 and has a version of django on it. 
You will need to pip install requests, as this a library used in my api.
Then run the client_api.py within this virtual environment by doing the command python client_api.py
To use the program you can use the basic commands given in the brief, examples below of each 
instruction.

login sc16tb.pythonanywhere.com
logout
list
news -id="TSB" -cat="pol" -reg="uk" -date="12/2/2019
post
delete 12

The client_api.py has a fair amount of validation, to ensure invalid arguments can't be used.
