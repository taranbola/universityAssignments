import java.io.*;
import java.util.*;

class BankAccount{
  private int id;
  private String name;  //private values for bank account
  private int balance;

  public BankAccount(int id, String name){
    id = 0;
    name = "";                               //initliase of values
    balance = 0;
  };

  public BankAccount(int id1, String name1, int bal){
    id = id1;
    name = name1;          //fill the values with the paramater vals
    balance = bal;
  };

  public int getId(){return id;}

  public String getName(){return name;}

  public int getBalance(){return balance;}

  public void deposit(int amount){
    if(amount > 0){
      balance = balance + amount;
    }                           //function to deposit money in the account
    else{
      throw new IllegalArgumentException();
    }
  }

  public void withdraw(int amount){
    if(amount > 0 && amount <= balance){
      balance = balance - amount;          //function to withdraw money from the account
    }
    else{
        throw new IllegalArgumentException();
    }
  }

  @Override
  public String toString(){
    return "Bank Account: ID = " + id + " ∥ Name = " + name + " ∥ Balance = " + balance;
  }
}

class overdraftAccount extends BankAccount{
  private int overdraftLimit;

   public overdraftAccount(int id, String name, int balance){
     super(id,name,balance);                                          //this takes in parameters and puts them in BankAccount parent
     this.overdraftLimit = 300;
   }

   public int getOverdraftLimit(){return overdraftLimit;}

   public void setOverdraftLimit(int limit){
     if (limit < 0){
       throw new IllegalArgumentException("You're limit is at 0.");             //prevents taking anymore out if
     }                                                                          //limit is less than 0
     else{
       overdraftLimit = limit;
     }
   }

   @Override
   public void withdraw(int amount){
     int newBal = super.getBalance();
     if(amount > 0 && amount <= overdraftLimit){
       newBal = newBal - amount;          //function to withdraw money from the account, overrides the BankAccount one.
     }
     else{
         throw new IllegalArgumentException();
     }
   }

   @Override
   public String toString(){
     return "Overdraft Account: Overdraft Limit = " + overdraftLimit;   //outputs as a string
   }

}

class savingsAccount extends BankAccount{
  private double interestRate;

  public savingsAccount(int id, String name, int balance){
    super(id,name,balance);                                 //this takes in parameters and puts them in BankAccount parent
    this.interestRate = 1.0;
  }

  public double getInterestRate(){return interestRate;}

  public void setInterestRate(double rate ){
    if (rate < 0){                                              //rate can't be less than 0
      throw new IllegalArgumentException("You're Interest Rate can't be negative");
    }
    else{
      interestRate = rate;
    }
  }

  public void applyInterest(){
    double rateValue = (interestRate *  super.getBalance() )/100;       //calculates the interest
    super.deposit( (int) (rateValue));
  }

  @Override
  public String toString(){
    return "Savings Account: Interest Rate = " + interestRate;        //says what the account is and it's private variables.
  }

}

public class Ex13{
    public static void main(String[] args) {
      BankAccount BankAccount = new BankAccount(123,"Taran",20000);

      System.out.println("ID = " + BankAccount.getId());
      System.out.println("Name = " + BankAccount.getName());
      System.out.println("Balance = " + BankAccount.getBalance());   //parameters to create a new account
      BankAccount.deposit(3000);
      BankAccount.withdraw(2500);
      System.out.println("New Balance = " + BankAccount.getBalance());
      System.out.println(BankAccount + "\n");              //overrides calls the toString function

      overdraftAccount overdraftAccount = new overdraftAccount(123,"Taran",20000);
      System.out.println("Overdraft Limit = " + overdraftAccount.getOverdraftLimit());
      overdraftAccount.setOverdraftLimit(1200);                                                       //shows old limit and new changed one.
      System.out.println("New Overdraft Limit = " + overdraftAccount.getOverdraftLimit());
      System.out.println(overdraftAccount + "\n");

      savingsAccount savingsAccount = new savingsAccount(123,"Taran",20000);

      System.out.println("Balance = " + savingsAccount.getBalance());
      System.out.println("Interest Rate = " + savingsAccount.getInterestRate() + "%");
      savingsAccount.setInterestRate(100.0);                                          //shows balance without then with Interest added.
      System.out.println("New Interest Rate = " + savingsAccount.getInterestRate() + "%");
      savingsAccount.applyInterest();
      System.out.println("New Balance = " + savingsAccount.getBalance());
      System.out.println(savingsAccount);
    }
}
