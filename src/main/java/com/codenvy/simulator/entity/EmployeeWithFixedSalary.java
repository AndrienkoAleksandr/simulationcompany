package com.codenvy.simulator.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
@Entity
@Table(name="Employees")
public class EmployeeWithFixedSalary extends Employee {
    public double calculationSalary(){
        return getSalary();
    }
}
