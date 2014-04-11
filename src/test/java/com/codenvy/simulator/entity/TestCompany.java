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
    int testAmountEmployee;

    @Before
    public void setupTestCompany() {
        company = new Company();
        employees = new ArrayList<Employee>();
        for (int i = 0; i < testAmountEmployee; i++) {
            employees.add(new EmployeeWithFixedSalary());
        }
        company.setEmployees(employees);
    }

    @Test
    public void testEarnedMoney() {
        int amountEmployee = 10;
        double earnedMoney = company.earnMoney();
        assertTrue(earnedMoney <= Constant.MAX_FIXED_SALARY * amountEmployee);
    }

    @Test
    public void testPaySalaryStaffWithPositiveProfit() {
        company.setProfit(100000.0);
        company.paySalaryStaff();
        for(Employee employee: employees){
            assertNotNull(employee);
            assertTrue(employee.getSalary() > 0);
        }
    }

    @Test
    public void testPaySalaryStaffWithNegativeProfit() {
        company.takeEmployeesOnWork();
        company.setProfit(-100000.0);
        company.paySalaryStaff();
        for(Employee employee: employees){
            assertNotNull(employee);
            assertTrue(employee.getSalary() >= 0);
        }
    }

    @After
    public void clear() {
        company = null;
        employees = null;
    }
}

