package com.codenvy.simulator.entity;

import com.codenvy.simulator.constant.Constant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: Andrienko Aleksander
 * Date: 23.03.14
 * Time: 14:02
 */
public class TestCompany {

    Company company = null;
    List<Employee> employees = null;

    @Before
    public void setupTestCompany() {
        company = new Company();
        employees = new ArrayList<Employee>();
    }

    @Test
    public void testEarnedMoney() {
        int amountEmployee = 10;
        int amountEmployeeNegative = -10;
        assertTrue(company.earnedMoney(amountEmployee) <= Constant.MAX_FIXED_SALARY * Math.abs(amountEmployee));
        assertTrue(company.earnedMoney(amountEmployeeNegative) <= Constant.MAX_FIXED_SALARY * Math.abs(amountEmployeeNegative));
    }

    @Test
    public void testPaySalaryStaffWithPositiveProfit() {
        employees = generateEmployee();
        company.setProfit(100000.0);
        company.paySalaryStaff(employees);
        for(Employee employee: employees){
            assertNotNull(employee);
            assertTrue(employee.getSalary() > 0);
        }
    }

    @Test
    public void testPaySalaryStaffWithNegativeProfit() {
        employees = generateEmployee();
        company.setProfit(-100000.0);
        company.paySalaryStaff(employees);
        for(Employee employee: employees){
            assertNotNull(employee);
            assertTrue(employee.getSalary() > 0);
        }
    }

    private List<Employee> generateEmployee() {
        for(int i = 0; i < 5; i++) {
            employees.add(new EmployeeWithFixedSalary());
            employees.add(new EmployeeWithHourlyWages());
        }
        return employees;
    }

    @After
    public void clear() {
        company = null;
        employees = null;
    }
}

