package com.fasoo.service;

public interface GitService {
//    public void parsingPrivateGitRepository(String gitPath);
    public boolean parsingPublicGitRepository(String gitUrl, String userKey);
}
