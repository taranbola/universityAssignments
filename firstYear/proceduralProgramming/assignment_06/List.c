#include <stdlib.h>
#include <string.h>		
#include <stdio.h>

#include "List.h"

void InitialiseList(WordList *wordList)				//initialises the list
	{
	wordList->topNode = NULL;				//the top value becomes null to start it			
	}

void PrintList(WordList *wordList)				//prints the list of values
	{
	WordNode *walkNode = wordList->topNode;			//the first value will be the top node

	printf("====================\n");

	for ( ; walkNode != NULL; walkNode = walkNode->nextWord )			//prints the list of values starting at top node
		{
		PrintNode(walkNode);							//calls the function printnode
		}

	printf("====================\n");
	printf("\n");
	}

void AddNodeToEnd(WordList *wordList, WordNode *wordNode)			//orders the nodes
	{
	WordNode *lastNode = wordList->topNode;					//the last node becomes the topnode
	if (lastNode == NULL)							//if its null..
		{
		wordNode->nextWord = NULL;					//next word is nulll

		wordList->topNode = wordNode;					//top node will become the word node
		}
	else
		{
		while (lastNode->nextWord != NULL)				//if it isn't null
			{
			lastNode = lastNode->nextWord;				//the last node becomes hte next word
			}

		lastNode->nextWord = wordNode;					//the last node becomes the next word

		wordNode->nextWord = NULL;					//the word willbecome null
		}
	}

void AddNode(WordList *wordList, WordNode *wordNode)
	{
	wordNode->nextWord = wordList->topNode;					//adds the top node as the next word
	
	wordList->topNode = wordNode;						//the top node becomes the word node
	}

int FindByKey(WordList *wordList, char *keyName)
	{
	WordNode *keyNode = wordList->topNode;					//top node becomes the keynode we are looking for

	while (keyNode != NULL)							//whilst thats not null
		{
		if (strcmp(keyNode->word, keyName) == 0)			//compares the keyname and word checked, if it's false
			{
			keyNode->amount++;					//increments by 1
			return keyNode;						//returns by 1
			}

		keyNode = keyNode->nextWord;					//the next word becomes the keynode
		}

	return keyNode;
	}

