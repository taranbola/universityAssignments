
#include <stdio.h>
#include <stdlib.h>
#include <math.h>                   //libraries for input,output, power of
#include "valueTree.h"              //boolean and header file for c file
#include <stdbool.h>

Node *makeNode( double x, double y, int level ){      //makes the inital node...
  printf("makeNode\n");                               //head
  int i;
  Node *node = (Node *)malloc(sizeof(Node));  // allocate the data structure

  node->level = level;          // set the node data
  node->xy[0] = x;
  node->xy[1] = y;

  for( i=0; i<4; ++i )
    node->child[i] = NULL;          // set children to NULL

  return node;
}

void makeChildren( Node *parent ){        //function that makes children from the parent
  printf("makeChildren\n");
  double x = parent->xy[0];           //the parents data
  double y = parent->xy[1];
  int level = parent->level;          //makes the children from parents level
  int maxLevel = 10;
  if(level >= maxLevel){          //if the level is over the maxLevel of 8
      level = 9;                  //..the level is limited to 8
  }

  double hChild = pow(2.0,-(level+1));    // child edge length
  parent->child[0] = makeNode( x,y, level+1 );
  parent->child[1] = makeNode( x+hChild,y, level+1 );
  parent->child[2] = makeNode( x+hChild,y+hChild, level+1 );    //creates the children at...
  parent->child[3] = makeNode( x,y+hChild, level+1 );           //the level + 1 (below parent)

  parent -> child[3] -> nextLeaf = parent -> nextLeaf;            //linked list that goes through the
  parent -> nextLeaf = parent->child[0];                          //the list without actually going
  parent -> child[0] -> nextLeaf = parent -> child[1];            //through the parent
  parent -> child[1] -> nextLeaf = parent -> child[2];
  parent -> child[2] -> nextLeaf = parent -> child[3];

  return;
}

void printOut( FILE *fp, Node *node ) {          //creates the printf statement to be written to quad.gnu
  printf("printOut\n");
  double x = node->xy[0];
  double y = node->xy[1];                           // node data
  int level = node->level;
  double h = pow(2.0,-level);

  fprintf(fp, " %g %g\n",x,y);
  fprintf(fp, " %g %g\n",x+h,y);
  fprintf(fp, " %g %g\n",x+h,y+h);              //print out the corner points...
  fprintf(fp, " %g %g\n",x,y+h);                //for the plots
  fprintf(fp, " %g %g\n\n",x,y);

  return;
}

void writeTree( Node *head ){              // open a file and prepare to write
  printf("writeTree\n");
  FILE *fp = fopen("quad.out","w");       //the file it's writing to
  writeNode(fp,head);                     //runs write node function
  fclose(fp);                             //closes file
  return;
}

void writeNode( FILE *fp, Node *node ){    // recursively search for leaf nodes
  printf("writeNode\n");
  int i;
  if( node->child[0] == NULL )            //if child does't exist...
    printOut( fp, node );                 //runs printOut function
  else{
    for ( i=0; i<4; ++i ){
        writeNode( fp, node->child[i] );        //else, it'll run this again with
    }                                           //...new child being added
  }
  return;
}

void destroyNode( Node *node ){               //wipes memory of the node
  printf("destroyNode\n");
  int i;
  if( node->child[0] == NULL )
    free( node );                           //frees memory from the system
  else{
    for ( i=0; i<4; ++i ){
        destroyNode( node->child[i] );    //any children, destroys that memory
    }
  }
  return;
}
//---------------------------------TASK 2----------------------------------//

void growTree( Node *node ){              //grows the tree an extra level
  printf("growTree\n");
  int i;
  if( node->child[0] == NULL )          //if there is no child...
      makeChildren( node );             //makes one
  else{
      for ( i=0; i<4; ++i ){              //if there is less than 4 children
          growTree( node->child[i] );     //runs the function again, with new
      }                                   //new child
  }
  return;
}
//---------------------------------TASK 4----------------------------------//

void indicate( Node *node ){
  printf("indicate\n");
  int counter = 1;
  int i;
  while(counter != 0){                        //counter iterates through and
    counter = 0;                              //makes new nodes based on
    if( node->child[0] == NULL ){             //indicator parameters
        bool checking = indicator( node, 0.2, 1 );
        if ( checking == false ){
            makeChildren( node );
            counter++;
        }
    }
    else{
        for ( i=0; i<4; ++i ){                    //if it can't goes through
            indicate( node->child[i] );           //makes new children
        }
    }
  }
  return;
}

//---------------------------------TASK 3----------------------------------//

void growLeaf( Node *head ){          //grows the leaf using linked list
  printf("growLeaf\n");
  if( head->nextLeaf != NULL ){         //if the head doesn't have a value
      growLeaf(head->nextLeaf);         //then run the function again
  }
  if( head->child[0] == NULL ){         //if the child is null
      makeChildren( head );             //make children function called
  }
  return;
}

void writeLeaf( Node *head ){
  printf("writeLeaf\n");
  FILE *fp = fopen("quad.out","w");
  writeLeafNode(fp,head);           //same as writeTree but ueses writeLeafNode
  fclose(fp);                       //...instead
  return;
}

void writeLeafNode( FILE *fp, Node *node ){       //recursively looking for leaf
  printf("writeLeaf\n");                          //nodes without parent.
  printOut( fp, node );                           //then write it to printOut
  if( node->nextLeaf != NULL ){                 //writes the leaf
    writeLeafNode( fp, node->nextLeaf);
  }
  return;
}
