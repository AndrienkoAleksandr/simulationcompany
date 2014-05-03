package com.codenvy.simulator.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by Andrienko Aleksander on 29.04.2014.
 */
public class GenerateCompanyException  extends Exception implements IsSerializable {
    private String message;
    public GenerateCompanyException() {
    }
    public GenerateCompanyException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
}
