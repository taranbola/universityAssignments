#include "Node.h"

struct word_list
	{
	WordNode *topNode;		//structure allows wordlist to have wordNode with the topnode
	};

typedef struct word_list WordList;	//allows word_list ti have wordList

void InitialiseList(WordList *wordList);	//calls the function initialise

void PrintList(WordList *wordList);		//calls the function printlist

void AddNodeToEnd(WordList *wordList, WordNode *wordNode);	//calls the function addnodetoend

void AddNode(WordList *wordList, WordNode *wordNode);		//calls the function addnode

int FindByKey(WordList *wordList, char *keyName);		//calls the function findbykey
