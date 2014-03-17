package com.codenvy.simulator.generator;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.entity.EmployeeWithFixedSalary;
import com.codenvy.simulator.entity.EmployeeWithHourlyWages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created Andrienko Aleksander on 17.03.14.
 */
public class RandomGenerator {

    public int generateAmount() {
        return generateAmount(Constant.MAX_AMOUNT_EMPLOYEE);
    }

    public int generateAmount(int number) {
        return (new Random()).nextInt(number);
    }

    public List<Employee> generateListAllEmployees(int amount) {
        List<Employee> employeeList = new ArrayList<Employee>();
        int amountEmployeeWithFixedSalary = generateAmount(amount);
        int amountEmployeeWithHourlyWages = amount - amountEmployeeWithFixedSalary;
        generateListSomeEmployees(employeeList, amountEmployeeWithFixedSalary, "EmployeeWithFixedSalary");
        generateListSomeEmployees(employeeList, amountEmployeeWithHourlyWages, "EmployeeWithHourlyWages");
        return employeeList;
    }

    public void generateListSomeEmployees(List<Employee> employeeList,int amountSomeEmployee, String className) {
        for (int i = 0; i < amountSomeEmployee; i++) {
            if (className.equals("EmployeeWithFixedSalary")) {
                employeeList.add(i, new EmployeeWithFixedSalary());
            }
            if (className.equals("EmployeeWithHourlyWages")) {
                employeeList.add(i, new EmployeeWithHourlyWages());
            }
            employeeList.get(i).setFirstName(generateFirstName());
            employeeList.get(i).setSecondName(generateSecondName());
        }
    }

    private String generateFirstName() {
        return Constant.FIRST_NAME_LIST[generateAmount(Constant.FIRST_NAME_LIST_LENGTH)];
    }

    private String generateSecondName() {
        return Constant.SECOND_NAME_LIST[generateAmount(Constant.SECOND_NAME_LIST_LENGTH)];
    }
}
