package com.codenvy.simulator.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Andrienko Aleksander on 28.04.14.
 */
public class Simulate implements EntryPoint {
    private VerticalPanel topPanel = new VerticalPanel();
    private VerticalPanel radioButtonPanel = new VerticalPanel();
    private VerticalPanel bottomPanel = new VerticalPanel();
    private FlexTable employeesTable = new FlexTable();
    private Label companyName = new Label("Company name:");
    private Label companyNameValue = new Label();
    private Label companyTotalMoney = new Label("Company earned:");
    private Label companyTotalMoneyValue = new Label();
    private Label employeeListLabel = new Label("Company pay salary to staff:");
    private Label companyProfit = new Label("Company profit after payment salary:");
    private Label companyProfitValue = new Label();
    private RadioButton[] radioButtons = new RadioButton[Constant.sortingList.length];
    private Button sorting = new Button("Sort");
    private SimulateServiceAsync simulateServiceAsync;
    private Label errorLabel = new Label();
    private String typeOfSorting = Constant.sortingList[2];

    private JSONArray employees;
    private JSONObject company;

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
            simulateServiceAsync = SimulateService.App.getInstance();
            AsyncCallback<String> callback = new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable throwable) {
                    errorLabel.setText("Connection failed!!!");
                }
                @Override
                public void onSuccess(String data) {
                    if (data == null) {
                        Window.Location.replace("/error/404.jsp");
                    }
                    parseStringToJSON(data);
                    drawTopDataOfCompany();
                    drawEmployeeTable();
                    drawRadioButtonPanel();
                    drawBottomDataOfCompany();
                }
            };
            simulateServiceAsync.generateCompany(callback);
        }

    private void parseStringToJSON(String data) {
        JSONValue value = JSONParser.parseStrict(data);
        JSONObject dataJSON = value.isObject();
        company = dataJSON.get("company").isObject();
        employees = dataJSON.get("employee").isArray();
    }

    private void drawTopDataOfCompany() {
        companyNameValue.setText(company.get("fullName").isString().stringValue());
        companyTotalMoneyValue.setText(String.valueOf(company.get("totalProfit").isNumber().doubleValue()));
        topPanel.add(companyName);
        topPanel.add(companyNameValue);
        topPanel.add(companyTotalMoney);
        topPanel.add(companyTotalMoneyValue);
        topPanel.add(employeeListLabel);
    }

    private void drawEmployeeTable() {
        int row = 1;
        for(int i = 0; i < employees.size(); i++) {
            JSONObject employee = employees.get(i).isObject();
            String fullName = employee.get("firstName").isString().stringValue() + " "
                    + employee.get("secondName").isString().stringValue();
            String salary = String.valueOf(employee.get("salary").isNumber().doubleValue());
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
        simulateServiceAsync = SimulateService.App.getInstance();
        AsyncCallback<String> callback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("error " + throwable.toString());
            }
            @Override
            public void onSuccess(String data) {
                JSONValue value = JSONParser.parseStrict(data);
                JSONObject dataJSON = value.isObject();
                employees = dataJSON.get("employee").isArray();
                drawEmployeeTable();
            }
        };
        simulateServiceAsync.doSort(typeOfSorting, (int)company.get("id").isNumber().doubleValue(),
                company.get("typeOfSavingData").isString().stringValue(), callback);
    }

    private void drawBottomDataOfCompany() {
        companyProfitValue.setText(String.valueOf(company.get("profit").isNumber().doubleValue()));
        bottomPanel.add(companyProfit);
        bottomPanel.add(companyProfitValue);
        bottomPanel.add(errorLabel);
    }
}
