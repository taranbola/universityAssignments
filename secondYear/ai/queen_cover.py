# Definition of Queen's Cover problem by Taranvir Bola,sc16tb
# For use with queue_search.py

from copy import deepcopy

# Representation of a state:
# (full_board_state)
# So on a small 3x3 board after 1 move we might have a state such as:
#
# ( [[1,1,1],
#    [1,2,1],
#    [1,1,1]]  )

def queen_initial_state(x,y):
      return [[0 for a in range(x)] for b in range(y)]          #will fill the matrix x*y with 0's

def queen_possible_actions(state):
      ans = []
      for a in range(GLOBAL_Y):
            for b in range(GLOBAL_X):                    # will get all the possible moves by
                  if state[a][b] != 2:                       #can be anywhere on the board
                        ans.append((a,b))
      return ans

def queen_successor_state(action, state):

      diagonalRightDown1=diagonalLeftDown1 = diagonalRightUp1 = diagonalLeftUp1 = action[0]
      diagonalRightDown2=diagonalLeftDown2 = diagonalRightUp2 = diagonalLeftUp2 = action[1]

      newstate = deepcopy(state)
      newstate[action[0]][action[1]] = 2                          #replaces the queen with a 2 value

      for a in range(GLOBAL_X):
            newstate[action[0]][a] = 1                     #this will change everything on y axis to 1
      for b in range(GLOBAL_Y):
            newstate[b][action[1]] = 1                     #this will change everything on x axis to 1

      while diagonalRightDown1 < GLOBAL_Y-1 and diagonalRightDown2 < GLOBAL_X-1:     #this will do diagonal right down
          newstate[diagonalRightDown1+1][diagonalRightDown2+1] = 1
          diagonalRightDown1 += 1
          diagonalRightDown2 += 1

      while diagonalLeftDown1 > 0 and diagonalLeftDown2 < GLOBAL_X-1:          #this will do diagonalleft down
          newstate[diagonalLeftDown1-1][diagonalLeftDown2+1] = 1
          diagonalLeftDown1 -= 1
          diagonalLeftDown2 += 1

      while diagonalRightUp1 < GLOBAL_Y-1 and diagonalRightUp2 > 0:           #this will do diagonal right up
          newstate[diagonalRightUp1+1][diagonalRightUp2-1] = 1
          diagonalRightUp1 += 1
          diagonalRightUp2 -= 1

      while diagonalLeftUp1 > 0 and diagonalLeftUp2 > 0:
          newstate[diagonalLeftUp1-1][diagonalLeftUp2-1] = 1                 #this will do diagonal left up
          diagonalLeftUp1 -= 1
          diagonalLeftUp2 -= 1

      return newstate

def queen_goal_state(state):
    for a in range(GLOBAL_Y):
        for b in range(GLOBAL_X):                   # will go through the whole board and ...
            if state[a][b] == 0:                 # will see if any values are 0.
                return False                   #If any are 0 it will return false, else it will be true.
    return True

def make_qc_problem_info(x,y):
	def queen_print_problem_info():
		print( "The Queen's Cover Problem (",x,"x",y,"board)" )             #will print info about problem
	return queen_print_problem_info

def make_qc_problem(x,y):
    global GLOBAL_X, GLOBAL_Y
    GLOBAL_X = x
    GLOBAL_Y = y                                                      #global variables that are used in successor
    return  ( None,
                make_qc_problem_info(x,y),
                queen_initial_state(x,y),
                queen_possible_actions,                             #calls all the functions
                queen_successor_state,
                queen_goal_state
              )
