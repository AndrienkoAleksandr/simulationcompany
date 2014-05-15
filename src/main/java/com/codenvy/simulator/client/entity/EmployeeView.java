package com.codenvy.simulator.client.entity;

/**
 * Created by Andrienko Aleksanderon 14.05.14.
 */
public interface EmployeeView {
    public Integer getId();

    public void setId(Integer id);

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getSecondName();

    public void setSecondName(String secondName);

    public Double getSalary();

    public void setSalary(Double salary);
}
