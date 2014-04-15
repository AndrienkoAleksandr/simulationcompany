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

/**
 * Created by USER on 14.04.2014.
 */
public class RunServlet extends HttpServlet {
    public static final String RUN_PAGE = "/WEB-INF/run/run.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmployeeDao employeeDao = null;
        CompanyDao companyDao = null;
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
