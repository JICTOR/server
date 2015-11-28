package com.fasoo.service;


public interface CodeService {
    public String classInfoToCode(String userKey, String classInfoKey);
    public String gitCodeJsonFromCommitId(String commitId, String classInfoKey);
}
