package com.codenvy.simulator.client.entity;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 30.04.2014.
 */
public class CompanyClient implements IsSerializable{

    private Integer id;
    private String fullName;
    private List<EmployeeClient> employees;
    private Double totalProfit;
    private Double profit;
    private String typeOfSavingData;

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

    public List<EmployeeClient> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeClient> employees) {
        this.employees = employees;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getTypeOfSavingData() {
        return typeOfSavingData;
    }

    public void setTypeOfSavingData(String typeOfSavingData) {
        this.typeOfSavingData = typeOfSavingData;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    @Override
    public String toString() {
        return id + " " + fullName + totalProfit + profit + typeOfSavingData;
    }
}
