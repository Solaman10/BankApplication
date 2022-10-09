//CustomerFileHandler
package customer;

import Transaction.*;

import bank.AccountHandler;
import bank.Bank;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

public class CustomerFileHandler {

    public static CustomerFileHandler handler;

    public static CustomerFileHandler getInstance() {

        if(handler==null) {
            handler = new CustomerFileHandler();
        }
        return handler;
    }

    public static final String fileName = "bank_db.txt";

    public void initialize() throws IOException {

        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String customerInfo = reader.readLine();

        do{
            Customer customerClass = castStringToCustomer(customerInfo);
            Bank.customerList.add(customerClass);
            Bank.customerMap.put(customerClass.customerId,customerClass);

            int transId = TransactionHandler.getLastTransId(customerClass.customerId);
            if(transId==0)
            {
                Transaction transaction = new Transaction(++transId, "opening", Bank.INITIAL_BALANCE, customerClass.balance);
                TransactionHandler transHandler = new TransactionHandler();
                transHandler.writeTransaction(customerClass.customerId, transaction);
            }

            Bank.refCustomerId = Bank.customerList.get(Bank.customerList.size()-1).customerId;
            Bank.refAccountNum = Bank.customerList.get(Bank.customerList.size()-1).accoundNumber;

            customerInfo = reader.readLine();
        }while (customerInfo!=null);
        try {
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            reader.close();
        }
    }

    private Customer castStringToCustomer(String customerInfo) {

        String[] trimedInfo = customerInfo.split(" ");

        Customer customer = new Customer(Integer.parseInt(trimedInfo[0]),trimedInfo[2],Double.parseDouble(trimedInfo[3]),trimedInfo[4],Integer.parseInt(trimedInfo[1]));

        return customer;
    }

    public void addCustomerToFile(Customer customer) throws IOException {

        File file = new File(fileName);
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(file,true));
            Bank.customerList.add(customer);
            Bank.customerMap.put(customer.customerId,customer);
            writer.write("\n"+customer.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(writer!=null)
                writer.close();
        }
    }

    public void finalizeFile() throws FileNotFoundException {

        Bank.customerList.clear();

        File file = new File(fileName);
        BufferedWriter writer = null;
        boolean newLineFlag = true;

        try {
            writer = new BufferedWriter(new FileWriter(file));
            Set keySet = Bank.customerMap.keySet();
            Iterator iterator = keySet.iterator();

            while (iterator.hasNext())
            {
                int customerId = (int) iterator.next();
                Customer customer = Bank.customerMap.get(customerId);

                if(newLineFlag==true)
                {
                    writer.write(customer.toString());
                    newLineFlag = false;
                }
                else
                {
                    writer.write("\n" + customer.toString());
                }
                Bank.customerList.add(customer);
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(writer!=null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}