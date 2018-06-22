struct word_struct
	{
	char *word;			//structure that creates a string for word and a floar for amount
	float amount;

	struct word_struct *nextWord;		//it will go to next word
	};

typedef struct word_struct WordNode;		//creates the wordnode structure

WordNode *CreateFilledNode(char *string, float occurences);		//calls the fillednode

void PrintNode(WordNode *wordNode);					//calls the printnode function

