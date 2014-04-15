package com.codenvy.simulator.dao;

import com.codenvy.simulator.entity.Employee;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public interface EmployeeDao {
    public void saveOrUpdate(Employee employee);
    public void delete(Employee employee);
    public List<Employee> orderByFirstName(int idCompany);
    public List<Employee> orderByLastName(int idCompany);
    public List<Employee> orderBySalary(int idCompany);

}