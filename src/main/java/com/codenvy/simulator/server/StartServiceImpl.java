package com.codenvy.simulator.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public class StartServiceImpl extends RemoteServiceServlet {
    public void startGenerator(String companyName, String typeOfStorage) {
        getServletContext().setAttribute("companyName", companyName);
        getServletContext().setAttribute("typeOfStorage", typeOfStorage);
        getServletContext().setAttribute("start", true);
    }
}