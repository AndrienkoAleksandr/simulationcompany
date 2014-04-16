package com.codenvy.simulator.servlet.run;

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
import com.google.inject.Inject;
import com.google.inject.Injector;

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
 * Created by Andrienko Aleksander on 14.04.2014.
 */
public class RunServlet extends HttpServlet {
    public static final String RUN_PAGE = "/WEB-INF/run/run.jsp";
    public static final String NOT_FOUND = "/WEB-INF/error/404.jsp";
    @Inject private EmployeeDao employeeDao = null;
    @Inject private CompanyDao companyDao = null;

    @Inject
    public RunServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Company company = (Company)request.getSession().getAttribute("company");
        String typeOfSorting = request.getParameter("sorting");
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
        if (request.getParameter("storage") == null &&
                (request.getSession().getAttribute("start") == null ||
                        request.getSession().getAttribute("start") == true)) {
            request.getRequestDispatcher(NOT_FOUND).include(request, response);
            return;
        }
        if (request.getSession().getAttribute("start") != null
                    && (boolean)request.getSession().getAttribute("start")) {
            String storage = request.getParameter("storage");
            String companyName = request.getParameter("company name");
            Company company = null;
            Injector injector = null;
            switch (storage) {
                case "JDBC":
                    injector = Guice.createInjector(new JDBCModule());
                    company = injector.getInstance(Company.class);
                    employeeDao = new EmployeeDaoImplJDBC();
                    companyDao = new CompanyDaoImplJDBC();
                    break;
                case "Hibernate":
                    injector = Guice.createInjector(new HibernateModule());
                    company = injector.getInstance(Company.class);
                    employeeDao = new EmployeeDaoImplHibernate();
                    companyDao = new CompanyDaoImplHibernate();
                    break;
                case "Files":
                    injector = Guice.createInjector(new FileModule());
                    company = injector.getInstance(Company.class);
                    employeeDao = new EmployeeDaoImplFile();
                    companyDao = new CompanyDaoImplFile();
                    Path path = Paths.get(getServletContext().getRealPath("/"));
                    EmployeeDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_EMPLOYEE_FILE);
                    CompanyDaoImplFile.path = Paths.get(path.toString() + Constant.PATH_TO_COMPANY_FILE);
                    break;
            }
            company.setFullName(companyName);
            company.takeEmployeesOnWork();
            request.getSession().setAttribute("earned_money", company.earnMoney());
            company.paySalaryStaff();
            company.saveCompanyToStorage();
            company.saveEmployeeListToStorage();
            request.getSession().setAttribute("company", company);
            request.getSession().setAttribute("start", false);
            request.getRequestDispatcher(RUN_PAGE).include(request, response);
        } else {
            request.getRequestDispatcher(RUN_PAGE).include(request, response);
        }
    }
}
