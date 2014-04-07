package com.codenvy.simulator.util;


import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by first on 24.03.14.
 */
public class DatabaseConnection {

    private static Connection connection = null;

    private static final String BASE_DATE_NAME = "simulator";
    private static final String URL = "jdbc:mysql://localhost:3306/" + BASE_DATE_NAME;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection()    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL , USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  connection;
    }
}
