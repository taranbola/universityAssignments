// Code for hexdump assignment
//
// Hamish Carr, University of Leeds, 2016
//
// This unit has three similar routines for printing out data
//
// hexdump xxxx:		Standard hexadecimal output (16 columns)
// hexdump -c xxxx:		Single character output (16 columns)
// hexdump -d xxxx:		Two byte integer output	(8 columns)

// routine to print out in hexadecimal format
void printHexadecimal(int nBytes, unsigned char *data);

// routine to print out in decimal format
void printDecimal(int nBytes, unsigned char *data);

// routine to print out in character format
void printCharacter(int nBytes, unsigned char *data);

