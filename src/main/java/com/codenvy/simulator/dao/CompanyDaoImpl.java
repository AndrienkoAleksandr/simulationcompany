package com.codenvy.simulator.dao;

import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Created by Andrienko Aleksanderon 16.03.14.
 */
public class CompanyDaoImpl implements CompanyDao {

    private Session session = HibernateUtil.getSessionFactory().openSession();

    @Override
    public void save(Company company) {
        session.save(company);
    }
}
