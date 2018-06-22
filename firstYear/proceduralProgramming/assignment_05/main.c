#include <stdio.h> 					//includes the standard input/output header file
#include <stdlib.h>					//this is the standard library
#include "print.h"					//includes the print.h which is the header file for print.c and calls stage 3 and hex code 
#include "decrypt.h" 					//includes the decrypt.h which is the header file for decrypt.c and calls stage 4 and 5 

int main(int arg, char *argv[]){			//the main function which takes 2 arguements, runs all the other modules here
FILE *f= fopen(argv[1],"r");				//opens the file that they have opened
if (argv[1]== NULL ||argv[2]!= NULL ||f == NULL){	//sees if the file isn't null, doesn't have 2 or more parameters and that it can actually open the file
	printf("ERROR loading file, either no file or too many parameters\n");		//prints error message
	exit(0);									//terminates the program
}


fseek(f, 0, SEEK_END);									//goes the end of the file and adds to counter to find the end
long filesize = ftell(f);								//this stores the end value index in the variable filesize					
rewind(f);										//goes back to the beginning 
unsigned char *buffer = (unsigned char *) malloc(filesize);				//this allocates memory using the filesize to store the buffer variable
long nread = fread((void*)buffer, 1, filesize, f); 					//this reads the file and stores the contents in buffer
if (nread != filesize){									//long nread is the number of lines it has to read, this should be the ...			
	printf("Couldn't read file");							//same as filesize, if it isn't it the same, it says an error message.. 
	exit(0);									//and terminates
}
fclose(f);										//closes the file
printHexadecimal(filesize, buffer);							//calls the printHexadecimal function
print(filesize, buffer);								//calls the print function
decrypt(filesize, buffer);								//calls the decrypt function	
}


