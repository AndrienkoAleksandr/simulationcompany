package com.codenvy.simulator.generator;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.entity.EmployeeWithFixedSalary;
import com.codenvy.simulator.entity.EmployeeWithHourlyWages;
import com.codenvy.simulator.entity.EnumEmployee;
import com.codenvy.simulator.entity.factory.FactoryEmployeeFixedSalary;
import com.codenvy.simulator.entity.factory.FactoryEmployeeHourlyWages;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.compare;
import static java.lang.Double.compare;

/**
 * Created Andrienko Aleksander on 17.03.14.
 */
public class RandomGenerator {

    public RandomGenerator() {
    }

    public int generateAmountOfEmployee() {
        return generateRandomIntNumber(1, Constant.MAX_AMOUNT_EMPLOYEE);
    }

    public int generateRandomIntNumber(int numberMin, int numberMax) {
        int result = compare(numberMax, numberMin);
        switch (result) {
            case 0:
                return numberMin;
            case -1:
                return (new Random()).nextInt(numberMin - numberMax) + numberMax;
        }
        return (new Random()).nextInt(numberMax - numberMin) + numberMin;
    }

    public double generateRandomDoubleNumber(double numberMin, double numberMax) {
        int result = compare(numberMax, numberMin);
        switch (result) {
            case 0:
                return numberMin;
            case -1:
                return (new Random()).nextDouble() * (numberMin - numberMax) + numberMax;
        }
        return (new Random()).nextDouble() * (numberMax - numberMin) + numberMin;
    }

    public List<Employee> generateListAllEmployees(int amount) {
        List<Employee> employeeList = new ArrayList<Employee>();
        int amountEmployeeWithFixedSalary = generateRandomIntNumber(1, amount);
        int amountEmployeeWithHourlyWages = amount - amountEmployeeWithFixedSalary;
        employeeList.addAll(generateListSomeEmployees(amountEmployeeWithFixedSalary, EnumEmployee.EmployeeWithFixedSalary));
        employeeList.addAll(generateListSomeEmployees(amountEmployeeWithHourlyWages, EnumEmployee.EmployeeWithHourlyWages));
        return employeeList;
    }

    public List<Employee> generateListSomeEmployees(int amountSomeEmployee, EnumEmployee className) {
        amountSomeEmployee = Math.abs(amountSomeEmployee);
        List<Employee> employees = new ArrayList<Employee>();
        for (int i = 0; i < amountSomeEmployee; i++) {
            if (className.equals(EnumEmployee.EmployeeWithFixedSalary)) {
                employees.add(i, new FactoryEmployeeFixedSalary().create());
                setInitialParamsOfEmployee(employees.get(i));
            }
            if (className.equals(EnumEmployee.EmployeeWithHourlyWages)) {
                employees.add(i, new FactoryEmployeeHourlyWages().create());
                ((EmployeeWithHourlyWages)employees.get(i))
                        .calculationSalary(generateRandomDoubleNumber(Constant.MIN_WAGES_PER_HOUR, Constant.MAX_WAGES_PER_HOUR));
                setInitialParamsOfEmployee(employees.get(i));
            }
        }
        return employees;
    }

    public void setInitialParamsOfEmployee(Employee employee) {
        employee.setFirstName(generateFirstName());
        employee.setSecondName(generateSecondName());
        employee.setDataOfBirth(generateRandomDate());
    }

    private String generateFirstName() {
        return Constant.FIRST_NAME_LIST[generateRandomIntNumber(0, Constant.FIRST_NAME_LIST_LENGTH)];
    }

    private String generateSecondName() {
        return Constant.SECOND_NAME_LIST[generateRandomIntNumber(0, Constant.SECOND_NAME_LIST_LENGTH)];
    }

    private Date generateRandomDate() {
        long day = generateRandomIntNumber(Constant.MIN_DAY_OF_BIRTH, Constant.MAX_DAY_OF_BIRTH);
        long month = generateRandomIntNumber(Constant.MIN_MONTH_OF_BIRTH, Constant.MAX_MONTH_OF_BIRTH);
        long year = generateRandomIntNumber(Constant.MIN_YEAR_OF_BIRTH, Constant.MAX_YEAR_OF_BIRTH);
        return Date.valueOf(year + "-" + month + "-" + day);
    }
}
