package com.codenvy.simulator.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public class Start implements EntryPoint {
    private VerticalPanel initTopPanel = new VerticalPanel();
    private VerticalPanel initBottomPanel = new VerticalPanel();
    private Label choseStorage = new Label("Save date with: ");
    private TextBox companyNameInput = new TextBox();
    private Label errors = new Label();
    private Label companyNameLabel = new Label("Enter name company");
    private Label startLabel = new Label("Enter \"Run\" if you want to start simulation.");
    private String companyName = "Adidas";
    private String typeOfStorage = Constant.storageList[0];
    private RadioButton[] radioButtons = new RadioButton[Constant.storageList.length];
    private PushButton startButton;
    private StartServiceAsync startService;
    private Image startImage =  new Image("resources/img/run.jpg");

    public void onModuleLoad() {
        initTopPanel.add(choseStorage);
        String[] storageList = Constant.storageList;
        int i = 0;
        for (RadioButton radioButton: radioButtons) {
            String storage = storageList[i];
            radioButton = new RadioButton("storage", storage);
            radioButton.ensureDebugId(
                    "cwRadioButton-storage-" + storage.replaceAll(" ", ""));
            if (i == 0) {
                radioButton.setValue(true);
            }
            final RadioButton finalRadioButton = radioButton;
            radioButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                    typeOfStorage = finalRadioButton.getText();
                }
            });
            initTopPanel.add(radioButton);
            i++;
        }
        initTopPanel.add(companyNameLabel);
        companyNameInput.setText("Adidas");
        companyNameInput.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent keyDownEvent) {
                if (keyDownEvent.getNativeKeyCode() == 13) {
                    setCompanyName(companyNameInput.getText());
                }
            }
        });
        initTopPanel.add(companyNameInput);
        initTopPanel.add(companyNameInput);
        initBottomPanel.addStyleName("start-bottom-panel");
        initBottomPanel.add(startLabel);
        startImage.addStyleName("start_button_image");
        startButton = new PushButton(startImage);
        startButton.setSize("100", "100");
        startButton.addStyleName("start_button");
        startButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                setCompanyName(companyNameInput.getText());
                sendDateToServer();
            }
        });
        initBottomPanel.add(startButton);
        initBottomPanel.add(errors);
        RootPanel.get("start-top-panel").add(initTopPanel);
        RootPanel.get("start-bottom-panel").add(initBottomPanel);
    }

    private void setCompanyName(String  nameOfCompany) {
        nameOfCompany = nameOfCompany.trim();
        if (!nameOfCompany.matches("^[0-9a-zA-Z\\.]{1,25}$")) {
            Window.alert("'" + nameOfCompany + "' is not a valid symbol.");
            companyNameInput.selectAll();
            return;
        }
        companyName = companyNameInput.getText();
    }

    private void sendDateToServer() {
        if (startService == null) {
            startService = GWT.create(StartService.class);
        }
        AsyncCallback<Void> callback = new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
            errors.setText("Connection failed!!!");
            }

            @Override
            public void onSuccess(Void avoid) {
                String host = Window.Location.getPath();
                host = host.substring(0, host.lastIndexOf("/") + 1);
                Window.Location.replace(host + "simulate.html") ;
            }
        };
        startService.startGenerator(companyName, typeOfStorage, callback);
    }
}
