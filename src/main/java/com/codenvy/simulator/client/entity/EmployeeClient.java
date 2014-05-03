package com.codenvy.simulator.client.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Andrienko Aleksander on 30.04.2014.
 */
public class EmployeeClient implements IsSerializable{

    private Integer id;
    private String firstName;
    private String secondName;
    private Double salary;

    public EmployeeClient() {
    }

    public EmployeeClient(Integer id, String firstName, String secondName, Double salary) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + " " + firstName + " " + secondName + " " + salary;
    }
}
