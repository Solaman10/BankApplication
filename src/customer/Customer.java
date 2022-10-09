//Customer BankApplication
package customer;

public class Customer {

    public static final String SPACE = " ";

    public int customerId;
    public String name;
    public double balance;
    public String password;
    public int accoundNumber;

    public Customer(int customerId, String name, double balance, String password, int accoundNumber) {
        this.customerId = customerId;
        this.name = name;
        this.balance = balance;
        this.password = password;
        this.accoundNumber = accoundNumber;
    }

    @Override
    public String toString() {
        return customerId + SPACE + accoundNumber + SPACE + name + SPACE + balance + SPACE + password;
    }
}
