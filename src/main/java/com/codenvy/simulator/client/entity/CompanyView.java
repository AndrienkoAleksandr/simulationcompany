package com.codenvy.simulator.client.entity;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 14.05.14.
 */
public interface CompanyView {
    public Integer getId();

    public void setId(Integer id);

    public String getFullName();

    public void setFullName(String fullName);

    public List<EmployeeView> getEmployees();

    public void setEmployees(List<EmployeeView> employees);

    public Double getProfit();

    public void setProfit(Double profit);

    public String getTypeOfSavingData();

    public void setTypeOfSavingData(String typeOfSavingData);

    public Double getTotalProfit();

    public void setTotalMoney(Double totalMoney);
}
