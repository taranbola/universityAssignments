
#include <stdio.h>
#include <stdlib.h>
#include <math.h>                     // libraries needed for node.c
#include <stdbool.h>

struct qnode{                   // data structure definition
  int level;
  double xy[2];
  struct qnode *child[4];
  int maxLevel;                 // node data
  struct qnode *nextLeaf;
};

typedef struct qnode Node;// data type definition

Node *makeNode( double x, double y, int level );
void makeChildren( Node *parent );
void printOut( FILE *fp, Node *node );            //declaration of all
void writeTree( Node *head );                     //functions in node.c
void writeNode( FILE *fp, Node *node );
void destroyNode( Node *node );

void growTree( Node *node );                      //task 2 function

void indicate( Node *node );                      //task 4 functions

void growLeaf( Node *node );
void writeLeaf( Node *head );                    //task 3 functions
void writeLeafNode( FILE *fp, Node *node );
