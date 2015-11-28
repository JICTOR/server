package com.fasoo.web.command;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FilesUploadCommand {

    private List<MultipartFile> files;
    private String userMail;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
