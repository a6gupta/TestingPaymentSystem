package DeserializingResponseObj;

public class TransactionResponseObj {

    private String transaction_id;
    private String transaction_time;
    private DebtorResponseObj debtor;
    private CreditorResponseObj creditor;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }

    public DebtorResponseObj getDebtor() {
        return debtor;
    }

    public void setDebtor(DebtorResponseObj debtor) {
        this.debtor = debtor;
    }

    public CreditorResponseObj getCreditor() {
        return creditor;
    }

    public void setCreditor(CreditorResponseObj creditor) {
        this.creditor = creditor;
    }

    @Override
    public String toString() {
        return "TransactionResponseObj{" +
                "transaction_id='" + transaction_id + '\'' +
                ", transaction_time='" + transaction_time + '\'' +
                ", debtor=" + debtor +
                ", creditor=" + creditor +
                '}';
    }
}
