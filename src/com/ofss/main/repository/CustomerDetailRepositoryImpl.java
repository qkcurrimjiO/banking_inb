package com.ofss.main.repository;

import com.ofss.main.domain.CustomerDetail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDetailRepositoryImpl implements CustomerDetailRepository {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/banking_inb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public void save(CustomerDetail customerDetail) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO customer_detail (first_name, last_name, date_of_birth, address, email, phone_number, locked_status, approval_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
        
            preparedStatement.setString(1, customerDetail.getFirstName());
            preparedStatement.setString(2, customerDetail.getLastName());
            preparedStatement.setDate(3, new java.sql.Date(customerDetail.getDateOfBirth().getTime()));
            preparedStatement.setString(4, customerDetail.getAddress());
            preparedStatement.setString(5, customerDetail.getEmail());
            preparedStatement.setLong(6, customerDetail.getPhoneNumber());
            preparedStatement.setBoolean(7, customerDetail.isLockedStatus());
            preparedStatement.setBoolean(8, customerDetail.isApprovalStatus());
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
    public CustomerDetail findById(int customerId) {
        try{
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM customer_detail WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new CustomerDetail(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("date_of_birth"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getLong("phone_number"),
                    rs.getBoolean("locked_status"),
                    rs.getBoolean("approval_status"),
                    null
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
        return null; // Return null if customer with given ID is not found or if there was an exception
    }

    @Override
    public CustomerDetail findByEmail(String email) {
        try{
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM customer_detail WHERE email = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new CustomerDetail(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("date_of_birth"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getLong("phone_number"),
                    rs.getBoolean("locked_status"),
                    rs.getBoolean("approval_status"),
                    null
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
        return null; // Return null if customer with given email is not found or if there was an exception
    }

    @Override
    public List<CustomerDetail> findAll() {
        List<CustomerDetail> customers = new ArrayList<>();
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM customer_detail";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                CustomerDetail customer = new CustomerDetail(
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getDate("date_of_birth"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getLong("phone_number"),
                    rs.getBoolean("locked_status"),
                    rs.getBoolean("approval_status"),
                    null
                );
                customers.add(customer);
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
            return customers; // Return the list of customers retrieved from the database
        }

    @Override
    public void update(CustomerDetail customerDetail) {
        try{
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE customer_detail SET first_name = ?, last_name = ?, date_of_birth = ?, address = ?, email = ?, phone_number = ?, locked_status = ?, approval_status = ? WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerDetail.getFirstName());
            preparedStatement.setString(2, customerDetail.getLastName());
            preparedStatement.setDate(3, new java.sql.Date(customerDetail.getDateOfBirth().getTime()));
            preparedStatement.setString(4, customerDetail.getAddress());
            preparedStatement.setString(5, customerDetail.getEmail());
            preparedStatement.setLong(6, customerDetail.getPhoneNumber());
            preparedStatement.setBoolean(7, customerDetail.isLockedStatus());
            preparedStatement.setBoolean(8, customerDetail.isApprovalStatus());
            preparedStatement.setInt(9, customerDetail.getCustomerId());
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

    @Override
    public void delete(int customerId) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "DELETE FROM customer_detail WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerId);
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

    public void updateLockedStatus(int customerId, boolean lockedStatus) {
        System.out.println("customer_id " + customerId );
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE customer_detail SET locked_status = ? WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, lockedStatus);
            preparedStatement.setInt(2, customerId);
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

    public void updateApprovalStatus(int customerId, boolean approvalStatus) {
        System.out.println("customer_id " + customerId );
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE customer_detail SET approval_status = ? WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, approvalStatus);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        } finally {
            // Close resources in finally block
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