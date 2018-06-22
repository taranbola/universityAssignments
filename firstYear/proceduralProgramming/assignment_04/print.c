#include <stdio.h> 						//includes the standard input/output header file
#include "print.h" 						//include the print.h file which initialises the functions
void printHexadecimal(int nBytes, unsigned char *data){ 	// routine to print out in hexidecimal format

int x = 0; 							//counter variable through each byte of data
int y = 0; 							//used to count each variable and get to the end of the line 
int z = 0; 							//counter used to work out the offset

for (x=0; x<nBytes; x++){ 					//for loop goes through each byte of data

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
	if(*(data+x)<16){					//if the data is less than 16 
		printf("0%x ", *(data+x));			//then it will add 0 to the character as it prints out a blank space
	}
	else{							//otherwise it will
	printf("%x ", *(data+x));				//print out the data normally
	} 
y++;								//y counter increments through each loop
}
printf("\n");							//prints new line once for loop finished
}

void printDecimal(int nBytes, unsigned char *data){		// routine to print out in decimal format
unsigned short * pdata = (unsigned short*) data; 		//changes the *data variable to a new unsigned short variable called * pdata
int x = 0;    						 	//counter that goes through each byte
int z = 0;							//counter that will print the offset
int a = 0; 							//counter that will see wo 

for (x=0; x<(nBytes/2); x++){ 					//for loop goes through half the bytes of data
	if (z == 0){						//it will print 
		printf("%06x\t", z);				//it will print the first off set 000000
		z=z+16;						//this will add 16 to the off set counter
	}
	if (a == 8){						//if a is 8 
		printf("\n");					//prints an empty liine	
		printf("%06x\t", z);				//prints the offset for every line after 0
		a=0;         					//resets a to 0 after 8 lines 
		z=z+16;						//adds 16 to z 
	}
		
printf("%05d ", *pdata);					//prints the decimel value 
*pdata++;							//goes to the next value in pdata
a++;								//increments a 
}
printf("\n"); 							//goes to the next line 
}

void printCharacter(int nBytes, unsigned char *data){		// routine to print out in character format

int x = 0;							//this is a counter that goes through each byte 
int a = 0;							//counter that is used to count through each letter to the corresponding byte
int b = 0;							//used to print a out every 8 lines
int c = 0;							//counter used to work out the remainder on the last line  
int d = 0;							//used to loop through to print the remaining spaces at the end of the code 
char ascii;							//used to find the ascii values and only print relevant characters

for (x=0; x<nBytes; x++){					//for loops through each byte of data

	if (x == 0){						//for loop that prints out the first offset 
		printf("%0.6x ", x);
	}
	printf("%.02x ", *(data+x)); 				//prints out the the byte of data in hex

	if ((x+1)%8 == 0 && x!=0){				//prints out every time x reaches a denomination of 8 that = 0
		printf("|");					//prints a seperator 
		for (a=b; a<(x+1); a++){			//loops through each value in the a 
			ascii= *(data+a);			//checks to see if this value..
			if (ascii <32||ascii>126){		//is not in the ascii limit
				printf(".");			//if it does it replaces it with .
			}
			else{					//if it isn't..
				printf("%c", *(data+a));	//it will print it normally
			}		
		}
		printf("|");					// prints seperator
		b=b+8;		  				//adds 8 every time the for loop finishes
		printf("\n");					//goes to next line
		printf("%0.6x ", (x+1));			//prints the offset of the next line
	}
								//THIS IS IF STATEMENT IS FOR THE LAST LINE 
	if (x==(nBytes-1)){					//this will see if this the last value in bytes
		c = (x+1)%8;					//if this is it will work out.. 
		c = 8-c;					//the remainder opf the last line, subsequentlty the spaces left 
		for (d=0; d<c; d++){				//for loop that prints...
			printf("   ");				//spaces to make it print out properly.
		}
		printf("|");					//prints seperator
		for (a=b; a<(x+1); a++){			//loops through each value in a 
			ascii= *(data+a);			//checks to see if this ascii value
			if (ascii <32||ascii>126){		//see if in limit
				printf(".");			//if it is, it prints .
						}
			else{					//else...
			printf("%c", *(data+a));		//it prints normally
			}	
	}
	for (d=0; d<c; d++){					//prints the remaining characters at the end
			printf(" ");				//prints the space
	}			
	printf("|");						//prints seperator
	printf("\n");						//goes to next line
}
}								// © Taranvir Bola 10/11/2016
}							


