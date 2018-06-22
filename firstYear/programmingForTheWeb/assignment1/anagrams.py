def anagram(str1,str2):                             #1
    if sorted(str1.lower())== sorted(str2.lower()):
        print(True)
    else:
        print(False)

def test_anagram():                                     #2
    print("<-------------test_anagram---------->")
    anagram("hello","HEllo");
    anagram("hello","hi");
    anagram("bonjour","bonjour");
    print("The First input is hello and HEllo should be True.")
    print("The second input is hello and hi should be False.")
    print("The third input is bonjour and bonjour should be True.")
def get_dictionary_wordlist():                      #3
    with open('dictionary.txt') as file:
        lines = [word.strip() for word in file]
    print (len(lines))        
    for each in lines[0:10]:
        print(each)
    return lines

def test_get_dictionary_wordlist():                 #4              
    print("<-------------test_get_dictionary_wordlist---------->")
    get_dictionary_wordlist();
    
def find_anagrams_in_wordlist(str, str_list ):          #5
    for each in str_list:
        if sorted(str)== sorted(each):
            print(each)
        else:
            print("")
        
def find_anagrams(str):                         #6
    with open('dictionary.txt') as file:
        lines = [word.strip() for word in file]
    for each in lines:
        if sorted(str)== sorted(each):
            print(each)
            
def test_find_anagrams():                           #7
    print("<-------------test_find_anagrams---------->")
    find_anagrams("course");
    print("")
    find_anagrams("salt");   
    
def sub_anagram(str1,str2):                     #8
    s2 = list(str2)
    try:
        for char in str1:
            s2.remove(char)
    except ValueError:
        return False
    return True
    
def find_sub_anagram_in_wordlist(str, str_list):        #9
    for each in str_list:
        s2 = list(str)
        try:
            for char in each:
                s2.remove(char)
            print(each)
        except ValueError:
            continue

def test_find_sub_anagram_in_wordlist():                #10
    print("<-------------test_find_sub_anagram_in_wordlist---------->")
    find_sub_anagram_in_wordlist("function", ["in","tin","function","jwf","hello","func"]);
    print ("The list above should have in, tin, function and func. These are all subanagrams of the word function.")
    print("")
    find_sub_anagram_in_wordlist("source", ["sour","rce","jfl","our","hello","dfsf"]);
    print ("The list above should have sour, rce, function and our. These are all subanagrams of the word source.")

def remove_letters(str1, str2):                     #11
    s1 = list(str1)
    s2 = list(str2)
    for each in s1:
        s2.remove(each)
    print(''.join(s2))

def find_two_word_anagrams(str):                   #12
  newlevel=[]
  space = " "
  level=[]
  str = str + space
  length = len(str)
  with open('dictionary.txt') as file:
        lines = [word.strip() for word in file]
  def permute(prefix, suffix):
    level.append(prefix)
    if len(suffix)==0:
       return
    for i in range(len(suffix)):
      permute(prefix + suffix[i], suffix[:i]+suffix[i+1:])
  permute("",str)
  level.remove("")
  for each in level:
      if len(each) == length:
          if each.startswith(' ') or each.endswith(' '):
              continue
          else:
              newlevel.append(each)
              
  for element in newlevel:
    element = element.split(' ')
    print (element)
    for each in lines:
        if each == element[0]:
            for deach2 in lines:
                if deach2 == element[1]:
                    print(element[0],element[1])
        else:
            continue
      
  return element

test_anagram();
test_get_dictionary_wordlist();
test_find_anagrams();
test_find_sub_anagram_in_wordlist();
