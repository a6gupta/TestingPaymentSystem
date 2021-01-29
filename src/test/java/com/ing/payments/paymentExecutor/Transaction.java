package com.ing.payments.paymentExecutor;

public class Transaction {
    Float transactionAmount;
    String debtorAccount;
    Float debtorAccountBalance;
    String creditorAccount;
    Float creditorAccountBalance;

    public Float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(String debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public Float getDebtorAccountBalance() {
        return debtorAccountBalance;
    }

    public void setDebtorAccountBalance(Float debtorAccountBalance) {
        this.debtorAccountBalance = debtorAccountBalance;
    }

    public String getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(String creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public Float getCreditorAccountBalance() {
        return creditorAccountBalance;
    }

    public void setCreditorAccountBalance(Float creditorAccountBalance) {
        this.creditorAccountBalance = creditorAccountBalance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionAmount=" + transactionAmount +
                ", debtorAccount='" + debtorAccount + '\'' +
                ", debtorAccountBalance=" + debtorAccountBalance +
                ", creditorAccount='" + creditorAccount + '\'' +
                ", creditorAccountBalance=" + creditorAccountBalance +
                '}';
    }
}
