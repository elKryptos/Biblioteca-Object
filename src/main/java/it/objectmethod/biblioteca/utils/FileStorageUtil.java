package it.objectmethod.biblioteca.utils;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileStorageUtil {
    public static void createStorageDirectory() {
        File directory = new File("FileStorage");
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (!created) {
                System.out.println("Error creating directory");
            } else {
                System.out.println("Directory created");
            }
        } else {
            System.out.println("Directory already exists");
        }
    }
}
