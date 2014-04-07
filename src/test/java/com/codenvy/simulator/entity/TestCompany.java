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

    CompanySingleton companySingleton = null;
    List<Employee> employees = null;
    int testAmountEmployee;

    @Before
    public void setupTestCompany() {
        companySingleton = CompanySingleton.getInstance();
        employees = new ArrayList<Employee>();
        for (int i = 0; i < testAmountEmployee; i++) {
            employees.add(new EmployeeWithFixedSalary());
        }
        companySingleton.setEmployees(employees);
    }

    @Test
    public void testEarnedMoney() {
        int amountEmployee = 10;
        double earnedMoney = companySingleton.earnMoney();
        assertTrue(earnedMoney <= Constant.MAX_FIXED_SALARY * amountEmployee);
    }

    @Test
    public void testPaySalaryStaffWithPositiveProfit() {
        companySingleton.setProfit(100000.0);
        companySingleton.paySalaryStaff();
        for(Employee employee: employees){
            assertNotNull(employee);
            assertTrue(employee.getSalary() > 0);
        }
    }

    @Test
    public void testPaySalaryStaffWithNegativeProfit() {
        companySingleton.takeEmployeesOnWork();
        companySingleton.setProfit(-100000.0);
        companySingleton.paySalaryStaff();
        for(Employee employee: employees){
            assertNotNull(employee);
            assertTrue(employee.getSalary() >= 0);
        }
    }

    @After
    public void clear() {
        companySingleton = null;
        employees = null;
    }
}

