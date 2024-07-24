package com.ofss.main.domain;
import java.sql.Timestamp;

public class TransactionDetail {
    private int transactionId;
    private Timestamp dateTime;
    private String transactionType;
    private double amount;
    private int accountId;

    public TransactionDetail() {}

    public TransactionDetail(int transactionId, Timestamp dateTime, String transactionType, double amount, int accountId) {
        this.transactionId = transactionId;
        this.dateTime = dateTime;
        this.transactionType = transactionType;
        this.amount = amount;
        this.accountId = accountId;
    }

    // Getters
    public int getTransactionId() {
        return transactionId;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public int getAccountId() {
        return accountId;
    }

    // Setters
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String toString() {
        return "TransactionDetail{" +
                "transactionId=" + transactionId +
                ", dateTime=" + dateTime +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", accountId=" + accountId +
                '}';
    }

}
