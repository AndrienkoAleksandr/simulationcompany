package com.codenvy.simulator.application;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.file.CompanyDaoImplFile;
import com.codenvy.simulator.dao.file.EmployeeDaoImplFile;
import com.codenvy.simulator.dao.hibernate.CompanyDaoImplHibernate;
import com.codenvy.simulator.dao.hibernate.EmployeeDaoImplHibernate;
import com.codenvy.simulator.dao.jdbc.CompanyDaoImplJDBC;
import com.codenvy.simulator.dao.jdbc.EmployeeDaoImplJDBC;
import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.module.FileModule;
import com.codenvy.simulator.module.HibernateModule;
import com.codenvy.simulator.module.JDBCModule;
import com.codenvy.simulator.util.HibernateUtil;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 06.04.14.
 */
public class Application {

    private static EmployeeDao employeeDao = null;
    private static CompanyDao companyDao = null;
    private static Company company = null;

    public static void main(String[] args) {
        System.out.println("Create new company with name \"Adidas\"");
        int choice = getUserChoiceOfDataStorage();
        company.setFullName("Adidas");
        List<Employee> employeeList = company.takeEmployeesOnWork();
        printEmployeeList(employeeList);
        company.setProfit(company.earnMoney());

        System.out.println("The company earned: " + company.getProfit() + "!!!");
        System.out.println("The company must to pay staff salaries");

        company.paySalaryStaff();

        System.out.println("finance status company after payment salary: profit = " + company.getProfit());
        if (company.getProfit() < 0) {
            System.out.println("Ops, the company had a loss!!!");
        }
        company.saveCompanyToStorage();
        company.saveEmployeeListToStorage(company.getId());
        System.out.println("Salary:");
        printEmployeeList(employeeList);
        System.out.println("List of employee order by salary:");
        printEmployeeList(employeeDao.orderBySalary(company.getId()));
        System.out.println("List of employee order by second name:");
        printEmployeeList(employeeDao.orderBySecondName(company.getId()));
        System.out.println("Employee with first name Walt");
        printEmployeeList(employeeDao.findEmployeesWithFirstName("Walt", company.getId()));
        if (choice == 2) {
            HibernateUtil.stopConnectionProvider();
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


    public static int getUserChoiceOfDataStorage() {
        int choice = 0;
        do {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.println("If you want save date with jdbc enter 1," +
                        "\nwith hibernate enter 2" +
                        "\nwith file enter 3");
                choice = Integer.parseInt(reader.readLine());
                if (choice == 1) {
                    employeeDao = new EmployeeDaoImplJDBC();
                    companyDao = new CompanyDaoImplJDBC();
                    Injector injector = Guice.createInjector(new JDBCModule());
                    company = injector.getInstance(Company.class);
                    break;
                }
                if (choice == 2) {
                    HibernateUtil.getSessionFactory();
                    employeeDao = new EmployeeDaoImplHibernate();
                    companyDao = new CompanyDaoImplHibernate();
                    Injector injector = Guice.createInjector(new HibernateModule());
                    company = injector.getInstance(Company.class);
                    break;
                }
                if (choice == 3) {
                    employeeDao = new EmployeeDaoImplFile();
                    companyDao = new CompanyDaoImplFile();
                    Injector injector = Guice.createInjector(new FileModule());
                    company = injector.getInstance(Company.class);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Error input!");
            }
            System.out.println("You must enter only digit only 1 or 2");
        } while (true);
        return choice;
    }
}
