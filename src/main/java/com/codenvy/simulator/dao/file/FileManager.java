package com.codenvy.simulator.dao.file;

import com.codenvy.simulator.entity.Employee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.createFile;

/**
 * Created by Andrienko Aleksander on 07.04.14.
 */
public class FileManager {

    private static FileManager fileManager = null;

    private FileManager() {
    }

    public static FileManager getInstance(){
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }

    protected void writeToFile(Path path,List<String> lines) {
        try {
            Files.delete(path);
            createFileIfNotExist(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(path, charset, StandardOpenOption.APPEND)) {
            for (String line: lines) {
                writer.write(line);writer.newLine();
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    protected List<String> readFile(Path path) {
        List<String> result = new ArrayList<String>();
        createFileIfNotExist(path);
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return result;
    }

    protected void createFileIfNotExist(Path path) {
        Set<PosixFilePermission> perms
                = PosixFilePermissions.fromString("rw-rw-rw-");
        FileAttribute<Set<PosixFilePermission>> attr
                = PosixFilePermissions.asFileAttribute(perms);
        try {
            Path directories = path.getParent();
            if (!Files.exists(directories)) {
                Files.createDirectories(directories);
            }
            if (!Files.exists(path)) {
                createFile(path, attr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
