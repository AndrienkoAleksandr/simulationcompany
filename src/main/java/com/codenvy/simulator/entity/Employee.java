package com.codenvy.simulator.entity;

import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
@Entity
@Table(name="Employees")
public abstract class Employee {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="date_of_birth")
    private Date dataOfBirth;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "company_id")
    private Integer idCompany;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_id", referencedColumnName="id" ,insertable = false, updatable = false)
    private Company company;

    public Employee() {
    }

    protected Employee(Date dataOfBirth, String firstName, Double salary) {
        this.dataOfBirth = dataOfBirth;
        this.firstName = firstName;
        this.salary = salary;
    }

    public Integer getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(Integer idCompany) {
        this.idCompany = idCompany;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(Date dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public JSONObject toJSON() {
        JSONObject employeeObject = new JSONObject();
        try {
            employeeObject.put("id", id);
            employeeObject.put("firstName", firstName);
            employeeObject.put("secondName", secondName);
            employeeObject.put("salary", salary);
            employeeObject.put("idCompany", idCompany);
            employeeObject.put("dataOfBirth", dataOfBirth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return employeeObject;
    }
}
