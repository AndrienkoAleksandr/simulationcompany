package com.codenvy.simulator.dao;

import com.codenvy.simulator.entity.Company;
import com.codenvy.simulator.util.HibernateUtil;
import org.hibernate.Session;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

/**
 * Created by Andrienko Aleksanderon 16.03.14.
 */
public class CompanyDaoImpl implements CompanyDao {

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public int saveOrUpdate(Company company) {
        Session session =null;
        int id = 0;
        try {
            session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(company);
            session.getTransaction().commit();
        } catch (Exception e) {
            printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return id;
    }
}