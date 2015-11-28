package com.fasoo.service;

public interface SunburstVisualService {
    public String classInfoListToSunburstJson(String userKey);
    public String sunburstJsonFromCommitId(String commitId);
}
