package com.codenvy.simulator.dao;

import com.codenvy.simulator.entity.Company;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public interface CompanyDao {
    public void saveOrUpdate(Company company);
    public void deleteFromId(Company company);
}
