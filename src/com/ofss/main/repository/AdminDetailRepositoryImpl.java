package com.ofss.main.repository;

import com.ofss.main.domain.AdminDetail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDetailRepositoryImpl implements AdminDetailRepository {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/banking_inb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public AdminDetail findByUsername(String username) {
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM admin_detail WHERE username = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new AdminDetail(
                        rs.getInt("admin_id"),
                        rs.getString("username"),
                        rs.getString("password"));
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
        return null;
    }
}