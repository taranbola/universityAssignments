#include <stdio.h>		//include the standard io library
#include <ctype.h>		//includes the library to use isalpha to find the letter
#include <string.h>		//library to handle strings
#include <stdlib.h>		//the standard library 
#include "List.h"		//list header file that calls all the functions


int main(int argc, char* argv[])
	{
	int increment;				//counts if there is a word currently there
	int counter= 0;				//counts through each letter in the file or input
	int count = 1;				//count to ensure that it'll continue running even if there is no letters
	char input;				//where the current letter is held
	char wordtemp[250]; 			//tempoararily holds up to a 250 letter word

WordList myList;				//creates my list as list where the nodes will be held

InitialiseList(&myList);			//initalises the list by calling that function

for(counter=0;counter<count;counter++){		//for loops through each letter
  	while((input = getchar())!= EOF)	//goes through each letter until the end of the 
	{
		if (isalpha(input)== 0){	//this will see if it is false and if it is...
			count++;		//count is incrementeed
			break;			//breaks out of the code 
		}
		wordtemp[increment] = tolower(input);		//turns the wordtemp to lowercase
		increment++;					//increments bt 1
	}
	if (FindByKey(&myList,wordtemp)!= 0)			//looks for key if its false
	{
		increment++;					//increments by 1
	}
	else							//else it will 
	{
		if(strlen(wordtemp) > 0)			//of the strlen is more than 1 
		{
			AddNode(&myList, CreateFilledNode(wordtemp,1));			//then it will add this node
		}
	}
	memset(wordtemp,0,strlen(wordtemp));				//removes the memory of this current string 
	increment=0;								//increments is reset to 0
}

PrintList(&myList);				//prints the list of values

}


