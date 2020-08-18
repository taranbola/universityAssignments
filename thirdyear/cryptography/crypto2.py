def IC(d,y):
    average = 0
    freqarray = []
    n = int(len(y) / d)
    x = 0
    while (x != d):
        tempfreq  = {"a":0,"b":0,"c":0,"d":0,"e":0,"f":0,"g":0,"h":0,"i":0,"j":0,"k":0,"l":0,"m":0,"n":0,"o":0,"p":0,"q":0,"r":0,"s":0,"t":0,"u":0,"v":0,"w":0,"x":0,"y":0,"z":0,}
        counter = 0
        for letter in y:
            if ((counter % d) == x):
                if letter in tempfreq:
                    tempfreq[letter] += 1
            counter += 1
        freqarray.append(tempfreq)
        x += 1

    for freq in freqarray:
        running_total = 0
        for each in freq:
            if(freq[each] != 0):
                running_total += ( freq[each] * (freq[each]-1) )
        ic = (1 / ((n * (n-1)))) * running_total
        average += ic

    average = average / d
    print("d : "+ str(d))
    print("IC average : "+ str(average))

if __name__ == "__main__":
    IC(2,"ffzicalpqhhrqhhaeooitoupawlpislrhiqxltkixbbwpoiekezhxrnedepeaepsoevmkivxbroiquvxeeuicouiyrdgboxvpeozbswsluuhrtlipaqhpoeixrryoshpsevxeawmctkiyrlxfskijplvbaqhftvglmpskwheitkpxswjlrdxeoxwxngcbauwjeqaflowqioppabxeivaxswlbiujfnhwqhryoxbduycbvzac")
