package com.ofss.main.repository;

import com.ofss.main.domain.TransactionDetail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDetailRepositoryImpl implements TransactionDetailRepository {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/banking_inb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public void save(TransactionDetail transactionDetail) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO transaction_detail (date_time, transaction_type, amount, account_id) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, transactionDetail.getDateTime());
            preparedStatement.setString(2, transactionDetail.getTransactionType());
            preparedStatement.setDouble(3, transactionDetail.getAmount());
            preparedStatement.setInt(4, transactionDetail.getAccountId());
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