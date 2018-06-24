#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <errno.h>
#include <time.h>

/**
 * directing This will executable output to a specified text file
 * @param  args This takes in an array of string inputs.
 * @return      This will return 1 to continue shell loop
 */
int directing(char **args){
  FILE *filePath;
  pid_t parentId;

  parentId = fork();
  if (parentId == 0) {
    char *fileName = args[3];
    filePath = freopen(fileName, "w+",stdout );

    if (execvp(args[1],args ) == -1) {
    printf("No File or Directory Found\n");
    }
  }
  return 1;
}

int pipelining(char **args){
  int sizeArray;
  int i;
  int counter;

  FILE *fp;
  pid_t parentId = fork();
  char **newArgs = (char**)calloc(2, sizeof(char*));
  newArgs[0] = args[0];
  newArgs[1] = args[1];
  char **secondArgs =(char**)calloc(2, sizeof(char*));
  secondArgs[0] = args[3];
  secondArgs[1] = args[4];
  // for(i= 0;args[i] != '\0';i++){
  //   sizeArray = i;
  // }
  // printf("No. of parameters: %d\n",sizeArray);
  // char **newArgs = (char**)calloc(sizeArray, sizeof(char*));
  // for (counter = 0; counter <2; counter++) {
  //    newArgs[counter] = args[counter+1];
  // }
  //printf("%s\n",newArgs[0] );

  if (parentId == 0) {
    fp = freopen("write.txt", "w+",stdout );
    if (execvp(newArgs[1],newArgs ) == -1) {
    printf("No File or Directory Found\n");
    }
  }
  return 1;
}

/**
 * backgroundExecute calls the forkPrograms as a backgroud executable
 * @param  args Array of string values
 * @return      1 to continue loop
 */
int backgroundExecute(char **args){
  bool bg = true;
  forkPrograms(args,bg);
  return 1;
}

/**
 * programExecute will run a given executable depending on paramaters.
 * @param  args Array of string values
 * @return      1 to continue shell loop
 */
int programExecute(char **args){
  bool bg = false;
  char *symbol =">";
  char *pipe = "|";

  if(args[2] && (!strcmp(args[2],pipe)) && args[3] && (!strcmp(args[3],"ex")) && args[4]){
      pipelining(args);
    }
  else if(args[2] &&  (!strcmp(args[2],symbol)) ) {
      directing(args);
  }
  else{
    forkPrograms(args,bg);
   }
  return 1;
}

/**
 * currentDirectory prints the current directory file path
 * @return  1 to continue shell loop
 */
int currentDirectory(){
  int length = pathconf(".", _PC_PATH_MAX);
  char *directory = (char*)calloc(length, sizeof(char));

  getcwd(directory, (size_t)length);
  printf("Current Directory: %s\n",directory);
  return 1;
}

/**
 * timedate prints the user information such as date and user
 * @return 1 to continue shell loop
 */
int shell(){
  char *username=getenv("USER");
  if(username!=NULL){
    printf("Current User: %s\n",username);
  }

  time_t currentTime;
  struct tm * timeDate;
  time ( &currentTime );
  timeDate = localtime ( &currentTime );
  printf("Current Date: %s", asctime(timeDate));

  return 1;
}

/**
 * directoryList will print the a list of files in current directory
 * @return 1 to continue shell loop
 */
int directoryList(){
  DIR *openedDirectory;
  struct dirent *fileName;

  openedDirectory = opendir(".");
  if(openedDirectory){
    while ((fileName = readdir(openedDirectory)) != NULL){
      printf("%s | ", fileName->d_name);
    }
    closedir(openedDirectory);
  }
  printf("\n");
  return 1;
}

/**
 * changeDirectory will change the directory will keep the shell in current directory
 * @param  args takes in paramaters to decide where to go
 * @return      1 to continue shell loop
 */
int changeDirectory(char **args){
  if (args[1] == NULL) {
    printf("Expected Arguments to Change Directory\n");
  }
  else {
    if (chdir(args[1]) != 0) {
      printf("No File or Directory Found\n");
    }
  }
  return 1;
}

/**
 * info Prints shell information
 * @return returns 1 to continue shell loop
 */
int info(){
  printf("COMP2211 Simplified Shell by sc16tb\n");
  return 1;
}

/**
 * programExit will exit the loop
 * @return 0 to exit loop
 */
int programExit(){
  exit(0);
  return 0;
}
