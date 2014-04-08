package com.codenvy.simulator.dao.jdbc;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.entity.CompanySingleton;
import com.codenvy.simulator.util.DatabaseConnection;

import java.sql.*;

/**
 * Created by Andrienko Aleksander on 24.03.14.
 */
public class CompanyDaoImplJDBC implements CompanyDao{
    @Override
    public void saveOrUpdate(CompanySingleton companySingleton) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Company(full_name, profit)" +
                    " values (?, ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, companySingleton.getFullName());
            preparedStatement.setDouble(2, companySingleton.getProfit());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                companySingleton.setId(resultSet.getInt(1));
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
