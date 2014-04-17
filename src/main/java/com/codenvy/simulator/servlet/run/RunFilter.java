package com.codenvy.simulator.servlet.run;

import com.codenvy.simulator.constant.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;

/**
 * Created by Andrienko Aleksander on 16.04.14.
 */
public class RunFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                          FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new FilteredRequest(request), response);

    }

    static class FilteredRequest extends HttpServletRequestWrapper {

        static String badChars = String.valueOf(Constant.FILE_BASE_DATE_SEPARATOR);
        static String defaultName = "Adidas";

        public FilteredRequest(ServletRequest request) {
            super((HttpServletRequest) request);
        }

        public String validateNameCompany(String input) {
            String result = "";
            for (int i = 0; i < input.length(); i++) {
                if (badChars.indexOf(input.charAt(i)) < 0) {
                    result += input.charAt(i);
                }
            }
            return result;
        }

        public boolean nameCompanyConsistOnlyWithSpace(String value) {
            for (char symbol : value.toCharArray()) {
                if (symbol != ' ') {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String getParameter(String paramName) {
            String value = super.getParameter(paramName);
            if ("company name".equals(paramName)) {
                if (value != null && !value.equals("") && !nameCompanyConsistOnlyWithSpace(value)) {
                    value = validateNameCompany(value);
                } else {
                    return defaultName;
                }
            }
            return value;
        }

        @Override
        public String[] getParameterValues(String paramName){
            String values[] = super.getParameterValues(paramName);
            if ("company name".equals(paramName)) {
                for (int index = 0; index < values.length; index++) {
                    values[index] = validateNameCompany(values[index]);
                }
            }
            return values;
        }
    }

    @Override
    public void destroy() {
    }

}