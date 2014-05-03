package com.codenvy.simulator.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
@RemoteServiceRelativePath("Start")
public interface StartService extends RemoteService {
    public void startGenerator(String companyName, String typeOfStorage);
}
