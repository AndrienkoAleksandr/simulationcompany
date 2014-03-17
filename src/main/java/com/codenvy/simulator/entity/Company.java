package com.codenvy.simulator.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
@Entity
@Table(name="Employees")
public class Company {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<Employee> employees;

    public Company() {
    }

    public Company(String fullName, List<Employee> employees) {
        this.fullName = fullName;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
