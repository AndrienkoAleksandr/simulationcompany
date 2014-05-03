package com.codenvy.simulator.client;

import com.codenvy.simulator.client.entity.CompanyClient;
import com.codenvy.simulator.client.entity.EmployeeClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public interface SimulateServiceAsync {
    void generateCompany(AsyncCallback<CompanyClient> async);
    void doSort(String typeOfSorting, Integer companyId, String typeOfSavingData, AsyncCallback<List<EmployeeClient>> async);
}