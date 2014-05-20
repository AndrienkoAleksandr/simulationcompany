package com.codenvy.simulator.entity;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.generator.RandomGenerator;
import com.google.inject.Inject;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
@Entity
@Table(name="Company")
public class Company {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "company")
    private List<Employee> employees;

    @Column(name = "profit")
    private Double profit;

    @Column(name = "total_profit")
    private Double totalProfit;

    @Column(name = "type_saving_data")
    private String typeOfSavingData;

    @Transient
    private RandomGenerator generator = new RandomGenerator();

    @Transient
     private EmployeeDao employeeDao;

    @Transient
    private CompanyDao companyDao;

    public Company() {}

    @Inject
    public Company(EmployeeDao employeeDao, CompanyDao companyDao) {
        this.employeeDao = employeeDao;
        this.companyDao = companyDao;
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
        if (employees != null) {
            this.employees = employees;
        }
    }

    public Double getProfit() {
        if (profit == null) {
            profit = 0d;
        }
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public String getTypeOfSavingData() {
        return typeOfSavingData;
    }

    public void setTypeOfSavingData(String typeOfSavingData) {
        this.typeOfSavingData = typeOfSavingData;
    }

    public RandomGenerator getGenerator() {
        return generator;
    }

    public void setGenerator(RandomGenerator generator) {
        this.generator = generator;
    }

    public List<Employee> takeEmployeesOnWork() {
        int amountOfEmployee = generator.generateAmountOfEmployee();
        employees = generator.generateListAllEmployees(amountOfEmployee);
        return employees;
    }

    public void saveCompanyToStorage() {
        companyDao.saveOrUpdate(this);
    }

    public void saveEmployeeListToStorage() {
        if (employees != null) {
            for (Employee employee: employees) {
                employee.setIdCompany(id);
                employeeDao.saveOrUpdate(employee);
            }
        }
    }

    public double earnMoney() {
        RandomGenerator profitGenerator = new RandomGenerator();
        double earnedMoney = profitGenerator.generateRandomDoubleNumber(1, Constant.MAX_FIXED_SALARY * employees.size());
        setTotalProfit(earnedMoney);
        setProfit(earnedMoney);
        return earnedMoney;
    }

    public void paySalaryStaff() {
        RandomGenerator salaryRandom = new RandomGenerator();
        for (Employee employee: employees) {
            double salary = 0;
            Class myClass = employee.getClass();
            String nameClass = myClass.getSimpleName();
            if ("EmployeeWithHourlyWages".equals(nameClass)) {
                double wagesPerHour = salaryRandom.generateRandomDoubleNumber(Constant.MIN_WAGES_PER_HOUR, Constant.MAX_WAGES_PER_HOUR);
                salary = ((EmployeeWithHourlyWages)employee).calculationSalary(wagesPerHour);
                employee.setSalary(salary);
            }
            if ("EmployeeWithFixedSalary".equals(nameClass)) {
                salary = salaryRandom.generateRandomDoubleNumber(Constant.MIN_FIXED_SALARY, Constant.MAX_FIXED_SALARY);
                employee.setSalary(salary);
            }
            setProfit(getProfit() - salary);
        }
    }
}
