package com.codenvy.simulator.server;

import com.codenvy.simulator.client.entity.CompanyClient;
import com.codenvy.simulator.client.entity.EmployeeClient;
import com.codenvy.simulator.client.exception.GenerateCompanyException;
import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.file.CompanyDaoImplFile;
import com.codenvy.simulator.dao.file.EmployeeDaoImplFile;
import com.codenvy.simulator.dao.hibernate.EmployeeDaoImplHibernate;
import com.codenvy.simulator.dao.jdbc.EmployeeDaoImplJDBC;
import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.module.FileModule;
import com.codenvy.simulator.module.HibernateModule;
import com.codenvy.simulator.module.JDBCModule;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.codenvy.simulator.client.SimulateService;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public class SimulateServiceImpl extends RemoteServiceServlet implements SimulateService {

    @Override
    public CompanyClient generateCompany() throws GenerateCompanyException {
        Boolean start = (Boolean) getServletContext().getAttribute("start");
        if (!start) {
            return (CompanyClient) getServletContext().getAttribute("company");
        }
            CompanyClient companyClient = new CompanyClient();
            List<EmployeeClient> employees = new ArrayList<EmployeeClient>();
            String storage = (String) getServletContext().getAttribute("typeOfStorage");
            String companyName = ((String) getServletContext().getAttribute("companyName"));
            checkAttribute(companyName, storage, start);
            Company company = null;
            Injector injector = null;
            switch (storage) {
                case "JDBC":
                    injector = Guice.createInjector(new JDBCModule());
                    company = injector.getInstance(Company.class);
                    break;
                case "Hibernate":
                    injector = Guice.createInjector(new HibernateModule());
                    company = injector.getInstance(Company.class);
                    break;
                case "Files":
                    injector = Guice.createInjector(new FileModule());
                    company = injector.getInstance(Company.class);
                    Path path = Paths.get(getServletContext().getRealPath("/"));
                    EmployeeDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_EMPLOYEE_FILE);
                    CompanyDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_COMPANY_FILE);
                    break;
            }
            company.setFullName(companyName);
            company.takeEmployeesOnWork();
            company.earnMoney();
            company.paySalaryStaff();
            company.setTypeOfSavingData(storage);
            company.saveCompanyToStorage();
            company.saveEmployeeListToStorage();

            companyClient.setFullName(companyName);
            companyClient.setTotalMoney(company.getTotalProfit());
            companyClient.setProfit(company.getProfit());
            companyClient.setTypeOfSavingData(storage);
            companyClient.setId(company.getId());
            for (Employee emp : company.getEmployees()) {
                employees.add(new EmployeeClient(emp.getId(), emp.getFirstName(),
                        emp.getSecondName(), emp.getSalary()));
            }
            companyClient.setEmployees(employees);
            getServletContext().setAttribute("start", false);
            getServletContext().setAttribute("company", companyClient);
            return companyClient;
    }

    @Override
    public List<EmployeeClient> doSort(String typeOfSorting, Integer companyId, String storage) throws GenerateCompanyException {
        List<Employee> sortedEmployee = new ArrayList<>();
        List<EmployeeClient> sortedEmployeeClient = new ArrayList<>();
        EmployeeDao employeeDao = null;
        switch (storage) {
            case "JDBC":
                employeeDao = new EmployeeDaoImplJDBC();
                break;
            case "Hibernate":
                employeeDao = new EmployeeDaoImplHibernate();
                break;
            case "Files":
                employeeDao = new EmployeeDaoImplFile();
                break;
        }
        switch (typeOfSorting) {
            case "ByFirstName":
                sortedEmployee = employeeDao.orderByFirstName(companyId);
                break;
            case "BySecondName":
                sortedEmployee = employeeDao.orderByLastName(companyId);
                break;
            case "BySalary":
                sortedEmployee = employeeDao.orderBySalary(companyId);
                break;
        }
        for(Employee employee: sortedEmployee) {
            EmployeeClient emp = new EmployeeClient();
            emp.setId(employee.getId());
            emp.setFirstName(employee.getFirstName());
            emp.setSecondName(employee.getSecondName());
            emp.setSalary(employee.getSalary());
            sortedEmployeeClient.add(emp);
        }
        return sortedEmployeeClient;
    }

    private void checkAttribute(String companyName, String storage, Boolean start) throws GenerateCompanyException {
        if (storage == null && (start == null || start)) {
            throw new GenerateCompanyException("type storage is empty");
        }
        if (start == null || !start) {
            throw new GenerateCompanyException("error start of generator");
        }
        if (companyName == null) {
            throw new GenerateCompanyException("name company null!");
        }
    }
}