package com.codenvy.simulator.restful;

import com.codenvy.simulator.client.entity.CompanyClient;
import com.codenvy.simulator.client.entity.EmployeeClient;
import com.codenvy.simulator.client.exception.GenerateCompanyException;
import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.file.CompanyDaoImplFile;
import com.codenvy.simulator.dao.file.EmployeeDaoImplFile;
import com.codenvy.simulator.dao.hibernate.CompanyDaoImplHibernate;
import com.codenvy.simulator.dao.jdbc.CompanyDaoImplJDBC;
import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.module.FileModule;
import com.codenvy.simulator.module.HibernateModule;
import com.codenvy.simulator.module.JDBCModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 12.05.14.
 */
@Path("/company")
public class CompanyResources {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCompany(
            @Context ServletContext context,
            @QueryParam("storage") String storage,
            @QueryParam("companyName") String companyName
    ) throws GenerateCompanyException {
        CompanyClient companyClient = new CompanyClient();
        List<EmployeeClient> employees = new ArrayList<EmployeeClient>();
        checkAttribute(companyName, storage);
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
                java.nio.file.Path path = Paths.get(context.getRealPath("/"));
                EmployeeDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_EMPLOYEE_FILE);
                CompanyDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_COMPANY_FILE);
                break;
        }
        if (company != null) {
            company.setFullName(companyName);

            company.takeEmployeesOnWork();
            company.earnMoney();
            company.paySalaryStaff();
            company.setTypeOfSavingData(storage);
            company.saveCompanyToStorage();
            company.saveEmployeeListToStorage();

            companyClient.setFullName(companyName);
            companyClient.setTotalMoney(company.getProfit());
            companyClient.setProfit(company.getProfit());
            companyClient.setTypeOfSavingData(storage);
            companyClient.setId(company.getId());
        }
        for (Employee emp : company.getEmployees()) {
            employees.add(new EmployeeClient(emp.getId(), emp.getFirstName(),
                    emp.getSecondName(), emp.getSalary()));
        }
        companyClient.setEmployees(employees);
        return Response.ok().entity(companyClient).build();
    }

    private void checkAttribute(String companyName, String storage) throws GenerateCompanyException {
        if (storage == null) {
            throw new GenerateCompanyException("type storage is empty");
        }
        if (companyName == null) {
            throw new GenerateCompanyException("name company null!");
        }
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCompany(@Context ServletContext context,
                               @QueryParam("storage") String storage,
                               @PathParam("id") String id) {
        Company company = new Company();
        CompanyClient companyClient = new CompanyClient();
        CompanyDao companyDao = null;
        switch (storage) {
            case "JDBC":
                companyDao = new CompanyDaoImplJDBC();
                break;
            case "Hibernate":
                companyDao = new CompanyDaoImplHibernate();
                break;
            case "Files":
                companyDao = new CompanyDaoImplFile();
                java.nio.file.Path path = Paths.get(context.getRealPath("/"));
                EmployeeDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_EMPLOYEE_FILE);
                CompanyDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_COMPANY_FILE);
                break;
        }
        if (companyDao != null) {
            company = companyDao.getCompanyById(Integer.parseInt(id));
        }
        if (company == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        companyClient.setId(company.getId());
        companyClient.setFullName(company.getFullName());
        companyClient.setProfit(company.getProfit());
        companyClient.setTypeOfSavingData(company.getTypeOfSavingData());


        List<EmployeeClient> employeeClients = new ArrayList<>();
        for (Employee employee: company.getEmployees()) {
            EmployeeClient employeeClient = new EmployeeClient();
            employeeClient.setId(employee.getId());
            employeeClient.setFirstName(employee.getFirstName());
            employeeClient.setSecondName(employee.getSecondName());
            employeeClient.setSalary(employee.getSalary());
            employeeClients.add(employeeClient);
        }
        companyClient.setEmployees(employeeClients);
        return Response.ok().entity(companyClient).build();
    }

}
