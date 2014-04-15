package com.codenvy.simulator.servlet;

import com.codenvy.simulator.dao.file.CompanyDaoImplFile;
import com.codenvy.simulator.dao.file.EmployeeDaoImplFile;
import com.codenvy.simulator.entity.Company;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by USER on 13.04.2014.
 */
public class IndexServlet extends HttpServlet {

    public static final String INDEX_PAGE = "/WEB-INF/index/index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("start", true);
        request.getRequestDispatcher(INDEX_PAGE).include(request, response);
    }
}
