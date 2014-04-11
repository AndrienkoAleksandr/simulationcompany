package com.codenvy.simulator.module;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.hibernate.CompanyDaoImplHibernate;
import com.codenvy.simulator.dao.hibernate.EmployeeDaoImplHibernate;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Created by Andrienko Aleksander on 07.04.14.
 */
public class HibernateModule implements Module{
    @Override
    public void configure(Binder binder) {
        binder.bind(EmployeeDao.class).to(EmployeeDaoImplHibernate.class);
        binder.bind(CompanyDao.class).to(CompanyDaoImplHibernate.class);
    }
}
