package com.fasoo.service.impl;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.utils.file.FileUtils;
import com.fasoo.logic.utils.file.ZipUtils;
import com.fasoo.logic.visual.sunburst.SunburstVisual;
import com.fasoo.logic.vo.user.ProjectUserVO;
import com.fasoo.parser.Parsing;
import com.fasoo.parser.data.DataAll;
import com.fasoo.parser.data.DataClassInfo;
import com.fasoo.service.FilesUploadingService;
import com.fasoo.web.command.FilesUploadCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilesUploadingServiceImpl implements FilesUploadingService {

    @Override
    public boolean parsingFileList(FilesUploadCommand filesUploadCommand, String userKey) {

        List<MultipartFile> fileList = filesUploadCommand.getFiles();

        if (fileList == null || fileList.size() <= 0) {
            return false;
        }

        FileUtils.makeBaseFolder(FileUtils.UPLOADED_FOLDER_PATH);

        List<File> javaFileList = fileListToJavaFileList(fileList);

        if (javaFileList.isEmpty()) {
            return false;
        }

        DataAll parsingResult = parsingJavaFileList(javaFileList);
        if(parsingResult == null){
            return false;
        }

        SunburstVisual sunburstVisual = new SunburstVisual();
        String sunburstJson = sunburstVisual.classInfoListToSunburstJson(parsingResult.getClassInfoList(), parsingResult.getFqnList());
        if(sunburstVisual.isCompileError()){
            return false;
        }

        ProjectUserVO projectUser = new ProjectUserVO();
        projectUser.setUserKey(userKey);
        projectUser.setFullyQualifiedNameMap(parsingResult.getFqnList());
        projectUser.setSunburstJson(sunburstJson);
        projectUser.setClassUseMap(sunburstVisual.getClassUseMap());
        projectUser.setUsedClassMap(sunburstVisual.getUsedClassMap());
        for (Object fullyQualifiedName : parsingResult.getFqnList().keySet()) {
            MongoDBUtils.insertClassInfo(fullyQualifiedName.toString(), (DataClassInfo) parsingResult.getClassInfoList().get(fullyQualifiedName));
        }
        MongoDBUtils.insertProjectUser(projectUser);
        return true;
    }

    private DataAll parsingJavaFileList(List<File> javaFileList) {

        DataAll parsingResult;
        try {
            parsingResult = Parsing.parsing(javaFileList);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return parsingResult;
    }

    private List<File> fileListToJavaFileList(List<MultipartFile> fileList) {

        List<File> result = new ArrayList<File>();
        List<File> unZipJavaFileList;

        List<String> fileNameList = new ArrayList<String>();
        String sameFileTmpDir = "";
        int sameFileTimDirIndex = 0;

        for (MultipartFile multipartFile : fileList) {

            String fileName = multipartFile.getOriginalFilename();

            if (fileName.endsWith(".zip")) {
                unZipJavaFileList = ZipUtils.unzip(multipartFile, FileUtils.UPLOADED_FOLDER_PATH);
                if (unZipJavaFileList.isEmpty()) {
                    continue;
                }
                result.addAll(unZipJavaFileList);
            } else if (fileName.endsWith(".java")) {
                if (fileNameList.contains(fileName)) {
                    sameFileTmpDir = Integer.toString(sameFileTimDirIndex++) + File.separator;
                    FileUtils.makeBaseFolder(FileUtils.UPLOADED_FOLDER_PATH + sameFileTmpDir);
                }
                fileNameList.add(fileName);

                File conFile = new File(FileUtils.UPLOADED_FOLDER_PATH + sameFileTmpDir, fileName);
                try {
                    multipartFile.transferTo(conFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
                result.add(conFile);
            }
        }
        return result;
    }
}
