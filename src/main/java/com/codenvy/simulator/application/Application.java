package com.codenvy.simulator.application;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.hibernate.CompanyDaoImpl;
import com.codenvy.simulator.dao.hibernate.EmployeeDaoImpl;
import com.codenvy.simulator.dao.jdbc.CompanyDaoImplJDBC;
import com.codenvy.simulator.dao.jdbc.EmployeeDaoImplJDBC;
import com.codenvy.simulator.entity.CompanySingleton;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.util.HibernateUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 06.04.14.
 */
public class Application {

    private static EmployeeDao employeeDao = null;
    private static CompanyDao companyDao = null;

    public static void main(String[] args) {
        System.out.println("Create new companySingleton with name \"Adidas\"");
        CompanySingleton companySingleton = CompanySingleton.getInstance();
        companySingleton.setFullName("Adidas");
        int choice = getUserChoiceOfDataStorage();
        List<Employee> employeeList = companySingleton.takeEmployeesOnWork();
        printEmployeeList(employeeList);
        companySingleton.setProfit(companySingleton.earnMoney());

        System.out.println("The companySingleton earned: " + companySingleton.getProfit() + "!!!");
        System.out.println("The companySingleton must to pay staff salaries");

        companySingleton.paySalaryStaff();

        System.out.println("finance status companySingleton after payment salary: profit = " + companySingleton.getProfit());
        if (companySingleton.getProfit() < 0) {
            System.out.println("Ops, the companySingleton had a loss!!!");
        }
        companyDao.saveOrUpdate(companySingleton);
        saveAllEmployees(employeeList, companySingleton.getId());
        System.out.println("Salary:");
        printEmployeeList(employeeList);
        System.out.println("List of employee order by salary:");
        printEmployeeList(employeeDao.orderBySalary(companySingleton.getId()));
        System.out.println("List of employee order by second name:");
        printEmployeeList(employeeDao.orderBySecondName(companySingleton.getId()));
        System.out.println("Employee with first name Walt");
        printEmployeeList(employeeDao.findEmployeeWithFirstName("Walt", companySingleton.getId()));
        if (choice == 2) {
            HibernateUtil.stopConnectionProvider();
        }
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


    public static int getUserChoiceOfDataStorage() {
        int choice = 0;
        do {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                System.out.println("If you want save date with jdbc enter 1, with hibernate 2");
                choice = Integer.parseInt(reader.readLine());
                if (choice == 1) {
                    employeeDao = new EmployeeDaoImplJDBC();
                    companyDao = new CompanyDaoImplJDBC();
                    break;
                }
                if (choice == 2) {
                    HibernateUtil.getSessionFactory();
                    employeeDao = new EmployeeDaoImpl();
                    companyDao = new CompanyDaoImpl();
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
