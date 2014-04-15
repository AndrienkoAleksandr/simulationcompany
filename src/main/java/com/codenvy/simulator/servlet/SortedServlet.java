package com.codenvy.simulator.servlet;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.entity.Company;
import com.google.inject.Inject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by first on 15.04.14.
 */
public class SortedServlet extends HttpServlet{
    @Inject
    public SortedServlet() {
    }

    @Inject private CompanyDao companyDao;
    @Inject private EmployeeDao employeeDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Company company = (Company) request.getSession().getAttribute("company");
        String typeOfSorting = request.getParameter("typeOfSorting");

    }
}
