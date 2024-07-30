package com.ofss.main.repository;

import com.ofss.main.domain.ChequeDetail;
import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChequeDetailRepositoryImpl implements ChequeDetailRepository {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/banking_inb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    @Override
    public void save(ChequeDetail chequeDetail) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO cheque_details (payer_id, payee_id, amount, cheque_date, cheque_status, payer_name) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, chequeDetail.getPayerId());
            preparedStatement.setInt(2, chequeDetail.getPayeeId());
            preparedStatement.setDouble(3, chequeDetail.getAmount());
            preparedStatement.setDate(4, chequeDetail.getChequeDate());
            preparedStatement.setString(5, chequeDetail.getChequeStatus());
            preparedStatement.setString(6, chequeDetail.getPayerName());
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ChequeDetail findById(int chequeId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM cheque_details WHERE cheque_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, chequeId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return new ChequeDetail(
                    rs.getInt("cheque_id"),
                    rs.getInt("payer_id"),
                    rs.getInt("payee_id"),
                    rs.getDouble("amount"),
                    rs.getDate("cheque_date"),
                    rs.getString("cheque_status"),
                    rs.getString("payer_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
                e.printStackTrace(); // Or handle gracefully
            }
        }
        return null;
    }

    @Override
    public List<ChequeDetail> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ChequeDetail> cheques = new ArrayList<>();
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM cheque_details";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cheques.add(new ChequeDetail(
                    rs.getInt("cheque_id"),
                    rs.getInt("payer_id"),
                    rs.getInt("payee_id"),
                    rs.getDouble("amount"),
                    rs.getDate("cheque_date"),
                    rs.getString("cheque_status"),
                    rs.getString("payer_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
                e.printStackTrace(); // Or handle gracefully
            }
        }
        return cheques;
    }

    @Override
    public void updateStatus(int chequeId, String status) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String sql = "UPDATE cheque_details SET cheque_status = ? WHERE cheque_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, chequeId);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }






}
