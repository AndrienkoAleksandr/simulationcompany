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
 * Created by first on 24.03.14.
 */
public class EmployeeDaoImplJDBC  implements EmployeeDao{
    @Override
    public void save(Employee employee) {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            String sql = "INSERT INTO " +
                    "Employees(date_of_birth, first_name, second_name, salary, Company_id, dtype)" +
                    " values (?, ?, ?, ?, ?, ?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, employee.getDataOfBirth());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getSecondName());
            preparedStatement.setDouble(4, employee.getSalary());
            preparedStatement.setDouble(5, employee.getIdCompany());
            Class newClass = employee.getClass();
            String typeEmployee = newClass.getSimpleName();
            preparedStatement.setString(6, typeEmployee);
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
    public List<Employee> findEmployeeWithFirstName(String name, int idCompany) {
        List<Employee> employees = new ArrayList<Employee>();
        Connection connection = null;
        connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM Employees WHERE first_name = ? AND Company_id = ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, idCompany);
            resultSet = statement.executeQuery();
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
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Employee> orderBySalary(int idCompany) {
        List<Employee> employees = new ArrayList<Employee>();
        Connection connection = null;
        connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM Employees WHERE Company_id = ? ORDER BY salary";
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idCompany);
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Employee employee = null;
                String d = resultSet.getString("dtype");
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
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Employee> orderBySecondName(int idCompany) {
        List<Employee> employees = new ArrayList<Employee>();
        Connection connection = null;
        connection = DatabaseConnection.getConnection();
        String sql = "SELECT * FROM Employees WHERE Company_id = ? ORDER BY second_name";
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idCompany);
            resultSet = statement.executeQuery();
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
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
