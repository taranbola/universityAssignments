#include <stdio.h> 					//includes the standard input/output header file
#include <stdlib.h>					//includes the standard input/output header file
#include "decrypt.h" 					//includes the decrypt.h which is the header file for decrypt.c and calls stage 4 and 5  

void decrypt(int filesize, unsigned char *buffer){			//the decrypt function which takes filesize and buffer from main 
int key;								//int variable that will contain the key
int c;									//the c variable that will be used to loop through each character
int d;									//used to have the print to have the result of the calc to decrypt it
printf("\n\nThe Decrypted files are:");					//print message 
for (c=0;c<filesize; c++){ 						//loops through each character in the file to the filesize or end
	if (buffer[c-1] == '\0'){					//if the previous character is null...
		key = buffer[c];					//then the current character becomes the key
		printf("\n");						//print break after each key is worked out
		continue;						//goes to the next character,skips code below in for loop
	}
	if (buffer[c] == '\0'){						//if the buffer is null 
		continue;						//then it will skip the code below and go to next character.
	}
	if (key == 99){							//if the key is currently 99(keyless)...
		key = keyless(filesize, buffer,c); 			//runs the keyless function and changes the key they worked out.
	}
	buffer[d] = ((buffer[c] - key - 65 + 26) % 26) + 65;		//removes the key and 65 from buffer,then adds 26, then mods 26 to makes sure not above 26, adds 65...  
	printf("%c",buffer[d]);						//to get a captital letter value. Then prints out the result of this calculation.
}
printf("\n");      							//prints empty line
}

int keyless(int filesize, unsigned char *buffer, int c){			//the keyless function to work out the function without any key
int keynumber;									//the keynumber which is the number from 1-26 or A-Z, used to loop through
int filecounter;								//file that counters through each letter in the file
int letteriteration = 0;							//iterates through each letter to see if the more than the most common letter
int mostcommonletter = 0;							//becomes the most common letter everytime there is a higher letter iteration
int outputkey = 0;								//the end key which is returned 

for(keynumber=1; keynumber<26; keynumber++){					//for each letter from a to z
	for (filecounter=0; c+filecounter<filesize;filecounter++){		//for each letter from the letter it was currently on in decrypt to the end of the file 
		if (buffer[c+filecounter] != '\0'){ 				//if the current value in the buffer isn't null then...
			if (buffer[c+filecounter]-keynumber == 'E'){		//and if the current value in the buffer - the key is 'E'(65 )then...
				letteriteration++;				//then iterate letteriteration by 1
			}
		}
	}
	if (letteriteration >mostcommonletter)					//if the letteriteration occurs more times than the current most common letter
	{
		mostcommonletter = letteriteration;				//mostcommonletter becomes the the current letter iteration as it is larger
		outputkey = keynumber;						//the current keynumber between 1 and 26, becomes the output key
	}
	letteriteration = 0;							//we reset letteriteration back to 0
}
return outputkey;								//then return output key which will be used in the function decrypt	
}
