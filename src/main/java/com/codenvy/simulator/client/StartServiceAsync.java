package com.codenvy.simulator.client;


import com.codenvy.simulator.client.entity.CompanyClient;
import com.google.gwt.core.client.GWT;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.RestServiceProxy;

import javax.ws.rs.POST;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public interface StartServiceAsync extends RestService {

    @POST
    void createCompany(MethodCallback<CompanyClient> callback);

    /**
     * Utility class to get the instance of the Rest Service
     */
    public static final class Util {

        private static StartServiceAsync instance;

        public static final StartServiceAsync get(String companyName, String storage) {
            if (instance == null) {
                instance = GWT.create(StartServiceAsync.class);
                ((RestServiceProxy) instance).setResource(new Resource(
                        "rest/company")
                        .addQueryParam("companyName", companyName)
                        .addQueryParam("storage", storage));
            }
            return instance;
        }

        private Util() {
            // Utility class should not be instantiated
        }
    }
}