package com.fasoo.logic.utils.file;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

//TODO : file encoding (folder 이름이 한글일 경우 check)
public class ZipUtils {

    public static List<File> unzip(MultipartFile uploadFile, String rootPath) {

        List<File> result = new ArrayList<File>();
        File conFile = new File(rootPath, uploadFile.getOriginalFilename());
        try {
            uploadFile.transferTo(conFile);
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        zipToFileList(rootPath, conFile, result);
        return result;
    }

    private static void zipToFileList(String rootPath, File conFile, List<File> zipToFileListResult) {

        ZipFile zipFile;
        try {
            zipFile = new ZipFile(conFile, "UTF-8");
            Enumeration e = zipFile.getEntries();
            while (e.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) e.nextElement();
                zipEntryCase(rootPath, zipToFileListResult, zipFile, entry);
            }
            zipFile.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        conFile.delete();
    }

    private static void zipEntryCase(String rootPath, List<File> zipToFileListResult, ZipFile zipFile, ZipEntry entry) {

        File file;
        if (entry.isDirectory()) {
            return;
        }
        if (entry.getName().endsWith(".zip")) {
            file = zipEntryToFile(rootPath, zipFile, entry);
            if(file == null){
                return;
            }
            zipToFileList(rootPath + entry.getName().split(".zip")[0] + File.separator, file, zipToFileListResult);
        }
        if (entry.getName().endsWith(".java")) {
            file = zipEntryToFile(rootPath, zipFile, entry);
            if(file == null){
                return;
            }
            zipToFileListResult.add(file);
        }
    }

    private static File zipEntryToFile(String zipPath, ZipFile zipFile, ZipEntry entry) {

        File result = new File(zipPath, entry.getName());
        result.getParentFile().mkdirs();

        BufferedInputStream zipFileEntryBIS;
        FileOutputStream zipFileEntryFOS;

        try {
            zipFileEntryBIS = new BufferedInputStream(zipFile.getInputStream(entry));
            zipFileEntryFOS = new FileOutputStream(result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        byte buffer[] = new byte[1024];
        BufferedOutputStream zipFileEntryBOS = new BufferedOutputStream(zipFileEntryFOS, buffer.length);
        int readLength;
        try {
            while ((readLength = zipFileEntryBIS.read(buffer, 0, buffer.length)) != -1) {
                zipFileEntryBOS.write(buffer, 0, readLength);
            }
            zipFileEntryBOS.flush();
            zipFileEntryBOS.close();
            zipFileEntryFOS.close();
            zipFileEntryBIS.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
