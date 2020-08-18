from bs4 import BeautifulSoup
import requests
import json
from time import sleep
json_dictionary = {}

#This main function will loop through and keep letting you enter
#commands. It has a good amount of validation inbuilt within it.
#You can enter the following commands:
#   build - builds the inverted index
#   load - loads the inverted index but can only be ran once build has
#   load without - loads the inverted index without build (has to
#                  have invertedindex.json in the current directory)
#   print - prints a given words inverted index but can only be ran
#           once load has been ran
#   find - finds a given word or set of words shared urls and outputs
#          them in a ranked order based on frequency of the words.
#   exit - will exit the program
def main():
    exit = False
    build = False
    loaded = False

    while(exit == False):
        user_input = input("> ")
        user_params = user_input.split()
        if (len(user_params) == 0):
            invalid()
            continue

        if(user_params[0] == "exit"):
            exit = True

        if(user_params[0] == "build"):
            crawlerthread("http://example.webscraping.com")
            json_dictionary = {}
            build = True

        if(len(user_params) == 1):
            if(user_params[0] == 'load'):
                if (build == True):
                    funcLoad()
                    loaded = True
                else:
                    print("You need to have ran build to do this command.")

        if(len(user_params) == 2):
            if(user_params[0] == "load" and user_params[1] == "without"):
                print("Loading the file system without the build command...")
                funcLoad()
                loaded = True

        if (loaded == True):
            if(user_params[0] == 'print'):
                if(len(user_params) == 2):
                    funcPrint(user_params[1])
                else:
                    print("Enter only 2 arguments; print then a word")
            if(user_params[0] == "find"):
                if(len(user_params) >= 2):
                    funcFind(user_params)
                else:
                    print("Enter at least 2 arguments, find then a search value")

#This is the main crawler that will build the inverted index.
#It will parse a given url excluding certain redirected urls.
#Then generate the words and build the .json list by calling other
#functions. It will have a politeness delay of 5 seconds.
def crawlerthread(frontier):
    #these urls are excluded as they redirect to already
    #accessed urls. This is for efficency reasons.
    URL1 = 'places/default/user/login?_next=/places/default/'
    URL2 = 'places/default/user/register?_next=/places/default/'
    URL3 = 'places/default/user/login?_next=/places/default/index'
    URL4 = 'places/default/user/register?_next=/places/default/index'
    seenurls = []
    listofurls = ['/places/default/index']

    for each in listofurls:
        if each in seenurls:
            continue
        else:
            newfrontier = frontier + each
            r1 = requests.get(newfrontier)
            content = BeautifulSoup(r1.content ,features="html.parser")
            print("Currently crawling: " + str(each))

            for l in content.find_all('a'):
                link = l.get('href')
                if ( URL1 in link) or ( URL2 in link) and ( URL3 != link and URL4 != link):
                    continue
                else:
                    listofurls.append(link)
            foundwords = getwords(content)
            buildList(foundwords,newfrontier)
            seenurls.append(each)
            listofurls.remove(each)
            sleep(5)

#This will generate a list of words, given some html content.
#It looks for things in specific html tags and removes other
#unneeded content.
def getwords(content):
    tags = ['h1','h2','h3','td','li']
    words = []
    for each in tags:
        for type in content.find_all(each):
            for string in type.text.split():
                string = string.replace(':','')
                words.append(string)

    return words

#This will generate the frequency of the urls for a given word.
#It will then write it to a file called invertedindex.json_file
#This will the file needed to load the inverted index
def buildList(foundwords,frontier):
    global json_dictionary
    for each in foundwords:
        if each not in json_dictionary:
            json_dictionary[each] = {frontier: 1}
        else:
            if frontier in json_dictionary[each]:
                json_dictionary[each][frontier] += 1
            else:
                json_dictionary[each][frontier] = 1

    with open('invertedindex.json', 'w') as invertedindex:
        json.dump(obj=json_dictionary, fp=invertedindex, indent=2)

#This will load the invertedindex.json file into the
#variable json_dictionary which will be used to access
#the inverted index from other functions
def funcLoad():
    global json_dictionary
    with open('invertedindex.json') as json_file:
        data = json.load(json_file)
        json_dictionary = data
    print("Loaded the index from the file system ")

#This will print the inverted index of a given word.
#Quite a simple program that searches the json_dictionary
#for the word and outputs the index
def funcPrint(word):
    counter = 0
    for each in json_dictionary:
        if(each == word):
            for url in json_dictionary[word]:
                print(str(url) + " : " + str(json_dictionary[word][url]))
            return
    print ("Unable to find the word")

#This will print the shared urls of a given set of words
# It will then print them in a ranking order of the frequency
#of them within the page
def funcFind(words):
    words.remove("find")

    list_of_links =[]
    new_links = []
    finished_results = {}

    for word in words:
        links = json_dictionary.get(word)
        if links is None:
            print("Couldn't find " + str(word))
            continue
        else:
            for link in links:
                list_of_links.append(link)

    for each in list_of_links:
        correct = 0
        for word in words:
            links = json_dictionary.get(word)
            if links is None or each not in links:
                correct += 1
        if (correct == 0):
            if each not in new_links:
                new_links.append(each)

    for each in new_links:
        counter = 0
        for word in words:
            links = json_dictionary.get(word)
            count = links.get(each)
            counter = counter + count
        finished_results[each] = counter

    for key, value in sorted(finished_results.items(), reverse=True, key=lambda item: item[1]):
        print(f"{key} : {value}")

#A simple function that outputs an error message
def invalid():
    print("""
    You can do the commands build, load, print, find and exit.
    You can load without a build by typing load without however
    this will need need to have the file invertedindex.json in
    the current directory. You need to have loaded the program
    first to run the print or find commands.
    """)

if __name__ == "__main__":
    main()
