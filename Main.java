import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // 3 bank accounts
        BankAccount[] accounts = new BankAccount[3];
        accounts[0] = new BankAccount("Aarav Sharma", "ACC001", 15000, 1234);
        accounts[1] = new BankAccount("Priya Mehta",  "ACC002", 8500,  5678);
        accounts[2] = new BankAccount("Rohan Verma",  "ACC003", 22000, 9999);

        System.out.println("==================================");
        System.out.println("       WELCOME TO JAVA BANK      ");
        System.out.println("==================================");

        // ---- STEP 1: Show accounts ----

        System.out.println("\nAccounts available:");
        System.out.println("1. Aarav Sharma  (ACC001)  PIN: 1234");
        System.out.println("2. Priya Mehta   (ACC002)  PIN: 5678");
        System.out.println("3. Rohan Verma   (ACC003)  PIN: 9999");

        // ---- STEP 2: Pick account by number 1, 2, or 3 ----

        System.out.print("\nEnter account number (1, 2, or 3): ");

        int accChoice = 0;
        try {
            accChoice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  Invalid input! Please enter a number. Exiting.");
            sc.close();
            return;
        }

        BankAccount selected = null;

        if (accChoice == 1) {
            selected = accounts[0];
        } else if (accChoice == 2) {
            selected = accounts[1];
        } else if (accChoice == 3) {
            selected = accounts[2];
        } else {
            System.out.println("  Invalid choice. Exiting.");
            sc.close();
            return;
        }

        System.out.println("Welcome, " + selected.getName() + "!");

        // ---- STEP 3: Enter PIN (max 3 attempts) ----

        int pin = 0;
        int attempts = 0;
        boolean pinCorrect = false;

        while (attempts < 3) {
            System.out.print("Enter PIN: ");
            try {
                pin = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Invalid PIN! Must be a number.");
                attempts++;
                System.out.println("  Attempts remaining: " + (3 - attempts));
                continue;
            }

            if (selected.isCorrectPin(pin)) {
                pinCorrect = true;
                break;  // correct PIN, move on
            } else {
                attempts++;
                if (attempts < 3) {
                    System.out.println("  Wrong PIN! Attempts remaining: " + (3 - attempts));
                }
            }
        }

        // If all 3 attempts failed, lock out
        if (!pinCorrect) {
            System.out.println("  *** Account locked! Too many wrong PIN attempts. ***");
            sc.close();
            return;
        }

        // ---- STEP 4: Account Menu ----

        int choice = 0;

        while (choice != 7) {

            System.out.println("\n--- ACCOUNT MENU ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Account Details");
            System.out.println("5. Mini Statement");   // NEW
            System.out.println("6. Change PIN");        // NEW
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input! Please enter a number between 1 and 7.");
                continue;
            }

            if (choice == 1) {

                selected.checkBalance(pin);

            } else if (choice == 2) {

                System.out.print("  Enter amount to deposit: Rs.");
                try {
                    double amount = Double.parseDouble(sc.nextLine().trim());
                    selected.deposit(amount);
                } catch (NumberFormatException e) {
                    System.out.println("  Invalid amount! Please enter a valid number.");
                }

            } else if (choice == 3) {

                System.out.print("  Enter amount to withdraw: Rs.");
                try {
                    double amount = Double.parseDouble(sc.nextLine().trim());
                    selected.withdraw(amount, pin);
                } catch (NumberFormatException e) {
                    System.out.println("  Invalid amount! Please enter a valid number.");
                }

            } else if (choice == 4) {

                // UPDATED: now passes pin to show balance too
                selected.showDetails(pin);

            } else if (choice == 5) {

                // NEW: Mini Statement
                selected.printStatement(pin);

            } else if (choice == 6) {

                // NEW: Change PIN
                System.out.print("  Enter current PIN: ");
                try {
                    int oldPin = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("  Enter new PIN: ");
                    int newPin = Integer.parseInt(sc.nextLine().trim());
                    // changePin() returns true only if old PIN matched and new PIN is valid
                    boolean changed = selected.changePin(oldPin, newPin);
                    if (changed) {
                        pin = newPin;  // sync local pin so current session uses new PIN
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  Invalid PIN! Must be a number.");
                }

            } else if (choice == 7) {

                System.out.println("  Thank you! Goodbye.");

            } else {

                System.out.println("  Wrong choice. Try again.");

            }
        }

        sc.close();
    }
}