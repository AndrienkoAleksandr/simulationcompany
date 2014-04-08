package com.codenvy.simulator.entity.factory;

import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.entity.EmployeeWithFixedSalary;

/**
 * Created by Andrienko Aleksanderon 01.04.14.
 */
public class FactoryEmployeeFixedSalary implements FactoryEmployee {

    @Override
    public Employee create() {
        return new EmployeeWithFixedSalary();
    }
}
