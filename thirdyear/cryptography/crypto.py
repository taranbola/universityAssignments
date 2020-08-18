def IC(y):
    freq = {"a":0,"b":0,"c":0,"d":0,"e":0,"f":0,"g":0,"h":0,"i":0,"j":0,"k":0,"l":0,"m":0,"n":0,"o":0,"p":0,"q":0,"r":0,"s":0,"t":0,"u":0,"v":0,"w":0,"x":0,"y":0,"z":0,}
    for letter in y:
        if letter in freq:
            freq[letter] += 1

    running_total = 0
    for each in freq:
        running_total += ( freq[each] * (freq[each]-1) )

    ic = (1 / ((len(y) * (len(y)-1)))) * running_total
    print(ic)

if __name__ == "__main__":
    IC("ffzicalpqhhrqhhaeooitoupawlpislrhiqxltkixbbwpoiekezhxrnedepeaepsoevmkivxbroiquvxeeuicouiyrdgboxvpeozbswsluuhrtlipaqhpoeixrryoshpsevxeawmctkiyrlxfskijplvbaqhftvglmpskwheitkpxswjlrdxeoxwxngcbauwjeqaflowqioppabxeivaxswlbiujfnhwqhryoxbduycbvzac")
