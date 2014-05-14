package com.codenvy.simulator.client;

import com.codenvy.simulator.client.entity.CompanyClient;
import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.fusesource.restygwt.client.Resource;

import javax.ws.rs.GET;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public interface SimulateServiceAsync extends RestService {
    @GET
    void getCompany(MethodCallback<CompanyClient> callback);

//    void doSort(String typeOfSorting, Integer companyId, String typeOfSavingData, AsyncCallback<List<EmployeeClient>> async);



    /**
     * Utility class to get the instance of the Rest Service
     */
    public static final class Util {

        private static SimulateServiceAsync instance;

    public static final SimulateServiceAsync get(String id, String storage) {
            if (instance == null) {
                instance = GWT.create(SimulateServiceAsync.class);
                ((RestServiceProxy) instance).setResource(new Resource(
                        "rest/company/" + id).addQueryParam("storage", storage));
            }
            return instance;
        }

        private Util() {
            // Utility class should not be instantiated
        }
    }

}