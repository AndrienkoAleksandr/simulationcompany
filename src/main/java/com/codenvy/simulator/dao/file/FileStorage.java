package com.codenvy.simulator.dao.file;

import java.util.List;

/**
 * Created by Andrienko Aleksander on 09.04.14.
 */
public abstract class FileStorage {

    protected int generateId(List<String> lines) {
        int result = 0;
        for (String line: lines) {
            int id = getIdFromLine(line);
            if (id > result) {
                result = id;
            }
        }
        return ++result;
    }

    protected int getIdFromLine(String line) {
        int result = -1;
        if (line != null && line.length() != 0) {
            int beginId = line.indexOf("{") + 1;
            int endId = line.indexOf(",");
            String id = line.substring(beginId, endId);
            try {
                result = Integer.parseInt(id);
            } catch(Exception e) {
                e.printStackTrace();
                result = -1;
            }
        }
        return result;
    }

    protected String findLineForId(int id, List<String> lines) {
        int num = -1; String result = null;
        for (String line: lines) {
            num = getIdFromLine(line);
            if (id == num) {
                result = line;
                break;
            }
        }
        return result;
    }
}
