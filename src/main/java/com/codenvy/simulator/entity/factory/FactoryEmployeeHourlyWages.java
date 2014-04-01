package com.codenvy.simulator.entity.factory;

import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.entity.EmployeeWithHourlyWages;

/**
 * Created by first on 01.04.14.
 */
public class FactoryEmployeeHourlyWages implements FactoryEmployee{

    @Override
    public Employee create() {
        return new EmployeeWithHourlyWages();
    }
}
