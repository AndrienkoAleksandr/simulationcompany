package com.codenvy.simulator.generator;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.entity.EmployeeWithFixedSalary;
import com.codenvy.simulator.entity.EmployeeWithHourlyWages;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Random;

/**
 * Created Andrienko Aleksander on 17.03.14.
 */
public class RandomGenerator {

    public int generateAmountOfEmployee() {
        return generateRandomIntNumber(1, Constant.MAX_AMOUNT_EMPLOYEE);
    }

    public int generateRandomIntNumber(int numberMin, int numberMax) {
        return (new Random()).nextInt(numberMax - numberMin) + numberMin;
    }

    public double generateRandomDoubleNumber(double numberMin, double numberMax) {
        return (new Random()).nextDouble() * (numberMax - numberMin) + numberMin;
    }

    public List<Employee> generateListAllEmployees(int amount) {
        List<Employee> employeeList = new ArrayList<Employee>();
        int amountEmployeeWithFixedSalary = generateRandomIntNumber(1, amount);
        int amountEmployeeWithHourlyWages = amount - amountEmployeeWithFixedSalary;
        generateListSomeEmployees(employeeList, amountEmployeeWithFixedSalary, "EmployeeWithFixedSalary");
        generateListSomeEmployees(employeeList, amountEmployeeWithHourlyWages, "EmployeeWithHourlyWages");
        return employeeList;
    }

    public void generateListSomeEmployees(List<Employee> employeeList,int amountSomeEmployee, String className) {
        for (int i = 0; i < amountSomeEmployee; i++) {
            if (className.equals("EmployeeWithFixedSalary")) {
                employeeList.add(i, new EmployeeWithFixedSalary());
//                employeeList.get(i).setSalary(generateRandomDoubleNumber(Constant.MIN_FIXED_SALARY, Constant.MAX_FIXED_SALARY));
            }
            if (className.equals("EmployeeWithHourlyWages")) {
                employeeList.add(i, new EmployeeWithHourlyWages());
                ((EmployeeWithHourlyWages)employeeList.get(i))
                        .calculationSalary(generateRandomDoubleNumber(Constant.MIN_WAGES_PER_HOUR, Constant.MAX_WAGES_PER_HOUR));
            }
            employeeList.get(i).setFirstName(generateFirstName());
            employeeList.get(i).setSecondName(generateSecondName());
            employeeList.get(i).setDataOfBirth(generateRandomDate());
        }
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
