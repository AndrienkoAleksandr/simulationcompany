package com.codenvy.simulator.application;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.CompanyDaoImpl;
import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.generator.RandomGenerator;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public class Application {



    public static void main(String[] args) {
        CompanyDao companyDao = new CompanyDaoImpl();
        Company company = new Company();
        company.setFullName("Adidas");
        RandomGenerator generator = new RandomGenerator();
        //generate random amount employees of company
        int amountOfEmployee = generator.generateAmount();
        //generate List of Employee
        List<Employee> employeeList = generator.generateListAllEmployees(amountOfEmployee);

        company.setEmployees(employeeList);
        //save Result to Base Date
        companyDao.save(company);
    }
}
