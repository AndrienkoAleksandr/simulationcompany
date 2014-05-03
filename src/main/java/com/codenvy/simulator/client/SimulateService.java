package com.codenvy.simulator.client;

import com.codenvy.simulator.client.entity.CompanyClient;
import com.codenvy.simulator.client.entity.EmployeeClient;
import com.codenvy.simulator.client.exception.GenerateCompanyException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

import java.util.List;

/**
* Created by Andrienko Aleksander on 28.04.14.
*/
@RemoteServiceRelativePath("Simulate")
public interface SimulateService extends RemoteService {
    CompanyClient generateCompany() throws GenerateCompanyException;
    List<EmployeeClient> doSort(String typeOfSorting, Integer companyId, String typeOfSavingData) throws GenerateCompanyException;

    public static class App {
        private static final SimulateServiceAsync ourInstance = (SimulateServiceAsync) GWT.create(SimulateService.class);

        public static SimulateServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
