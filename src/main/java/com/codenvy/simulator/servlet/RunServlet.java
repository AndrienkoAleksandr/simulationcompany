package com.codenvy.simulator.servlet;

import com.codenvy.simulator.constant.Constant;
import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.file.CompanyDaoImplFile;
import com.codenvy.simulator.dao.file.EmployeeDaoImplFile;
import com.codenvy.simulator.dao.jdbc.CompanyDaoImplJDBC;
import com.codenvy.simulator.dao.jdbc.EmployeeDaoImplJDBC;
import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.entity.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 14.04.2014.
 */
public class RunServlet extends HttpServlet {
    public static final String RUN_PAGE = "/WEB-INF/run/run.jsp";
    public static final String NOT_FOUND = "/WEB-INF/error/404.jsp";
    private EmployeeDao employeeDao = null;
    private CompanyDao companyDao = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Company company = (Company)request.getSession().getAttribute("company");
        String typeOfSorting = (String)request.getParameter("sorting");
        List<Employee> sortedEmployee = new ArrayList<>();
        int companyId = company.getId();
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
        company.setEmployees(sortedEmployee);
        request.getSession().setAttribute("company", company);
        request.getRequestDispatcher(RUN_PAGE).include(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("start", false);
        String storage = request.getParameter("storage");
        String companyName = request.getParameter("company name");
        switch (storage) {
            case "JDBC":
                employeeDao = new EmployeeDaoImplJDBC();
                companyDao = new CompanyDaoImplJDBC();
                break;
            case "Hibernate":
                employeeDao = new EmployeeDaoImplJDBC();
                companyDao = new CompanyDaoImplJDBC();
                break;
            case "Files":
                employeeDao = new EmployeeDaoImplFile();
                companyDao = new CompanyDaoImplFile();
                Path path = Paths.get(getServletContext().getRealPath("/"));
                EmployeeDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_EMPLOYEE_FILE);
                CompanyDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_COMPANY_FILE);
                break;
        }
        Company company = new Company();
        company.setFullName(companyName);
        company.takeEmployeesOnWork();
        request.getSession().setAttribute("earned_money", company.earnMoney());
        company.paySalaryStaff();
        companyDao.saveOrUpdate(company);
        //Todo
        for (Employee employee: company.getEmployees()) {
            employee.setIdCompany(company.getId());
            employeeDao.saveOrUpdate(employee);
        }
        request.getSession().setAttribute("company", company);
        request.getRequestDispatcher(RUN_PAGE).include(request, response);
    }
}
