package com.codenvy.simulator.dao;

import com.codenvy.simulator.entity.Employee;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public interface EmployeeDao {
    public void saveOrUpdate(Employee employee);
    public List<Employee> findEmployeesWithFirstName(String name, int idCompany);
    public List<Employee> orderBySalary(int idCompany);
    public List<Employee> orderBySecondName(int idCompany);
    public void delete(Employee employee);
}