import java.util.*;

class map{
    public static void main(String[] args){
      Scanner userInput = new Scanner(System.in);   //takes in filename as a parameter
      TreeMap<String,String> phoneBook = new TreeMap<>();
      int i = 0;
      phoneBook.put("ASDA","07400832054");
      phoneBook.put("Tesco's","01332776816");
      phoneBook.put("Morrision's","01332361020");
      phoneBook.put("Iceland","01672361020");
      phoneBook.put("Aldi","01336761020");
      phoneBook.put("Lidl","01332676712");
      phoneBook.put("Poundland","0145616020");

      while(i < 1){
        System.out.println("Enter a name that's in the phonebook ");
        String input = userInput.nextLine();
          if((input.equals("quit")) == true){
            System.out.print("Quit");
            System.out.print("\033[H\033[2J");
            System.out.flush();
            break;
          }
          else if((phoneBook.containsKey(input)) == true ){
            String value = phoneBook.get(input);
            System.out.printf("%s: %s%n", input, value);
          }
          else{
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.print("ERROR - Invalid Name in Phonebook - TRY AGAIN\n");
          }
      }
    }
}
