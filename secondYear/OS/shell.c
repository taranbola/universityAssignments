#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <errno.h>
#include "features.h"

/**
 * forkPrograms This will execute the program either in the background or not.
 * @param  args       These are the execute parameters like the filename
 * @param  background This is either true/false depending on if it's in the background
 * @return            returns 1 to continue the shell loop
 */
int forkPrograms(char **args, bool background){
  int *parentState;
  pid_t parentId;

  parentId = fork();
  if (parentId == 0) {
    if (execvp(args[1],args ) == -1) {
    printf("No File or Directory Found\n");
    }
  }

  if(background == false){
    wait(parentState);
  }
  return 1;
}

/**
 * getFunctions This loops through all the functions depending on user's input.
 * @param  args Array of the users text input to be checked.
 * @return      returns 1 to continue loop
 */
int getFunctions(char **args){
  int c;
  char *functionInput[8] = {"cd","info","exit","ls","pwd","ex","exb","shell"};
  int (*functionNames[8]) (char **)= {&changeDirectory,&info,&programExit,&directoryList,&currentDirectory,&programExecute,&backgroundExecute,&shell};

  if (args[0] == NULL) {
    return 1;
  }
  for (c = 0; c < 8;c++) {
    if (strcmp(args[0], functionInput[c]) == 0) {
      return (*functionNames[c])(args);
    }
  }
  return 1;
}

/**
 * readLines reads in the user input line and seperates strings into an array
 * @return  the array of user inputs in char's
 */
char **readLines(void){
  int position = 0;
  char *token;                   //pointer to array of values.
  char *userInput = NULL;
  ssize_t lineBuffer = 0; // have getline allocate a buffer for us

  getline(&userInput, &lineBuffer, stdin);
  int length = strlen(userInput);
  char **data = (char**)calloc(length, sizeof(char*));

  token = strtok(userInput, " \t\r\n\a");
  while (token != NULL) {
    data[position] = token;
    position++;
    token = strtok(NULL, " \t\r\n\a");
  }
  data[position] = NULL;
  return data;
  free(userInput);
}

/**
 * main description loops through the functions until
 * @param  argc Number of values inputted
 * @param  argv The paramaters inputted
 */
int main(int argc, char **argv){
  char **args;
  int status = 1;

  while(status == 1){
    printf("|~>");
    args = readLines();
    status = getFunctions(args);

    free(args);
  }
}
