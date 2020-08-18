import requests
from requests import Request, Session
from http import cookies
from requests.exceptions import ConnectionError
s = requests.Session()
api = "none"

#This main function will run all the commands and call the appropriate functions.
#It will also handle any paramaters given and there processing. It will also
#do validation to make sure the user sends the correct amount of paramaters
def main():
    console = True
    while console == True:
        userInput = input("> ")
        userFuncs = userInput.split()

        if len(userInput) == 0:
            print("Please Enter a Command from register, login, logout, list, view, average, rate")
            continue
        if(userFuncs[0] == "login"):
            if(len(userFuncs) == 2):
                global api
                api = login(userFuncs[1])
            else:
                print("Enter 2 arguments, login then a url")
        if(userFuncs[0] == 'logout'):
            logout();
        if(userFuncs[0] == 'register'):
            register();
        if(userFuncs[0] == 'list'):
            list();
        if(userFuncs[0] == 'view'):
            view();
        if(userFuncs[0] == 'average'):
            if(len(userFuncs) == 3):
                average(userFuncs[1],userFuncs[2])
            else:
                print("Enter 3 arguments, average then the Professor ID then the Module Code")
        if(userFuncs[0] == 'rate'):
            if(len(userFuncs) == 6):
                rate(userFuncs[1],userFuncs[2],userFuncs[3],userFuncs[4],userFuncs[5])
            else:
                print("Enter 6 arguments: rate, Professor ID, Module Code, Year, Semester, Rating being Given")

#This function will register to the web service. It will make sure you enter
#the url of the webservice you are registering for, a username, email and a
#password. It will handle any errors in regards to the url being incorrect.
#It will send a post request to the service with the values given.
#It will display the message the service sends back.
def register():
    service = input("Enter the website service to register to: ")
    if (service == ""):
        print("You need to enter a Web Service.")
        return
    username = input("Enter an Username: ")
    if (username == ""):
        print("You need to enter a Username.")
        return
    email = input("Enter an Email Address: ")
    if (email == ""):
        print("You need to enter an Email Address.")
        return
    password = input("Enter a Password: ")
    if (password == ""):
        print("You need to enter a Password.")
        return
    try:
        r = s.post('http://' + service + '/api/register', data = {"email":email,"username":username,"password":password})
        if (r.status_code == 404):
            print("404 Error for url: " + service)
            return "none"
        else:
            print(str(r) + " " + str(r.status_code) + " " + str(r.text))
            return
    except ConnectionError as error:
        print("Can't connect to " + service)
        return

#This function will allow the user to login, it takes a paramater of the url
#the user entered. It will ask for a username and a password and it will then
#send a post request to the api and check any connection errors which may occur.
#It will then display if the login was acceptable.
def login(api):
    username = input("Enter an Username: ")
    if (username == ""):
        print("You need to enter an Username.")
        return "none"
    password = input("Enter a Password: ")
    if (password == ""):
        print("You need to enter a Password.")
        return "none"
    try:
        r = s.post('http://'+ api + '/api/login', data = {"username":username,"password":password})
        if (r.status_code == 404):
            print("404 Error for url: " + api)
            return "none"
        else:
            print(str(r) + " " + str(r.status_code) + " " + str(r.text))
            return api
    except ConnectionError as error:
        print("Can't connect to " + api)
        return "none"

#This logout function will check if the user has logged in and then will
#send a post request to the api to logout the user. It will then logout on the
#server side aswell.
def logout():
    global api
    if (api == "none"):
        print("You must be logged in order to logout")
        return
    else:
        r = s.post('http://' + api + '/api/logout')
        print(str(r) + " " + str(r.status_code) + " " + str(r.text))
        api = "none"
        return

#This will call the list function of the api and it will check the user has
#logged in. It will then a simply send a get request to the url and display
#the output of what is sent back.
def list():
    global api
    if (api == "none"):
        print("You must be logged in to use the list function")
        return
    else:
        r = s.get('http://' + api + '/api/list')
        print(str(r) + " " + str(r.status_code) + " " + str(r.text))
        return

#This is very similar to the list function except we are calling the view
#function instead and displaying what is sent back.
def view():
    global api
    if (api == "none"):
        print("You must be logged in to use the view function")
        return
    else:
        r = s.get('http://' + api + '/api/view')
        print(str(r) + " " + str(r.status_code) + " " + str(r.text))
        return

#The average function takes in 2 paramaters a professor_code and a module code.
#These are sent to the average function and will then it will display the
#contents of the return message. The user has to be logged in to use this.
def average(procode, modcode):
    global api
    if (api == "none"):
        print("You must be logged in to use the average function")
        return
    else:
        r = s.get('http://' + api + '/api/average', params = {"professor_code":procode,"module_code":modcode})
        print(str(r) + " " + str(r.status_code) + " " + str(r.text))
        return

#This rate function takes in a professor_code, module code, year, semester and
#the rating they are going to give. Once it has checked the user is logged in
#it will then send a post request to the function and display the contents back.
#These messages can confirm they have rated it tell the user there is a problem
# with the parameters they gave.
def rate(procode, modcode, year, sem, rating):
    global api
    if (api == "none"):
        print("You must be logged in to use the rate function")
        return
    else:
        r = s.post('http://' + api + '/api/rate', data = {"professor_code":procode,"module_code":modcode,"year":year,"semester":sem,"rating":rating})
        print(str(r) + " " + str(r.status_code) + " " + str(r.text))
        return

if __name__ == "__main__":
    main()
