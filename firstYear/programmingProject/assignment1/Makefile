
# code details

EXE_DIR = .
EXE = $(EXE_DIR)/main

SRC= node.c main.c valueTree.c

# generic build details

CC=      cc
COPT=    -O
CFLAGS=

# compile to  object code

OBJ= $(SRC:.c=.o)

.c.o:
	$(CC) $(COPT) -c -lm -o $@ $<

# build executable

$(EXE): $(OBJ)
	$(CC) $(OBJ) $(CFLAGS) -g -lm -o $(EXE)

# clean up compilation

clean:
	rm -f $(OBJ) $(EXE)

# dependencies

node.o:  node.c valueTree.h
main.o:  main.c node.h valueTree.h
valueTree.o:  valueTree.c valueTree.h
