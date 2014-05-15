package com.codenvy.simulator.client;

import com.codenvy.simulator.client.entity.MyBeanFactory;
import com.codenvy.simulator.client.entity.CompanyView;
import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

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
    private Image startImage = new Image("/resources/img/run.jpg");

    private MyBeanFactory companyAutoBean = GWT.create(MyBeanFactory.class);

    public void onModuleLoad() {
        initTopPanel.add(choseStorage);
        String[] storageList = Constant.storageList;
        int i = 0;
        for (RadioButton radioButton : radioButtons) {
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
            public void onClick(ClickEvent event) {
                loadCompany();
            }
        });

        initBottomPanel.add(startButton);
        initBottomPanel.add(errors);
        RootPanel.get("start-top-panel").add(initTopPanel);
        RootPanel.get("start-bottom-panel").add(initBottomPanel);
    }

    private void setCompanyName(String companyName) {
        companyName = companyName.trim();
        if (!companyName.matches("^[0-9a-zA-Z\\.]{1,25}$")) {
            Window.alert("'" + companyName + "' is not a valid symbol.");
            companyNameInput.selectAll();
            return;
        }
        companyName = companyNameInput.getText();
    }

    private void loadCompany() {
        String url = "rest/company?companyName=" + companyName + "&storage=" + typeOfStorage;
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
        requestBuilder.setHeader("Content-Type", "application/json");
        requestBuilder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                CompanyView companyView = deserializeFromJson(response.getText());
                Window.Location.replace("/simulate.html?id=" +
                        companyView.getId() + "&storage=" + companyView.getTypeOfSavingData() +
                        "&gwt.codesvr=127.0.0.1:9997");

            }

            @Override
            public void onError(Request request, Throwable exception) {
                Window.alert("Error while loading persons! Cause: "
                        + exception.getMessage());
            }
        });

        try {
            requestBuilder.send();
        } catch (RequestException e) {
            e.printStackTrace();
        }
    }

    String serializeToJson(CompanyView companyView) {
        AutoBean<CompanyView> bean = AutoBeanUtils.getAutoBean(companyView);
        return AutoBeanCodex.encode(bean).getPayload();
    }

    CompanyView deserializeFromJson(String json) {
        AutoBean<CompanyView> bean = AutoBeanCodex.decode(companyAutoBean, CompanyView.class, json);
        return bean.as();
    }
}
