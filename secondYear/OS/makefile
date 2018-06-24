
# code details

EXE_DIR = .
EXE = $(EXE_DIR)/shell

SRC= shell.c features.c

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

features.o:  features.c features.h
shell.o:  shell.c features.h
