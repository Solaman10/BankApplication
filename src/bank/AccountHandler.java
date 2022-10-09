//AccountHandler
package bank;

import Transaction.Transaction;
import customer.Customer;
import customer.CustomerFileHandler;
import Transaction.TransactionHandler;
import customer.CustomerHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class AccountHandler {

    static Scanner sc = new Scanner(System.in);
    public static void deposit(int customerId,double amount) throws IOException {

        if(amount<0)
        {
            System.out.println("-----------------------------------");
            System.out.println("Please enter valid amount");
            System.out.println("-----------------------------------");
            return;
        }

        Customer customer = Bank.customerMap.get(customerId);

        customer.balance+=amount;

        Bank.customerMap.put(customer.customerId,customer);

        int transId = TransactionHandler.getLastTransId(customer.customerId);
        Transaction transaction = new Transaction(++transId,"deposit",amount,customer.balance);
        TransactionHandler handler = new TransactionHandler();
        handler.writeTransaction(customerId,transaction);

        CustomerFileHandler.getInstance().finalizeFile();

        System.out.println("-----------------------------------");
        System.out.println("   Amount deposited successfully");
        System.out.println("-----------------------------------");
    }

    public static void withdraw(int customerId,double amount) throws IOException {

        if(amount<0)
        {
            System.out.println("-----------------------------------");
            System.out.println("    Please enter valid amount");
            System.out.println("-----------------------------------");
            return;
        }

        Customer customer = Bank.customerMap.get(customerId);

        if(customer.balance<amount)
        {
            System.out.println("-------------------------------------------------------------");
            System.out.println("Insufficient Balance. Please try to withdraw within $"+customer.balance);
            System.out.println("-------------------------------------------------------------");
            return;
        }

        if(customer.balance>=1000)
        {
            customer.balance -= amount;
            Bank.customerMap.put(customer.customerId, customer);

            CustomerFileHandler.getInstance().finalizeFile();

            int transId = TransactionHandler.getLastTransId(customer.customerId);
            Transaction transaction = new Transaction(++transId,"withdraw",amount,customer.balance);
            TransactionHandler handler = new TransactionHandler();
            handler.writeTransaction(customerId,transaction);

            System.out.println("-----------------------------------");
            System.out.println("    Amount withdrew successfully");
            System.out.println("-----------------------------------");
        }
        else
        {
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("  Transaction failed: Minimum account balance must be maintained at least $1000");
            System.out.println("---------------------------------------------------------------------------------");
            return;
        }
    }

    public static void transfer(int fromCustId,int toCustId,double amount) throws IOException {

        Customer toCustomer = Bank.customerMap.get(toCustId);

        if(toCustomer==null)
        {
            System.out.println("---------------------------------------------------------");
            System.out.println("  This Customer Account Not Exist: Transaction Failed");
            System.out.println("---------------------------------------------------------");
            return;
        }

        if(amount<0)
        {
            System.out.println("Please enter valid amount");
            return;
        }

        Customer fromCustomer = Bank.customerMap.get(fromCustId);

        if(fromCustomer.balance<amount)
        {
            System.out.println("------------------------------------------------------------------");
            System.out.println("Insufficient Balance. Please try to transfer within $"+fromCustomer.balance);
            System.out.println("------------------------------------------------------------------");
            return;
        }

        if(fromCustomer.balance>=1000)
        {
            fromCustomer.balance -= amount;
            Bank.customerMap.put(fromCustomer.customerId, fromCustomer);

            int transId = TransactionHandler.getLastTransId(fromCustId);
            Transaction transaction = new Transaction(++transId,"transfer",amount,fromCustomer.balance);
            TransactionHandler handler = new TransactionHandler();
            handler.writeTransaction(fromCustId,transaction);

            toCustomer.balance+=amount;
            Bank.customerMap.put(toCustomer.customerId,toCustomer);
            transId = TransactionHandler.getLastTransId(toCustId);

            Transaction trans = new Transaction(++transId,"received",amount,toCustomer.balance);
            TransactionHandler handlerr = new TransactionHandler();
            handlerr.writeTransaction(toCustId,trans);

            CustomerFileHandler.getInstance().finalizeFile();

            System.out.println("-----------------------------------");
            System.out.println("  Amount Transferred Successfully");
            System.out.println("-----------------------------------");
        }
    }
}
