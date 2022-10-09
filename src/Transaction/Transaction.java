//Transaction
package Transaction;

public class Transaction {

    public int transId;
    public String transType;
    public double transAmount;
    public double balance;

    public Transaction(int transId, String transType, double transAmount, double balance) {
        this.transId = transId;
        this.transType = transType;
        this.transAmount = transAmount;
        this.balance = balance;
    }

    private static final String SPACE = " ";

    @Override
    public String toString() {
        return transId + SPACE + transType + SPACE + transAmount + SPACE + balance;
    }
}
