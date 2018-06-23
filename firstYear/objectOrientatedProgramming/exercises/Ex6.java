import java.util.Scanner;							
class stringInput {								
    public static void main(String[] args) {					
	Scanner input = new Scanner(System.in);			

    	System.out.println("Enter some text:");			
	String s = input.nextLine();												
	int length = s.length();
	int counter = 0;
	int a = 0;
	int e = 0;				//these contain the variables for...
	int i = 0;				//the loop counter, each vowel and...
	int o = 0;				//the length of the string
	int u = 0;

	if(length <10) {
		System.out.println("Too Short");		
	}
	else{
		for(counter = 0; counter<length; counter++){
			
			if ((s.charAt(counter) == 'a')|(s.charAt(counter) == 'A')){
				a++;
			}
			else if ((s.charAt(counter) == 'e')|(s.charAt(counter) == 'E')){		//this will increment each counter of ...
				e++;									//each vowel. Its done for both lower ...
			}										//uppercase.
			else if ((s.charAt(counter) == 'i')|(s.charAt(counter) == 'I')){			
				i++;									
			}
			else if ((s.charAt(counter) == 'o')|(s.charAt(counter) == 'O')){
				o++;
			}
			else if ((s.charAt(counter) == 'u')|(s.charAt(counter) == 'U')){
				u++;
			}
		}
		System.out.println("The number of characters is " + length);
		System.out.println("Number of a's: " + a);						//outputs no. of characters and...
		System.out.println("Number of e's: " + e);						//each instance of each vowel	
		System.out.println("Number of i's: " + i);						
		System.out.println("Number of o's: " + o);
		System.out.println("Number of u's: " + u);
	}		
    }
}
