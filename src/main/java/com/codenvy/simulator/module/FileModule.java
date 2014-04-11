package com.codenvy.simulator.module;

import com.codenvy.simulator.dao.CompanyDao;
import com.codenvy.simulator.dao.EmployeeDao;
import com.codenvy.simulator.dao.file.CompanyDaoImplFile;
import com.codenvy.simulator.dao.file.EmployeeDaoImplFile;
import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Created by Andrienko Aleksander on 07.04.14.
 */
public class FileModule implements Module{
    @Override
    public void configure(Binder binder) {
        binder.bind(EmployeeDao.class).to(EmployeeDaoImplFile.class);
        binder.bind(CompanyDao.class).to(CompanyDaoImplFile.class);
    }
}
