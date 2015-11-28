package com.fasoo.logic.utils.file;

import java.io.*;

public class FileUtils {

    public static final String UPLOADED_FOLDER_PATH = System.getProperty("user.dir") + File.separator + ".jictor" + File.separator;

    public static void makeBaseFolder(String baseFilePath) {
        File baseFolder = new File(baseFilePath);
        baseFolder.mkdir();
    }

    public static String readFileToString(String filePath) {
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null) {
                fileData.append(line + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return fileData.toString();
    }

    public static void writeJsonToFile(String json, String fileName) {
        try {
            FileWriter writer = new FileWriter(UPLOADED_FOLDER_PATH + fileName + ".json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDirectory(File dirPath) {
        if (!dirPath.exists()) {
            return;
        }
        for (String filePath : dirPath.list()) {
            File file = new File(dirPath, filePath);
            if (file.isDirectory()) {
                deleteDirectory(file);
            }
            file.delete();
        }
    }
}
