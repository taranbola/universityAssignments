#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "valueTree.h"                    //header file for the valueTree

int main( int argc, char **argv ){
  Node *head = makeNode( 0.0,0.0, 0);    // create the head node:  level 0
  makeChildren( head );                   // split to level 1
  makeChildren( head->child[2] );         // split one node to level 2

  growTree(head);                        //grows the tree
  //growLeaf(head);                        //grows using linked list
  indicate(head);                        //makes a data depedent quadtree
  writeTree(head);                       //writes the tree to the gnu
  //writeLeaf(head);                       //writes the linked list to the gnu
  destroyNode(head);                     //free's up to the nodes memory
  return 0;
}
