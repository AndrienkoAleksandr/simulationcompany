package com.codenvy.simulator.constant;

/**
 * Created by Andrienko Aleksander on 16.03.14.
 */
public final class Constant {

    // String
    public static final String[] FIRST_NAME_LIST = new String[] {"Glori", "Jim", "Carry", "Denis",
            "Walt", "Corry", "Andrew", "Zoy", "Harry", "Floyd", "Henry", "Boris",
            "Cris", "Marry", "May", "Iona", "Candy", "Cherry", "Eddy", "Holly"};

    public static final String[] SECOND_NAME_LIST = new String[]{"Abramson",
            "Adamson", "Berry", "Eddington", "Osborn", "Mayer",
            "Eagle", "Beckhem", "Serafim", "Anderson", "Fluffy",
            "Ruby", "Java", "Cuper", "Unit", "Korn",
            "Glens", "Tramp", "Donalds", "Babcock", "Backer"};

    public static final String PATH_TO_COMPANY_FILE = "storage/company.bin";

    public static final String PATH_TO_EMPLOYEE_FILE = "storage/employee.bin";

    public static final String[] storageList = new String[]{"JDBC", "Hibernate", "Files"};

    public static final String[] sortingList = new String[]{"ByFirstName", "BySecondName", "BySalary"};

    public static final char FILE_BASE_DATE_SEPARATOR = ',';

    // Integer
    public static final Integer FIRST_NAME_LIST_LENGTH = FIRST_NAME_LIST.length;

    public static final Integer SECOND_NAME_LIST_LENGTH = SECOND_NAME_LIST.length;

    public static final Integer MAX_AMOUNT_EMPLOYEE = 40;

    //double
    public static final double MIN_FIXED_SALARY = 1000;

    public static final double MAX_FIXED_SALARY = 8000;

    public static final double MIN_WAGES_PER_HOUR = 15;

    public static final double MAX_WAGES_PER_HOUR = 20;

    //int
    public static final int MIN_YEAR_OF_BIRTH = 1976;

    public static final int MAX_YEAR_OF_BIRTH = 1995;

    public static final int MIN_DAY_OF_BIRTH = 1;

    public static final int MAX_DAY_OF_BIRTH = 28;

    public static final int MIN_MONTH_OF_BIRTH = 1;

    public static final int MAX_MONTH_OF_BIRTH = 11;


}
