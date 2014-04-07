package com.codenvy.simulator.generator;

import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.entity.EmployeeWithFixedSalary;
import com.codenvy.simulator.entity.EmployeeWithHourlyWages;
import com.codenvy.simulator.entity.EnumEmployee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: Andrienko Aleksander
 * Date: 22.03.14
 * Time: 21:55
 */
public class TestRandomGenerator {

    RandomGenerator generator = null;
    List<Employee> employeeList = null;
    Employee employeeFS = null;
    Employee employeeHW = null;

    @Before
    public void setupTestRandom() {
        generator = new RandomGenerator();
        employeeList = new ArrayList<Employee>();
        employeeFS = new EmployeeWithFixedSalary();
        employeeHW = new EmployeeWithHourlyWages();
    }

    @Test
    public void testGenerateNotNegativeAmountOfEmployee() {
        for(int i = 0; i < 1000; i++) {
            assertTrue(generator.generateAmountOfEmployee() > 0);
        }
    }

    @Test
    public void testMaxRangeGenerateRandomIntNumberIsNotLargeThanSecondArgument() {
        int max = 100;
        assertTrue(generator.generateRandomIntNumber(0, max) <= max);
    }

    @Test
    public void testMinRangeGenerateRandomIntNumberIsNotLargeThanSecondArgument() {
        int min = 10;
        assertTrue(generator.generateRandomIntNumber(min, 100) >= min);
    }

    @Test
    public void testMaxRangeGenerateRandomDoubleNumberIsNotLargeThanSecondArgument() {
        double max = 100.0;
        assertTrue(generator.generateRandomDoubleNumber(0.0, max) <= max);
    }

    @Test
    public void testMinRangeGenerateRandomDoubleNumberIsNotLargeThanSecondArgument() {
        double min = 10.0;
        assertTrue(generator.generateRandomDoubleNumber(min, 100.0) >= min);
    }

    @Test
    public void testGenerateListAllEmployeesReturnNotNullList() {
        assertNotNull(generator.generateListAllEmployees(10));
    }

    @Test
    public void testGenerateListAllEmployeesCheckSizeOfList() {
        int amountEmployee = 10;
        assertTrue(generator.generateListAllEmployees(amountEmployee).size() <= amountEmployee);
    }

    @Test
    public void testGenerateListAllEmployeesFirstAndSecondNamesAndDayOfBirthNotNull() {
        employeeList = generator.generateListAllEmployees(10);
        for(Employee employee: employeeList) {
            assertNotNull(employee.getFirstName());
            assertNotNull(employee.getSecondName());
            assertNotNull(employee.getDataOfBirth());
        }
    }

    @Test
    public void generateGenerateListSomeEmployeesReturnNotNull() {
        assertNotNull(generator.generateListSomeEmployees(10, EnumEmployee.EmployeeWithHourlyWages));
        assertNotNull(generator.generateListSomeEmployees(10, EnumEmployee.EmployeeWithFixedSalary));
    }

    @Test
    public void generateGenerateListSomeEmployeesCheckListCollection() {
        assertEquals(10, generator.generateListSomeEmployees(10, EnumEmployee.EmployeeWithHourlyWages).size());
        assertEquals(10, generator.generateListSomeEmployees(10, EnumEmployee.EmployeeWithFixedSalary).size());
    }

    @Test
    public void testGenerateListSomeEmployeesCheckWorkWithNegativeAmountOfEmployee() {
        assertTrue(generator.generateListSomeEmployees(-10, EnumEmployee.EmployeeWithHourlyWages).size() == 10);
        assertTrue(generator.generateListSomeEmployees(-10, EnumEmployee.EmployeeWithFixedSalary).size() == 10);
    }

    @Test
    public void testGenerateListSomeEmployeesFirstAndSecondNamesAndDayOfBirthNotNull() {
        //type employee EmployeeWithHourlyWages
        for(Employee employee: generator.generateListSomeEmployees(10, EnumEmployee.EmployeeWithHourlyWages)) {
            assertNotNull(employee.getFirstName());
            assertNotNull(employee.getSecondName());
            assertNotNull(employee.getDataOfBirth());
        }
        //type employee EmployeeWithFixedSalary
        for(Employee employee: generator.generateListSomeEmployees(10, EnumEmployee.EmployeeWithFixedSalary)) {
            assertNotNull(employee.getFirstName());
            assertNotNull(employee.getSecondName());
            assertNotNull(employee.getDataOfBirth());
        }
    }

    @Test
    public void testSetInitialParamsOfEmployeeCheckInitFirstName() {
        generator.setInitialParamsOfEmployee(employeeFS);
        generator.setInitialParamsOfEmployee(employeeHW);
        assertTrue(employeeFS.getFirstName() != null && !employeeFS.getFirstName().equals(""));
        assertTrue(employeeHW.getFirstName() != null && !employeeHW.getFirstName().equals(""));
    }

    @Test
    public void testSetInitialParamsOfEmployeeCheckInitSecondName() {
        generator.setInitialParamsOfEmployee(employeeFS);
        generator.setInitialParamsOfEmployee(employeeHW);
        assertTrue(employeeFS.getSecondName() != null && !employeeFS.getFirstName().equals(""));
        assertTrue(employeeHW.getSecondName() != null && !employeeHW.getFirstName().equals(""));
    }

    @Test
    public void testSetInitialParamsOfEmployeeCheckInitDateOFBirthNotNull() {
        generator.setInitialParamsOfEmployee(employeeFS);
        generator.setInitialParamsOfEmployee(employeeHW);
        assertTrue(employeeFS.getDataOfBirth() != null);
        assertTrue(employeeHW.getDataOfBirth() != null);
    }

    @Test
    public void testSetInitialParamsOfEmployeeCheckInitDateOFBirth() {
        generator.setInitialParamsOfEmployee(employeeFS);
        generator.setInitialParamsOfEmployee(employeeHW);
        for (int i = 0; i <1000; i++) {
            assertTrue((employeeFS.getDataOfBirth()).after(Date.valueOf("1975-12-31")));
            assertTrue((employeeFS.getDataOfBirth()).before(Date.valueOf("1995-12-29")));
        }
    }

    @After
    public void clear() {
        generator = null;
        employeeList = null;
        employeeFS = null;
        employeeHW = null;
    }
}

