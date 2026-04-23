// =============================================
//  BankAccount.java
//  Stores details and methods for one account
// =============================================

import java.util.ArrayList;  // NEW: needed for transaction history

public class BankAccount {

    // Private variables - only this class can access them directly
    // This is called ENCAPSULATION in OOPs
    private String name;
    private String accountNumber;
    private double balance;
    private int pin;

    // NEW: ArrayList to store transaction history
    // ArrayList is like an array but it grows automatically as you add items
    private ArrayList<String> transactionHistory;

    // Constructor - runs when we create a new BankAccount object
    public BankAccount(String name, String accountNumber, double balance, int pin) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();  // NEW: start with empty history
    }

    // Method to deposit money
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("  Invalid amount!");
        } else {
            balance = balance + amount;
            System.out.println("  Rs." + amount + " deposited successfully!");
            transactionHistory.add("DEPOSIT   | Rs." + amount + " | Balance: Rs." + balance);  // NEW
        }
    }

    // Method to withdraw money (needs correct PIN)
    public void withdraw(double amount, int enteredPin) {
        if (enteredPin != pin) {
            System.out.println("  Wrong PIN! Transaction failed.");
        } else if (amount <= 0) {
            System.out.println("  Invalid amount!");
        } else if (amount > balance) {
            System.out.println("  Insufficient balance! Available: Rs." + balance);
        } else {
            balance = balance - amount;
            System.out.println("  Rs." + amount + " withdrawn successfully!");
            transactionHistory.add("WITHDRAWAL| Rs." + amount + " | Balance: Rs." + balance);  // NEW
        }
    }

    // Method to check balance (needs correct PIN)
    public void checkBalance(int enteredPin) {
        if (enteredPin != pin) {
            System.out.println("  Wrong PIN! Cannot view balance.");
        } else {
            System.out.println("  Current Balance: Rs." + balance);
        }
    }

    // NEW: Method to print transaction history (needs correct PIN)
    public void printStatement(int enteredPin) {
        if (enteredPin != pin) {
            System.out.println("  Wrong PIN! Cannot view statement.");
        } else if (transactionHistory.isEmpty()) {
            System.out.println("  No transactions yet.");
        } else {
            System.out.println("  ------ MINI STATEMENT ------");
            // Loop through the ArrayList and print each transaction
            for (int i = 0; i < transactionHistory.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + transactionHistory.get(i));
            }
            System.out.println("  ----------------------------");
        }
    }

    // NEW: Method to change PIN - returns true if successful, false if not
    public boolean changePin(int oldPin, int newPin) {
        if (oldPin != pin) {
            System.out.println("  Wrong current PIN! PIN not changed.");
            return false;
        } else if (newPin <= 0) {
            System.out.println("  Invalid new PIN!");
            return false;
        } else {
            pin = newPin;
            System.out.println("  PIN changed successfully!");
            return true;
        }
    }

    // UPDATED: showDetails now also shows balance (with PIN confirmation)
    public void showDetails(int enteredPin) {
        if (enteredPin != pin) {
            System.out.println("  Wrong PIN! Cannot view full details.");
        } else {
            System.out.println("  Name           : " + name);
            System.out.println("  Account Number : " + accountNumber);
            System.out.println("  Balance        : Rs." + balance);  // NEW: balance shown here now
        }
    }

    // Method to verify PIN without exposing it (used for login attempts)
    public boolean isCorrectPin(int enteredPin) {
        return enteredPin == pin;
    }

    // Getter for name (to use in menus)
    public String getName() {
        return name;
    }

    // Getter for account number (to use in search)
    public String getAccountNumber() {
        return accountNumber;
    }
}