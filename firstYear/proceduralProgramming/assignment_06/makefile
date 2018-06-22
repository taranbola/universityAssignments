
word: Main.o List.o Node.o
	gcc -g -std=c99 -o word Main.o List.o Node.o -Wall

Main.o: Main.c List.h
	gcc -g -c -std=c99 -o Main.o Main.c -Wall

List.o: List.c List.h
	gcc -g -c -std=c99 -o List.o List.c -Wall

Node.o: Node.c Node.h
	gcc -g -c -std=c99 -o Node.o Node.c -Wall

clean:
	rm debt *.o -Wall
