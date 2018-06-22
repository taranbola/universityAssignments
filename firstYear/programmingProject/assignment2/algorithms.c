#include <stdio.h>
#include<stdlib.h>
#include <time.h>

int bubblesort(int numberOfElements, int *array){
  int eachElement, sortingElement, swappingElement;

  for ( eachElement=0; eachElement < ( numberOfElements-1 ); eachElement++ ){
    for ( sortingElement=0; sortingElement < numberOfElements - eachElement - 1; sortingElement++ ){
      if ( array[sortingElement] > array[sortingElement+1] ){       //goes through each of the values
        swappingElement = array[sortingElement];                    //and the adjacent one and swaps
        array[sortingElement] = array[sortingElement+1];            //if they are in the wrong order.
        array[sortingElement+1] = swappingElement;
      }
    }
  }

  // printf("Sorted list using bubble sort:");
  // for ( eachElement = 0; eachElement < numberOfElements; eachElement++ )
  //    printf( " %d", array[eachElement] );
  return 0;
}

int selectionsort(int n, int *array){
    int i,steps,temp;

    for(steps=0;steps<n;++steps){
      for(i=steps+1;i<n;++i){
           if(array[steps]>array[i]){         //compares all unsorted number to
               temp=array[steps];             //get smallest one, then swaps,
               array[steps]=array[i];         //with the first number,
               array[i]=temp;                 //repeat until completed.
           }
      }
    }

    // printf("Sorted List using Selection Sort:");
    // for(i=0;i<n;++i){
    //     printf("%d  ",array[i]);
    // }

    return 0;
}

double quicksort(int first,int last,int *array){

  int i, j, pivot, temp;

   if(first<last){
      pivot=first;
      i=first;
      j=last;                             //it picks a pivot as the first
                                          //element, then adds the larger numbers
      while(i<j){                          //after it, the smaller numbers
         while(array[i]<=array[pivot]&&i<last)      //before it. 
            i++;
         while(array[j]>array[pivot])
            j--;
         if(i<j){
            temp=array[i];
            array[i]=array[j];
            array[j]=temp;
         }
      }

      temp=array[pivot];
      array[pivot]=array[j];
      array[j]=temp;
      quicksort(first,j-1,array);
      quicksort(j+1,last,array);

   }
}
