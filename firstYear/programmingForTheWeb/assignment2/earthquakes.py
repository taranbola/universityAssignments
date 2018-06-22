import urllib.request

def get_text_lines_from_url( url ):
    url_data = urllib.request.urlopen(url)
    lines = url_data.readlines() # Read all lines into a list
    decoded_lines = []
    for line in lines:
        decoded_line = line.decode('utf-8')[0:-1]
        print( decoded_line )
        
        
get_text_lines_from_url('http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.csv');