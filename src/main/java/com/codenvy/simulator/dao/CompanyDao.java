package com.codenvy.simulator.dao;

import com.codenvy.simulator.entity.CompanySingleton;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public interface CompanyDao {
    public void saveOrUpdate(CompanySingleton companySingleton);
}
