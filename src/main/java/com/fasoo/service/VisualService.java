package com.fasoo.service;

public interface VisualService {
    public String getFullyQualifiedNameListJson(String userKey);
    public String getCommitIdListJson(String userKey);
    public String fullyQualifiedNameListJsonFromCommitId(String commitId);
    public String getSearchDataListJson(String userKey);
    public String getSearchCommitIdListJson(String userKey);
}


