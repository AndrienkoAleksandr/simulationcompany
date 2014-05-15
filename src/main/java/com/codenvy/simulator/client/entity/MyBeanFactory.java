package com.codenvy.simulator.client.entity;


import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

/**
 * Created by Andrienko Aleksander on 14.05.14.
 */
public interface MyBeanFactory extends AutoBeanFactory {
    AutoBean<CompanyView> company();
    AutoBean<EmployeeView> employee();
}
