package com.codenvy.simulator.client;

import com.codenvy.simulator.client.entity.CompanyClient;
import com.codenvy.simulator.client.entity.EmployeeClient;
import com.codenvy.simulator.client.exception.GenerateCompanyException;
import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.List;

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
    private CompanyClient company;
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
            simulateServiceAsync = SimulateService.App.getInstance();
            AsyncCallback<CompanyClient> callback = new AsyncCallback<CompanyClient>() {
                @Override
                public void onFailure(Throwable throwable) {
                    errorLabel.setText("Connection failed!!!");
                }
                @Override
                public void onSuccess(CompanyClient data) {
                    if (data == null) {
                        Window.Location.replace("/error/404.jsp");
                    }
                    company = data;
                    drawTopDataOfCompany();
                    drawEmployeeTable();
                    drawRadioButtonPanel();
                    drawBottomDataOfCompany();
                }
            };
            simulateServiceAsync.generateCompany(callback);
        }

    private void drawTopDataOfCompany() {
        companyNameValue.setText(company.getFullName());
        companyTotalMoneyValue.setText(String.valueOf(company.getTotalMoney()));
        topPanel.add(companyName);
        topPanel.add(companyNameValue);
        topPanel.add(companyTotalMoney);
        topPanel.add(companyTotalMoneyValue);
        topPanel.add(employeeListLabel);
    }

    private void drawEmployeeTable() {
        int row = 1;
        for (EmployeeClient emp: company.getEmployees()) {
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
        simulateServiceAsync = SimulateService.App.getInstance();
        AsyncCallback<List<EmployeeClient>> callback = new AsyncCallback<List<EmployeeClient>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("error " + throwable.toString());
            }
            @Override
            public void onSuccess(List<EmployeeClient> data) {
                company.setEmployees(data);
                drawEmployeeTable();
            }
        };
        simulateServiceAsync.doSort(typeOfSorting, company.getId(), company.getTypeOfSavingData(), callback);
    }

    private void drawBottomDataOfCompany() {
        companyProfitValue.setText(String.valueOf(company.getProfit()));
        bottomPanel.add(companyProfit);
        bottomPanel.add(companyProfitValue);
        bottomPanel.add(errorLabel);
    }
}
