package com.codenvy.simulator.dao.jdbc;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.util.DatabaseConnection;

import java.sql.*;

/**
 * Created by Andrienko Aleksander on 24.03.14.
 */
public class CompanyDaoImplJDBC implements CompanyDao{
    @Override
    public void saveOrUpdate(Company company) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Company(full_name, profit)" +
                    " values (?, ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, company.getFullName());
            preparedStatement.setDouble(2, company.getProfit());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                company.setId(resultSet.getInt(1));
            }
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
