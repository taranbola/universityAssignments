import requests
from requests import Request, Session
from http import cookies
import json
from requests.exceptions import ConnectionError
s = requests.Session()
url = "empty"
import datetime

def main():
    #This main function is where the for loop for picking each option is
    exit = True
    while(exit == True):
        user_input = input("> ")
        user_params = user_input.split()
        #if a user enters a certain command then it will run that
        #commands function
        if (len(user_params) == 0):
            invalid()
            continue
        if(user_params[0] == "login"):
            if(len(user_params) == 2):
                global url
                url = funcLogin(user_params[1])
            else:
                print("Enter 2 arguments, login then a url")
        if(user_params[0] == "logout"):
            funcLogout()
        if(user_params[0] == 'post'):
            funcPost();
        if(user_params[0] == 'list'):
            funcList()
        if(user_params[0] == 'news'):
            funcNews(user_params)
        if(user_params[0] == 'delete'):
            funcDelete(user_params)

def funcLogin(url):
    #asks them for a username,password then will try to login to a given domain
    #else it will say there is an error connnection if it fails
    username = input("Enter a username: ")
    password = input("Enter a password: ")
    try:
        r = s.post('http://' + url + '/api/login/', data = {"username":username,"password":password})
        print(str(r) + " " + str(r.status_code) + " " + str(r.text))
        return url
    except ConnectionError as e:
        print("Unable to make a connection to " + url)

def funcLogout():
    #if they are logged in it will send a logout request
    global url
    if(url == "empty"):
        print("You need to login in order to logout")
        return
    r = s.post('http://' + url + '/api/logout/')
    print(str(r) + " " + str(r.status_code) + " " + str(r.text))
    url = "empty"

def funcPost():
    #will allow you to post a story data, depending on if they are logged in.
    global url
    if(url == "empty"):
        print("You need to login in order to post")
        return

    headline = input("Enter a headline: ")
    category = input("Enter a category <tech,pol,art,trivia>: ")
    region = input("Enter a region <uk,eu,w>: ")
    details = input("Enter the details: ")
    story = {"headline":headline,"category":category,"region":region,"details":details}
    datasending = json.dumps(story)
    r = s.post('http://' + url + '/api/poststory/', data = datasending,headers={'Content-Type' : 'application/json'})
    print(str(r) + " " + str(r.status_code) + " " + str(r.text))

def funcNews(options):
    urlgiven = ""
    categories = ["pol","art","tech","trivia"]
    regions = ["uk","eu","w"]
    check = {"story_cat":"*","story_region":"*","story_date":"*"}
    #looks at the arguments given to determine what stories to get
    if len(options) > 1:
        options.remove("news")
        for each in options:
            sp = each.split("=")
            if len(sp) == 2:
                secondval = sp[1].replace('"','')
                #this will look for the category
                if (sp[0] == "-cat"):
                    secondval = secondval.lower()
                    if (secondval in categories):
                        check["story_cat"] = secondval
                    else:
                        print("Invalid -cat Argument")
                        return

                elif (sp[0] == "-reg"):
                    #this will look at the region
                    secondval = secondval.lower()
                    if (secondval in regions):
                        check["story_region"] = secondval
                    else:
                        print ("Invalid -reg Argument")
                        return

                elif (sp[0] == "-date"):
                    #this will try and create a date instance to see if it a valid
                    #date and will then add to data
                    try:
                        datetime.datetime.strptime(secondval,'%d/%m/%Y').date
                        check["story_date"] = secondval
                    except ValueError:
                        print("Incorrect date format, should be dd-mm-yyyy")
                        return

                elif (sp[0] == "-id"):
                    #this will look for an id given to get the url of it.
                    #it will then use that url to get stories from.
                    r = s.get('http://directory.pythonanywhere.com/api/list/',headers={'Content-Type' : 'application/json'})
                    aglist = json.loads(r.text).get("agency_list")
                    for each in aglist:
                        secondval = secondval.upper()
                        if(secondval in each["agency_code"]):
                            urlgiven = each['url']
                    if(urlgiven == ""):
                        print("Invalid -id Arguement")
                        return
                else:
                    print("You entered an invalid option")
                    return
            else:
                print("Invalid Argument format")

    datasending = json.dumps(check)
    if(urlgiven != ""):
        #validation to ensure that there will be no problems if incorrect
        #data is sent back.
        #This is for multiple urls.
        print(urlgiven + '/api/getstories/')
        requrl = requests.get(urlgiven + '/api/getstories/', data = datasending,headers={'Content-Type' : 'application/json'})
        if(requrl.status_code == 200):
            print(requrl.text)
        elif(requrl.status_code == 404):
            print("No stories from " + urlgiven + " could be found.")
        else:
            print(requrl.status_code)
            print("Unable to retrieve api data as website can't be accessed.")
    else:
        #This is only for 1 url.
        r = s.get('http://directory.pythonanywhere.com/api/list/',headers={'Content-Type' : 'application/json'})
        aglist = json.loads(r.text).get("agency_list")
        for each in aglist:
            localurl = each['url']
            requrl = requests.get(localurl + '/api/getstories/', data = datasending,headers={'Content-Type' : 'application/json'})
            if(requrl.status_code == 200):
                print("\n" + localurl + '/api/getstories/')
                print(requrl.text)
            elif(requrl.status_code == 404):
                print("\n" + localurl + '/api/getstories/')
                print("No stories from " + localurl + " could be found.")
            else:
                print("Unable to retrieve api data as " + localurl + " can't be accessed.")

def funcList():
    #prints the list of news directories
    r = s.get('http://directory.pythonanywhere.com/api/list/',headers={'Content-Type' : 'application/json'})
    print("Status Code: " + str(r.status_code))
    print(str(r.text))

def funcDelete(params):
    #if logged in it will allow you to delete a story key.
    if(url == "empty"):
        print("You need to login in order to delete a story")
        return
    if(len(params) == 2):
        try:
           val = int(params[1])
           if val > 0:
               story = {"story_key":params[1]}
               datasending = json.dumps(story)
               r = s.post('http://' + url + '/api/deletestory/', data = datasending,headers={'Content-Type' : 'application/json'})
               print(str(r) + " " + str(r.status_code) + " " + str(r.text))
           else:
               raise ValueError
        except ValueError:
           print("That's not a story key!")
    else:
        print("Please provide delete followed by a story key")

def invalid():
    #error message if they enter no commands
    print("""
    You can do the commands login with a given url,
    post , delete with a given story key, logout and news.
    news has the options -id, -cat, -reg and -date
    """)

if __name__ == "__main__":
    main()
