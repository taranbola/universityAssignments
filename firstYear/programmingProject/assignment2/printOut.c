#include <stdio.h>
#include<stdlib.h>
#include <time.h>

void fileWrite( char *name[], int noOfElements, double timeTaken){
    time_t rawtime;
    struct tm *info;
    char buffer[80];         //used to generate the timestamp
    time( &rawtime );
    info = localtime( &rawtime );

    FILE *plot = fopen("plot.out", "a");            //opens the plot and prints
    fprintf(plot,"%s %f\n", name,timeTaken);        //the algorithms name and time

    FILE *fp = fopen("testLog.txt", "a");           //prints timeTaken, timestamp, name and amount of no's
    fprintf(fp,"\n%s: %s", name,asctime(info));     //to testLog
    fprintf(fp,"It took %f seconds, to sort %d numbers.\n", timeTaken,noOfElements);
    fclose(fp);
}

void bubblesortPrintOut(char *name[],int numberOfElements, int *array){
  clock_t start, end;
  double cpu_time_used;

  start = clock();
  bubblesort(numberOfElements, array);        //timing of how long bubblesort takes
  end = clock();
  cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
  printf("Time taken for bubblesort:%f\n",cpu_time_used);       //prints to terminal
  fileWrite(name,numberOfElements,cpu_time_used);               //writes to the files
}

void selectionsortPrintOut(char *name[],int n, int *array){
  clock_t start, end;
  double cpu_time_used;

  start = clock();
  selectionsort(n, array);                //timing of how long selectionsort takes
  end = clock();
  cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
  printf("Time taken for selectionsort:%f\n",cpu_time_used);   //prints to terminal
  fileWrite(name,n,cpu_time_used);                             //writes to the files
}

void quicksortPrintOut(char *name[],int first,int last,int *array){
  clock_t start, end;
  double cpu_time_used;

  start = clock();
  quicksort(first,last, array);          //timing of how long quicksort takes
  end = clock();
  cpu_time_used = ((double) (end - start)) / CLOCKS_PER_SEC;
  printf("Time taken for quicksort:%f\n",cpu_time_used);      //prints to terminal
  fileWrite(name,last+1,cpu_time_used);                       //write to the files
}

void errorMessage(){
  printf("\n!!ERROR!! Please restart program and enter a valid number.\n");
  exit(0);        //prints message and exits the program.
}
