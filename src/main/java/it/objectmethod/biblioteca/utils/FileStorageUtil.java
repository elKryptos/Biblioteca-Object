package it.objectmethod.biblioteca.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileStorageUtil {

    private static String filepath;

    public static String getFilePath() {
        return filepath;
    }

    public static void setFilePath(String filepath) {
        FileStorageUtil.filepath = filepath;
    }

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

    public boolean controlFormatName(String nome) {
        String[] parole = nome.split(" ");
        for (String parola : parole) {
            if (!Character.isUpperCase(parola.charAt(0)) ||
                    !parola.substring(1).equals(parola.substring(1).toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public String correctFormatName(String nome) {
        StringBuilder nomeCorreto = new StringBuilder(" ");
        String[] parole = nome.split(" ");
        for (String parola : parole) {
            if (parola.length() > 1) {
                nomeCorreto.append(Character.toUpperCase(parola.charAt(0)))
                .append(parola.substring(1).toLowerCase())
                .append(" ");
            } else {
                nomeCorreto.append(parola.toUpperCase()).append(" ");
            }
        }
        return nomeCorreto.toString().trim();
    }
}
