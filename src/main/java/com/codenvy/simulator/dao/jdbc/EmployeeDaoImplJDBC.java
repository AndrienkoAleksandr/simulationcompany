package com.codenvy.simulator.dao.jdbc;

import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.entity.EmployeeWithFixedSalary;
import com.codenvy.simulator.entity.EmployeeWithHourlyWages;
import com.codenvy.simulator.entity.EnumEmployee;
import com.codenvy.simulator.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 24.03.14.
 */
public class EmployeeDaoImplJDBC  implements EmployeeDao {

    @Override
    public void saveOrUpdate(Employee employee) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO Employees" +
                    "(id, date_of_birth, first_name, second_name, salary, Company_id, dtype)" +
                    " values (?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "date_of_birth = ?, first_name = ?,  second_name = ?," +
                    "salary = ?, Company_id = ?, dtype = ?";
            preparedStatement = connection.prepareStatement(sql);
            if (employee.getId() == null) {
                preparedStatement.setString(1, null);
            } else {
                preparedStatement.setInt(1, employee.getId());
            }
            preparedStatement.setDate(2, employee.getDataOfBirth());
            preparedStatement.setString(3, employee.getFirstName());
            preparedStatement.setString(4, employee.getSecondName());
            preparedStatement.setDouble(5, employee.getSalary());
            if (employee.getIdCompany() != null) {
                preparedStatement.setDouble(6, employee.getIdCompany());
            } else {
                throw new IllegalArgumentException("field company_id can't be null!!!");
            }
            Class newClass = employee.getClass();
            String typeEmployee = newClass.getSimpleName();
            preparedStatement.setString(7, typeEmployee);
            preparedStatement.setDate(8, employee.getDataOfBirth());
            preparedStatement.setString(9, employee.getFirstName());
            preparedStatement.setString(10, employee.getSecondName());
            preparedStatement.setDouble(11, employee.getSalary());
            preparedStatement.setDouble(12, employee.getIdCompany());
            preparedStatement.setString(13, typeEmployee);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        completeRequest(preparedStatement, connection);
    }

    @Override
    public void delete(Employee employee) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "DELETE FROM Employees WHERE id = ? and Company_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employee.getId());
            preparedStatement.setInt(2, employee.getIdCompany());
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
    public List<Employee> orderByFirstName(int idCompany) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM Employees WHERE Company_id = ? ORDER BY first_name";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idCompany);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doPrepareStatement(statement, connection);
    }

    @Override
    public List<Employee> orderBySalary(int idCompany) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM Employees WHERE Company_id = ? ORDER BY salary";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idCompany);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doPrepareStatement(statement, connection);
    }

    @Override
    public List<Employee> orderByLastName(int idCompany) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM Employees WHERE Company_id = ? ORDER BY second_name";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idCompany);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doPrepareStatement(statement, connection);
    }

    public void completeRequest(PreparedStatement statement, Connection connection) {
        try {
            statement.close();
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

    public List<Employee> doPrepareStatement(PreparedStatement statement, Connection connection) {
        List<Employee> employees = new ArrayList<Employee>();
        try {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Employee employee = null;
                if(resultSet.getString("dtype").equals(String.valueOf(EnumEmployee.EmployeeWithHourlyWages))) {
                    employee = new EmployeeWithHourlyWages();
                }
                if(resultSet.getString("dtype").equals(String.valueOf(EnumEmployee.EmployeeWithFixedSalary))) {
                    employee = new EmployeeWithFixedSalary();
                }
                employee.setId(resultSet.getInt("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setSecondName(resultSet.getString("second_name"));
                employee.setSalary(resultSet.getDouble("salary"));
                employee.setDataOfBirth(resultSet.getDate("date_of_birth"));
                employee.setIdCompany(resultSet.getInt("Company_id"));
                employees.add(employee);
            }
            resultSet.close();
            completeRequest(statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}