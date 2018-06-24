@Author Taranvir Bola, Matthew Cutts, Daniel Aves, Benjamin Ashby,
Mitchell Gladstone and Qasim Hussain

In this repository there are files to run a website, server and a till system.

./flask_install.sh = Install flask files, create db and run website
./dbcreate.sh = Create the database
./run.sh = Run the website
./test.sh = Test the website
cd Desktop then ant main = Run the till system
        Login - 'user' Password - 'password'
cd Desktop then ant docs = Create the Java docs
cd Desktop then ant test = Test the Till System


To first install the flask files and to create the database you must run
./flask_install.sh in the terminal. This will then automatically run the website
as well.
To run the website without installing the files again you can run the command
./run.sh.
To create a blank version of the database you can run the command ./dbcreate.sh.
In order to run the till system you need to go into the Desktop folder and
run ant main, the website must be running before you do this.

 To run the tests for flask you need to run the ./test.sh in the terminal, this
 should have all the tests for the website pass.
 To run the tests for the till system run 'ant test' this will compile all tests
 including testFX robot which depends upon oracle jdk.
 To get the java documentation for the classes you have to run the command
 'ant docs' to get a folder for documentation on the till system.
