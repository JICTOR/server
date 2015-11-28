package com.fasoo.service;

import com.fasoo.web.command.FilesUploadCommand;

public interface FilesUploadingService {
    public boolean parsingFileList(FilesUploadCommand uploadForm, String userKey);
}
