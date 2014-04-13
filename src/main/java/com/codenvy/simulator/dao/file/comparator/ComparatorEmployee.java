package com.codenvy.simulator.dao.file.comparator;

import com.codenvy.simulator.entity.Employee;

import java.util.Comparator;

/**
 * Created by Andrienko Aleksander on 13.04.14.
 */
public class ComparatorEmployee implements Comparator<Employee>{

    private EnumComparatorEmployee enumEmployee;

    public ComparatorEmployee (EnumComparatorEmployee enumEmployee) {
        this.enumEmployee = enumEmployee;
    }

    @Override
    public int compare(Employee a, Employee b) {
        if (enumEmployee.equals(EnumComparatorEmployee.BY_FIRST_NAME)) {
            return a.getFirstName().compareTo(b.getFirstName());
        }
        if (enumEmployee.equals(EnumComparatorEmployee.BY_SECOND_NAME )) {
            return a.getSecondName().compareTo(b.getSecondName());
        }
        if (enumEmployee.equals(EnumComparatorEmployee.BY_SALARY)) {
            return Double.compare(a.getSalary(), b.getSalary());
        }
        return 0;
    }
}
