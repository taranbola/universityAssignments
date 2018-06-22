#include <stdio.h>      //used for all I/O
#include <string.h>	//used for strlen
int main(void){
    int ascii[100]; //array which contains the ascii values of the letters which are inputted
    int y = 0;      //the column counter of the upper array
    int x = 0;	    //the length of the input counter of the upper array
    int z = 0;	    //the row counter of the upper array
    char input[100];	//the array which contains all of the values of the input
    char upper[7][570] = // the array which contains all of the ascii art values
	{
    	"        *    * *    * *   *   **     **       *   *     *                                     *   *     *   ***** ***** *   * ***** ***** ***** ***** *****                *         *      **   ***   ***  ****   **** ****  ***** *****  **** *   * ***** ***** *   * *     *   * *   *  ***  ****   ***  ****   **** ***** *   * *   * *   * *   * *   * *****  ***  *      ***    *         *           *               *         **        *                        **                                              *                                          **    *    **         ",
  	"        *    * *    * *  **** **  * *  *     *   *       *  * * *   *                        *  *   * * *       *     * *   * *     *         * *   * *   *   **    **    *           *       * *   * *   * *   * *     *   * *     *     *     *   *   *      *  *  *  *     ** ** **  * *   * *   * *   * *   * *       *   *   * *   * *   * *   * *   *     *  *     *       *   * *         *          *               *        *  *       *                         *                                              *                                         *      *      *        ",
  	"        *    * *  ***** * *      *  * *     *   *         *  ***    *                        *  *   *   *       *     * *   * *     *         * *   * *   *   **    **   *    *****    *      * *  ** *   * *   * *     *   * *     *     *     *   *   *      *  * *   *     ** ** **  * *   * *   * *   * *   * *       *   *   * *   * *   *  * *   * *     *   *     *       *  *   *         *    ***  *      ***      *  ***   *     **** *       *     *   *  *    *   ****  ****   ***  ****   **** * **   ***  ***** *   * *   * *   * *   * *   * *****  *      *      *   *    ",
  	"        *          * *   ***    *    *          *         *   *    ***        *****         *   *   *   *   ***** ***** ***** ***** *****     * ***** *****             *               *   **  * * * ***** ****  *     *   * ***** ***** * *** *****   *      *  **    *     * * * * * * *   * ****  *   * ****   ***    *   *   *  * *  * * *   *     *     *    *      *      *                        * ****  *   *  **** *   * ***** *   * ****              * *     *   * * * *   * *   * *   * *   * *   * *      *    *   * *   * *   *  * *   * *     *  *       *       * * * * ",
  	"        *         *****   * *  *    * * *       *         *  ***    *     **          **   *    *   *   *   *         *     *     * *   *     * *   *     *   **    **   *    *****    *    *   *  *  *   * *   * *     *   * *     *     *   * *   *   *      *  * *   *     *   * *  ** *   * *     * * * *   *     *   *   *   *  * *  ** **  * *    *    *     *       *     *                     **** *   * *     *   * *****  *     **** *   *   *     *   **      *   * * * *   * *   * ****   **** *      ***   *    *   *  * *  * * *   *     *     *    *      *      *     *  ",
  	"                  * *   ****  *  ** *  *         *       *  * * *   *     **          **   *    *   *   *   *         *     *     * *   *     * *   *     *   **    **    *           *         *     *   * *   * *     *   * *     *     *   * *   *   *   *  *  *  *  *     *   * *  ** *   * *     *  ** *   *     *   *   *   *  * *  ** ** *   *   *   *      *       *     *                    *   * *   * *   * *   * *      *        * *   *   *     *   **      *   * * * *   * *   * *         * *         *  *  * *   *  * *  * * *  * *   *     *     *      *      *        ",
  	"        *         * *     *      **  ** *         *     *                 *               *       *   ***** ***** *****     * ***** *****     * ***** *****         *      *         *      *    ***  *   * ****   **** ****  ***** *      ***  *   * *****  **   *   * ***** *   * *   *  ***  *      **** *   * ****    *    ***    *   *   * *   *   *   *****  ***      *  ***        *****        ***   ***   ***   ***   ***   *     ***  *   *  ***  **    *  *   ***  * * * *   *  ***  *       **  *      ***    **   ****   *    * *  * * * *     *****   **    *    **         "
	};

    while (input[x] = getchar())	// while loop that will that gets the value and puts it in the input array
	{
        if(input[x]=='\n')		//if input goes onto another line...
		{
		input[x]='\0';		//add the value null to the last index in the input array
		break;			//exits the while loop
		}
    	ascii[x] = (input[x] - 32)*6;	//works out the ascii code for each letter and puts it in the ascii array
    	x++;				//goes through each value by incrementing it
    	}

    x = 0;				//x is 0
    while (input[x] != '\0')		// while the input doesn't loop to the null value then...
	printf("%c",input[x++]);	//prints out each of the letters of the input
    printf("\n");			//prints empty line

    for (z = 0; z < 7; z++)		//from 0 to the end of the each row it will increment
	{
	for (x=0; x<strlen(input); x++)  //from 0 to to the length of the string it will increment
		{
		for (y = 0; y< 6; y++)	// from 0 to the length of 1 character plus the null value
			{
			printf("%c", upper[z][y+ascii[x]]);	// prints out the columns and rows of each value. loops through each value of ascii array
			}
		printf(" ");	//prints space
		}
    	printf("\n");	//prints a line break
    	}
    return 0;
}
