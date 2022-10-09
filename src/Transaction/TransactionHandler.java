//TransactionHandler
package Transaction;

import customer.Customer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TransactionHandler {

    public void writeTransaction(int customerId,Transaction transaction) {

        String fileName = customerId+".txt";
        BufferedWriter writer = null;
        boolean newFile = false;

        try {
            File file = new File(fileName);

            if(!file.exists()) {
                file.createNewFile();
                newFile = true;
            }


            writer = new BufferedWriter(new FileWriter(file,true));

            if(!newFile){writer.write("\n");newFile=false;}
            writer.write(transaction.toString());

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getLastTransId(int customerId) throws IOException {

        int lastId,lastTransId = 0;
        String fileName = customerId+".txt";

        try {
            File file = new File(fileName);

            if(!file.exists())
            {
                return 0;
            }

            Scanner sc = new Scanner(file);
            String trans = null;
            while(sc.hasNext())
            {
                trans = sc.nextLine();
            }

            lastTransId = getId(trans);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lastTransId;
    }

    private static int getId(String trans) {

        String[] id = trans.split(" ");
        return Integer.parseInt(id[0]);
    }

    public void viewTransHistory(int customerId) {

        String fileName = customerId+".txt";

        try
        {
            File file = new File(fileName);

            if(!file.exists())
            {
                System.out.println("------------------------------------------------------");
                System.out.println("  There is no customer with this given customer id");
                System.out.println("------------------------------------------------------");
                return;
            }

            Scanner sc = new Scanner(file);
            String trans = "";

            System.out.println("-----------------------------------------");
            while(sc.hasNext())
            {
                trans = sc.nextLine();
                System.out.println(trans);
            }
            System.out.println("-----------------------------------------");
            sc.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}
