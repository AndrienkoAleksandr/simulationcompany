package com.codenvy.simulator.entity;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.generator.RandomGenerator;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<Employee> employees;

    @Column(name = "profit")
    private Double profit;

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

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public double earnedMoney(int amountOfEmployee) {
        RandomGenerator profitGenerator = new RandomGenerator();
        return profitGenerator.generateRandomDoubleNumber(1, Constant.MAX_FIXED_SALARY * amountOfEmployee);
    }

    public void paySalaryStaff(List<Employee> employeeList) {
        RandomGenerator salaryRandom = new RandomGenerator();
        for (Employee employee: employeeList) {
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
