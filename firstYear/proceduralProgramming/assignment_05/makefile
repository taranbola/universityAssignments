#creating the executable using object files of main print and decrypt
main: main.o print.o decrypt.o		
	gcc -g -o main main.o print.o decrypt.o

#creates the object file of main by running the main c file
main.o: main.c 
	gcc -g -c -o main.o main.c

#creates the object file of print by running the main c file
print.o: print.c print.h
	gcc -g -c -o print.o print.c

#creates the object file of print by running the main c file
decrypt.o: decrypt.c decrypt.h
	gcc -g -c -o decrypt.o decrypt.c

