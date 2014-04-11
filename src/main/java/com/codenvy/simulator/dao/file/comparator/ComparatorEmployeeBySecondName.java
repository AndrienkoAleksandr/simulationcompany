package com.codenvy.simulator.dao.file.comparator;

import com.codenvy.simulator.entity.Employee;

import java.util.Comparator;

/**
 * Created by Andrienko Aleksander on 10.04.14.
 */
public class ComparatorEmployeeBySecondName implements Comparator<Employee>{
    @Override
    public int compare(Employee a, Employee b) {
        return a.getSecondName().compareTo(b.getSecondName());
    }
}
