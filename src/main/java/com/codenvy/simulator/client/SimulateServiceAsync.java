package com.codenvy.simulator.client;

import com.codenvy.simulator.client.entity.CompanyClient;
import com.codenvy.simulator.client.entity.EmployeeClient;
import com.google.gwt.core.client.GWT;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;
import org.fusesource.restygwt.client.Resource;

import javax.ws.rs.GET;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public interface SimulateServiceAsync extends RestService {
    @GET
    void getCompany(MethodCallback<CompanyClient> callback);
    @GET
    void sortCompany(MethodCallback<List<EmployeeClient>> callback);

    /**
     * Utility class to get the instance of the Rest Service
     */
    public static final class Util {

        private static SimulateServiceAsync instance;
        private static SimulateServiceAsync instance2;

    public static final SimulateServiceAsync get(String id, String storage) {
            if (instance == null) {
                instance = GWT.create(SimulateServiceAsync.class);
                ((RestServiceProxy) instance).setResource(new Resource(
                        "rest/company/get/" + id).addQueryParam("storage", storage));
            }
            return instance;
        }

    public static final SimulateServiceAsync sort(String id, String storage, String typeOfSorting) {
            instance2 = GWT.create(SimulateServiceAsync.class);
            ((RestServiceProxy) instance2).setResource(new Resource(
                    "rest/company/sort/" + id)
                    .addQueryParam("storage", storage)
                    .addQueryParam("sorting", typeOfSorting));
        return instance2;
    }

        private Util() {
            // Utility class should not be instantiated
        }
    }

}