package com.ofss.main.repository;

import com.ofss.main.domain.AccountDetail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDetailRepositoryImpl implements AccountDetailRepository {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/banking_inb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public void save(AccountDetail accountDetail) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO account_detail (customer_id,account_type, min_balance,current_balance,overdraft_avail) values (?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountDetail.getCustomerId());
            preparedStatement.setString(2, accountDetail.getAccountType());
            preparedStatement.setDouble(3, accountDetail.getMinBalance());
            preparedStatement.setDouble(4, accountDetail.getCurrentBalance());
            preparedStatement.setBoolean(5, accountDetail.isOverdraftAvail());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<AccountDetail> findByCustomerId(int customerId) {
        List<AccountDetail> accounts = new ArrayList<>();
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM account_detail WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                AccountDetail account = new AccountDetail(
                    rs.getInt("account_id"),
                    rs.getInt("customer_id"),
                    rs.getTimestamp("opening_date"),
                    rs.getString("account_type"),
                    rs.getDouble("min_balance"),
                    rs.getDouble("current_balance"),
                    rs.getBoolean("overdraft_avail")
                );
                accounts.add(account);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return accounts;
    }

    @Override
    public AccountDetail findByAccountId(int accountId) {
        AccountDetail accountDetail = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM account_detail WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                accountDetail = new AccountDetail(
                    rs.getInt("account_id"),
                    rs.getInt("customer_id"),
                    rs.getTimestamp("opening_date"),
                    rs.getString("account_type"),
                    rs.getDouble("min_balance"),
                    rs.getDouble("current_balance"),
                    rs.getBoolean("overdraft_avail")
                );
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception as needed
            }
        }
        return accountDetail;
    }

    @Override
    public void updateBalance(int accountId, double newBalance) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE account_detail SET current_balance = ? WHERE account_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception as needed
            }
        }
    }
}