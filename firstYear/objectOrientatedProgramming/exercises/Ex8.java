class BankAccount{
  private int id;
  private String name;  //private values for bank account
  private int balance;

  public BankAccount(int id, String name){
    id = 0;
    name = "";                               //initliase of values
    balance = 0;
  };

  public BankAccount(int id, String name, int bal){
    id = id;
    name = name;          //fill the values with the paramater vals
    balance = bal;
  };

  public int getId(){return id;}

  public String getName(){return name;}

  public int getBalance(){return balance;}

  public boolean deposit(int amount){
    if(amount > 0){
      balance = balance + amount;
      return true;
    }                           //function to deposit money in the account
    else{
      return false;
    }
  }

  public boolean withdraw(int amount){
    if(amount > 0 && amount <= balance){
      balance = balance - amount;
      return true;            //function to withdraw money from the account
    }
    else{
      return false;
    }
  }
}

public class Ex8{
    public static void main(String[] args) {
      BankAccount BankAccount = new BankAccount(123,"Taran",20000);
      System.out.println("ID = " + BankAccount.getId());
      System.out.println("Name = " + BankAccount.getName());
      System.out.println("Balance = " + BankAccount.getBalance());   //parameters to create a new account
      BankAccount.deposit(3000);
      BankAccount.withdraw(2500);
      System.out.println("New Balance = " + BankAccount.getBalance());
    }
}
