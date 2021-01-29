package DeserializingResponseObj;

import java.util.ArrayList;
import java.util.List;

public class CreditorResponseObj {

    private String creditor_account ;
    private Float creditor_previous_balance;
    private Float amount_credited;
    private Float creditor_updated_balance;

    public String getCreditor_account() {
        return creditor_account;
    }

    public void setCreditor_account(String creditor_account) {
        this.creditor_account = creditor_account;
    }

    public Float getCreditor_previous_balance() {
        return creditor_previous_balance;
    }

    public void setCreditor_previous_balance(Float creditor_previous_balance) {
        this.creditor_previous_balance = creditor_previous_balance;
    }

    public Float getAmount_credited() {
        return amount_credited;
    }

    public void setAmount_credited(Float amount_credited) {
        this.amount_credited = amount_credited;
    }

    public Float getCreditor_updated_balance() {
        return creditor_updated_balance;
    }

    public void setCreditor_updated_balance(Float creditor_updated_balance) {
        this.creditor_updated_balance = creditor_updated_balance;
    }

    @Override
    public String toString() {
        return "CreditorResponseObj{" +
                "creditor_account='" + creditor_account + '\'' +
                ", creditor_previous_balance=" + creditor_previous_balance +
                ", amount_credited=" + amount_credited +
                ", creditor_updated_balance=" + creditor_updated_balance +
                '}';
    }
}
