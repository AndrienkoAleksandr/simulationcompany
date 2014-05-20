package com.codenvy.simulator.restful;

import com.codenvy.simulator.client.entity.CompanyClient;
import com.codenvy.simulator.client.entity.EmployeeClient;
import com.codenvy.simulator.client.exception.GenerateCompanyException;
import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.file.CompanyDaoImplFile;
import com.codenvy.simulator.dao.file.EmployeeDaoImplFile;
import com.codenvy.simulator.dao.hibernate.CompanyDaoImplHibernate;
import com.codenvy.simulator.dao.hibernate.EmployeeDaoImplHibernate;
import com.codenvy.simulator.dao.jdbc.CompanyDaoImplJDBC;
import com.codenvy.simulator.dao.jdbc.EmployeeDaoImplJDBC;
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
            default:
                return Response.status(Response.Status.NOT_FOUND).build();
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
            companyClient.setTotalProfit(company.getProfit());
            companyClient.setProfit(company.getProfit());
            companyClient.setTotalProfit(company.getTotalProfit());
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

    @Path("get/{id}")
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
            default:
                return Response.status(Response.Status.NOT_FOUND).build();
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
        companyClient.setTotalProfit(company.getTotalProfit());
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

    @Path("/sort/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortCompany(
                                @QueryParam("storage") String storage,
                                @PathParam("id") String id,
                                @QueryParam("sorting") String typeOfSorting) {
        int companyId = Integer.parseInt(id);
         List<Employee> sortedEmployee = new ArrayList<>();
        final List<EmployeeClient> sortedEmployeeClient = new ArrayList<>();
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
            default:
                return Response.status(Response.Status.NOT_FOUND).build();
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
        return Response.ok().
                entity(new GenericEntity<List<EmployeeClient>>(sortedEmployeeClient) {
        }).build();
    }


}
