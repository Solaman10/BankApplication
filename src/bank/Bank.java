//Bank
package bank;

import customer.Customer;

import java.util.*;

public class Bank {

    public static List<Customer> customerList = new ArrayList<Customer>();
    public static Map<Integer,Customer> customerMap = new HashMap<Integer,Customer>();


    public static int refCustomerId;
    public static int refAccountNum;
    public static double INITIAL_BALANCE = 10000.0;
}
