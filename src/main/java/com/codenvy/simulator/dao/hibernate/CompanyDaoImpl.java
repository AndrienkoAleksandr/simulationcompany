package com.codenvy.simulator.dao.hibernate;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.entity.CompanySingleton;
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
    public void saveOrUpdate(CompanySingleton companySingleton) {
        Session session = null;
        int id = 0;
        try {
            session = getSession();
            session.beginTransaction();
            session.saveOrUpdate(companySingleton);
            session.getTransaction().commit();
        } catch (Exception e) {
            printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
