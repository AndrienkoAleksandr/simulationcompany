package com.codenvy.simulator.client;

import com.codenvy.simulator.client.exception.GenerateCompanyException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

/**
* Created by Andrienko Aleksander on 28.04.14.
*/
@RemoteServiceRelativePath("Simulate")
public interface SimulateService extends RemoteService {
    String generateCompany() throws GenerateCompanyException;
    String doSort(String typeOfSorting, Integer companyId, String typeOfSavingData) throws GenerateCompanyException;

    public static class App {
        private static final SimulateServiceAsync ourInstance = (SimulateServiceAsync) GWT.create(SimulateService.class);

        public static SimulateServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
