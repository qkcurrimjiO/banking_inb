package com.ofss.main.domain;

import java.sql.Timestamp;

public class AccountDetail {
    private int accountId;
    private int customerId;
    private Timestamp openingDate;
    private String accountType;
    private double minBalance;
    private double currentBalance;
    private boolean overdraftAvail;

    public AccountDetail() {}

    public AccountDetail(int accountId, int customerId, Timestamp openingDate, String accountType, double minBalance, double currentBalance, boolean overdraftAvail) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.openingDate = openingDate;
        this.accountType = accountType;
        this.minBalance = minBalance;
        this.currentBalance = currentBalance;
        this.overdraftAvail = overdraftAvail;
    }

    // Getters and Setters
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Timestamp openingDate) {
        this.openingDate = openingDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public boolean isOverdraftAvail() {
        return overdraftAvail;
    }

    public void setOverdraftAvail(boolean overdraftAvail) {
        this.overdraftAvail = overdraftAvail;
    }

    @Override
    public String toString() {
        return "AccountDetail{" +
                "accountId=" + accountId +
                ", customerId=" + customerId +
                ", openingDate=" + openingDate +
                ", accountType='" + accountType + '\'' +
                ", minBalance=" + minBalance +
                ", currentBalance=" + currentBalance +
                ", overdraftAvail=" + overdraftAvail +
                '}';
    }
}