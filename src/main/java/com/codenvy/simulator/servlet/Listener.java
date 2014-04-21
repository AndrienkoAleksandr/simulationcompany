package com.codenvy.simulator.servlet;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Date;

/**
 * Created by first on 21.04.14.
 */
public class Listener implements HttpSessionAttributeListener, ServletRequestListener {
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        System.out.println("attribute 'start' is create" + " = " + event.getSession().getAttribute("start"));
        Date data = new Date(event.getSession().getCreationTime());
        event.getSession().setAttribute("time", data);
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        System.out.println("attribute 'start' changed" + " = " + event.getSession().getAttribute("start"));
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("Request complete!");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("Request begin!");
    }
}
