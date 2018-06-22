
#creating the executable
print:	hexdump.o print.o		
	gcc -g -std=c99 -o print hexdump.o print.o
#creating the c file
print.o: print.c print.h
	gcc -g -c -std=c99 -o print.o print.c
#cleans the file
clean:
	rm print *.o
	rm print.o *.o
