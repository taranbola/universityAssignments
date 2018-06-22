# -*- coding: utf-8 -*-
"""
Created on Wed Nov 16 16:21:09 2016

@author: sc16tb
"""
import csv
from itertools import islice

def display_playlist(filename):
    with open(filename,"r") as file:
        playlist = csv.reader(file, delimiter=",")
        print("{0:25s}{1:25s}{2:25s}".format("TRACK"," ARTIST"," ALBUM"))
        for each in islice(playlist,1,6):
            print("{0:25s}{1:25s}{2:25s}".format(each[0],each[1],each[2]))
            
def make_html_playlist(filename):
 with open(filename,"w") as html:
     html.write("<html>\n")
     html.write("<head>\n")
     html.write("<meta charset=\"UTF-8\">\n")
     html.write("<style>\n")
     html.write("table, th, td{\n")
     html.write("border: 2px solid black;\n")
     html.write("border-collapse: collapse;\n")
     html.write("background-color: powderblue;}\n")
     html.write("</style>\n")
     html.write("<title>Geek Music Playlist</title>\n")
     html.write("</head>\n")
     html.write("<body>\n")
     html.write("<h2>Tracks of the playlist: geek-music</h2>\n")
     html.write("<table>\n")
     
     with open("geek-music.csv","r") as playlist:
         playlist.readline()
         html.write("<tr>\n")
         html.write("<th >TRACK</th>\n")	
         html.write("<th>ARTIST</th>\n")
         html.write("<th>ALBUM</th>\n")
         html.write("</tr>\n")
         
         reader = csv.reader(playlist)
         
         for row in reader:
             html.write("<tr>\n")
             for each in row:
                 html.write("<td>"+ each + "</td>\n")
             html.write("</tr>\n")
    
     html.write("</table>\n")
     html.write("</body>\n")
     html.write("</html>\n")
 
display_playlist("geek-music.csv");
make_html_playlist("index.html");
