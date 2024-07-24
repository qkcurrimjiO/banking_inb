package com.ofss.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.ofss.main.domain.AccountDetail;
import com.ofss.main.domain.CustomerDetail;
import com.ofss.main.domain.LoginDetail;

import com.ofss.main.service.CustomerDetailService;
import com.ofss.main.service.CustomerDetailServiceImpl;
import com.ofss.main.service.LoginDetailService;
import com.ofss.main.service.LoginDetailServiceImpl;
import com.ofss.main.service.AdminDetailService;
import com.ofss.main.service.AdminDetailServiceImpl;
import com.ofss.main.service.AccountDetailService;
import com.ofss.main.service.AccountDetailServiceImpl;


public class ProjectINBMain {
    public static void main(String[] args) {
        // Establish the database connection using the App class
        String continueChoice;

        Connection connection = App.getConnection();
        if (connection == null) {
            System.out.println("Failed to establish database connection.");
            return;
        }

        CustomerDetailService customerService = new CustomerDetailServiceImpl();
        LoginDetailService loginService = new LoginDetailServiceImpl();
        AdminDetailService adminService = new AdminDetailServiceImpl();
        AccountDetailService accountService = new AccountDetailServiceImpl();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Are you an admin or a customer?");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("Enter your choice");
            int userType = scanner.nextInt();
            scanner.nextLine();

            if (userType == 1) {
                // Admin login
                System.out.println("Enter admin username");
                String adminUsername = scanner.nextLine();
                System.out.println("Enter admin password");
                String adminPassword = scanner.nextLine();

                boolean isAdminAuthenticated = adminService.authenticate(adminUsername, adminPassword);
                if (isAdminAuthenticated) {
                    System.out.println("Admin login successful");
                    System.out.println("Menu");
                    System.out.println("1. Unblock an account");
                    System.out.println("2. Activate an account");
                    int adminChoice = scanner.nextInt();

                    if (adminChoice == 1) {
                        System.out.println("Enter customer ID to unlock");
                        int customerId = scanner.nextInt();
                        scanner.nextLine(); 

                        customerService.updateLockedStatus(customerId, false);

                        LoginDetail loginDetail = loginService.findByCustomerID(customerId);
                        if (loginDetail != null) {
                            loginService.updateAttempts(loginDetail.getUsername(), 0);
                        }
                        System.out.println("Customer account unlocked successfully");

                    } else if (adminChoice == 2) {
                        System.out.println("Enter customer ID to activate");
                        int customerId = scanner.nextInt();
                        scanner.nextLine(); 

                        customerService.updateApprovalStatus(customerId, true);
                        System.out.println("Customer account unlocked successfully");                        
                    }
                } else {
                    System.out.println("Invalid admin credentials");
                }

            } else if (userType == 2) {
                System.out.println("MENU:");
                System.out.println("1. Register user");
                System.out.println("2. Login");
                System.out.println("Enter your choice");
                int ch = scanner.nextInt();
                scanner.nextLine(); 

                switch (ch) {
                case 1:
                    // Registration details
                    System.out.println("Enter first name");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter last name");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter DOB (YYYY-MM-DD):");
                    String dobStr = scanner.nextLine();
                    LocalDate dateOfBirth = LocalDate.parse(dobStr);
                    System.out.println("Enter address");
                    String address = scanner.nextLine();
                    System.out.println("Enter email");
                    String email = scanner.nextLine();
                    System.out.println("Enter phone number");
                    long phoneNumber = scanner.nextLong();
                    scanner.nextLine(); 

                    CustomerDetail newCustomer = new CustomerDetail(0, firstName, lastName, java.sql.Date.valueOf(dateOfBirth), address, email, phoneNumber, false, false, null);
                    boolean customerResult = customerService.registerCustomer(newCustomer);

                    if (customerResult) {
                        System.out.println("Customer registered successfully");

                        // After registering customer, register login details
                        System.out.println("Enter username");
                        String username = scanner.nextLine();
                        System.out.println("Enter password");
                        String password = scanner.nextLine();

                        // Find the new customer ID to associate with login detail
                        int customerId = customerService.getCustomerByEmail(email).getCustomerId();
                        LoginDetail newLogin = new LoginDetail(customerId, username, password, 0);
                        loginService.save(newLogin);

                        System.out.println("Login details registered successfully");

                        // Ask for account type and create the account
                        System.out.println("Enter account type (Current/Savings):");
                        String accountType = scanner.nextLine();
                        accountService.createAccount(customerId, accountType);

                        System.out.println("Account created successfully");
                    } else {
                        System.out.println("Failed to register new customer");
                    }

                    break;

                case 2:
                    // Login details
                    System.out.println("Enter username");
                    String username = scanner.nextLine();
                    System.out.println("Enter password");
                    String password = scanner.nextLine();

                    boolean isAuthenticated = loginService.authenticate(username, password);
                    if (isAuthenticated) {
                            System.out.println("Login successful");
                            // Display account details
                            int customerId = loginService.findByUsername(username).getCustomerId();
                            List<AccountDetail> accounts = accountService.getAccountsByCustomerId(customerId);
                            System.out.println("Your accounts:");
                            for (AccountDetail account : accounts) {
                                System.out.println("Account Type: " + account.getAccountType() + ", Balance: " + account.getCurrentBalance());
                            }
                            
                            // Transfer money option
                            System.out.println("Do you want to transfer money? (Yes/No)");
                            String transferChoice = scanner.nextLine();
                            if (transferChoice.equalsIgnoreCase("Yes")) {
                                System.out.println("Enter your account ID:");
                                int fromAccountId = scanner.nextInt();
                                System.out.println("Enter receiver's account ID:");
                                int toAccountId = scanner.nextInt();
                                System.out.println("Enter amount to transfer:");
                                double amount = scanner.nextDouble();
                                scanner.nextLine(); // Consume the newline character

                                accountService.transferMoney(fromAccountId, toAccountId, amount);
                                System.out.println("Transfer successful");
                            }
                        } else {
                            System.out.println("Login failed");
                        }
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
            System.out.println("Do you want to continue (Yes/No)?");
            continueChoice = scanner.nextLine();
        } while (continueChoice.equalsIgnoreCase("Yes"));

        scanner.close();
        // Close the connection
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to close connection");
            e.printStackTrace();
        }
    }
}