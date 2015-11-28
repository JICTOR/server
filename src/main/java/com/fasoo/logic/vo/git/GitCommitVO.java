package com.fasoo.logic.vo.git;

import com.fasoo.parser.gitTest.GitDiffVO;
import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GitCommitVO {
    private String gitCommitId;
    private List<GitDiffVO> diffList;
    private DualHashBidiMap fullyQualifiedNameMap;
    private List<String> addedFullyQualifiedNameKeyList;
    private String sunburstJson;
    private Map<String, Set<String>> classUseMap;
    private Map<String, Set<String>> usedClassMap;
    private int order;

    public GitCommitVO() {
        gitCommitId = null;
        diffList = new ArrayList<GitDiffVO>();
        fullyQualifiedNameMap = new DualHashBidiMap();
        addedFullyQualifiedNameKeyList = new ArrayList<String>();
        sunburstJson = null;
    }

    public String getGitCommitId() {
        return gitCommitId;
    }

    public void setGitCommitId(String gitCommitId) {
        this.gitCommitId = gitCommitId;
    }

    public List<GitDiffVO> getDiffList() {
        return diffList;
    }

    public void setDiffList(List<GitDiffVO> diffList) {
        this.diffList = diffList;
    }

    public DualHashBidiMap getFullyQualifiedNameMap() {
        return fullyQualifiedNameMap;
    }

    public void setFullyQualifiedNameMap(DualHashBidiMap fullyQualifiedNameMap) {
        this.fullyQualifiedNameMap = fullyQualifiedNameMap;
    }

    public List<String> getAddedFullyQualifiedNameKeyList() {
        return addedFullyQualifiedNameKeyList;
    }

    public void setAddedFullyQualifiedNameKeyList(List<String> addedFullyQualifiedNameKeyList) {
        this.addedFullyQualifiedNameKeyList = addedFullyQualifiedNameKeyList;
    }

    public String getSunburstJson() {
        return sunburstJson;
    }

    public void setSunburstJson(String sunburstJson) {
        this.sunburstJson = sunburstJson;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Map<String, Set<String>> getClassUseMap() {
        return classUseMap;
    }

    public void setClassUseMap(Map<String, Set<String>> classUseMap) {
        this.classUseMap = classUseMap;
    }

    public Map<String, Set<String>> getUsedClassMap() {
        return usedClassMap;
    }

    public void setUsedClassMap(Map<String, Set<String>> usedClassMap) {
        this.usedClassMap = usedClassMap;
    }
}
