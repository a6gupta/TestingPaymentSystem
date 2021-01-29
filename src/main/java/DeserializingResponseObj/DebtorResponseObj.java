package DeserializingResponseObj;

public class DebtorResponseObj {

    private String debtor_account;
    private Float debtor_previous_balance;
    private Float amount_debited;
    private Float debtor_updated_balance;

    public String getDebtor_account() {
        return debtor_account;
    }

    public void setDebtor_account(String debtor_account) {
        this.debtor_account = debtor_account;
    }

    public Float getDebtor_previous_balance() {
        return debtor_previous_balance;
    }

    public void setDebtor_previous_balance(Float debtor_previous_balance) {
        this.debtor_previous_balance = debtor_previous_balance;
    }

    public Float getAmount_debited() {
        return amount_debited;
    }

    public void setAmount_debited(Float amount_debited) {
        this.amount_debited = amount_debited;
    }

    public Float getDebtor_updated_balance() {
        return debtor_updated_balance;
    }

    public void setDebtor_updated_balance(Float debtor_updated_balance) {
        this.debtor_updated_balance = debtor_updated_balance;
    }

    @Override
    public String toString() {
        return "DebtorResponseObj{" +
                "debtor_account='" + debtor_account + '\'' +
                ", debtor_previous_balance=" + debtor_previous_balance +
                ", amount_debited=" + amount_debited +
                ", debtor_updated_balance=" + debtor_updated_balance +
                '}';
    }
}
