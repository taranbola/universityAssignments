#include <stdio.h> 					//includes the standard input/output header file		
#include <stdlib.h>					//this is the standard library
#include "print.h" 					//includes the print.h which is the header file for print.c and calls stage 3 and hex code 

void printHexadecimal(int filesize, unsigned char *buffer){ 	// routine to print out in hexidecimal format

int x = 0; 							//counter variable through each byte of data
int y = 0; 							//used to count each variable and get to the end of the line 
int z = 0; 							//counter used to work out the offset

for (x=0; x<filesize; x++){ 					//for loop goes through each byte of data

	if (z == 0){ 						//if statement 
		printf("%0.6x ", z);  				//which prints the first offset of 000000
		z++;      					//increments offset counter 
	}		
	if (y == 8){   						 //if statement which counts how many bytes of data is being printed out
		printf(" ");					//prints space
	}
	if (y == 16){						//if statement which when gets to 16 bytes of data...
		printf("\n");					//goes to new line
		y=0;						//resets y to 0
		printf("%0.6x ", z);				//prints the next lines offset
		z++;						//increments offset counter
	}
	if(*(buffer)<16){					//if the data is less than 16 
		printf("%0.2x ", *(buffer+x));			//then it will add 0 to the character as it prints out a blank space
	}
	else{							//otherwise it will
	printf("%x ", *(buffer+x));				//print out the data normally
	} 
y++;								//y counter increments through each loop
}
printf("\n");							//prints new line once for loop finished
}

void print(int filesize, unsigned char *buffer){		//the print function which takes filesize and buffer from main 
int key;							//the int variable for the key 
int c;								//the int variable c is used to count through each character in the text file 
for (c=0;c<filesize; c++){					//the for loop that counts through each character in the text file to the end
	if (buffer[c-1] == '\0'){				//if the previous character was nulll...
		key = buffer[c];				//the key will become this character
		printf("\nThe key is %d--",key);		//prints out the key
	}

	else{							//else...
		printf("%c",buffer[c]);				//it will print out the whole cipher
	}	
}	
}

