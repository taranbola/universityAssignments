#include<stdio.h>
#include<stdlib.h>
#include<time.h>
#include <ctype.h>

int main(){
   FILE *plot = fopen("plot.out", "w+");        //uses to overwrite the file everytime I run it.
   clock_t start, end;
   double cpu_time_used;
   int *data;                   //pointer to array of values.
   int counter,len, choice;
   char *names[3]= {"Bubblesort","Selectionsort","Quicksort"};    //array of names of algorithms.

    printf("Enter amount of numbers to sort: " );
    if(scanf("%d", &len) == 0){
      errorMessage();                     //user asks for amount of no. else it runs an errorMessage
    }

    data = (int*)calloc(len, sizeof(int));        //allocates memory depending on user input.

    for(counter=0;counter<len;++counter){
      data[counter] = 1 + rand() % (100 + 1 - 0);           //random;y generates the numbers
    }

    printf("1 for bubblesort, 2 for selectionsort, 3 for quicksort\n");
    printf("4 for bubblesort + selectionsort, 5 for bubblesort + quicksort\n");     //asks the user for choice of algorithms
    printf("6 for selectionsort + quicksort, 7 for all: ");
    scanf("%d",&choice);
    switch(choice) {
      case 1 :
        bubblesortPrintOut(names[0],len,data);      //just bubblesort
        break;
      case 2 :
        selectionsortPrintOut(names[1],len,data);     //just selectionsort
        break;
      case 3 :
        quicksortPrintOut(names[2],0,len-1,data);     //just quicksort
        break;
      case 4 :
        bubblesortPrintOut(names[0],len,data);        //both bubblesort and selectionsort
        selectionsortPrintOut(names[1],len,data);
        break;
      case 5 :
        selectionsortPrintOut(names[1],len,data);     //both selectionsort and quicksort
        quicksortPrintOut(names[2],0,len-1,data);
        break;
      case 6 :
        bubblesortPrintOut(names[0],len,data);        //both bubblesort and quicksort
        quicksortPrintOut(names[2],0,len-1,data);
        break;
      case 7 :
        bubblesortPrintOut(names[0],len,data);        //runs all the algorithms
        selectionsortPrintOut(names[1],len,data);
        quicksortPrintOut(names[2],0,len-1,data);
        break;
      default:
        errorMessage();                             //default errorMessage if not one of them
    }
    free(data);                                     //frees the memory
}
