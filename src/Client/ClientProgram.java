package Client;

import java.util.Scanner;

public class ClientProgram {
    public static void main(String[] args) {
        System.out.println("Enter your customer ID:");

        try {
            Scanner in = new Scanner(System.in);
            int customerId = Integer.parseInt(in.nextLine());

            try (Client client = new Client(customerId)) {
                System.out.println("Logged in successfully.");

                while (true) {
                    int[] accountNumbers = client.getAccountNumbers();
                    System.out.println("Your accounts:");
                    for (int account : accountNumbers)
                        System.out.printf("  Account %5d: balance %10d GBP%n", account, client.getBalance(account));

                    System.out.println("Choose between the following 2 options:\n" +
                            "[1] Transfer money\n" +
                            "[2] Create a new account\n" +
                            "(Press 1 or 2)");
                    int choice = Integer.parseInt(in.nextLine());

                    if (choice == 1){
                        System.out.println("Enter the account number to transfer from or -1 to print the account list:");
                        int fromAccount = Integer.parseInt(in.nextLine());
                        if (fromAccount < 0)
                            continue;

                        System.out.println("Enter the account number to transfer to (this could be someone else's account):");
                        int toAccount = Integer.parseInt(in.nextLine());

                        System.out.println("Enter the amount to be transferred:");
                        int amount = Integer.parseInt(in.nextLine());

                        client.transfer(fromAccount, toAccount, amount);
                    }
                    else if (choice == 2){
                        System.out.println("Create new account");
                        int newAccount = client.createAccount();
                        System.out.println("Account " + newAccount + " created.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
