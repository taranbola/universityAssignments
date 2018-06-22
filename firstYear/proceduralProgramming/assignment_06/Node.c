#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "Node.h"

WordNode *CreateFilledNode(char *string, float occurences)
	{
	WordNode *newNode = (WordNode *) malloc(sizeof(WordNode));		//checks the size of word to allocate memory

	newNode->word = strdup(string);						//the word is whatever string is 
	newNode->amount = occurences;						//the amount is whatever occurences is 

	newNode->nextWord = NULL;						//next word is null
	
	return newNode;								//it returns these values as a new node
	}

void PrintNode(WordNode *wordNode)	
	{
	printf("%20s: %.0f\n", wordNode->word, wordNode->amount);		//prints the new node with the word and occurences
	}


