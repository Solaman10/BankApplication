//CustomerHandler
package customer;

import Transaction.Transaction;
import bank.Bank;
import Transaction.TransactionHandler;

import java.io.IOException;
import java.util.Scanner;

public class CustomerHandler {

    static Scanner sc = new Scanner(System.in);
    public static void addCustomer() throws IOException {

        String name;
        String password;
        String retypePassword=null;

        System.out.print("Enter customer name: ");
        name = sc.nextLine();
        System.out.print("Enter password: ");
        password = sc.nextLine();
        if(isValidPass(password)) {
            System.out.print("Re-type password: ");
            retypePassword = sc.nextLine();
        }
        else {
            System.out.println("Process of Adding Customer is Failed: Invalid Password");
            return;
        }

        if(!(password.equals(retypePassword))) {
            System.out.println("Process of Adding Customer is Failed: Mismatched Password");
            return;
        }
        else
        {
            password = getEncryptedPass(password);

            Bank.refCustomerId++;
            Bank.refAccountNum+=11011;

            Customer customer = new Customer(Bank.refCustomerId,name,Bank.INITIAL_BALANCE,password,Bank.refAccountNum);
            CustomerFileHandler.getInstance().addCustomerToFile(customer);

            logTransaction(customer.customerId,customer.balance);

            System.out.println("-----------------------------------");
            System.out.println("    Customer added Successfully");
            System.out.println("-----------------------------------");
//            System.out.println(customer.toString());

        }
    }

    private static void logTransaction(int customerId,double balance) {

        Transaction transaction = new Transaction(customerId,"opening",Bank.INITIAL_BALANCE,balance);

        TransactionHandler handler = new TransactionHandler();
        handler.writeTransaction(customerId,transaction);
    }

    private static boolean isValidPass(String password) {

        int capsCount = 0,smallCount = 0,numCount = 0;
        if(password.length()<8)
        {
            System.out.println("Process failed : Password must be contain at least 8 characters");
            return false;
        }
        for(int i=0;i<password.length();i++)
        {
            if(password.charAt(i)>=97 && password.charAt(i)<=122)
                ++capsCount;
            if(password.charAt(i)>=65 && password.charAt(i)<=90)
                ++smallCount;
            if(password.charAt(i)>=48 && password.charAt(i)<=57)
                ++numCount;
            else if(!((password.charAt(i)>=97 && password.charAt(i)<=122) ||  (password.charAt(i)>=65 && password.charAt(i)<=90) || (password.charAt(i)>=48 && password.charAt(i)<=57)))
            {
                System.out.println("Process failed: Password should not contain any special characters");
                return false;
            }
        }

        if(!(capsCount>=2 && smallCount>=2 && numCount>=2))
        {
            System.out.println("Process failed: Password does not meet the STANDARD_PASSWORD_CONSTRAINTS");
            return false;
        }
        return true;
    }


    private static String getEncryptedPass(String password) {

        char[] passChar = password.toCharArray();

        for(int i=0;i<passChar.length;i++)
        {
            if((passChar[i]>=97 && passChar[i]<=122) ||(passChar[i]>=65 && passChar[i]<=90) || (passChar[i]>=48 && passChar[i]<=57))
            {
                if(passChar[i]=='Z')
                {
                    passChar[i] = 'A';
                }
                else if(passChar[i]=='z')
                {
                    passChar[i] = 'a';
                }
                else if(passChar[i]=='9')
                {
                    passChar[i]='0';
                }
                else
                    passChar[i]+=1;
            }
        }
        return String.valueOf(passChar);
    }

    public static boolean getAuth(int customerId,String password) {

        for(Customer c:Bank.customerList)
        {
            if(c.customerId==customerId && c.password.equals(getEncryptedPass(password)))
                return true;
        }
        return false;
    }
}
