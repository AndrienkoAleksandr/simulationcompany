package com.codenvy.simulator.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
@Entity
@Table(name="Employees")
public class EmployeeWithHourlyWages extends Employee {
    public double calculationSalary(double wagesPerHour) {
        return  20.8 * 8 * wagesPerHour;
    }
}
