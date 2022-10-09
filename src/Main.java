//Main BankApplication
import Transaction.Transaction;
import bank.AccountHandler;
import bank.Bank;
import customer.CustomerFileHandler;
import customer.CustomerHandler;
import Transaction.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.spec.ECField;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        CustomerFileHandler.getInstance().initialize();
        printMenu();
        CustomerFileHandler.getInstance().finalizeFile();
    }

    private static void printMenu() throws IOException {

        int option = 0;
        boolean loop = true,switchLoop=true;

        while (loop) {
            System.out.print("1-Add Customer\n2-Deposit\n3-Withdraw\n4-Fund Transfer\n5-Transaction History\n6-View All Customers and Their details\n7-Exit\nPlease enter an option: ");
            try {
                switchLoop = true;
                option = sc.nextInt();
            } catch (Exception e) {
                switchLoop = false;
                sc.nextLine();
            }

            if (switchLoop == true)
            {
                switch (option) {

                    case 1:
                        CustomerHandler.addCustomer();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        viewTransHistory();
                        break;
                    case 6:
                        System.out.println("-----------------------------------");
                        for (int i = 0; i < Bank.customerList.size(); i++)
                            System.out.println(Bank.customerList.get(i));
                        System.out.println("-----------------------------------");
                        break;
                    case 7:
                        loop = false;
                        break;
                    default: {
                        System.out.println("-----------------------------------");
                        System.out.println("   Please Enter the valid option");
                        System.out.println("-----------------------------------");

                    }
                }
            }
            else
            {
                System.out.println("-----------------------------------");
                System.out.println("   Please Enter the valid option");
                System.out.println("-----------------------------------");
            }
        }
    }

    private static void deposit() throws IOException {

        int customerId;
        String password;
        double amount;

        System.out.println("--------------------------");
        System.out.print("Enter customer id: ");
        customerId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter password: ");
        password = sc.nextLine();

        if(CustomerHandler.getAuth(customerId,password))
        {
            System.out.print("Enter deposit amount: ");
            amount = sc.nextDouble();

            AccountHandler.deposit(customerId,amount);
        }
        else
        {
            System.out.println("-------------------------------");
            System.out.println("    Invalid Account Details");
            System.out.println("-------------------------------");
        }
    }

    private static void withdraw() throws IOException {

        int customerId;
        String password;
        double amount;

        CustomerHandler custHandler = new CustomerHandler();
        AccountHandler accHandler = new AccountHandler();

        System.out.println("----------------------------------------------");

        System.out.print("Enter customer id: ");
        customerId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter password: ");
        password = sc.nextLine();

        if(CustomerHandler.getAuth(customerId,password))
        {
            System.out.print("Enter withdraw amount: ");
            amount = sc.nextDouble();

            AccountHandler.withdraw(customerId,amount);
        }
        else
        {
            System.out.println("----------------------------------");
            System.out.println("     Invalid Account Details");
            System.out.println("----------------------------------");
        }
    }

    private static void transfer() throws IOException {

        int fromCustomerId;
        int toCustomerId;
        String password;
        double amount;

        System.out.println("------------------------------------");
        System.out.print("Enter customer id: ");
        fromCustomerId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter password: ");
        password = sc.nextLine();

        if(CustomerHandler.getAuth(fromCustomerId,password))
        {
            System.out.print("Enter customer id to transferred: ");
            toCustomerId = sc.nextInt();
            System.out.print("Enter the amount to transfer: ");
            amount = sc.nextDouble();

            AccountHandler.transfer(fromCustomerId,toCustomerId,amount);
        }
        else
        {
            System.out.println("------------------------------------");
            System.out.println("     Invalid Account Details");
            System.out.println("------------------------------------");
        }
    }

    private static void viewTransHistory() {

        TransactionHandler transHandler = new TransactionHandler();

        int customerId;
        String password;

        System.out.println("----------------------------------");
        System.out.print("Enter customer id: ");
        customerId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter password: ");
        password = sc.nextLine();

        if(CustomerHandler.getAuth(customerId,password))
        {
            TransactionHandler handler = new TransactionHandler();
            handler.viewTransHistory(customerId);
        }
        else
        {
            System.out.println("----------------------------------");
            System.out.println("    Invalid Account Details");
            System.out.println("----------------------------------");
        }

    }
}
