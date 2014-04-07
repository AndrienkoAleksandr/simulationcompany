package com.codenvy.simulator.dao;

import com.codenvy.simulator.entity.Employee;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public interface EmployeeDao {
    public void save(Employee employee);
    public List<Employee> findEmployeeWithFirstName(String name, int idCompany);
    public List<Employee> orderBySalary(int idCompany);
    public List<Employee> orderBySecondName(int idCompany);
}