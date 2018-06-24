Each example appears in its own subdirectory.

To compile an example for the first time on SoC Linux machines, cd into
its subdirectory and enter

  qmake && make

This runs the qmake command to generate a makefile and then, if generation
was successful, uses the makefile to compile the code.  To compile
subsequently, just enter 'make'.

(Note: normally, you would first need to run 'qmake -project' and then
edit the .pro file, but we've done this step for you.)

You can clean up intermediate files using

  make clean

You can return the subdirectory to its pristine state with

  make distclean

(If you do this, you will need to rerun qmake to recreate the makefile.)
