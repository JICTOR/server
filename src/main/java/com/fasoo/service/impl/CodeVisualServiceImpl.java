package com.fasoo.service.impl;

import com.fasoo.logic.utils.db.MongoDBUtils;
import com.fasoo.logic.utils.file.FileUtils;
import com.fasoo.parser.data.DataClassInfo;
import com.fasoo.parser.data.DataPosition;
import com.fasoo.service.CodeService;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.springframework.stereotype.Service;

import java.io.IOException;

//TODO : method 안에 있는 code 중복 없애기
@Service
public class CodeVisualServiceImpl implements CodeService{

    private final String OPEN_BRACKET = "!@#gummy";
    private final String CLOSE_BRACKET = "!@#bear";

    @Override
    public String gitCodeJsonFromCommitId(String commitId, String classInfoKey) {

        DualHashBidiMap fullyQualifiedNameList = MongoDBUtils.findFullyQualifiedNameMapFromCommitId(commitId);
        if(fullyQualifiedNameList == null){
            return null;
        }

        DataClassInfo classInfo = MongoDBUtils.findClassInfo(classInfoKey);
        if(classInfo == null){
            return null;
        }
        try {
            return getGitCode(classInfo, fullyQualifiedNameList, commitId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String classInfoToCode(String userKey, String classInfoKey) {

        DualHashBidiMap fullyQualifiedNameList = MongoDBUtils.findFullyQualifiedNameMapFromProjectUserKey(userKey);
        if(fullyQualifiedNameList == null){
            return null;
        }

        DataClassInfo classInfo = MongoDBUtils.findClassInfo(classInfoKey);
        if(classInfo == null){
            return null;
        }

        try {
            return getCode(classInfo, fullyQualifiedNameList, userKey);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getGitCode(DataClassInfo classInfo, DualHashBidiMap fullyQualifiedNameList, String commitId) throws IOException {

        String sourceCode = FileUtils.readFileToString(classInfo.getJavaFilePath());
        if (sourceCode == null) {
            return null;
        }

        for (DataPosition callPosition : classInfo.getCallPositions()) {
            sourceCode = addGitTagInSourceCode(sourceCode, callPosition, fullyQualifiedNameList, commitId);
        }
        sourceCode = sourceCode.replaceAll(">", "&gt");
        sourceCode = sourceCode.replaceAll("<", "&lt");
        sourceCode = sourceCode.replaceAll(OPEN_BRACKET, "<");
        sourceCode = sourceCode.replaceAll(CLOSE_BRACKET, ">");
        return sourceCode;
    }

    private String addGitTagInSourceCode(String sourceCode, DataPosition callPosition, DualHashBidiMap fullyQualifiedNameList, String commitId) {

        int position = callPosition.getCallPosition();
        int length = callPosition.getSimpleNamelength();
        String head = sourceCode.substring(0, position);
        String tail = sourceCode.substring(position + length);
        String hashKey = fullyQualifiedNameList.getKey(callPosition.getFqn()).toString();

        String body = OPEN_BRACKET + "a href='#' onClick=\"window.open('/git_code_view/" + commitId + "/" + hashKey + "', 'git_code_view/"
                + commitId + "/" +callPosition.getFqn() + "')\""
                + CLOSE_BRACKET + sourceCode.substring(position, position + length) + OPEN_BRACKET + "/a" + CLOSE_BRACKET;

        return head + body + tail;
    }

    private String getCode(DataClassInfo classInfo, DualHashBidiMap fullyQualifiedNameList, String userKey) throws IOException {

        String sourceCode = FileUtils.readFileToString(classInfo.getJavaFilePath());
        if (sourceCode == null) {
            return null;
        }

        for (DataPosition callPosition : classInfo.getCallPositions()) {
            sourceCode = addTagInSourceCode(sourceCode, callPosition, fullyQualifiedNameList, userKey);
        }
        sourceCode = sourceCode.replaceAll(">", "&gt");
        sourceCode = sourceCode.replaceAll("<", "&lt");
        sourceCode = sourceCode.replaceAll(OPEN_BRACKET, "<");
        sourceCode = sourceCode.replaceAll(CLOSE_BRACKET, ">");
        return sourceCode;
    }

    private String addTagInSourceCode(String sourceCode, DataPosition callPosition, DualHashBidiMap fullyQualifiedNameList, String userKey) {

        int position = callPosition.getCallPosition();
        int length = callPosition.getSimpleNamelength();
        String head = sourceCode.substring(0, position);
        String tail = sourceCode.substring(position + length);
        String hashKey = fullyQualifiedNameList.getKey(callPosition.getFqn()).toString();

        String body = OPEN_BRACKET + "a href='#' onClick=\"window.open('/code_view/" + userKey + "/" + hashKey + "', 'code_view/"
                + userKey + "/" + callPosition.getFqn() + "')\""
                + CLOSE_BRACKET + sourceCode.substring(position, position + length) + OPEN_BRACKET + "/a" + CLOSE_BRACKET;

        return head + body + tail;
    }
}
