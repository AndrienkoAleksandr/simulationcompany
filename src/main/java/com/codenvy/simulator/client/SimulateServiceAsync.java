package com.codenvy.simulator.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public interface SimulateServiceAsync {
    void generateCompany(AsyncCallback<String> async);
    void doSort(String typeOfSorting, Integer companyId, String typeOfSavingData, AsyncCallback<String> async);
}