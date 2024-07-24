package com.ofss.main.repository;

import com.ofss.main.domain.LoginDetail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDetailRepositoryImpl implements LoginDetailRepository {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/banking_inb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public LoginDetail save(LoginDetail loginDetail) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO login_detail (customer_id, username, password, attempts) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
        
            preparedStatement.setInt(1, loginDetail.getCustomerId());
            preparedStatement.setString(2, loginDetail.getUsername());
            preparedStatement.setString(3, loginDetail.getPassword());
            preparedStatement.setInt(4, loginDetail.getAttempts());
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
        return loginDetail;
    }

    @Override
    public LoginDetail findByUsername(String username) {
        LoginDetail loginDetail = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM login_detail WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                loginDetail = new LoginDetail(
                    rs.getInt("customer_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("attempts")
                );
            }
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
            return loginDetail;
        }

    @Override
    public LoginDetail findByCustomerID(int customer_id) {
        LoginDetail loginDetail = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM login_detail WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                loginDetail = new LoginDetail(
                    rs.getInt("customer_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("attempts")
                );
            }
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
        return loginDetail;
    }

    @Override
    public void updateAttempts(String username, int attempts) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE login_detail SET attempts = ? WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println("username " + username);
            System.out.println("attempts " + attempts);
            preparedStatement.setInt(1, attempts);
            preparedStatement.setString(2, username);
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