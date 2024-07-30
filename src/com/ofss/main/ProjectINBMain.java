package com.ofss.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.ofss.main.domain.AccountDetail;
import com.ofss.main.domain.CustomerDetail;
import com.ofss.main.domain.LoginDetail;
import com.ofss.main.domain.ChequeDetail;

import com.ofss.main.service.CustomerDetailService;
import com.ofss.main.service.CustomerDetailServiceImpl;
import com.ofss.main.service.LoginDetailService;
import com.ofss.main.service.LoginDetailServiceImpl;
import com.ofss.main.service.AdminDetailService;
import com.ofss.main.service.AdminDetailServiceImpl;
import com.ofss.main.service.AccountDetailService;
import com.ofss.main.service.AccountDetailServiceImpl;
import com.ofss.main.service.ChequeDetailService;
import com.ofss.main.service.ChequeDetailServiceImpl;


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
        ChequeDetailService chequeService = new ChequeDetailServiceImpl();

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
                    System.out.println("3: Managing cheques");
                    System.out.println("Enter your choice");
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

                    } else if (adminChoice == 3) {
                        // Admin options for approving/rejecting cheques
                        System.out.println("Do you want to approve/reject cheques? (Yes/No)");
                        scanner.nextLine();
                        String chequeApprovalChoice = scanner.nextLine();
                        if (chequeApprovalChoice.equalsIgnoreCase("Yes")) {
                            List<ChequeDetail> cheques = chequeService.getAllCheques();
                            for (ChequeDetail cheque : cheques) {
                                System.out.println("Cheque ID: " + cheque.getChequeId() + ", Payer ID: " + cheque.getPayerId() + ", Payee ID: " + cheque.getPayeeId() + ", Amount: " + cheque.getAmount() + ", Status: " + cheque.getChequeStatus());
                            }
                            System.out.println("Enter cheque ID to approve/reject:");
                            int chequeId = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            System.out.println("Approve (1) or Reject (2)?");
                            int approvalChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            if (approvalChoice == 1) {
                                chequeService.approveOrRejectCheque(chequeId, true);
                                System.out.println("Cheque approved");
                            } else if (approvalChoice == 2) {
                                chequeService.approveOrRejectCheque(chequeId, false);
                                System.out.println("Cheque rejected");
                            } else {
                                System.out.println("SQL Error");
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid admin credentials");
                }
            } else if (userType == 2) {
                System.out.println("Menu:");
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

                            // Customer Menu
                            boolean customerMenuActive = true;
                            while (customerMenuActive) {
                                System.out.println("MENU:");
                                System.out.println("1. Transfer money");
                                System.out.println("2. Issue cheque");
                                System.out.println("3. Logout");
                                System.out.println("Enter your choice");
                                int customerChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (customerChoice) {
                                case 1:
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
                                break;

                                case 2:
                                // Issue cheque
                                System.out.println("Do you want to issue a cheque? (Yes/No)");
                                String issueChequeChoice = scanner.nextLine();
                                if (issueChequeChoice.equalsIgnoreCase("Yes")) {
                                    System.out.println("Enter payer account ID:");
                                    int payerId = scanner.nextInt();
                                    System.out.println("Enter payee account ID:");
                                    int payeeId = scanner.nextInt();
                                    System.out.println("Enter amount:");
                                    double amount = scanner.nextDouble();
                                    System.out.println("Enter cheque date (YYYY-MM-DD):");
                                    String chequeDateStr = scanner.next();
                                    scanner.nextLine(); // Consume the newline character
                                    LocalDate chequeDate = LocalDate.parse(chequeDateStr);

                                    ChequeDetail newCheque = new ChequeDetail(0, payerId, payeeId, amount, java.sql.Date.valueOf(chequeDate), "Not Cleared", username);
                                    try {
                                        chequeService.issueCheque(newCheque);
                                        System.out.println("Cheque issued successfully");
                                    } catch (IllegalArgumentException e) {
                                        System.out.println(e.getMessage());
                                    }
                                 }
                                 break;

                            case 3:
                                    customerMenuActive = false;
                                    System.out.println("Logged out successfully");
                                    break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                            }
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