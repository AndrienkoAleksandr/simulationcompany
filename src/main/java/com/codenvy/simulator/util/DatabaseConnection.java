package com.codenvy.simulator.util;

import com.codenvy.simulator.dao.file.FileManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * Created by Andrienko Aleksander on 24.03.14.
 */
public class DatabaseConnection {

    private static Connection connection = null;

    private static String[] configParamName = new String[]{"url", "username", "password"};
    private static String BASE_DATE_NAME = "simulator";
    private static String URLBaseData = "jdbc:mysql://localhost:3306/" + BASE_DATE_NAME;
    private static String USER_NAME = "root";
    private static String PASSWORD = "develop";

    public static Connection getConnection()    {
        try {
            getConfigParam();
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URLBaseData , USER_NAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  connection;
    }

    public static void getConfigParam() {
        URI urlCurrentClass = null;
        try {
            Path path = Paths.get(DatabaseConnection.class.getResource("/").toURI());
            String configFileName = "hibernate.cfg.xml";
            Path configFilePath = Paths.get(path.toString(), configFileName);
            FileManager fileManager = FileManager.getInstance();
            List<String> configLine = fileManager.readFile(configFilePath);
            URLBaseData = searchConfigParam(configLine, 0);
            USER_NAME = searchConfigParam(configLine, 1);
            PASSWORD = searchConfigParam(configLine, 2);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static String searchConfigParam(List<String> configLine, int i) {
        int begin = 0;
        int end = 0;
        String paramValue = "";
        for (String param: configLine) {
            begin = param.indexOf(configParamName[i]);
            if (begin > 0) {
                begin = param.indexOf(">", begin);
                end = param.indexOf("<", begin);
                if (end < 0 && i == 2) {
                    end = param.length() - 1;
                }
                paramValue = param.substring(begin + 1, end);
                break;
            }
        }
        return paramValue;
    }
}
