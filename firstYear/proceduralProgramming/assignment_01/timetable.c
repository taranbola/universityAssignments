/*
You should complete your assignment in here
*/
#include <stdio.h> //this is the library
int main (int argc, char **argv){  //this is the main function that outputs the info
printf( //prints the output 
"Comp1711- Monday,10am-11am \n" //done 
"Comp1011- Monday,15pm-17pm \n"//done 
"Comp1421- Tuesday, 11am-12pm \n"//done 
"Comp1711- Tuesday,13pm-14pm \n"//done 
"CompTutorial- Wednesday,10am-11am \n"//done   the different output of the list 
"Comp1711- Wednesday,11am-13pm \n"//done
"Comp1911- Thursday,10am-11pm \n"//done
"Comp1421- Thursday,11am-12pm \n"//done
"Comp1421- Thursday,14pm-15pm \n"//done 
"Comp1211- Thursday,16pm-17pm \n"//done 
"Comp1911- Friday,10am-11pm \n"//done
"Comp1211- Friday,11am-12pm \n"//done 	
"COMP Level 1 Employability- Friday,12pm-13pm \n"
"\n"

"|                    |  Monday            |  Tuesday           |  Wednesday         |   Thurday          |   Friday           |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|\n"
"|                    |Comp1711            |                    |CompTutorial        |Comp1911            |Comp1911            |\n"
"|                    |Procedural          |                    |                    |Proffessional       |Proffesional        |\n"
"|                    |Programming         |                    |                    |Computing           |Computing           |\n"
"|                    |Chem LT B (2.17)    |                    |Long Room           |ChemWBlock LT F G.74|RogerSt LT 17 (7.17)|\n"   //the timetable
"|10:00am - 11:00am   |Carr,Hamish,Dr      |                    |Duggleby,Gillian    |Duggleby,Gillian    |Duggleby,Gillian    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|\n"
"|                    |                    |Comp1421            |Comp1711            |Comp1421            |Comp1211            |\n"
"|                    |                    |Fundamental Maths   |Procedural          |Fundamental Maths   |Computer            |\n"
"|                    |                    |Concepts            |Programming         |Concepts            |Architecture        |\n"
"|                    |                    |RogerS LT 25 (12.25)|DEC 10              |EC Stoner Lab       |RogerSt LT 17 (7.17)|\n"
"|11:00am - 12:00pm   |                    |Adler,Isolde,Dr     |Carr,Hamish,Dr      |Wilson,Sam          |McEvoy,Kevin,       |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|\n"
"|                    |                    |                    |Comp1711            |                    |Comp Employability  |\n"
"|                    |                    |                    |Procedural          |                    |                    |\n"
"|                    |                    |                    |Programming         |                    |                    |\n"
"|                    |                    |                    |DEC 10              |                    |RogerSt LT 17 (8.18)|\n"
"|12:00pm - 13:00pm   |                    |                    |Carr,Hamish,Dr      |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|\n"
"|                    |                    |Comp1711            |                    |                    |                    |\n"
"|                    |                    |Procedural          |                    |                    |                    |\n"
"|                    |                    |Programming         |                    |                    |                    |\n"
"|                    |                    |ChemWBlock LT F G.74|                    |                    |                    |\n"
"|13:00pm - 14:00pm   |                    |Carr,Hamish,Dr      |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|\n"
"|                    |                    |                    |                    |Comp1421            |                    |\n"
"|                    |                    |                    |                    |Fundamental Maths   |                    |\n"
"|                    |                    |                    |                    |Concepts            |                    |\n"
"|                    |                    |                    |                    |Chem LT B (2.17)    |                    |\n"
"|14:00pm - 15:00pm   |                    |                    |                    |Adler,Isolde,Dr     |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|\n"
"|                    |Comp1011            |                    |                    |                    |                    |\n"
"|                    |Programming For The |                    |                    |                    |                    |\n"
"|                    |Web                 |                    |                    |                    |                    |\n"
"|                    |SocialScience(10.11)|                    |                    |                    |                    |\n"
"|15:00pm - 16:00pm   |Bennett,Brandon,Dr  |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|\n"
"|                    |Comp1011            |                    |                    |Comp1211            |                    |\n"
"|                    |Programming For The |                    |                    |Computer            |                    |\n"
"|                    |Web                 |                    |                    |Architecture        |                    |\n"
"|                    |SocialScience(10.11)|                    |                    |Roger S LT 17 (7.17)|                    |\n"
"|16:00pm - 17:00pm   |Bennett,Brandon,Dr  |                    |                    |McEvoy,Kevin,Dr     |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|                    |                    |                    |                    |                    |                    |\n"
"|--------------------|--------------------|--------------------|--------------------|--------------------|--------------------|\n");	      
;return 0;} // closes the brackets and returns a value of 0







