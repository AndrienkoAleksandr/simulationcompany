package com.codenvy.simulator.dao;

import com.codenvy.simulator.entity.Employee;
import com.codenvy.simulator.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public class EmployeeDaoImpl implements EmployeeDao{

    private Session session= HibernateUtil.getSessionFactory().openSession();

    @Override
    public void save(Employee employee) {
        session.save(employee);
    }
}
