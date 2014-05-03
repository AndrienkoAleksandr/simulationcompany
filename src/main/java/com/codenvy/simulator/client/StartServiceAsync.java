package com.codenvy.simulator.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
* Created by Andrienko Aleksander on 28.04.14.
*/
public interface StartServiceAsync {
    void startGenerator(String companyName, String typeOfStorage, AsyncCallback<Void> async);
}
