package com.codenvy.simulator.application;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.CompanyDaoImpl;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.EmployeeDaoImpl;
import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.generator.RandomGenerator;
import com.codenvy.simulator.util.HibernateUtil;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public class Application {

    private static EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static CompanyDao companyDao = new CompanyDaoImpl();

    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
        CompanyDao companyDao = new CompanyDaoImpl();
        System.out.println("Create new company with name \"Adidas\"");
        Company company = new Company();
        company.setFullName("Adidas");
        RandomGenerator generator = new RandomGenerator();

        System.out.println("Generate personal company");
        int amountOfEmployee = generator.generateAmountOfEmployee();
        System.out.println("Personal company consist of " + amountOfEmployee + " employees");
        List<Employee> employeeList = generator.generateListAllEmployees(amountOfEmployee);
        printEmployeeList(employeeList);
        company.setProfit(company.earnedMoney(amountOfEmployee));
        System.out.println("The company earned: " + company.getProfit() + "!!!");
        System.out.println("The company must to pay staff salaries");
        company.paySalaryStaff(employeeList);
        System.out.println("finance status company after payment salary: profit = " + company.getProfit());
        if (company.getProfit() < 0) {
            System.out.println("Ops, the company had a loss!!!");
        }
        companyDao.saveOrUpdate(company);
        saveAllEmployees(employeeList, company.getId());
        System.out.println("Salary:");
        printEmployeeList(employeeList);
        System.out.println("List of employee order by salary:");
        printEmployeeList(employeeDao.orderBySalary(company.getId()));
        System.out.println("List of employee order by second name:");
        employeeDao.findEmployeeWithSecondName(company.getId());
        printEmployeeList(employeeDao.orderBySalary(company.getId()));
        System.out.println("Employee with first name Walt");
        printEmployeeList(employeeDao.findEmployeeWithFirstName("Walt", company.getId()));
        HibernateUtil.stopConnectionProvider();
    }

    private static void saveAllEmployees(List<Employee> employeeList, int idCompany) {
        for (Employee employee: employeeList) {
            employee.setIdCompany(idCompany);
            employeeDao.save(employee);
        }
    }

    private static void printEmployeeList(List<Employee> employeeList) {
        System.out.println("-----------------------");

        for(Employee employee: employeeList) {
            String employeeInfo = employee.getFirstName() + " "
                    + employee.getSecondName() + " " + employee.getDataOfBirth() + " ";
            if (employee.getSalary() != null) {
                employeeInfo += employee.getSalary();
            }
            System.out.println(employeeInfo);
        }
        System.out.println("-----------------------");
    }
}
