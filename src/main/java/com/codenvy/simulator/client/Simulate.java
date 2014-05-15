package com.codenvy.simulator.client;

import com.codenvy.simulator.client.entity.*;
import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
public class Simulate implements EntryPoint {

    private MyBeanFactory companyAutoBean = GWT.create(MyBeanFactory.class);

    private VerticalPanel topPanel = new VerticalPanel();
    private VerticalPanel radioButtonPanel = new VerticalPanel();
    private VerticalPanel bottomPanel = new VerticalPanel();
    private FlexTable employeesTable = new FlexTable();
    private Label companyName = new Label("Company name:");
    private Label companyNameValue = new Label();
    private Label employeeListLabel = new Label("Company pay salary to staff:");
    private Label companyProfit = new Label("Company profit after payment salary:");
    private Label companyProfitValue = new Label();
    private RadioButton[] radioButtons = new RadioButton[Constant.sortingList.length];
    private Button sorting = new Button("Sort");
    private Label errorLabel = new Label();
    private CompanyView company;
    private String typeOfSorting = Constant.sortingList[2];

    public void onModuleLoad() {
        employeesTable.setText(0, 0, "Employee");
        employeesTable.setText(0, 1, "Salary");
        drawDataOfCompany();
        RootPanel.get("companyInfoBegin").add(topPanel);
        RootPanel.get("list employee").add(employeesTable);
        RootPanel.get("sort").add(radioButtonPanel);
        RootPanel.get("companyInfoEnd").add(bottomPanel);
    }

    private void drawDataOfCompany(){
        String id = Window.Location.getParameter("id");
        String storage = Window.Location.getParameter("storage");

        String url = "rest/company/get/" + id + "?storage=" + storage;
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
        requestBuilder.setHeader("Content-Type", "application/json");
        requestBuilder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                company = deserializeFromJson(response.getText());
                drawTopDataOfCompany();
                drawEmployeeTable();
                drawRadioButtonPanel();
                drawBottomDataOfCompany();
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

    private void drawTopDataOfCompany() {
        companyNameValue.setText(company.getFullName());
        topPanel.add(companyName);
        topPanel.add(companyNameValue);
        topPanel.add(employeeListLabel);
    }

    private void drawEmployeeTable() {
        int row = 1;
        for (EmployeeView emp: company.getEmployees()) {
            String fullName = emp.getFirstName() + " " + emp.getSecondName();
            String salary = String.valueOf(emp.getSalary());
            employeesTable.setText(row, 0, fullName);
            employeesTable.setText(row, 1, salary);
            row++;
        }
    }

    private void drawRadioButtonPanel() {
        int i = 0;
        String[] sortList = Constant.sortingList;
        for (RadioButton radioButton: radioButtons) {
            String sorting = sortList[i];
            radioButton = new RadioButton("sorting", sorting);
            radioButton.ensureDebugId(
                    "cwRadioButton-sorting-" + sorting.replaceAll(" ", ""));
            if (i == 2) {
                radioButton.setValue(true);
            }
            final RadioButton finalRadioButton = radioButton;
            radioButton.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> booleanValueChangeEvent) {
                    typeOfSorting = finalRadioButton.getText();
                }
            });
            radioButtonPanel.add(radioButton);
            i++;
        }
        sorting.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                doSort();
            }
        });
        radioButtonPanel.add(sorting);
    }

    private void doSort() {
        String id = Window.Location.getParameter("id");
        String storage = Window.Location.getParameter("storage");
        String url = "rest/company/sort/" + id + "?storage=" + storage + "&sorting=" + typeOfSorting;
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);
        requestBuilder.setHeader("Content-Type", "application/json");
        requestBuilder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                CompanyView companyView = deserializeFromJson(response.getText());
                company.setEmployees(companyView.getEmployees());
                drawEmployeeTable();
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

    private void drawBottomDataOfCompany() {
        companyProfitValue.setText(String.valueOf(company.getProfit()));
        bottomPanel.add(companyProfit);
        bottomPanel.add(companyProfitValue);
        bottomPanel.add(errorLabel);
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
