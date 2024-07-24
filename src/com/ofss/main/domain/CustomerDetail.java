package com.ofss.main.domain;

import java.util.Date;
import java.util.List;

public class CustomerDetail {
    private int customerId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private String email;
    private long phoneNumber;
    private boolean lockedStatus;
    private boolean approvalStatus;
    private List<AccountDetail> accounts; // List of accounts for the customer

    // Default constructor
    public CustomerDetail() {}

    // Parameterized constructor
    public CustomerDetail(int customerId, String firstName, String lastName, Date dateOfBirth, String address, String email, long phoneNumber, boolean lockedStatus, boolean approvalStatus, List<AccountDetail> accounts) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lockedStatus = lockedStatus;
        this.approvalStatus = approvalStatus;
        this.accounts = accounts;
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isLockedStatus() {
        return lockedStatus;
    }

    public void setLockedStatus(boolean lockedStatus) {
        this.lockedStatus = lockedStatus;
    }

    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public List<AccountDetail> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountDetail> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "CustomerDetail{" +
                "customerId=" + customerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", lockedStatus=" + lockedStatus +
                ", approvalStatus=" + approvalStatus +
                ", accounts=" + accounts +
                '}';
    }
}