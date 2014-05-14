package com.codenvy.simulator.dao.jdbc;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 24.03.14.
 */
public class CompanyDaoImplJDBC implements CompanyDao{
    @Override
    public void saveOrUpdate(Company company) {
        Connection connection = null;
        ResultSet resultSet = null;
        String sql;
        try {
            connection = DatabaseConnection.getConnection();

            sql = "INSERT INTO Company (id, full_name, profit, type_saving_data) " +
                    "VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
                    "full_name = ?, profit = ?, type_saving_data = ?";

            //we need saveOrUpdate id_company for next operation
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if (company.getId() == null) {
                preparedStatement.setString(1, null);
            } else {
                preparedStatement.setInt(1, company.getId());
            }
            preparedStatement.setString(2, company.getFullName());
            preparedStatement.setDouble(3, company.getProfit());
            preparedStatement.setString(4, company.getTypeOfSavingData());
            preparedStatement.setString(5, company.getFullName());
            preparedStatement.setDouble(6, company.getProfit());
            preparedStatement.setString(7, company.getTypeOfSavingData());
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

    @Override
    public void deleteFromId(Company company) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "DELETE FROM Company WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, company.getId());
            preparedStatement.executeUpdate();
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

    @Override
    public Company getCompanyById(int idCompany) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM Company WHERE id = ?";
        List<Company> companies = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idCompany);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getInt("id"));
                company.setTypeOfSavingData(resultSet.getString("type_saving_data"));
                company.setProfit(resultSet.getDouble("profit"));
                company.setFullName(resultSet.getString("full_name"));
                EmployeeDao employeeDao = new EmployeeDaoImplJDBC();
                company.setEmployees(employeeDao.getEmployeesByCompanyId(idCompany));
                companies.add(company);

            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return companies.get(0);
    }
}
