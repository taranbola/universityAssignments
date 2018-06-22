
# code details

EXE_DIR = .
EXE = $(EXE_DIR)/main

SRC= main.c algorithms.c printOut.c

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

algorithms.o:  algorithms.c algorithms.h
main.o:  main.c algorithms.h
printOut.o:  printOut.c printOut.h
