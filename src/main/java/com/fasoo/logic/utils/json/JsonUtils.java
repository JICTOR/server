package com.fasoo.logic.utils.json;

import com.fasoo.logic.vo.user.GitUserVO;
import com.fasoo.logic.vo.git.GitCommitVO;
import com.fasoo.logic.vo.user.ProjectUserVO;
import com.fasoo.parser.data.DataClassInfo;
import com.google.gson.Gson;

public class JsonUtils {

    public static String objectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static DataClassInfo jsonToDataClassInfo(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, DataClassInfo.class);
    }

    public static GitCommitVO jsonToGitCommitVO(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GitCommitVO.class);
    }

    public static GitUserVO jsonToGitUserVO(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GitUserVO.class);
    }

    public static ProjectUserVO jsonToProjectUserVO(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ProjectUserVO.class);
    }
}
