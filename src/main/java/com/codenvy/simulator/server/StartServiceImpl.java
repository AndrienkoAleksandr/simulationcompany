package com.codenvy.simulator.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.codenvy.simulator.client.StartService;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public class StartServiceImpl extends RemoteServiceServlet implements StartService {
    public void startGenerator(String companyName, String typeOfStorage) {
        getServletContext().setAttribute("companyName", companyName);
        getServletContext().setAttribute("typeOfStorage", typeOfStorage);
        getServletContext().setAttribute("start", true);
    }
}